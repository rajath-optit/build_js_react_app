def uploadToS3(artifactPath, bucketName, destination) {
     
    sh "aws s3 cp ${artifactPath} s3://${bucketName}/${destination}" 
}

def uploadToGcloud(artifactPath, bucketName, destination) {
     
    sh "gsutil cp ${artifactPath} gs://${bucketName}/${destination}" 
}

def uploadToBlob(artifactPath, containerName, artifactName, storageAccountName) {
     
    sh """
    az storage blob upload \
      --container-name ${containerName} \
      --file ${artifactPath} \
      --name ${artifactName} \
      --account-name ${storageAccountName}
    """ 
}

return this
