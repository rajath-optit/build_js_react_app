#!/bin/bash

# Jenkins home directory path
JENKINS_HOME="/var/lib/jenkins"

# Date and time to include in the backup file name
DATE_TIME=$(date +"%Y%m%d_%H%M%S")

# Tar the Jenkins home directory
echo "Tarring $JENKINS_HOME directory"
tar -cvf jenkins_backup_$DATE_TIME.tar -C $JENKINS_HOME .

# Check the exit code of the tar command
exitcode=$?
if [ "$exitcode" != "0" ]; then
    echo "Error: Tar command failed with exit code $exitcode"
    exit $exitcode
fi
echo "Backup completed successfully"
