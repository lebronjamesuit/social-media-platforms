# social-media-platforms
...
...









<img width="1138" alt="Screenshot 2023-07-09 at 20 42 26" src="https://github.com/lebronjamesuit/social-media-platforms/assets/11584601/633db9db-6940-4e60-8450-f56745fe4e6c">

Features
- User registration and login with JWT authentication
- Password encryption using BCrypt
- Role-based authorization with Spring Security
- Customized access denied handling
- Logout mechanism
- Refresh token
Technologies
- Spring Boot 3.0
- Spring Security 6.0
- JSON Web Tokens (JWT) - nimbusds jwt
- BCrypt

Docker image: mysql
# Mysql
docker run --detach --env MYSQL_ROOT_PASSWORD=root --env MYSQL_USER=user --env MYSQL_PASSWORD=password --env MYSQL_DATABASE=social-media-db --name container-mysql-8 --publish 3306:3306 mysql:8-oracle


