# jwt

## ๐ฉ๐ปโ๐ป ๋ชฉ์ฐจ
- jwt๋ฅผ ์ํ security ์ค์ 
- jwt Bearer ์ธ์ฆ๋ฐฉ์
- jwt filter ๋ฑ๋ก ํ์คํธ
- jwt ๋ก๊ทธ์ธ ์๋
- jwt ํ ํฐ ๋ง๋ค์ด์ ์๋ตํ๊ธฐ
- jwt ํ ํฐ ์๋ฒ๊ตฌ์ถ


## ๐ฉ๐ปโ๐ป server log password ์ถ๋ ฅ
- user / {pw}
- springboot ํฐ๋ฏธ๋ ๋ก๊ทธ์ ์ถ๋ ฅ
  - Using generated security password: 5f8a1d65-b3ac-4442-9e55-7e1f46db7649

## ๐ฉ๐ปโ๐ป ํ์๊ฐ์ join
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

## ๐ฉ๐ปโ๐ป ๋ก๊ทธ์ธ login
http://localhost:8080/login
=> header Authentication Bearer {token} ์์ฑ
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

์ธ์ฆ์ด ํ์ํ ํ์ด์ง
๋ก๊ทธ์ธ์ ๋ง๋ค์ด์ง jwt ํ ํฐ์ ์ด์ฉํด์ ์์ฒญ
```
curl --location --request GET 'http://localhost:8080/api/v1/user/12323' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWQiOjEsImV4cCI6MTY0NjI4NjQ5MywidXNlcm5hbWUiOiJ0ZXN0In0.I77Q1gfAApNSIisNr2GS9QkvEoGaxuvzYOdDRd-Q3xltepzHCNOTdfu39LGG2ZgzmnH2B9Fw59VtCfJ0jIV9GA'


```
&nbsp;

## ๐ฉ๐ปโ๐ป  session ๋ฐฉ์ login
1. username, password => ๋ก๊ทธ์ธ ์ ์
2. ์๋ฒ์ชฝ SESSIONID ์์ฑ
3. ํด๋ผ์ด์ธํธ ์ฟ ํค SESSIONID๋ฅผ ์๋ต
4. ์์ฒญํ  ๋๋ง๋ค ์ฟ ํค๊ฐ SESSIONID๋ฅผ ํญ์ ๋ค๊ณ  ์๋ฒ์ชฝ์ผ๋ก ์์ฒญํ๊ธฐ ๋๋ฌธ์ ์๋ฒ๋ SESSIONID๊ฐ ์ ํจํ์ง ํ๋จํด์ ์ ํจํ๋ฉด ์ธ์ฆ์ด ํ์ํ ํ์ด์ง๋ก ์ ๊ทผํ๊ฒ ํ๋ฉด 

&nbsp;

## ๐ฉ๐ปโ๐ป   jwt Token
1. username, password => ๋ก๊ทธ์ธ ์ ์
2. jwt ํ ํฐ์ ์์ฑ
3. ํด๋ผ์ด์ธํธ ์ชฝ์ผ๋ก jwtํ ํฐ์ ์๋ต

[ํด๋ผ์ด์ธํธ]
4. ์์ฒญํ  ๋๋ง๋ค jwtํ ํฐ์ ๊ฐ์ง๊ณ  ์์ฒญ
5. ์๋ฒ๋ jwtํ ํฐ์ด ์ ํจํ์ง๋ฅผ ํ๋จ! (ํํฐ๋ฅผ ๋ง๋ค์ด์ผ ํจ)

&nbsp;

## ๐ฉ๐ปโ๐ป TEST URL

- http://localhost:8080/api/v1/user
- http://localhost:8080/api/v1/manager
- http://localhost:8080/api/v1/admin
