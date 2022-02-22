pipeline {
    agent any
    tools {
        maven 'Maven'
    }

    stages {
        stage('Initialize') {
            steps {
                echo 'PATH = ${M2_HOME}/bin:${PATH}'
                echo 'M2_HOME = /opt/maven'
            }
        }
        stage('Build') {
            steps {
                dir("/var/lib/jenkins/workspace/jumpthequeue/jumpthequeue") {
                    sh 'mvn clean package'
                }
            }
        }
        stage('Maven SonarQube') {
            environment {
                mvn = tool 'Maven';
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=develop"
                }
            }
        }
        stage('Angular SonarQube') {
            environment {
                scannerHome = tool 'SonarQube';
            }
            steps {                 
                withSonarQubeEnv('SonarQube') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
        
    }
}