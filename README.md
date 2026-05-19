# CyberStore API

REST API de e-commerce desenvolvida com Java 17 e Spring Boot 3, seguindo arquitetura em camadas (Resource → Service → Repository) e boas práticas de design de APIs RESTful.

---

## Tecnologias

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.0-6DB33F?style=flat&logo=spring-boot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-Hibernate-59666C?style=flat&logo=hibernate&logoColor=white)
![H2](https://img.shields.io/badge/H2-In--Memory-003545?style=flat)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Production-4169E1?style=flat&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=flat&logo=apache-maven&logoColor=white)

---

## Modelo de domínio

```
User ──< Order >── OrderItem >── Product >── Category
                       │
                    Payment
```

| Entidade    | Relacionamento                                             |
|-------------|-----------------------------------------------------------|
| `User`      | Um usuário pode ter vários pedidos (`1:N`)                 |
| `Order`     | Um pedido pertence a um usuário e pode ter um pagamento    |
| `OrderItem` | Chave composta (`order_id` + `product_id`), armazena preço e quantidade no momento da compra |
| `Payment`   | Relacionamento `1:1` com `Order` via `@MapsId`            |
| `Product`   | Muitos produtos pertencem a muitas categorias (`N:N`)      |
| `Category`  | Lado inverso do relacionamento com `Product`               |

### Status do pedido (`OrderStatus`)

| Código | Status            |
|--------|-------------------|
| 1      | WAITING_PAYMENT   |
| 2      | PAID              |
| 3      | SHIPPED           |
| 4      | DELIVERED         |
| 5      | CANCELED          |

---

## Endpoints da API

### Users `/users`

| Método   | Rota          | Descrição              | Status de sucesso |
|----------|---------------|------------------------|-------------------|
| `GET`    | `/users`      | Lista todos os usuários | `200 OK`          |
| `GET`    | `/users/{id}` | Busca usuário por ID   | `200 OK`          |
| `POST`   | `/users`      | Cria novo usuário      | `201 Created`     |
| `PUT`    | `/users/{id}` | Atualiza usuário       | `200 OK`          |
| `DELETE` | `/users/{id}` | Remove usuário         | `204 No Content`  |

### Orders `/orders`

| Método | Rota            | Descrição             | Status de sucesso |
|--------|-----------------|-----------------------|-------------------|
| `GET`  | `/orders`       | Lista todos os pedidos | `200 OK`          |
| `GET`  | `/orders/{id}`  | Busca pedido por ID   | `200 OK`          |

### Products `/products`

| Método | Rota              | Descrição              | Status de sucesso |
|--------|-------------------|------------------------|-------------------|
| `GET`  | `/products`       | Lista todos os produtos | `200 OK`          |
| `GET`  | `/products/{id}`  | Busca produto por ID   | `200 OK`          |

### Categories `/categories`

| Método | Rota                | Descrição               | Status de sucesso |
|--------|---------------------|-------------------------|-------------------|
| `GET`  | `/categories`       | Lista todas as categorias | `200 OK`          |
| `GET`  | `/categories/{id}`  | Busca categoria por ID  | `200 OK`          |

---

## Tratamento de erros

Respostas de erro seguem um modelo padronizado (`StandardError`):

```json
{
  "timestamp": "2025-01-01T12:00:00Z",
  "status": 404,
  "error": "Resource not found",
  "message": "Resource not found. Id 99",
  "path": "/users/99"
}
```

| Exceção                    | HTTP Status         |
|----------------------------|---------------------|
| `ResourceNotFoundException` | `404 Not Found`    |
| `DatabaseException`         | `400 Bad Request`  |

---

## Perfis de ambiente

### `test` — H2 em memória (padrão local)

Ativo por padrão via `application.properties`:

```properties
spring.profiles.active=test
```

Configuração (`application-test.properties`):

```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Ao subir no perfil `test`, o `TestConfig` popula automaticamente o banco com dados de exemplo (categorias, produtos, usuários, pedidos e itens de pedido).

### `prod` — PostgreSQL

Configure as variáveis de ambiente antes de subir:

```properties
spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
spring.datasource.username=<usuario>
spring.datasource.password=<senha>
```

---

## Como executar localmente

### Pré-requisitos

- Java 17 (recomendado: Eclipse Temurin)
- Maven 3.9+ (ou use o Maven Wrapper incluído)

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/CyberStore-API.git
cd CyberStore-API
```

### 2. Execute a aplicação

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
.\mvnw.cmd spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### 3. Acesse o H2 Console (perfil test)

URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

| Campo        | Valor                  |
|--------------|------------------------|
| Driver Class | `org.h2.Driver`        |
| JDBC URL     | `jdbc:h2:mem:testdb`   |
| User Name    | `sa`                   |
| Password     | *(deixar em branco)*   |

---

## Estrutura do projeto

```
src/main/java/com/api/CyberStore_API/
├── config/
│   └── TestConfig.java              # Seed de dados para perfil test
├── entities/
│   ├── enums/
│   │   └── OrderStatus.java         # Enum com códigos numéricos
│   ├── pk/
│   │   └── OrderItemPK.java         # Chave composta embeddable
│   ├── Category.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Payment.java
│   ├── Product.java
│   └── User.java
├── repositories/
│   ├── CategoryRepository.java
│   ├── OrderItemRepository.java
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   └── UserRepository.java
├── resources/
│   ├── exceptions/
│   │   ├── ResourceExceptionHandler.java  # @ControllerAdvice global
│   │   └── StandardError.java             # Modelo de erro padronizado
│   ├── CategoryResource.java
│   ├── OrderResource.java
│   ├── ProductResource.java
│   └── UserResource.java
└── services/
    ├── exceptions/
    │   ├── DatabaseException.java
    │   └── ResourceNotFoundException.java
    ├── CategoryService.java
    ├── OrderService.java
    ├── ProductService.java
    └── UserService.java
```

---

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
