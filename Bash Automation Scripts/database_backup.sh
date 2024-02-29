#!/bin/bash

Username="Admin"
Pass="admin"
Schema="Admin"
Backup_dir="backups"
log_dir="logs"
log_path="logs/database_log.log"
timestamp=$(date +%Y%m%d_%H%M)
log_timestamp=$(date +%Y-%m-%d_%H:%M:%S)
ERROR="Backup failed"
INFO="Backup completed without any errors"

if [ ! -d $Backup_dir ]
then
    mkdir -p "$Backup_dir";
fi

if [ ! -d $log_dir ]
then
    mkdir -p $log_dir
fi

expdp ${Username}/${Pass}@XE schemas=${Schema} directory=${Backup_dir} dumpfile=${Schema}_backup_${timestamp}.dmp logfile=${Schema}_backup_${timestamp}.log
if [ $? -eq 0 ]
then
    cd ${Backup_dir}
    mkdir Backup_${timestamp}
    mv ${Schema}_backup_${timestamp}.dmp ${Schema}_backup_${timestamp}.log Backup_${timestamp}

    echo "$log_timestamp [INFO] $INFO" >> ../$log_path
else
    powershell -Command "Add-Type -AssemblyName System.Windows.Forms; [System.Windows.Forms.MessageBox]::Show('${ERROR}', 'Alert', 'OK', 'Error')";
    echo "$log_timestamp [ERROR] $ERROR" >> $log_path
fi