
package org.common.build.Python

def buildWithMaven() {
    echo 'Building with Maven...'
    sh 'pip -r requirements.txt'
}

return this
