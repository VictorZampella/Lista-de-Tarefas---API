# API de Gerenciamento de Tarefas

Este projeto é uma API REST para organizar tarefas. Com ela, é possível cadastrar e consultar usuários, perfis, tarefas, categorias, etiquetas e comentários.

A API permite criar, listar, buscar, atualizar e excluir esses dados. As listagens têm paginação, e também existem buscas por nome, título, telefone, texto e status da tarefa.

Os dados são organizados em entidades relacionadas. Um usuário pode ter várias tarefas, uma tarefa pode ter vários comentários e etiquetas, e cada perfil está ligado a um usuário. O status da tarefa pode ser `PENDENTE`, `EM_ANDAMENTO` ou `CONCLUIDA`.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 Database
- Swagger / Springdoc OpenAPI

## Como executar

Com Java 17 ou superior instalado, abra um terminal na pasta do projeto Maven e execute:

```powershell
cd api/api
.\mvnw.cmd spring-boot:run
```

Quando a aplicação estiver rodando, acesse o Swagger para consultar e testar os endpoints:

http://localhost:8080/swagger-ui/index.html

O banco H2 usado pelo projeto é em memória. Os dados são apagados quando a aplicação é encerrada.

## Autoria

**Victor de Curtis Oliveira Zampella**  
Telefone para contato: **11930103545**
