# API Algamoney

<img src="https://i.imgur.com/aTaviZA.png" title="source: imgur.com" />

## Visão Geral
A API **Algamoney** é um serviço RESTful desenvolvido em **Spring Boot** para gerenciar lançamentos financeiros, categorias, pessoas e autenticação de usuários.

###
Antes de consumir a API no seu frontend, adicione o path no arquivo [CorsConfig.java no método allowedOrigins()](src/main/java/com/keidson/algamoney_api/cors/CorsConfig.java)

## Dependências
- Java 17
- Maven 3.8.1+
- PostgreSQL 13+

## Como Rodar o Projeto

1. **Clone o repositório:**
   ```sh
   git clone <https://github.com/keidsondesigner/algamoney-api.git>
   cd algamoney-api
   ```

2. **Configure o banco de dados:**
   - Atualize as configurações do banco de dados PostgreSQL em `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DATABASE>
     spring.datasource.username=<USERNAME>
     spring.datasource.password=<PASSWORD>
     ```

3. **Rodando o Projeto via Docker**

   3.1 **Certifique-se de ter o Docker instalado na sua máquina.**

   3.2 **Construa a imagem Docker:**
      ```sh
      docker build -t algamoney-api .
      ```

   3.3 **Rode o contêiner:**
      ```sh
      docker run -p 8080:8080 algamoney-api
      ```

   Isso irá iniciar o contêiner com a aplicação rodando no Java 17, sem a necessidade de ter o Java instalado na sua máquina local. A aplicação estará disponível em `http://localhost:8080`.

4. **Compile e rode o projeto localmente:**
   - Dependências:
   - Java 17
   - Maven 3.8.1+
   - PostgreSQL 13+
   ```sh
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

5. **Acesse a aplicação:**
   - Após subir o serviodor, a aplicação estará disponível em `http://localhost:8080`.


## Autenticação

A API utiliza autenticação via **JWT**. Para acessar os endpoints protegidos, é necessário incluir um token **Bearer** no cabeçalho das requisições.


### 1. Registrar Usuário
**Endpoint:** `POST /auth/register`

**Request Body:**
```json
{
  "nome": "teste",
  "email": "teste@gmail.com",
  "senha": "teste"
}
```

### 2. Login
**Endpoint:** `POST /auth/login`

**Request Body:**
```json
{
  "email": "admin@algamoney.com",
  "senha": "admin"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

---

## Categorias

### 3. Criar Nova Categoria
**Endpoint:** `POST /categorias`

**Headers:**
- Authorization: Bearer {token}

**Request Body:**
```json
{
  "nome": "Alimentação"
}
```

### 4. Buscar Todas as Categorias
**Endpoint:** `GET /categorias`

**Headers:**
- Authorization: Bearer {token}

### 5. Buscar Categoria por ID
**Endpoint:** `GET /categorias/{id}`

**Headers:**
- Authorization: Bearer {token}

---

## Pessoas

### 6. Criar Nova Pessoa
**Endpoint:** `POST /pessoas`

**Headers:**
- Authorization: Bearer {token}

**Request Body:**
```json
{
  "nome": "João Silva",
  "endereco": {
    "logradouro": "Rua X",
    "numero": "123",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "estado": "SP"
  },
  "ativo": true
}
```

### 7. Buscar Todas as Pessoas
**Endpoint:** `GET /pessoas`

**Headers:**
- Authorization: Bearer {token}

### 8. Buscar Pessoa por ID
**Endpoint:** `GET /pessoas/{id}`

**Headers:**
- Authorization: Bearer {token}

### 9. Atualizar Pessoa
**Endpoint:** `PUT /pessoas/{id}`

**Headers:**
- Authorization: Bearer {token}

**Request Body:**
```json
{
  "nome": "João Silva Editado",
  "ativo": true
}
```

### 10. Excluir Pessoa
**Endpoint:** `DELETE /pessoas/{id}`

**Headers:**
- Authorization: Bearer {token}

---

## Lançamentos

### 11. Criar Novo Lançamento
**Endpoint:** `POST /lancamentos`

**Headers:**
- Authorization: Bearer {token}

**Request Body:**
```json
{
  "pessoa": { "codigo": 7 },
  "categoria": { "codigo": 5 },
  "descricao": "Salário",
  "dataVencimento": "2024-07-23",
  "valor": 3000.00,
  "tipo": "RECEITA"
}
```

### 12. Buscar Todos os Lançamentos
**Endpoint:** `GET /lancamentos`

**Headers:**
- Authorization: Bearer {token}

### 13. Buscar Lançamento por ID
**Endpoint:** `GET /lancamentos/{id}`

**Headers:**
- Authorization: Bearer {token}

### 14. Excluir Lançamento
**Endpoint:** `DELETE /lancamentos/{id}`

**Headers:**
- Authorization: Bearer {token}

---

## Filtros e Paginação

### 15. Buscar Lançamentos Paginados
**Endpoint:** `GET /lancamentos/paginados?page=1&size=5`

**Headers:**
- Authorization: Bearer {token}

### 16. Filtrar Lançamento por Descrição
**Endpoint:** `GET /lancamentos/filtrar?descricao=Aluguel`

**Headers:**
- Authorization: Bearer {token}

### 17. Filtrar Lançamento por Data de Vencimento
**Endpoint:** `GET /lancamentos/filtrar?dataVencimentoDe=2024-09-10&dataVencimentoAte=2024-09-15`

**Headers:**
- Authorization: Bearer {token}

