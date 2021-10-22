# CST438-Project2
CST438-Project2 (Group C)

Title :- Wishlist

- [Chaitanya Parwatkar](https://github.com/parw8649)
- [Noah Ahmed](https://github.com/noa316)
- [Kathryn Grose](https://github.com/katgrose)
- [Barbara Kondo](https://github.com/bkondo)

This project includes a backend and a frontend. The backend basically involves creation of the API calls and they are fetched by the front end. For the tech stack Springboot is used on the backend and for the frontend javascript is used. The project creates an API to allow users to create and edit a web-based wishlist where they can add items by entering the image URLs as per their choice. 

The project includes the following API's for the user: Create an account, Login, Log out, Delete account, List all items, show specific list, add, update and remove items. As an Admin all the user functionalities are possible and additionally all users can be viewed, new users can be created, updated or deleted. Username are kept unique and the password requirement is to have atleast 6 characters, alphanumeric and include atleast one special character. Passwords are specifically encryted and secured and the user's password is not visible to anyone. 

For persistence and to provide as a communication between the frontend and backend Jason web tokens (JWT) are used. Everytime an access token is generated when the login API is called and provides persistence until the logout API is called after which the token expires. A seprate access token is generated for the Admin and the user to diffrentiate their roles and fulfill their individual requirements separately. 

Firbase is integrated using Springboot at the backend to add a persisitence layer. The Firebase database is used to maintain the persistence and record of the user's information, specifically the user and product details. Firebase also stores and keeps in track the generated access tokens for the users. 



**Entity Relationship Diagrams**
![Entity Relationship Diagram](https://github.com/parw8649/CST438-Project2/blob/develop/wk09_project02groupC_ERDs.png)

**Sitemap**
![Sitemap](https://github.com/parw8649/CST438-Project2/blob/bKondo-patch-1/wk05_project02groupC_sitemap.png)

**Page Mockups: User and Wishlist**
![user page mockups](https://github.com/parw8649/CST438-Project2/blob/bKondo-patch-1/wk05_project02groupC_user_page_mockups.png)
![wishlist page mockups](https://github.com/parw8649/CST438-Project2/blob/bKondo-patch-1/wk05_project02groupC_wishlist_page_mockups.png)

**Dependencies Used:**



	https://mvnrepository.com/artifact/com.google.firebase/firebase-admin
	implementation group: 'com.google.firebase', name: 'firebase-admin', version: '7.1.1'

	Lombok
	compileOnly 'org.projectlombok:lombok'

	Lombok Annotation
	annotationProcessor 'org.projectlombok:lombok'

	https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security
	implementation group: 'org.springframework.boot', name: 'spring-boot-starter-security', version: '2.5.4'

	https://mvnrepository.com/artifact/javax.validation/validation-api
	implementation group: 'javax.validation', name: 'validation-api', version: '2.0.1.Final'

	https://mvnrepository.com/artifact/org.modelmapper/modelmapper
	implementation group: 'org.modelmapper', name: 'modelmapper', version: '2.4.0'

	JWT
	implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.0'

	https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api
	implementation group: 'javax.xml.bind', name: 'jaxb-api', version: '2.3.1'

	testImplementation 'org.springframework.boot:spring-boot-starter-test'
  

**Libraries Used:**

**Demo Video:**
