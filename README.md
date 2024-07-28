# palindrome-checker-api

It is a Spring boot application to check whether a word is a palindrome.
It provides several endpoints:
POST http://localhost:8080/v1/palindrome-checker
```
Sample Request: 

curl --location 'http://localhost:8080/v1/palindrome-checker' \
--header 'Content-Type: application/json' \
--data '{
"userName" : "user1",
"text": "kayak"
}'
```

GET http://localhost:8080/v1/palindrome-checker/user1
```
Sample Response: 

{
"id": "6b694673-9859-4af9-b0e2-f2f9eda0e441",
"text": "kayak",
"palindrome": true
}
```
## Getting Started

Clone code:
```
git clone https://github.com/schalla0/palindromeCheckerApi
``` 

### Prerequisites

You need maven installed on local.
In order to build the project:
```
mvn clean package
```

### Running program

In order to run jar:
```
java -jar palindrome-1.0.0-SNAPSHOT.jar --spring.config.location=/<path-to-configuration/application.properties  
```

## Configuration

Cache Configuration used ehcache.xml

## Authors

* **Sunil Challa** - *Initial work* 

