package com.cos.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");

        //POST  http://localhost:8080/api/v1/token
        //Bearer Token 입력  postman
        // 토근 : cos 를 만들어줘야 함, id, pw정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답을 해준다.
        // 요청할 때 마다 header에 Authoriaztion에 value값으로 토큰을 가지고 옴
        // 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증만 하면 됨.(RSA, HS256)
        if(req.getMethod().equals("POST")) {
            System.out.println("POST 요청됨");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);
            if(headerAuth.equals("Bearer cos")) {
                chain.doFilter(req, res);
            }else{
                PrintWriter out = res.getWriter();
                out.println("인증안됨");

            }
        }

        System.out.println("필터3");
    }
}
