# Health Check Application

This is a Java-based web application that provides a health check API to monitor the status of the application instance. It uses **Java 17**, **Hibernate ORM** for database management, and is deployed on **Apache Tomcat 10**. The application automatically bootstraps the database schema and provides a RESTful API endpoint (`/healthz`) to check the health of the application.

------

## **Features**

1. **Database Bootstrapping**:
   - Automatically creates or updates the database schema, tables, indexes, and sequences using Hibernate.
   - No manual SQL scripts are required.
2. **Health Check API**:
   - Endpoint: `/healthz`
   - Inserts a record into the `health_check` table with a timestamp.
   - Returns:
     - `200 OK` if the record is inserted successfully.
     - `503 Service Unavailable` if the database insert fails.
   - Rejects requests with a payload and returns `400 Bad Request`.
   - Supports only the `GET` method; other methods return `405 Method Not Allowed`.
3. **Database Table**:
   - Table: `health_check`
     - `check_id`: Primary key, auto-incremented.
     - `datetime`: Timestamp in UTC for each health check.
4. **Cache Control**:
   - Ensures API responses are not cached by setting the `Cache-Control: no-cache` header.

------

## **Technologies Used**

- **Java 17**
- **Hibernate ORM** (for database management)
- **Apache Tomcat 10** (for deployment)
- **MySQL** (database)
- **Maven** (build tool)

------

## **Prerequisites**

Before running the application, ensure the following are installed:

1. **Java 17**:

   bash

   ```
   sudo apt update
   sudo apt install openjdk-17-jdk
   ```

2. **Apache Tomcat 10**:

   bash

   ```
   sudo apt install tomcat10
   ```

3. **MySQL**:

   bash

   ```
   sudo apt install mysql-server
   ```

4. **Maven**:

   bash

   ```
   sudo apt install maven
   ```

------

## **Setup and Deployment**

### **1. Clone the Repository**

Clone the project repository to your local machine:

bash

```
git clone <repository-url>
cd healthcheck-app
```

### **2. Configure the Database**

1. Start MySQL and create the database:

   bash

   ```
   sudo systemctl start mysql
   sudo mysql -u root
   ```

   sql

   ```
   CREATE DATABASE csye6225;
   ```

2. Update the `persistence.xml` file with your database credentials:

   xml

   ```
   <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/healthcheckdb"/>
   <property name="javax.persistence.jdbc.user" value="csye6225"/>
   <property name="javax.persistence.jdbc.password" value="StrongP@ssw0rd!"/>
   ```

   run HTML

### **3. Build the Project**

Use Maven to build the project:

bash

```
mvn clean package
```

### **4. Deploy to Tomcat**

1. Copy the generated WAR file to Tomcat's `webapps` directory:

   bash

   ```
   sudo cp target/healthcheck-app.war /var/lib/tomcat10/webapps/
   ```

2. Restart Tomcat:

   bash

   ```
   sudo systemctl restart tomcat10
   ```

### **5. Access the Application**

The application will be available at:

```
http://localhost:8080/healthcheck-app/healthz
```

------

## **API Documentation**

### **Health Check Endpoint**

- **URL**: `/healthz`
- **Method**: `GET`
- **Request Payload**: None (returns `400 Bad Request` if payload is present).
- **Response**:
  - `200 OK`: Record inserted successfully.
  - `503 Service Unavailable`: Database insert failed.
  - `405 Method Not Allowed`: Non-GET methods are not supported.

------

## **Testing**

### **1. Test the Health Check API**

Use `curl` to test the `/healthz` endpoint:

bash

```
curl -v -X GET http://localhost:8080/healthcheck-app/healthz
```

or

```
curl -vvvv http://localhost:8080/healthcheck-app/healthz
```

### remote test:

```
http://34.27.203.52:8080/healthcheck-app/healthz
```

### **2. Verify Database Records**

Check the `health_check` table to ensure records are being inserted:

bash

```
mysql -u root -p
```

sql

```
USE healthcheckdb;
SELECT * FROM health_check;
```

------

## **Troubleshooting**

### **1. Application Not Deployed**

- Check Tomcat logs (`/var/log/tomcat10/catalina.out`) for errors.
- Ensure the WAR file is correctly placed in `/var/lib/tomcat10/webapps/`.

### **2. Database Connection Issues**

- Verify MySQL is running:

  bash

  ```
  sudo systemctl status mysql
  ```

- Check `persistence.xml` for correct database credentials.

### **3. API Returns 404**

- Ensure the context path is correct:

  ```
  http://localhost:8080/healthcheck-app/healthz
  ```

- Check if the `HealthCheckServlet` is correctly annotated with `@WebServlet("/healthz")`.

------

## **License**

This project is licensed under the MIT License. See the [LICENSE](https://chat.deepseek.com/a/chat/s/LICENSE) file for details.

