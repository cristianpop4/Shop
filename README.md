# 🛒 Shop

A RESTful backend application for an online shop, built with **Spring Boot 3** as part of a learning course. The project covers core concepts like REST API design, JPA/Hibernate, email notifications, and custom exception handling.

---

## 📌 Features

- Manage products with stock tracking and price filtering
- Place orders with automatic total calculation
- Leave product reviews with ratings and comments
- User management with email uniqueness validation
- Welcome email sent automatically on user registration
- Custom exception handling for not found and duplicate resources

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17+ | Language |
| Spring Boot 3 | Framework |
| Spring Data JPA | Database access layer |
| Spring Mail | Email notifications |
| PostgreSQL | Relational database |
| Maven | Build tool |
| Lombok | Boilerplate reduction |

---

## 📡 API Endpoints

### Products `/api/products`
| Method | Path | Description |
|---|---|---|
| GET | `/api/products` | List all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create a product |
| PUT | `/api/products/{id}` | Update a product |
| DELETE | `/api/products/{id}` | Delete a product |
| GET | `/api/products/expensive/{price}` | Products above a price |
| GET | `/api/products/low-stock/{stock}` | Products below a stock level |
| GET | `/api/products/search/{name}` | Search products by name |

### Orders `/api/orders`
| Method | Path | Description |
|---|---|---|
| POST | `/api/orders` | Create an order |
| GET | `/api/orders` | List all orders |

### Reviews `/api/reviews`
| Method | Path | Description |
|---|---|---|
| POST | `/api/reviews` | Create a review |
| GET | `/api/reviews` | List all reviews |
| GET | `/api/reviews/{id}` | Get review by ID |
| DELETE | `/api/reviews/{id}` | Delete a review |

### Users `/api/users`
| Method | Path | Description |
|---|---|---|
| POST | `/api/users` | Create a user |
| GET | `/api/users` | List all users |
| GET | `/api/users/{id}` | Get user by ID |
| PUT | `/api/users/{id}` | Update a user |
| DELETE | `/api/users/{id}` | Delete a user |

---

## 📧 Email Notifications

A welcome email is automatically sent to the user's address upon successful registration, using **Spring Mail** with `JavaMailSender`.

---

## 📚 About

This project was built as part of an **accredited Java programming course**, covering:

- REST API design with Spring Boot
- Entity relationships with JPA/Hibernate (OneToMany, ManyToMany)
- DTO pattern (Request/Response separation)
- Custom exception handling
- Email integration with Spring Mail
- Lombok for cleaner code