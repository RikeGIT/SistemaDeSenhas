# 🏦 Sistema de Senhas - Backend Spring Boot

Este projeto é um sistema de gestão de filas e atendimentos, permitindo a geração de senhas com lógica de prioridade, chamada por guichês específicos e monitoramento em tempo real via painel.

---

## 🚀 Como Rodar o Projeto

### 1. Requisitos

* Java 21
* MySQL Server

---

### 2. Configuração do Banco de Dados

Crie a base de dados no seu MySQL:

```sql
CREATE DATABASE sistemadesenhas_db;
```

Configure suas credenciais (usuário e senha) no arquivo:

`src/main/resources/application.properties`

---

### 3. Carga Inicial de Dados (Obrigatório)

Para que o sistema funcione corretamente, é necessário inserir os dados iniciais. Execute o script abaixo no seu terminal MySQL:

```sql
-- 1. Inserir os 4 Serviços
INSERT INTO servicos (nome, descricao, sigla) VALUES 
('Caixa', 'Atendimento financeiro rápido', 'CX'),
('Gerência', 'Abertura de contas e contratos', 'GR'),
('Suporte', 'Auxílio técnico e dúvidas', 'SP'),
('Comercial', 'Vendas e produtos', 'CM');

-- 2. Inserir as 4 Prioridades (Pesos definem a ordem de chamada)
INSERT INTO prioridades (nome, peso) VALUES 
('Normal', 1),
('Gestante/Autista', 5),
('Idoso 60+', 10),
('Idoso 80+', 20);

-- 3. Inserir os 4 Guichês Iniciais
INSERT INTO guiches (numero, setor, ocupado, servico_id) VALUES 
(1, 'Térreo', false, 1), -- Guichê 1 atende Caixa
(2, 'Térreo', false, 1), -- Guichê 2 atende Caixa
(3, 'Mezanino', false, 2), -- Guichê 3 atende Gerência
(4, 'Térreo', false, 3); -- Guichê 4 atende Suporte
```

---

## 🖥️ Interfaces Disponíveis

Após rodar a aplicação, acesse no navegador:

* **Totem de Senhas (Cliente):**
  http://localhost:8080/index.html

* **Painel do Atendente (Funcionário):**
  http://localhost:8080/atendente.html

* **Painel de Visualização (TV):**
  http://localhost:8080/painel.html

---

## 🛠️ Tecnologias Utilizadas

* Spring Boot 4.0.3
* Spring Data JPA (Persistência)
* Lombok (Produtividade)
* MySQL (Banco de Dados)
* HTML5 / JavaScript (Interfaces Estáticas)

---

## 📄 Regras de Negócio Implementadas

* **Lógica de Código:**
  Senhas prioritárias começam com `2`, normais com `1`.

* **Ordenação:**
  A chamada prioriza o peso da prioridade e, em seguida, a ordem de chegada.

* **Persistência de Estado:**
  O guichê mantém o vínculo com a senha ativa mesmo após atualização da página.

* **Limpeza Automática:**
  As senhas são removidas do banco de dados imediatamente após a finalização do atendimento.
