# cash register
Java. JSP. Servlets. JDBC. Filters. Log4j. I18n
The application was developed according to requirements.
###Functionality of application.

Access for different user roles  is  restricted. 
The cashier can create receipt or payment, add goods to the receipt by code or by it’s name, change quantity of goods in receipt, change status of receipt from created to closed

 The chief-cashier can cancel receipt, delete goods from receipt,create a full report with all receipts and payments(X-report),
create a full report for the tax operations by the end of the day(Z-report) .

 The manager can create goods, its description and add quantity in the storage. 
 
When the sale’s receipt was created and saved, the amount of goods in the storage decreased.
When the return’s receipt was created and saved. the amount of goods in the storage increased.

The application contains back- end and front-end.
The architecture of the application is monolith, and bases on the MVC pattern.
The data is stored  in the relational database MySql. To get access for the data storage is used JDBC API with pool connection.
The Data Access Object (DAO) pattern is used in application  to  isolate the business layer from the relational database layer. 
CRUD operations and transactional services were implemented to satisfy the requirements of the task.
The functionality of the application was realized by using Servlet API. Apache Tomcat was used as a container of servlets. The PRG design pattern was applied to prevent duplicate form submissions to the server. 
For restriction of the access for different users in the  application was used Filter API.
Authentication is used to verify that users really are in the database, and authorization is then used to grant the user permission to access different levels of information and perform specific functions, depending on the rules for different types of users.
The application used library  log4j  and it was covered with tests(JUnit4).
The inputted data was validated.The pagination mechanism is implemented.
JSP, CSS, Bootstrap were actually applied to develop  the user interface .
