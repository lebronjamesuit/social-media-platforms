# Social-media-platforms
-  This is a backend apis spring boot source code for confession social media system.
  
- Front-end: Angular 14 https://github.com/lebronjamesuit/social-media-angular14
  
- username/password:  user1 / user1password

# Deployment: 
- AWS Elastic Beanstalk currently does not offer built-in HTTPS support. To address this, I've configured an AWS Load Balancer (Application Load Balancer) to seamlessly route incoming requests to the EC2 Beanstalk environment, handling both HTTPS traffic on port 443 and HTTP on port 80.

- I decided for the backend system, I've implemented SSL/TLS decryption using a self-signed certificate.

  https://lbconfessionapis-2012569758.eu-west-2.elb.amazonaws.com/swagger-ui/index.html


Amazon RDS 

    Instance: db.t3.micro
    PostgreSQL 15.3-R2  

<img width="685" alt="image" src="https://github.com/lebronjamesuit/social-media-platforms/assets/11584601/46fbd605-e3a4-4424-b806-40488608c642">


# Features
- LRU (Least Recently Used) cache implementation on access O(1), modify O(n)
- User registration and login with JWT authentication
- Asymmetric keysRSA 2048, private key and public key
- Password encryption using BCrypt
- Role-based authorization with Spring Security
- Customized access denied handling
- Logout mechanism revoke tokens 
- Refresh token.
- Swagger-UI and Open API 3.0
- Async mail service 
- Cross-Origin Resource Sharing (CORS) basic configure

# Technologies
- Java 11+ 
- Spring Boot 3.0
- Spring Security 6.0
- JSON Web Tokens (JWT) - nimbusds jwt 
- Mapstruct, Lombok
- Postgresql 




# Spring Security + Oauth 2 Client combination flow chart.

<img width="1138" alt="Screenshot 2023-07-09 at 20 42 26" src="https://github.com/lebronjamesuit/social-media-platforms/assets/11584601/633db9db-6940-4e60-8450-f56745fe4e6c">

# Token management
- JSON Web Tokens format in this project
          
<img width="739" alt="image" src="https://github.com/lebronjamesuit/social-media-platforms/assets/11584601/26ff0fc2-8e6f-4cb0-9e67-ae866404901c">



- Access tokens remain valid for a short period, usually less than 15 minutes, in order to maintain security. 
- Refresh tokens, on the other hand, stay active for a longer duration, typically around one month.
- The front-end application (angular web app) automatically initiates a request for a new access token as soon as the current one expires.
  
![refresh tokn](https://github.com/lebronjamesuit/social-media-platforms/assets/11584601/2f9cb3cf-6c25-41a6-8fbe-c5fb053d3f5a)

Authorization
1. So it starts with the Client sending a login request to the server.
2. The server checks the credentials provided by the user, if the credentials are right, it creates a JSON Web Token (JWT).
3. It responds with a success message (HTTP Status 200) and the JWT.
4. The client uses this JWT in all the subsequent requests to the user, it provides this JWT as an Authorization header with Bearer authentication scheme.
5. When the server, receives a request against a secured endpoint, it checks the JWT and validates whether the token is generated and signed by the server or not.
6. If the validation is successful, the server responds accordingly to the client.














