def nexusUpload(artifactId, artifactPath, artifactType, nexusCredentialID, nexusGroupID, nexusURL, nexusRepository, nexusVersion) {
  
               nexusArtifactUploader artifacts: [[artifactId: params.ARTIFACT_ID, classifier: '', file: params.ARTIFACT_PATH, type: params.ARTIFACT_TYPE]], credentialsId: params.NEXUS_CREDENTIAL_ID, groupId: params.NEXUS_GROUP_ID, nexusUrl: params.NEXUS_URL, nexusVersion: 'nexus3', protocol: 'http', repository: params.NEXUS_REPOSITORY, version: version
    
}

return this
