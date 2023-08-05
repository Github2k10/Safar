

## SAFAR :- BUS RESERVATION MANAGEMENT PORTAL


 Project code: few-houses-4121

The bus reservation system portal is a web-based application that enables users to book bus tickets online. The system will allow users book available buses based on their source and destination cities, reserve the seats, and make payments securely. The system will also provide a UI based dashboard for admin to manage their bus related operations.


## Features

 * User and Admin authentication & validation with session uuid.

#### Admin Features:

    - Administrator Role of the entire application
    - Only registered admins with valid session token can add/update/delete route and bus from main database
    - Admin can access the details of different users and reservations.
#### User Features:
    - Registering themselves with application, and logging in to get the valid session token
    - Viewing list of available buses and booking a reservation
    - Only logged in user can access his reservations, profile updation and other features.

<br>

## ER Diagrm
<img src="https://github.com/AbhayKumarVishwakarma/Safar/blob/main/Bus%20Reservation%20System%20Portal/Frontend/src/images/ERDiagram2.jpg" > </img>

<br>

## Flow Chart
<img src='https://github.com/AbhayKumarVishwakarma/Safar/blob/main/Bus%20Reservation%20System%20Portal/Frontend/src/images/safar2.png'></img>

## Modules

* Login, Logout Module
* Admin Module
* User Module
* Route Module
* Bus Module
* Reservation Module
* Feedback Module

## 🏷️ Tech Stack Used :-
* Java
* Spring Framework
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* HTML
* CSS
* JavaScript

## Installation & Run

* Before running the API server, you should update the database config inside the [application.properties](https://github.com/AbhayKumarVishwakarma/few-houses-4121/blob/main/Bus%20Reservation%20System%20Portal/Backend/bus_reservation/src/main/resources/application.properties) file. 
* Update the port number, username and password as per your local database config.

```
      server.port=8999

      #db specific properties
      spring.datasource.url=jdbc:mysql://localhost:3306/busdb
      spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
      spring.datasource.username=root
      spring.datasource.password=root

      #ORM s/w specific properties
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true

```

## API Root Endpoint

`https://localhost:8000/`
 
`http://localhost:8000/swagger-ui/`

