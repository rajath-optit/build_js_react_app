def copyRepoToRemote(credentialID, remoteHost, remotePath) {
     withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "scp -r -i $SSH_PRIVATE_KEY ${env.workspace}/* $SSH_USERNAME@${remoteHost}:${remotePath}"
     }
}

def deployWithNode(credentialID, remoteHost, remotePath) {
     withCredentials([sshUserPrivateKey(credentialsId: "${credentialID}", keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USERNAME')]) {
                        sh "ssh -i $SSH_PRIVATE_KEY $SSH_USERNAME@${remoteHost} 'cd ${remotePath} ; npm install ; npm run build ; npm start '"
     }
}
return this
