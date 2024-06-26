package org.common.uniTest

def uniTest() {
    echo 'Unit test with Maven...'
    sh 'mvn test'
}

return this
