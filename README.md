# API Cloud Kitchen Restaurant

API REST para gerenciamento de restaurantes, cardápios e usuários, desenvolvida com Spring Boot 4.x. Suporta banco de dados H2 em memória (desenvolvimento) e PostgreSQL (produção via Docker).

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Spring Data JPA / Hibernate
- H2 Database (in-memory, para desenvolvimento e testes)
- PostgreSQL 16 (produção via Docker)
- Docker / Docker Compose
- Lombok
- Maven

## Arquitetura

O projeto segue uma arquitetura em camadas (Clean Architecture / Hexagonal):

```
src/main/java/.../
├── domain/
│   ├── entity/          # Entidades de domínio (UserType, Restaurant, MenuItem)
│   └── repository/      # Interfaces de repositório
├── application/
│   └── usecase/         # Casos de uso (interfaces e implementações)
└── infrastructure/
    ├── adapter/
    │   └── controller/  # Controllers REST e DTOs
    └── persistence/     # Entidades JPA e implementações de repositório
```

## Funcionalidades

### Usuários (`/user-types`)
- CRUD completo de usuários
- Suporte a dois tipos: **Dono de restaurante** (`owner: true`) e **Cliente** (`owner: false`)
- Endpoint especial: `GET /user-types/{id}/with-restaurants` — retorna o usuário e, se for dono, lista seus restaurantes

### Restaurantes (`/restaurants`)
- CRUD completo de restaurantes
- Campos: nome, endereço, tipo de cozinha, horário de funcionamento, dono (referência a usuário)
- Endpoint especial: `GET /restaurants/{id}/with-menu` — retorna o restaurante com todos os itens do cardápio

### Itens do Cardápio (`/menu-items`)
- CRUD completo de itens do cardápio
- Campos: nome, descrição, preço, disponibilidade apenas no local, caminho da foto, restaurante

## Como executar

### Opção 1 — Docker Compose (recomendado, com PostgreSQL)

**Pré-requisitos:** Docker e Docker Compose instalados.

```bash
docker compose up --build
```

Isso irá:
1. Subir um container PostgreSQL 16
2. Executar `docker/init.sql` automaticamente (cria tabelas e insere dados iniciais)
3. Aguardar o PostgreSQL ficar saudável
4. Construir e iniciar a aplicação Spring Boot conectada ao PostgreSQL

A aplicação estará disponível em: `http://localhost:8080`

Para parar e remover os containers:

```bash
docker compose down
```

Para remover também o volume de dados do PostgreSQL:

```bash
docker compose down -v
```

---

### Opção 2 — Execução local (com H2 em memória)

**Pré-requisitos:** Java 21+ e Maven (ou use o wrapper `./mvnw`).

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Executar os testes

```bash
./mvnw test
```

## Banco de Dados H2

O banco de dados H2 em memória é inicializado automaticamente com dados de exemplo.

**Console H2:** `http://localhost:8080/h2-console`

| Campo    | Valor                      |
|----------|----------------------------|
| JDBC URL | `jdbc:h2:mem:restaurantdb` |
| Username | `sa`                       |
| Password | `123`                      |

### Dados iniciais (seed)

- **3 donos de restaurante:** Fernando Almeida, Gabriela Lima, Henrique Santos
- **5 clientes:** Ana Silva, Bruno Costa, Carla Dias, Daniel Rocha, Elisa Souza
- **3 restaurantes:** Cantina do Fernando (Italiana), Sabores da Gabriela (Brasileira), Tempero do Henrique (Japonesa)
- **14 itens de cardápio** distribuídos entre os restaurantes

## Endpoints

### Usuários

| Método | Endpoint                          | Descrição                              |
|--------|-----------------------------------|----------------------------------------|
| POST   | `/user-types`                     | Criar usuário                          |
| GET    | `/user-types`                     | Listar todos os usuários               |
| GET    | `/user-types/{id}`                | Buscar usuário por ID                  |
| PUT    | `/user-types/{id}`                | Atualizar usuário                      |
| DELETE | `/user-types/{id}`                | Remover usuário                        |
| GET    | `/user-types/{id}/with-restaurants` | Buscar usuário com seus restaurantes |

### Restaurantes

| Método | Endpoint                    | Descrição                              |
|--------|-----------------------------|----------------------------------------|
| POST   | `/restaurants`              | Criar restaurante                      |
| GET    | `/restaurants`              | Listar todos os restaurantes           |
| GET    | `/restaurants/{id}`         | Buscar restaurante por ID              |
| PUT    | `/restaurants/{id}`         | Atualizar restaurante                  |
| DELETE | `/restaurants/{id}`         | Remover restaurante                    |
| GET    | `/restaurants/{id}/with-menu` | Buscar restaurante com cardápio      |

### Itens do Cardápio

| Método | Endpoint            | Descrição                        |
|--------|---------------------|----------------------------------|
| POST   | `/menu-items`       | Criar item do cardápio           |
| GET    | `/menu-items`       | Listar todos os itens            |
| GET    | `/menu-items/{id}`  | Buscar item por ID               |
| PUT    | `/menu-items/{id}`  | Atualizar item                   |
| DELETE | `/menu-items/{id}`  | Remover item                     |

## Exemplos de Requisição

### Criar usuário
```json
POST /user-types
{
  "name": "João Silva",
  "phone": "11999999999",
  "email": "joao.silva@example.com",
  "owner": false
}
```

### Criar restaurante
```json
POST /restaurants
{
  "name": "Meu Restaurante",
  "address": "Rua das Flores, 100 - São Paulo, SP",
  "cuisineType": "Italiana",
  "openingHours": "Seg-Sex 11:00-23:00",
  "ownerId": 1
}
```

### Criar item do cardápio
```json
POST /menu-items
{
  "name": "Spaghetti ao Pesto",
  "description": "Massa com molho pesto artesanal",
  "price": 42.90,
  "onlyInRestaurant": false,
  "photoPath": "/photos/spaghetti-pesto.jpg",
  "restaurantId": 1
}
```

## Coleção Postman

O arquivo `postman/PostmanCollection.json` contém todas as requisições configuradas para teste da API.

Importe o arquivo no Postman e configure a variável `baseUrl` como `http://localhost:8080`.

## Testes

O projeto possui cobertura de testes com mais de 80%:

- **Testes unitários** — casos de uso com Mockito
- **Testes de controller** — endpoints com `@WebMvcTest` e MockMvc
- **Testes de integração** — fluxo completo com `@SpringBootTest` e H2 em memória
