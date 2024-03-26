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

        stage('Stop and Remove Previous Container') {
            steps {
                script {
                    def containerStatus = sh(script: 'docker ps -q -f name=BackendContainer', returnStdout: true).trim()
                    if (containerStatus) {
                        sh "docker stop BackendContainer || true"
                        sh "docker rm BackendContainer || true"
                    } else {
                        echo "No previous container running"
                    }
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                sh "docker run -d --name BackendContainer -p 8080:8080 leeinhong9512/openjdk:17-alpine"
            }
        }
    }
}
