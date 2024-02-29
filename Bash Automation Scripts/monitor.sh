#!/bin/bash

disk_threshold=82

DISK_WARNING="Disk is running out of space"
DISK_INFO="Disk is in a Good Shape"

cpu_threshold=50

CPU_WARNING="CPU Usage Crossed ${cpu_threshold}%"

log_dir="logs"
log_path="logs/database_log.log"
curr_path="D:\ITI\University Case Study\Data-Management-System-for-University\Bash Automation Scripts"
timestamp=$(date +%Y-%m-%d_%H:%M:%S)

cd "$curr_path"

if [ ! -d $log_dir ]
then
    mkdir -p $log_dir
fi

disk_usage=$(df -h | awk 'BEGIN{FS=" "}{if (NR==3) { sub(/%/, "", $(NF-1)); print $(NF-1);}}')
cpu_usage=$(wmic cpu get loadpercentage | grep -Eo [0-9]+)

if [ $disk_usage -gt $disk_threshold ]
then
    echo "$timestamp [WARNING] $DISK_WARNING" >> $log_path ;
    powershell -Command "Add-Type -AssemblyName System.Windows.Forms; [System.Windows.Forms.MessageBox]::Show('${DISK_WARNING}', 'Warning', 'OK', 'Warning')";
else
    echo "$timestamp [INFO] $DISK_INFO" >> $log_path
fi

if [ $cpu_usage -gt $cpu_threshold ]
then
    echo "$timestamp [WARNING] $CPU_WARNING" >> $log_path ;
    powershell -Command "Add-Type -AssemblyName System.Windows.Forms; [System.Windows.Forms.MessageBox]::Show('${CPU_WARNING}', 'Warning', 'OK', 'Warning')";
fi
