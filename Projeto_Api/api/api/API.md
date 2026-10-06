# API de gerenciamento de tarefas

## Como executar e abrir o Swagger

Execute a aplicação Spring Boot. A interface interativa fica em `http://localhost:8080/swagger-ui.html` e o documento OpenAPI em `http://localhost:8080/v3/api-docs`.

O projeto usa H2 em memória; os dados são apagados quando a aplicação é encerrada.

## Endpoints

Cada recurso tem estas operações: `GET /recurso?page=0&size=10` lista uma página; `GET /recurso/{id}` consulta um item; `POST /recurso` cria; `PUT /recurso/{id}` atualiza; `DELETE /recurso/{id}` exclui. A resposta de listagem inclui os metadados de paginação do Spring Data.

| Recurso | Rota | Consulta personalizada |
|---|---|---|
| Usuários | `/usuarios` | `GET /usuarios/buscar?nome=Ana&page=0&size=10` |
| Perfis | `/perfis` | `GET /perfis/buscar?telefone=1199&page=0&size=10` |
| Tarefas | `/tarefas` | `GET /tarefas/buscar?titulo=prova&page=0&size=10`; também `/tarefas/status?status=PENDENTE&page=0&size=10` |
| Categorias | `/categorias` | `GET /categorias/buscar?nome=Estudo&page=0&size=10` |
| Etiquetas | `/etiquetas` | `GET /etiquetas/buscar?nome=urgente&page=0&size=10` |
| Comentários | `/comentarios` | `GET /comentarios/buscar?texto=reunião&page=0&size=10` |

## Exemplos de criação

Crie primeiro o usuário, para usar seu `id` ao criar um perfil, uma tarefa ou comentário.

```json
{
  "nome": "Ana Silva",
  "email": "ana@example.com"
}
```

```json
{
  "telefone": "11999990000",
  "usuario": { "id": 1 }
}
```

```json
{
  "titulo": "Estudar para a prova",
  "descricao": "Revisar o conteúdo da aula",
  "prazo": "2026-10-20",
  "status": "PENDENTE",
  "usuario": { "id": 1 },
  "categoria": { "id": 1 },
  "etiquetas": [{ "id": 1 }]
}
```

```json
{ "nome": "Estudo", "descricao": "Atividades da faculdade" }
```

```json
{ "nome": "urgente" }
```

```json
{
  "texto": "Vou revisar este conteúdo hoje.",
  "tarefa": { "id": 1 },
  "usuario": { "id": 1 }
}
```

## Relacionamentos e validações

- Usuário e Perfil: um para um.
- Usuário e Tarefa: um para muitos.
- Tarefa e Comentário: um para muitos.
- Tarefa e Etiqueta: muitos para muitos.
- Tarefa possui status `PENDENTE`, `EM_ANDAMENTO` ou `CONCLUIDA`.
- Nome, título, texto, telefone e email são obrigatórios conforme a entidade; o email também precisa ter formato válido.

## Códigos de resposta

- `200 OK`: consulta ou atualização concluída.
- `201 Created`: registro criado.
- `204 No Content`: registro excluído.
- `400 Bad Request`: JSON ou valores inválidos.
- `404 Not Found`: id não encontrado.
