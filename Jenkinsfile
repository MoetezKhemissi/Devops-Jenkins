pipeline {
    agent any
    triggers {
        pollSCM('* * * * *') // Checks for changes every minute; adjust as necessary
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Le code source a été récupéré.'
            }
        }

        stage('Build avec Maven') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {

                    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=test -Dsonar.host.url=http://192.168.2.30:9000 -Dsonar.login=sqa_6206a3ea12660fc8defce6dba4a4e0fa0761fbd5"
                }
            }
        }
        stage('Tests Mockito/Junit') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Nexus') {
            steps {
                sh 'mvn deploy -DskipTests=true'
            }
        }

        stage('Docker') {
            steps {
                script {
                    // Build the Docker image with Jenkins BUILD_NUMBER as the version
                    sh 'docker build -t kaddemimage:v${BUILD_NUMBER} -f Dockerfile ./'
                    
                    // Tagging the Docker image for Docker Hub
                    sh 'docker tag kaddemimage:v${BUILD_NUMBER} moetezkhemissi/moetezkhemissi-5infini2-g5:v${BUILD_NUMBER}'

                    // Login to Docker Hub (Ensure Docker Hub credentials are configured in Jenkins)
                    // The 'dockerhubcredentials' should be the ID of your Docker Hub credentials stored in Jenkins
                    sh 'docker login --username ${docker_username} --password ${docker_password}'
                    
                    // Push the Docker image to Docker Hub
                    sh 'docker push moetezkhemissi/moetezkhemissi-5infini2-g5:v${BUILD_NUMBER}'
                    
                    // Run Docker Compose
                    sh "IMAGE_VERSION=v${BUILD_NUMBER} docker compose up -d"
                }
            }
        }
    }
    post {
        success {
            mail to: 'khemissimoetez@gmail.com',
                 subject: "SUCCESS: Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "The pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} completed successfully."
        }
        failure {
            mail to: 'khemissimoetez@gmail.com',
                 subject: "FAILURE: Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "The pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} failed."
        }
        unstable {
            mail to: 'khemissimoetez@gmail.com',
                 subject: "UNSTABLE: Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "The pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} is unstable."
        }
        aborted {
            mail to: 'khemissimoetez@gmail.com',
                 subject: "ABORTED: Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "The pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} was aborted."
        }
    }
}
