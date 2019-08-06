### Usage of Server-Sent Events (SSE) for push notifications. With dogs.

**Tech stack**

* Spring Boot 2.1.6.RELEASE
* Angular 8.2.0


**Run it**

backend
```
cd spring-backend && mvnw package && java -jar target\dog-station-1-SNAPSHOT.jar
```


frontend
```
cd angular-client && ng serve
```

**Notes**

- Easy to adopt & implement
- Disadvantage is [connection limit of browsers](https://stackoverflow.com/questions/20382636/ajax-jquery-multiple-calls-at-the-same-time-long-wait-for-answer-and-not-able/20383771#20383771) (~6) - deal breaker for some use cases (multi-tab, multi-session app)


 