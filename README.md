# Spring Security - In-memory Authenticator
Java project with Spring and Gradle for basic in-memory authentication with authorization for routes.

UML Class Diagram:

![Image-04-InMemoryAuthenticator](images/Img-04-UML-Class-InMemoryAuthenticator.png)

Routes:
- `/`
- `/users`
- `/admins`
- `/accessDenied`


## Steps
The steps of project implementation:

1. Create project (in IntelliJ) with:
- Java language (17);
- Spring Framework (6.2.3);
- Dependencies: Web and Security.

![Image-01-IntelliJ](images/Img-01-IntelliJ.png)

2. Create the `RoutesController` class:
- in the `controllers` package;
- with the annotation `@RestController`;
- with the routes `/`, `/users`, `/admins`, `/accessDenied` of type GET.

![Image-02-RoutesController](images/Img-02-UML-Class-RoutesController.png)

3. Create the `SecurityConfig` class:
- in the `security` package;
- with the annotations `@Configuration` and `@EnableWebSecurity`;
- with all methods annotated with `@Bean`;
- with the following public methods:
    - `SecurityFilterChain securityFilterChain(HttpSecurity http)` to configure authorization for each route;
    - `UserDetailsService userDetailsService()` to create users;
    - `PasswordEncoder passwordEncoder()` to return an instance of `BCryptPasswordEncoder`;
    - `AuthenticationManager authenticationManager(UserDetailsService UserDetailsService,
      PasswordEncoder passwordEncoder)` to customize the authenticator with passwordEncoder;

![Image-03-SecurityConfig](images/Img-03-UML-Class-SecurityConfig.png)


## Code

```java
@RestController
public class RoutesController {

    @GetMapping("/")
    public String home(){
        return "Home Page - Allowed for everyone";
    }

    @GetMapping("/users")
    public String users(){
        return "Users Page - Allowed for logged-in users and administrators";
    }

    @GetMapping("/admins")
    public String admins(){
        return "Admins Page - Allowed for logged-in admins";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "Access denied Page";
    }

}
```


```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/users").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admins").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.accessDeniedPage("/accessDenied"))
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withDefaultPasswordEncoder()
                .username("usuario")
                .password("senha")
                .roles("USER")
                .build();

        UserDetails admin = User
                .withDefaultPasswordEncoder()
                .username("administrador")
                .password("codigo")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService UserDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(UserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);
    }
}
```


## References
https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html