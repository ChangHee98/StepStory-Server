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
                    def containerId = sh(script: 'docker ps -a -q -f name=BackendContainer', returnStdout: true).trim()
                    if (containerId) {
                        sh "docker stop $containerId || true"
                        sh "docker rm $containerId || true"
                    } else {
                        echo "No previous container found"
                    }
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                sh "docker run -d --name BackendContainer --network=host leeinhong9512/openjdk:17-alpine"
            }
        }
    }
}
