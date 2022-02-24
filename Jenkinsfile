pipeline {
    agent any
    triggers {
        githubPush()
    }
    tools {
        maven 'Maven'
    }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "172.19.0.2:8081"
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
        stage('Maven SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    dir("/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj") {
                        sh "mvn verify sonar:sonar -Dsonar.login=0c9c089575d7a724b74b6a6bdf69e66062dba06d"
                    }
                }
            }
        }
        stage('Publish to Nexus Repository Manager') {
            steps {
                dir("/var/jenkins_home/workspace/jumpthequeue_development/java/jtqj") {
                    script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
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