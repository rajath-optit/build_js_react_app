# Jenkins Backup Scripts

This repository contains scripts for backing up your Jenkins home directory. You can choose to either store the backup on an S3 bucket or locally on the Jenkins server.

## Prerequisites

- Jenkins installed on your server.
- AWS CLI installed and configured with appropriate credentials (for S3 backup).
- Bash shell environment.

## Usage

### Backup to S3

1. **Configuration**:
   - Set the `JENKINS_HOME` variable to the path of your Jenkins home directory.
   - Set the `YOUR_BUCKET_NAME` variable to the name of your S3 bucket.

2. **Running the Script**:
   - Make the script executable: `chmod +x backup_jenkins_to_s3.sh`.
   - Execute the script: `./backup_jenkins_to_s3.sh`.
   - The script will create a tar archive of the Jenkins home directory, upload it to the specified S3 bucket, and remove the archive after successful upload.

3. **Customization**:
   - You can customize the script by modifying the `JENKINS_HOME` and `YOUR_BUCKET_NAME` variables as needed.
   

4. **Error Handling**:
   - The script includes error handling for both the tar command and the AWS S3 upload command. If any of these commands fail, the script will exit with the corresponding exit code.

### Backup Locally

1. **Configuration**:
   - Set the `JENKINS_HOME` variable to the path of your Jenkins home directory.

2. **Running the Script**:
   - Make the script executable: `chmod +x Jenkins-backup-jenkinsserver.sh`.
   - Execute the script: `./Jenkins-backup-jenkinsserver.sh`.
   - The script will create a tar archive of the Jenkins home directory and store it locally on the Jenkins server.

3. **Customization**:
   - You can customize the script by modifying the `JENKINS_HOME` variable as needed.
   - The backup file will include the current date and time in the file name for easy identification.



## Notes

- It's recommended to periodically transfer the local backups to a secure location to prevent data loss in case of server issues.
- Ensure that there is sufficient disk space available on the Jenkins server to store the backup files.
- Set up a Freestyle Jenkins job to execute these backup scripts periodically using cron scheduling.

