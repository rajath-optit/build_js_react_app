def publishToContainer(String imageName, String dockerRepo, String imageTag, String credentialsId) {
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}"
        sh "docker tag ${imageName}:${imageTag} ${dockerRepo}/${imageName}:${imageTag}"
        sh "docker push ${dockerRepo}/${imageName}:${imageTag}"
    }
}

return this
