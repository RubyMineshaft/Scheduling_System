# Scheduling System


## Information

**Title:** Scheduling System  
**Purpose:** Provides a GUI that allows organization users to manage customers and appointments.  


**Author:** Matthew Etress  
**Contact:** metres1@wgu.edu  
**Application Version:** 1.0.0  
**Date:** 2021-06-02  


**IDE:** IntelliJ IDEA 2020.3.3 (Community Edition)  
**JDK:** Java 11.0.10  
**JavaFX Version:** JavaFX-SDK-16  


## Usage
Start the application by running the Main thread.
Once the app has finished compiling, you will be presented with a login form in English or French
depending on the default language set on your machine. Use "test" as the username and password.

Once authenticated you will be shown a table containing appointments.
Appointments can be filtered by week or month using the tabs above the table.
Appointments may be created, updated, or deleted using the buttons below the table.

The buttons on the left side of the screen can be used to navigate to the customer view (which also has buttons
for managing customer records) or to generate reports.

Once the user is finished with the application, they can click the log out button to return to the login screen.


## Additional Information
- The additional report generated displays the number of customers per first level division.
- MySQL Connector Driver Version: mysql-connector-java-8.0.22
- Lambda expressions are used in the AppointmentsController, and are discussed in the javadoc comments.
- Javadoc can be found in dist/javadoc.
