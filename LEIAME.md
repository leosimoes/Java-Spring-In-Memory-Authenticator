# Spring Security - Autenticador em memória
Projeto em Java com Spring e Gradle para autenticação básica em memória com autorização para rotas.

Diagrama de classes UML:

![Image-04-InMemoryAuthenticator](images/Img-04-UML-Class-InMemoryAuthenticator.png)

Rotas:
- `/`
- `/users` 
- `/admins`
- `/accessDenied`


## Passos
Os passos da implementação do projeto:

1. Criar projeto (no IntelliJ) com:
- Linguagem Java (17);
- Spring Framework (6.2.3);
- Dependências: Web e Security.

![Image-01-IntelliJ](images/Img-01-IntelliJ.png)

2. Criar a classe `RoutesController`:
- no pacote `controllers`;
- com a anotação `@RestController`;
- com as rotas `/`, `/users`, `/admins`, `/accessDenied` do tipo GET.

![Image-02-RoutesController](images/Img-02-UML-Class-RoutesController.png)

3. Criar a classe `SecurityConfig`:
- no pacote `security`;
- com as anotações `@Configuration` e `@EnableWebSecurity`;
- com todos os métodos anotados com `@Bean`;
- com os seguintes métodos públicos:
  - `SecurityFilterChain securityFilterChain(HttpSecurity http)` para configurar a autorização de cada rota;
  - `UserDetailsService userDetailsService()` para criar usuários;
  - `PasswordEncoder passwordEncoder()` para retornar uma instância de `BCryptPasswordEncoder`;
  - `AuthenticationManager authenticationManager(UserDetailsService UserDetailsService,
    PasswordEncoder passwordEncoder)` para personalizar o autenticador com passwordEncoder;

![Image-03-SecurityConfig](images/Img-03-UML-Class-SecurityConfig.png)

## Código

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


## Referências
https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html