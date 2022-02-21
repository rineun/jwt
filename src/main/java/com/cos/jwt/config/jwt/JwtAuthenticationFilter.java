package com.cos.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
//  http://localhost:8080/login 오쳥해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
    // 인증 요청시에 실행되는 함수 => /login
    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter : 진입");

        //1.username, password 받아서
        // reqest에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while ((input = br.readLine() )!= null){
//                System.out.println(input);
//            }
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
            System.out.println("loginRequestDto = " + loginRequestDto);

            System.out.println("JwtAuthenticationFilter : "+loginRequestDto);


            // 유저네임패스워드 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword());

            System.out.println("JwtAuthenticationFilter : 토큰생성완료");

            // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
            // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
            // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
            // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
            // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

            // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
            // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
            // 결론은 인증 프로바이더에게 알려줄 필요가 없음.


            // PrincipalDetailsService의 loadUseByUsername()함수가 실행된 후 정상이면 authentication 리턴됨
            // DB에 있는 usernamer과 password가 일치하다.
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            //  => 로그인 되었다는 뜻
            PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인완료됨 : "+principalDetailis.getUser().getUsername()); //로그인이 정상적으로 되었음

            //authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return 해주면 됨
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는것
            //굳이  JWT토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리때문에 session 넣어 줍니다.
            return authentication;
        } catch (Exception e) {
            e.printStackTrace();
        }



        //2.정상인지 로그인 시도를 해보는것.. authenticationManager로 로그인 시도를 하면!!
        // PrincipalDetailsService가 호출 lodaUserByUsername()함수가 실행됨.

        //3.PrincipalDetails를 세션에 담고 (권한관리를 위해서)

        //4.JWT 토큰을 만들어서 응답해주면됨



        return null;
    }


    // attemptAuthentication실행후 인증이 정상적으로 되었다면 successfulAuthentication함수가 실행
    // JWT Token 생성해서 request요청한 사용자에게 JWT토큰을 response에 담아주기
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        System.out.println("successfulAuthentication 인증이 완되었습니다");
        PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetailis.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME)) //만료시간
                .withClaim("id", principalDetailis.getUser().getId())
                .withClaim("username", principalDetailis.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));


        //response.addHeader("Authorization", "Bearer "+ jwtToken);
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
    }
}
