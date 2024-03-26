pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'dev', url: "https://github.com/InhongLee95/StepStory-Server.git"
            }
        }

        stage('Build Project') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Docker Login') {
            steps {
                sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t leeinhong9512/openjdk:17-alpine ."
            }
        }

        stage('Push Docker Image') {
            steps {
                sh "docker push leeinhong9512/openjdk:17-alpine"
            }
        }

        stage('Run Docker Container') {
            steps {
                sh "docker run -d --name BackendContainer -p 8081:8081 leeinhong9512/openjdk:17-alpine"
            }
        }
    }
}
