package org.common.build

def build() {
    echo 'Building with npm...'
    sh 'npm install'
    sh 'npm run build'
}

return this
