# Product Add to Cart using Microservice

This project is a Spring Boot application that manages product add to cart and the quantity of inventory for each product. It allows CRUD operations for product, inventory, cart in various product.

## Features
- Spring Eureka
- Spring Gateway
- Add, update, delete, and list product.
- Add, update, delete, and list cart.
- Add, update, delete, and list inventory.
- asscociation table for product to cart.
- sharing entities to each microservice.

## Prerequisites

- Java 17 or higher
- Maven
- MySQL

## Architecture


The project leverages a microservices architecture using Spring Boot, Eureka Server, and API Gateway to manage the features of product, cart, and inventory. 
It uses an association table to connect between product and cart, and utilizes Spring RestTemplate in the Inventory Service to interact and get the product ID to manage the quantity of each product. 
A shared microservice is used to share common entities across the different microservices.
## Getting Started

## Microservices
- API Gateway: Controls and routes requests to the appropriate microservice.
- Eureka Server: Service discovery for the microservices.
- Product Service: Manages CRUD operations for products.
- Cart Service: Manages CRUD operations for cart items.
- Inventory Service: Manages CRUD operations for inventory items and updates the quantity of each product.

## Clone the Repository

```sh
git clone [https://github.com/your-username/student-course-enrollment.git](https://github.com/RaysonKhoo/springBoot-practise.git)
cd student-course-enrollment

mvn clean install


mvn spring-boot:run

## Configuration
Ensure that MySQL is running and properly configured in the application.properties file for each microservice.

## Services Interaction
-  Product Service: Exposes endpoints to add, update, delete, and list products.
-  Cart Service: Exposes endpoints to add, update, delete, and list cart items.
-  Inventory Service: Uses RestTemplate to interact with the Product Service to get product details and manage inventory quantities.--  

## Example Usage
-  Register a new product using the Product Service.
-  Add the product to the cart using the Cart Service.
-  Update the inventory for the product using the Inventory Service.
-  List all products, cart items, and inventory to see the current state.
