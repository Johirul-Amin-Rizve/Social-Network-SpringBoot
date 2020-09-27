# Social-Media-SpringBoot
This Project created with JAVA version 11 and Built With,
* Spring Boot 2.
* Spring Framework 5.
* H2 Database.
* Thymeleaf

# Project Theme 
Mr. Arif owns a travel agency, he has so many dreams and ideas to improve his business. To spread his business to customers, Mr. Arif has decided to build a website like social media where user can register and post a status by checking in location (locations which are only covered by that travel agency).

# Run Project
Clone repository and build application using meven then run. You can fine H2 Database configuration in application.properties file. After run the application browse http://localhost:8080 for application home page. You can check http://localhost:8080/h2 for database console. No need to give any password just press connect for check database. By design, the this in-memory database is volatile, and data will be lost when we restart the application.

# ER Diagram

You can see the Social-Network-SpringBoot_ER_Diagram.jpg file in github repository.

# Project Feature 

1. User can register.
2. User can login.
3. User can post a status by checking in a location (from a drop down box).
4. User can change the privacy of the post to public, private. If he chooses public any users (including anonymous user) can see the post in home page of the web application. If he chooses private only that user can see the post from his personal profile page.
5. In profile section logged in user view his basic information and all of his / her post.
6. logged in user can view / edit / delete / pin to top his / her post in profile section also can comment in any post.
7. In public posts section logged in user view all other users public post and can add / view comments. 
8. In public posts section anonymous user all public post and can view comments only.
