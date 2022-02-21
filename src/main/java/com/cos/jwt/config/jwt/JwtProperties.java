package com.cos.jwt.config.jwt;

public interface JwtProperties {
    String SECRET = "안녕"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)     60000 * 10  ==> 10분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
