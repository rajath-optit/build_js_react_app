def nexusuploads(String version, String artifactid) {
  
               nexusArtifactUploader artifacts: [[artifactId: artifactid, classifier: '', file: params.ARTIFACT_PATH, type: params.ARTIFACT_TYPE]], credentialsId: params.NEXUS_CREDENTIAL_ID, groupId: params.NEXUS_GROUP_ID, nexusUrl: params.NEXUS_URL, nexusVersion: 'nexus3', protocol: 'http', repository: params.NEXUS_REPOSITORY, version: version
    
}

return this
