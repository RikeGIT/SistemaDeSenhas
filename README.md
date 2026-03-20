# Sistema de Senhas
## Como rodar em 3 passos

1. Crie um banco no MySQL:
   CREATE DATABASE sistemadesenhas_db;

2. Configure usuário e senha no application.properties

3. Rode o projeto e seja feliz.

# Rotas
## Crud Senhas

| Método | Endpoint     | Função    |
| ------ | ------------ | --------- |
| POST   | /senhas      | Criar     |
| GET    | /senhas      | Listar    |
| GET    | /senhas/{id} | Buscar    |
| PUT    | /senhas/{id} | Atualizar |
| DELETE | /senhas/{id} | Deletar   |
