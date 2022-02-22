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
                dir("/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj") {
                    sh 'mvn clean package'
                }
            }
        }
        stage('Maven SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    dir("/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj") {
                        sh "mvn clean verify sonar:sonar -Dsonar.projectKey=develop"
                    }
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