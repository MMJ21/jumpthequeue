pipeline {    
    agent any
    triggers {
        githubPush()
    }
    tools {
        maven 'Maven'
        docker 'Docker'
    }
    environment {
        firstDockerImageName = "jtq-backend"
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "172.19.0.4:8081"
        NEXUS_REPOSITORY = "maven-nexus-repo"
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
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
        stage('SonarQube') {
            parallel {
                stage('Maven SonarQube') {
                    steps {
                        withSonarQubeEnv('SonarQube') {
                            dir("/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj") {
                                sh "mvn verify sonar:sonar -Dsonar.login=0c9c089575d7a724b74b6a6bdf69e66062dba06d"
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
        stage('Build Backend Image') {
            steps {
                script {
                    def app
                    app = docker.build(firstDockerImageName, "/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj")
                }
            }
        }
        stage('Build Frontend Image') {
            steps {
                script {
                    def app
                    app = docker.build("jtq-frontend:{env.BUILD_ID}", "/var/jenkins_home/workspace/jumpthequeue_development/angular")
                }
            }
        }
    }
}