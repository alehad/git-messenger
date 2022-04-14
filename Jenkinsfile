pipeline {
    agent any
    environment {
        registry = 'alehad/msgr'
        jenkins_credentials = 'hub.docker.id'
        app_docker_image = ''
    }
    stages {
        stage('build messenger ws project') {
            agent {
                docker {
                    image 'maven:3.8.1-adoptopenjdk-11' 
                }
            }
            steps {
                dir('MessengerWS') {
                    sh 'mvn -B clean package'
                }
            }
        }
        stage('create docker image') {
            agent { label 'master' } // this ensures that jenkins will try to access docker running on same host jenkins is running
            steps {
                dir('MessengerWS') {
                    script {
                        app_docker_image = docker.build registry
                    }
                }
            }
        }
        stage('upload docker image') {
            agent { label 'master' }
            steps {
                script {
                    docker.withRegistry('', jenkins_credentials) {
                        app_docker_image.push("1.0")
                        app_docker_image.push("latest")
                    }
                }
            }
        }
    }
}