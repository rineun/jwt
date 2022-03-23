# jwt

## 👩🏻‍💻 목차
- jwt를 위한 security 설정
- jwt Bearer 인증방식
- jwt filter 등록 테스트
- jwt 로그인 시도
- jwt 토큰 만들어서 응답하기
- jwt 토큰 서버구축


## 👩🏻‍💻 server log password 출력
- user / {pw}
- springboot 터미널 로그에 출력
  - Using generated security password: 5f8a1d65-b3ac-4442-9e55-7e1f46db7649

## 👩🏻‍💻 회원가입 join
http://localhost:8080/api/v1/join
```

curl --location --request POST 'http://localhost:8080/api/v1/join' \
--header 'Authorization: Bearer cos' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "test",
    "password": "1234"

}

```

## 👩🏻‍💻 로그인 login
http://localhost:8080/login
=> header Authentication Bearer {token} 생성
```

curl --location --request POST 'http://localhost:8080/login' \
--header 'Authorization: Bearer cos' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "test",
    "password": "1234"

}'

```

&nbsp;

인증이 필요한 페이지
로그인시 만들어진 jwt 토큰을 이용해서 요청
```
curl --location --request GET 'http://localhost:8080/api/v1/user/12323' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWQiOjEsImV4cCI6MTY0NjI4NjQ5MywidXNlcm5hbWUiOiJ0ZXN0In0.I77Q1gfAApNSIisNr2GS9QkvEoGaxuvzYOdDRd-Q3xltepzHCNOTdfu39LGG2ZgzmnH2B9Fw59VtCfJ0jIV9GA'


```
&nbsp;

## 👩🏻‍💻  session 방식 login
1. username, password => 로그인 정상
2. 서버쪽 SESSIONID 생성
3. 클라이언트 쿠키 SESSIONID를 응답
4. 요청할 때마다 쿠키값 SESSIONID를 항상 들고 서버쪽으로 요청하기 때문에 서버는 SESSIONID가 유효한지 판단해서 유효하면 인증이 필요한 페이지로 접근하게 하면 

&nbsp;

## 👩🏻‍💻   jwt Token
1. username, password => 로그인 정상
2. jwt 토큰을 생성
3. 클라이언트 쪽으로 jwt토큰을 응답

[클라이언트]
4. 요청할 때마다 jwt토큰을 가지고 요청
5. 서버는 jwt토큰이 유효한지를 판단! (필터를 만들어야 함)

&nbsp;

## 👩🏻‍💻 TEST URL

- http://localhost:8080/api/v1/user
- http://localhost:8080/api/v1/manager
- http://localhost:8080/api/v1/admin
