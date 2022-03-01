pipeline {    
    agent any
    triggers {
        githubPush()
    }
    tools {
        maven 'Maven'
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
        stage('Build Backend Image') {
            steps {
                script {
                    docker.withRegistry('172.19.0.5:8082') {
                        def app
                        app = docker.build(firstDockerImageName, "/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj")
                        //app.push()
                    }
                    
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