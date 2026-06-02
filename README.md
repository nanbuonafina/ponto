# ⏰ Sistema de Controle de Ponto

Sistema web para gerenciamento de registro de ponto eletrônico, desenvolvido com arquitetura Full Stack utilizando **Spring Boot**, **React + TypeScript** e banco de dados relacional.

O sistema permite que colaboradores realizem registros de entrada e saída, acompanhem seus históricos de ponto, exportem relatórios e que administradores gerenciem usuários, registros e informações gerenciais através de um painel administrativo.

---

# 📋 Funcionalidades

## 👤 Funcionário

### Registro de Ponto

- Registrar entrada
- Registrar saída
- Registro automático da data e horário
- Validação de registros

### Histórico de Pontos

- Visualizar histórico completo de marcações
- Consulta de registros anteriores
- Exibição de:
  - Data
  - Horário
  - Tipo da marcação
  - Status

### Relatórios

- Exportação dos registros em:
  - Excel (.xlsx)
  - PDF (.pdf)
- Relatórios individuais do usuário autenticado

### Perfil

- Visualização dos dados do usuário
- Alteração de senha
- Política de senha forte

## 👨‍💼 Administrador

### Gestão de Usuários

- Listagem de usuários
- Cadastro de novos usuários
- Edição de usuários
- Exclusão de usuários
- Definição de perfis:
  - ADMIN
  - FUNCIONARIO

### Gestão de Registros de Ponto

- Visualização de todos os registros
- Consulta de registros por usuário
- Auditoria de marcações

### Dashboard Administrativo

- Quantidade total de usuários
- Quantidade de administradores
- Quantidade de funcionários
- Informações consolidadas do sistema

### Relatórios Gerenciais

- Exportação de relatórios administrativos
- Relatórios em Excel
- Relatórios em PDF

# 🔒 Segurança

## Autenticação

- Login via JWT (JSON Web Token)
- Sessões stateless
- Proteção de rotas

## Controle de Acesso

- Controle baseado em papéis (RBAC)
- Perfis:
  - ADMIN
  - FUNCIONARIO

## Política de Senhas

A senha deve conter:

- Mínimo de 8 caracteres
- Pelo menos 1 letra maiúscula
- Pelo menos 1 letra minúscula
- Pelo menos 1 número
- Pelo menos 1 caractere especial

Exemplo válido:

Senha@123

# 🏗️ Arquitetura

## Backend

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT
- Maven

## Frontend

- React
- TypeScript
- Vite
- Axios
- React Router DOM
- CSS

## Banco de Dados

Banco relacional utilizado para armazenar usuários, perfis, registros de ponto e informações de auditoria.

# 📂 Estrutura do Projeto

```text
├── backend/
├── frontend/
└── README.md
```

# 🚀 Como Executar o Projeto

## Backend

```bash
cd backend
mvn spring-boot:run
```

## Frontend

```bash
cd frontend
npm install
npm run dev
```

# 🔌 Principais Endpoints

## Autenticação

```http
POST /auth/login
```

## Usuários

```http
GET    /usuarios
POST   /usuarios
PUT    /usuarios/{id}
DELETE /usuarios/{id}
```

## Pontos

```http
POST /pontos/registrar
GET  /pontos/meus-registros
GET  /pontos
```

## Relatórios

```http
GET /relatorios/meus-pontos/pdf
GET /relatorios/meus-pontos/excel
GET /relatorios/geral/pdf
GET /relatorios/geral/excel
```

# 📄 Licença

Projeto destinado a fins acadêmicos e educacionais.
