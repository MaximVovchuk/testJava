# Project Setup Instructions

To run this project, follow these steps:

1. Ensure you have Docker installed on your system. If not, you can download it from [Docker's official website](https://www.docker.com/get-started).

2. Open a terminal and navigate to the project's root folder.

3. Run the following command to create a container with the required database:
   
   docker-compose up

This command will set up the necessary database environment.

4. Once the Docker container is up and running, you can start the project as usual. It will automatically connect to the database within the Docker container.

5. Your project should now work properly. You can access it through [Swagger](http://localhost:8080/swagger-ui/index.html) or using utilites like Postman.
