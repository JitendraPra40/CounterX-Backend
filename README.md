# Restaurant Management System

Spring Boot 3 backend scaffold for a small restaurant system. This first module covers the core database model plus the QR customer ordering and mock payment workflow.

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- MySQL
- Lombok
- Swagger/OpenAPI

## Configure MySQL

Update `src/main/resources/application.properties` if your local database credentials are different:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_management?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
```

## Run

```bash
mvn spring-boot:run
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

After login, click `Authorize` in Swagger UI and paste the JWT token value. Swagger will send it as:

```http
Authorization: Bearer <token>
```

## Seed Data

On first run, the app creates:

- Admin user `admin` with password `admin123`
- Tables 1 to 5
- Masala Dosa
- Tea
- Veg Meals
- Rice, Sugar, and Oil inventory records

## Admin Login

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Use the returned token for admin endpoints:

```http
Authorization: Bearer <token>
```

## Inventory Management

Inventory endpoints are admin-only:

- `GET /api/admin/inventory`
- `GET /api/admin/inventory/{id}`
- `POST /api/admin/inventory`
- `PUT /api/admin/inventory/{id}`
- `DELETE /api/admin/inventory/{id}`

Create or update inventory:

```json
{
  "itemName": "Rice",
  "quantity": 25,
  "unit": "kg",
  "lowStockThreshold": 5
}
```

## Order And Payment Workflow

Create an order:

```http
POST /api/orders
Content-Type: application/json
```

```json
{
  "tableNumber": 1,
  "items": [
    {
      "menuItemId": 1,
      "quantity": 2
    },
    {
      "menuItemId": 2,
      "quantity": 1
    }
  ]
}
```

The backend calculates the total and saves the order as `PENDING_PAYMENT`.

Complete mock payment:

```http
POST /api/payments
Content-Type: application/json
```

```json
{
  "orderId": 1,
  "paymentMethod": "UPI"
}
```

The backend creates a successful payment, generates a transaction id, and updates the order to `COMPLETED`.

## Public Endpoints

- `POST /api/auth/login`
- `GET /api/menu`
- `POST /api/orders`
- `GET /api/orders/{id}`
- `POST /api/payments`
