# BCI-exercise-2024

### To run this app you need:
postman
java
gradle/maven

Postman colection in file 'Ejercicio 1.postman_collection.json'

### DIAGRAM

Folder diagramas contains a component and sequence diagram.


### API DOCUMENTATION

### SIGN UP POST

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

Response:

{
    "id": "2a5d6512-057b-4e63-bc2b-9e81535d70ac",
    "created": "2024-09-20",
    "lastLogin": null,
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rhc2RzZEBzYWRhcy5jb20iLCJleHAiOjE3MjcwODAxMjEsImlhdCI6MTcyNjg2NDEyMX0.tj5fPV1U2FRrJIIIDDIj1Jf7zCnNU7cNBkeQ3XalwLB-J0V5y_c308kRXfc0xKV5AX9jc7HrY85U5c524RU8zA",
    "isActive": true
}


### LOGIN GET

curl --location 'http://localhost:8080/login?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rhc2RzZEBzYWRhcy5jb20iLCJleHAiOjE3MjcwODAxMjEsImlhdCI6MTcyNjg2NDEyMX0.tj5fPV1U2FRrJIIIDDIj1Jf7zCnNU7cNBkeQ3XalwLB-J0V5y_c308kRXfc0xKV5AX9jc7HrY85U5c524RU8zA'

Response:

{
    "id": "2a5d6512-057b-4e63-bc2b-9e81535d70ac",
    "created": "2024-09-20",
    "lastLogin": "2024-09-20",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2Rhc2RzZEBzYWRhcy5jb20iLCJleHAiOjE3MjcwODAxMzQsImlhdCI6MTcyNjg2NDEzNH0.bIJvmngd_wYzdolyhcIEeEpr0ns4cGAkcAdSf2gd-YLw_PExCyhe8v5LxQ-62TFcnkwNMijlVNBmRuE-8L0Sig",
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
    ],
    "isActive": true
}


### H2

http://localhost:8080/h2-console/login.jsp?jsessionid=d93ad3082ecb30d1e386a6b8d53ad492
URL: jdbc:h2:mem:testdb
User Name: User
password: user

### CONFIGURATIONS

No extra configuration is needed to run this, you only need an IDE.