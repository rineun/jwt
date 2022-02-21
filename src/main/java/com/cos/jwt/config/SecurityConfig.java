package com.cos.jwt.config;

import com.cos.jwt.config.jwt.JwtAuthenticationFilter;
import com.cos.jwt.config.jwt.JwtAuthorizationFilter;
import com.cos.jwt.filter.MyFilter1;
import com.cos.jwt.filter.MyFilter3;
import com.cos.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity  // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CorsConfig corsConfig;
    private final UserRepository userRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
                //.addFilterBefore( new MyFilter3(), BasicAuthenticationFilter.class) //securityFileter 가 가장먼저 실행됨- > filterConfig 우리가 만든것보다
               //.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class) //securityFileter 동작전에 실행됨
                .addFilter(corsConfig.corsFilter())
               //.addFilter(corsFilter)  // @CrossOrigin(인증 X), 시큐리티 필터에 등록 인증(o)
      			.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션사용 안함
                .and()
                .formLogin().disable()
                .httpBasic().disable()  // header id,pw 달고 다니는 방식

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))  //AuthenticationManager 파라미터 필요함
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("login");

    }
}
