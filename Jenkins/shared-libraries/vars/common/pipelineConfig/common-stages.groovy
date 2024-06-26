def buildStage(language) {
    stage('Build') {
        steps {
            script {
                if (language == 'js') {
                    CommonPipelineFunctions.buildWithNpm()
                } else if (language == 'java') {
                    CommonPipelineFunctions.buildWithMaven()
                }
            }
        }
    }
}

def unitTestStage(language) {
    stage('Unit Test') {
        steps {
            script {
                if (language == 'js') {
                    CommonPipelineFunctions.unitTestWithNpm()
                } else if (language == 'java') {
                    CommonPipelineFunctions.unitTestWithMaven()
                }
            }
        }
    }
}

def sonarQubeStage(projectKey, organization, sourcesDir, sonarTokenId) {
    stage('SonarQube Analysis') {
        steps {
            script {
                CommonPipelineFunctions.performSonarCloudAnalysis(projectKey, organization, sourcesDir, sonarTokenId)
            }
        }
    }
}

def dockerBuildStage(imageName, imageTag) {
    stage('Docker Build') {
        steps {
            script {
                CommonPipelineFunctions.dockerBuild(imageName, imageTag)
            }
        }
    }
}

def dockerPublishStage(imageName, dockerRepo, imageTag, dockerCredential) {
    stage('Docker Publish') {
        steps {
            script {
                CommonPipelineFunctions.publishToContainer(imageName, dockerRepo, imageTag, dockerCredential)
            }
        }
    }
}
