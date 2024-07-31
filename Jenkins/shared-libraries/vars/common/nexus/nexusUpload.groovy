def nexusUpload(artifactId, artifactPath, artifactType, nexusCredentialID, nexusGroupID, nexusURL, nexusRepository, nexusVersion) {
  
               nexusArtifactUploader artifacts: [[artifactId: ${artifactId}, classifier: '', file: ${artifactPath}, type: ${artifactType}]], credentialsId: ${nexusCredentialID}, groupId: ${nexusGroupID}, nexusUrl: ${nexusURL}, nexusVersion: 'nexus3', protocol: 'http', repository: ${nexusRepository}, version: ${nexusVersion}
            }
}

return this
