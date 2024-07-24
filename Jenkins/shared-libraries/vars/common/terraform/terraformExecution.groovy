def init() {   
    sh "terraform init" 
}

def plan() {   
    sh "terraform plan" 
}

def validate() {   
    sh "terraform validate" 
}

def apply() {   
    sh "terraform apply -auto-approve" 
}

def applyWithVar(varWithValue) {
    sh "terraform apply -auto-approve -var="${varWithValue}""
}

def applyWithVarFile(varFile) {
    sh "terraform apply -auto-approve -var-file="${varFile}""
}

return this
