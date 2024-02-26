# DevOps Project: Continuous Integration and Deployment Pipeline

This project showcases a comprehensive DevOps pipeline built with Jenkins, integrating various stages from source code management to deployment, leveraging Docker, SonarQube, Nexus, and monitoring with Grafana and Prometheus.

## Pipeline Overview

The Jenkins pipeline is designed to automate several key stages:

- **Source Code Management (SCM)**: Polls SCM for changes and initiates the pipeline.
- **Build**: Utilizes Maven to compile the source code.
- **Static Code Analysis**: Conducts code quality checks with SonarQube.
- **Unit Testing**: Executes tests using Mockito and JUnit.
- **Artifact Repository**: Deploys artifacts to Nexus.
- **Containerization**: Builds a Docker image and pushes it to Docker Hub.
- **Deployment**: Deploys the application using Docker Compose.

## Docker Configuration

The application is containerized using the following Dockerfile:

```Dockerfile
FROM openjdk:11
EXPOSE 8089
ADD target/kaddem-0.0.8.jar kaddem-0.0.8.jar
ENTRYPOINT ["java", "-jar", "kaddem-0.0.8.jar"]
```
This Dockerfile creates an image for the application, making it ready for deployment.

## Docker Compose Setup

The `docker-compose.yml` file orchestrates the application and its dependencies:

```yaml
version: '3.7'
services:
  app:
    image: "kaddemimage:${IMAGE_VERSION}"
    ports:
      - "8089:8089"
    depends_on:
      - db
    environment:
      - spring.datasource.url=jdbc:mysql://mysql:3306/kaddem?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=root

  db:
    container_name: mysql
    image: mysql:5.7
    restart: always
    ports:
      - "3306:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=root
      - MYSQL_DATABASE=kaddem
    volumes:
      - db-data:/var/lib/mysql

volumes:
  db-data:
```
  This configuration sets up the application and a MySQL database, ensuring they are linked and can communicate.

## Monitoring with Grafana and Prometheus

The project includes monitoring setups with Grafana and Prometheus, configured on the server. These tools provide insights into the application's performance and health, enabling data visualization and alerting based on metrics collected from the application and infrastructure.

*Example Grafana dashboard visualizing application metrics.*

## Conclusion

This DevOps project demonstrates a robust pipeline for continuous integration and deployment, emphasizing automation, quality checks, and monitoring. The use of Docker and Docker Compose simplifies deployment and scaling, while Grafana and Prometheus offer powerful monitoring capabilities to ensure the application's performance and reliability.

For more detailed instructions and additional configurations, please refer to the project documentation and source code.

