def publishToContainer(gcpSA_CredentialID, gcpRegion, gcpProjectID, imageName, imageTag) {
  withCredentials([file(credentialsId: '${gcpSA_CredentialID}', variable: 'SA_KEY_FILE')]) {
    sh "gcloud auth activate-service-account --key-file=${SA_KEY_FILE}"
    sh "gcloud auth configure-docker"
    sh "docker tag ${imageName}:${imageTag} ${gcpRegion}/gcr.io/${gcpProjectID}/${imageName}:${imageTag}"
    sh "docker push ${gcpRegion}/gcr.io/${gcpProjectID}/${imageName}:${imageTag}"
  }
}

return this
