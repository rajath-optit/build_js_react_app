// src/org/common/build/Java/BuildWithGradle.groovy

package org.common.build.Java

def buildWithGradle() {
    withEnv(["GRADLE_OPTS=-Dorg.gradle.daemon=false"]) {
        echo 'Building with Gradle...'
        sh './gradlew clean build'
    }
}

return this
