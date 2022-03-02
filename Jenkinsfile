pipeline {    
    agent any
    tools {
        maven 'Maven'
        nodejs 'NodeJS'
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
        stage('Build and Analyse Projects') {
            parallel {
                stage('Backend') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj") {
                                    sh 'mvn clean package'
                                }
                            }
                        }
                        stage('SonarQube') {
                            steps {
                                withSonarQubeEnv('SonarQube') {
                                    dir("/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj") {
                                        sh "mvn verify sonar:sonar -Dsonar.login=0c9c089575d7a724b74b6a6bdf69e66062dba06d"
                                    }
                                }
                            }
                        }
                    }                    
                }
                stage('Frontend') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("/var/jenkins_home/workspace/jumpthequeue_development/angular") {
                                    sh 'yarn install'
                                    sh 'npm run ng build'
                                }
                            }
                        }
                        stage('SonarQube') {
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
            }            
        }        
        stage('Create and Push Docker Images') {
            steps {
                script {
                    docker.withRegistry('http://172.19.0.4:8082/repository/docker-img-repo', 'nexus-admin-credentials') {
                        def backendImage
                        def frontendImage
                        backendImage = docker.build(backendDockerImageName, "/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj")
                        backendImage.push('latest')
                        frontendImage = docker.build(frontendDockerImageName, "/var/jenkins_home/workspace/jumpthequeue_development/angular")
                        frontendImage.push('latest')
                    }
                }
            }
        }
        post {
            cleanup {
                cleanWs()
            }
        }
    }
}