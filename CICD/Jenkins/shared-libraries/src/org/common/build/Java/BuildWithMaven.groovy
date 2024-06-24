// src/org/common/build/Java/BuildWithMaven.groovy

package org.common.build.Java

def buildWithMaven() {
    echo 'Building with Maven...'
    sh 'mvn clean install'
}

return this
