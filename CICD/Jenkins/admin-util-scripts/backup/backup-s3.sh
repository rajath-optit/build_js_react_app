#!/bin/bash


# Function to configure AWS CLI
configure_aws_cli() {
    echo "Configuring AWS CLI..."
    read -p "Enter your AWS access key ID: " aws_access_key_id
    read -p "Enter your AWS secret access key: " aws_secret_access_key
    read -p "Enter your region: " region
    aws configure set aws_access_key_id "$aws_access_key_id"
    aws configure set aws_secret_access_key "$aws_secret_access_key"
    aws configure set region "$region"
    # Optionally, set other configurations like output format, etc.
    echo "AWS CLI configuration completed."
}

# Check if AWS CLI is installed
if ! command -v aws &> /dev/null
then
    echo "AWS CLI is not installed."
    
    # Install AWS CLI
    read -p "Do you want to install AWS CLI? (y/n): " install_aws_cli
    if [ "$install_aws_cli" = "y" ]; then
        echo "Installing AWS CLI..."
        # Install AWS CLI based on the OS
        # For example, on Linux, you might use:
        sudo apt-get update && sudo apt-get install -y awscli
        # For macOS, you might use:
        # brew install awscli
        # For Windows, you might use:
        # choco install awscli
        
        # Configure AWS CLI after installation
        configure_aws_cli
    else
        echo "AWS CLI installation skipped."
        exit 1
    fi
else
    echo "Please configure aws cli"  
    # Check if AWS CLI is configured
    configure_aws_cli
   
fi




# Jenkins home directory path
JENKINS_HOME="/var/lib/jenkins"

# S3 bucket name
YOUR_BUCKET_NAME="optitjenkinsbackup"

# Tar the Jenkins home directory
echo "Tarring $JENKINS_HOME directory"
tar -cvf jenkins_backup.tar -C $JENKINS_HOME .

# Check the exit code of the tar command
exitcode=$?
if [ "$exitcode" != "1" ] && [ "$exitcode" != "0" ]; then
    exit $exitcode
fi

# Upload the tarred file to S3 bucket
echo "Uploading jenkins_backup.tar to S3 bucket"
aws s3 cp jenkins_backup.tar s3://$YOUR_BUCKET_NAME/

# Remove files after successful upload to S3
echo "Removing files after successful upload to S3"
rm -rf jenkins_backup.tar
