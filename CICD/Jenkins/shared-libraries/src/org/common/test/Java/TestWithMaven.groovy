package org.common.test.Java

def testWithMaven() {
    echo 'Unit test with Maven...'
    sh 'mvn test'
}

return this
