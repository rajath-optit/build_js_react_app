package org.common.test.ReactJS

def testWithNpm() {
    echo 'Unit test with npm...'
    sh 'npm test'
}

return this
