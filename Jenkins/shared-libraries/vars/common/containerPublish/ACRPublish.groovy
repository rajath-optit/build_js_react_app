
def publishToContainer(azureRegistry, imageName, imageTag) {

    sh "az acr login --name ${azureRegistry}"
    sh "docker tag ${imageName}:${imageTag} ${azureRegistry}.azurecr.io/${imageName}:${imageTag}"
    sh "docker push ${azureRegistry}.azurecr.io/${imageName}:${imageTag}"
}

return this
