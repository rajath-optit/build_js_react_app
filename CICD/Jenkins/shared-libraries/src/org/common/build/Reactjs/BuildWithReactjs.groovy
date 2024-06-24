package org.common.build.ReactJS

def buildWithNpm() {
    echo 'Building with npm...'
    sh 'npm install'
    sh 'npm run build'
}

return this
