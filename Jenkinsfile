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
        stage('Maven SonarQube') {
            def mvn = tool 'Maven';
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=develop"
                }
            }
        }
        stage('Angular SonarQube') {
            def scannerHome = tool 'SonarQube';
            steps {                 
                withSonarQubeEnv('SonarQube') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
        
    }
}