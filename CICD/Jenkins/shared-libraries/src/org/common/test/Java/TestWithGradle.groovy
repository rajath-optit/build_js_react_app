package org.common.test.Java

def testWithGradle() {
    withEnv(["GRADLE_OPTS=-Dorg.gradle.daemon=false"]) {
        echo 'Unit test with Gradle...'
        sh './gradlew test'
    }
}

return this
