pipeline {
    agent any
    triggers {
        githubPush()
    }
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
                    sh 'mvn clean install'
                }
            }
        }
        stage('Maven SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    dir("/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj") {
                        sh "mvn clean verify sonar:sonar -Dsonar.login=0c9c089575d7a724b74b6a6bdf69e66062dba06d"
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
                    dir("/var/jenkins_home/workspace/jumpthequeue_development/angular") {
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=develop"
                    }
                }
            }
        }
        
    }
}