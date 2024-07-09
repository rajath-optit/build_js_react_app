def deployWithGradle() {
    sh "gradle clean build"
}

def copyJarToRemote(credentialID, remoteHost) {
  
    withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -i $SSH_PRIVATE_KEY build/libs/*.jar $SSH_USERNAME@${remoteHost}:/root/"
    }
}

def copyWarToRemote(credentialID, remoteHost) {
  
    withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -i $SSH_PRIVATE_KEY build/libs/*.war $SSH_USERNAME@${remoteHost}:/root/"
    }
}

def deployJar(credentialID, remoteHost) {
  
   withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "ssh -i $SSH_PRIVATE_KEY $SSH_USERNAME@${remoteHost} 'java -jar /root/*.jar'"
    }
}

def deployWarToTomcat(credentialID, remoteHost) {
  
    withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -i $SSH_PRIVATE_KEY build/libs/*.war $SSH_USERNAME@${remoteHost}:/opt/tomcat/webapps/"
                        sh "ssh -i $SSH_PRIVATE_KEY $SSH_USERNAME@${remoteHost} 'sudo systemctl restart tomcat'"
    }
}

return this
