def deployWithMaven() {

    sh "mvn clean package"
}

def copyJarToRemote(credentialID, remoteHost) {
  
    withCredentials([sshUserPrivateKey(credentialsId: '${credentialID}', keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -i $SSH_PRIVATE_KEY target/*.jar $SSH_USERNAME@${remoteHost}:/root/"
    }
}

def deployWithMavenJar(credentialID, remoteHost) {
  
   withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "ssh -i $SSH_PRIVATE_KEY $SSH_USERNAME@${remoteHost} 'java -jar /root/*.jar'"
    }
}

def copyWarToTomcat(credentialID, remoteHost) {
  
    withCredentials([sshUserPrivateKey(credentialsId: '${credentialID}', keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -i $SSH_PRIVATE_KEY webapp/target/*.war $SSH_USERNAME@${remoteHost}:/opt/tomcat/webapps/"
    }
}

def deployWarToTomcat(credentialID, remoteHost) {
  
    withCredentials([sshUserPrivateKey(credentialsId: '${credentialID}', keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -i $SSH_PRIVATE_KEY webapp/target/*.war $SSH_USERNAME@${remoteHost}:/opt/tomcat/webapps/"
                        sh "ssh -i $SSH_PRIVATE_KEY $SSH_USERNAME@${remoteHost} 'sudo systemctl restart tomcat'"
    }
}

return this
