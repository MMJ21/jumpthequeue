pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
        stage('Sonarqube') {
            environment {
                scannerHome = tool 'SonarQube'
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "mvn clean package && ${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=develop -Dsonar.sources=. -Dsonar.exclusions=*.java"
                }
            }
        }
        
    }
}