# BCI-exercise-2024

llamado postman POST:

curl --location 'http://localhost:8080/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "dasd",
  "email": "asdasdsd@sadas.com",
  "password": "as2f1hzxSvbn",
  "phones": [
    {
      "number": 1,
      "citycode": 2,
      "countrycode": "AR"
    },
    {
      "number": 10,
      "citycode": 20,
      "countrycode": "AR"
    }
  ]
}'


llamado get login:

curl --location 'http://localhost:8080/login?token=?'

el ? cambiarlo por el token que de la respuesta arriba o de la respuesta este mismo.

http://localhost:8080/h2-console/login.jsp?jsessionid=d93ad3082ecb30d1e386a6b8d53ad492
URL: jdbc:h2:mem:testdb
User Name: User
password: user