def deployWithGradle() {
    sh "gradle clean build"
}

def copyJarToRemote(credentialID, remoteHost, artifactPath, remotePath) {
  
    withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -i $SSH_PRIVATE_KEY ${artifactPath} $SSH_USERNAME@${remoteHost}:${remotePath}"
    }
}

def copyWarToRemote(credentialID, remoteHost, artifactPath, remotePath) {
  
    withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -i $SSH_PRIVATE_KEY ${artifactPath} $SSH_USERNAME@${remoteHost}:${remotePath}"
    }
}

def deployJar(credentialID, remoteHost, remotePath) {
  
   withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "ssh -i $SSH_PRIVATE_KEY $SSH_USERNAME@${remoteHost} 'java -jar ${remotePath}/*.jar'"
    }
}

def deployWarToTomcat(credentialID, remoteHost, artifactPath, remotePath) {
  
    withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -i $SSH_PRIVATE_KEY ${artifactPath} $SSH_USERNAME@${remoteHost}:${remotePath}"
                        sh "ssh -i $SSH_PRIVATE_KEY $SSH_USERNAME@${remoteHost} 'sudo systemctl restart tomcat'"
    }
}

return this
