pipeline {    
    agent any
    triggers {
        githubPush()
    }
    tools {
        maven 'Maven'
    }
    environment {
        backendDockerImageName = "jtq-backend"
        frontendDockerImageName = "jtq-frontend"
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
                    docker.withRegistry('http://172.19.0.4:8082/repository/docker-img-repo', 'nexus-admin-credentials') {
                        def app
                        app = docker.build(backendDockerImageName, "/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj")
                        app.push('latest')
                    }
                }
            }
        }
        stage('Build Frontend Image') {
            steps {
                script {
                    def app
                    app = docker.build(frontendDockerImageName, "/var/jenkins_home/workspace/jumpthequeue_development/angular")
                }
            }
        }
    }
}