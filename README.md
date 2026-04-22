# 🚀 Sistema de Gestão de Senhas - Patos/PB

Sistema Full Stack desenvolvido para otimizar o fluxo de atendimento em unidades de serviço. A aplicação gerencia desde a emissão de senhas customizadas até o painel de chamadas em tempo real com suporte a múltiplos guichês.

---

## 🛠️ Tecnologias Principais

**Backend:** Java 21, Spring Boot 3.2.x, Spring Data JPA, Maven
**Frontend:** HTML5, CSS3 (Flexbox/Grid), JavaScript (Vanilla)
**Banco de Dados:** MySQL 8.x
**Relatórios:** JasperReports (Emissão de tickets)

---

## 🌟 Diferenciais da Versão Atual

* **Criação Automática:** O banco de dados e as tabelas são criados automaticamente na primeira execução.
* **Lógica de Senha Inteligente:** Prefixo baseado nas duas primeiras letras do serviço (ex: CX para Caixa) + sequência de 3 dígitos (ex: CX001).
* **Painel Multi-Guichê:** Suporte a atendimentos simultâneos com destaque visual e sonoro para a chamada mais recente.
* **Segurança de Concorrência:** Implementação de *Pessimistic Locking* para evitar que dois guichês chamem a mesma senha simultaneamente.

---

## ⚙️ Configuração e Execução

### 1. Pré-requisitos

* JDK 21 instalado
* MySQL Server rodando localmente

### 2. Configuração do Banco

A aplicação está configurada para se auto-gerenciar. Você só precisa garantir que as credenciais no arquivo `src/main/resources/application.properties` estejam corretas:

```
spring.datasource.url=jdbc:mysql://localhost:3306/sistemadesenhas_db?createDatabaseIfNotExist=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Executando a Aplicação

Utilize o Maven Wrapper incluso no projeto:

```
# No Windows
./mvnw.cmd spring-boot:run

# No Linux/Mac
./mvnw spring-boot:run
```

---

## 📂 Estrutura de Acesso

Após iniciar, o sistema estará disponível em:

```
http://localhost:8080
```

* `/index.html` → Totem de autoatendimento (emissão de senhas)
* `/painel.html` → Painel público (TV/monitor de chamadas)
* `/login.html` → Acesso para atendentes e administradores
* `/admin.html` → Gestão de serviços, prioridades, guichês e atendentes

---

## 🛠️ Manutenção Diária

 O administrador possui uma ferramenta exclusiva no painel de gestão para Resetar a Fila. Recomenda-se realizar essa operação ao final de cada expediente para reiniciar a numeração sequencial das senhas para o dia seguinte.  
