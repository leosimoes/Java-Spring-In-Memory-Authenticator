# Spring Security - In-memory Authenticator
Java project with Spring and Gradle for basic in-memory authentication with authorization for routes.

## Steps
The steps of project implementation:

1. Create project (in IntelliJ) with:
- Java language (17);
- Spring Framework (6.2.3);
- Dependencies: Web and Security.

![Image-01-IntelliJ](images/Img-01-IntelliJ.png)

2. Create the `RoutesController` class:
- with the annotation `@RestController`;
- with the `/login` route of type POST;
- with the routes `/, /users, /admins, /accessDenied` of type GET.

![Image-02-RoutesController](images/Img-02-UML-Class-RoutesController.png)


## References
https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html