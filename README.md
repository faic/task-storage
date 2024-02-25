# Task Storage API

RESTful API for a task storage

Java 11  
MySQL 8  
Gradle  
Spring Boot  
Spring Data JPA  
Flyway  
Docker  


- **GET** http request that returns a list of all tasks stored in the database.
- **GET** http request that returns a specific task by their ID.
- **POST** http request that stores a new task in the database.
- **PUT** http request that updates a specific task by their ID.
- **DELETE** http request that deletes a task in the database.

The API utilizes standard HTTP status codes (400 range for client errors, 500 range for server-side)  
Example usages of endpoints

GET /api/tasks  
GET /api/tasks?completed=true  
GET /api/tasks?priority=high  

**Example Response**

```json
[
   {
      "id":1,
      "description":"d3",
      "completed":true,
      "priority":"HIGH",
      "created":"2024-02-25T14:14:00Z"
   }
]
```
GET /api/tasks/1

**Example Response**

```json
{
   "id":1,
   "description":"d3",
   "completed":true,
   "priority":"HIGH",
   "created":"2024-02-25T14:14:00Z"
}
```
POST /api/tasks/

**Example Request**
```json
{
   "description":"d2",
   "priority":"LOW"
}

```

PUT /api/tasks/1

**Example Request**
```json
{
   "description":"d3",
   "priority":"HIGH",
   "completed":true
}

```

DELETE /api/tasks/1




