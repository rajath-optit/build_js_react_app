// Jenkins/shared-libraries/vars/common/pipelineConfig/common-stages.groovy

def executeCommonStages(gitCheckoutScript, sonarScript, dockerBuildScript, dockerPublishScript, buildWithNpmScript, unitTestWithNpmScript, params) {
    stage('Git Checkout Source Code Repo') {
        steps {
            script {
                gitCheckoutScript.gitCheckout(params.SOURCE_CODE_BRANCH_NAME, params.GIT_SOURCE_CODE_URL, params.GIT_SOURCE_CODE_CREDENTIAL)
            }
        }
    }

    stage('Build') {
        steps {
            script {
                buildWithNpmScript.build()
            }
        }
    }

    stage('Unit Test') {
        steps {
            script {
                unitTestWithNpmScript.uniTest()
            }
        }
    }

    stage('SonarQube Analysis') {
        steps {
            script {
                sonarScript.performSonarCloudAnalysis(params.SONAR_PROJECT_KEY, params.SONAR_ORGANIZATION, params.SONAR_SOURCES_DIR, params.SONAR_TOKEN)
            }
        }
    }

    stage('Docker Build') {
        steps {
            script {
                dockerBuildScript.dockerBuild(params.DOCKER_IMAGE_NAME, params.DOCKER_IMAGE_TAG)
            }
        }
    }

    stage('Docker Publish') {
        steps {
            script {
                dockerPublishScript.publishToContainer(params.DOCKER_IMAGE_NAME, params.DOCKER_REPO, params.DOCKER_IMAGE_TAG, params.DOCKER_CREDENTIAL)
            }
        }
    }
}
