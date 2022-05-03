# Abstract
Side Project for Spring & JPA Study

# Class diagram
![class_diagram](https://github.com/Aivyss/Spring_and_JPA/blob/master/docs/class_diagram.png?raw=true)

# Project Specification

## Database (RDBMS)
- H2 Database

## Application Framework
- Language: OpenJDK 17
- View Template Framework: Thymeleaf
- API Framework: Spirng Framework(Boot), v2.6.6
- Persist Framework: JPA (Hibernate)
- Test Framework: jUnit5, AssertJ, Mockito
- Other Dependencies
    - Lombok
    - Logback
    - spring-boot-devtools

# Domain Specification

## Table
- MEMBER
- ORDERS
  - ORDER_ITEM
- ITEM
  - CATEGORY_ITEM
- DELIVERY
- CATEGORY

## Sequence
- MEM_SEQ
- ORDER_SEQ
- ORDER_ITEM_SEQ
- DELIVERY_SEQ
- ITEM_SEQ
- CATEG_SEQ

## Entity
- Member
- Order
  - OrderItem
- Item
- Delivery
- Category
- Address

# Application Specification

## Account Functions
- Sign up a member
- List up members

## Item Functions
- Persist item
- Update item
- List up items

## Order Functions
- Order
- List up orders
- Cancel order

# Application Architecture
- Controller > Service > Repository > DB
- Controller > Domain
- Service > Domain
- Repository > domain

# Package Structure
- com.jpabook.jpashoot
  - domain
  - exception
  - persist
  - service
  - web
