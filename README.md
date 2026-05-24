# ⏱️ PontoCorp — Sistema Inteligente de Controle de Ponto

Sistema web completo para controle de ponto eletrônico com autenticação JWT, controle de acesso por níveis (ADMIN/FUNCIONÁRIO), exportação de relatórios Excel, dashboard moderno e arquitetura fullstack.

---

# 📌 Sobre o Projeto

O **PontoCorp** foi desenvolvido com foco em:

- segurança
- praticidade
- escalabilidade
- experiência moderna de usuário

A aplicação permite que funcionários registrem seus pontos eletrônicos e acompanhem seu histórico em tempo real, enquanto administradores possuem acesso a funcionalidades avançadas de gerenciamento.

---

# 🚀 Funcionalidades

## 🔐 Autenticação e Segurança

- Login com JWT
- Registro de usuários
- Criptografia de senha com BCrypt
- Controle de acesso por Roles
- Rotas protegidas
- Autenticação Stateless
- CORS configurado
- Segurança com Spring Security

---

## 👤 Funcionário

- Registrar entrada/saída
- Visualizar histórico de pontos
- Exportar relatório Excel
- Dashboard moderno
- Logout seguro

---

## 🛠️ Administrador

- Todas as funcionalidades do funcionário
- Visualizar pontos de todos os usuários
- Consultar pontos por funcionário
- Painel administrativo
- Backup de dados
- Restore de dados
- Exportação Excel administrativa

---

# 🧱 Arquitetura

## Backend

- Java 25
- Spring Boot
- Spring Security
- JWT
- JPA / Hibernate
- PostgreSQL
- Apache POI

## Frontend

- React
- TypeScript
- Vite
- Axios
- React Router DOM
- CSS puro

---

# 📂 Estrutura do Projeto

Saída de código
File README.md created successfully.

```bash
ponto/
│
├── backend/
│
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── model/
│   ├── repository/
│   ├── security/
│   ├── service/
│   └── BackendApplication.java
│
├── frontend/
│
│   ├── src/
│   │
│   ├── components/
│   ├── context/
│   ├── dashboard/
│   ├── pages/
│   ├── routes/
│   ├── services/
│   └── styles/
│
└── README.md