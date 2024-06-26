package org.common.UniTest

def uniTest() {
    withEnv(["GRADLE_OPTS=-Dorg.gradle.daemon=false"]) {
        echo 'Unit test with Gradle...'
        sh './gradlew test'
    }
}

return this
