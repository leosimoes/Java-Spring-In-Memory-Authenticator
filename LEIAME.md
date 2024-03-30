# Spring Security - Autenticador em memória
Projeto em Java com Spring e Gradle para autenticação básica em memória com autorização para rotas.

## Passos
Os passos da implementação do projeto:

1. Criar projeto (no IntelliJ) com:
- Linguagem Java (17);
- Spring Framework (6.2.3);
- Dependências: Web e Security.

![Image-01-IntelliJ](images/Img-01-IntelliJ.png)

2. Criar a classe `RoutesController`:
- com a anotação `@RestController`;
- com a rota `/login` do tipo POST;
- com as rotas `/, /users, /admins, /accessDenied` do tipo GET.

![Image-02-RoutesController](images/Img-02-UML-Class-RoutesController.png)


## Referências
https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html