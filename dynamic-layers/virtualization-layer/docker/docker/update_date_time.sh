#!/bin/bash

#***********************************************************************
#* FILENAME: update_date_time.sh
#*
#* DESCRIPTION:
#*   Synchronize date and time with NTP server pool.ntp.org.
#* 
#* AUTHOR: RVC       START DATE: 28/04/2019
#*
#* CHANGES:
#*
#* NOTE:
#*   This service should be run right after set_dns_nameservers.sh is run.
#* 
#***********************************************************************

# ---------- Define constant global variables ----------

# These values are used as return values of functions
TRUE=0
FALSE=1

# ---------- Function definitions ----------

# Function: is_tool_exist.
# ----------------------------
#   Check if a tool is installed on host or not?
#
#   $1: a tool. For example: "ls", "cp", "gst-launch-1.0"...
#
#   returns: true ($TRUE): if the tool exists.
#            false ($FALSE): if the tool doesn't exist.
function is_tool_exist
{
    # Return value
    RESULT=$FALSE
    
    if [ "$( which $1 )" != "" ]
    then
        RESULT=$TRUE
    fi
    
    return $RESULT
}

# Function: is_internet_connected.
# ----------------------------
#   Check if the board is connected to the Internet or not?
#
#   returns: true ($TRUE): if the board is connected to the Internet.
#            false ($FALSE): if the Internet cannot be reached.
function is_internet_connected
{
    # Return value
    RESULT=$FALSE
    
    nc -w 1 8.8.8.8 443 > /dev/null 2>&1
    if [ $? -eq 0 ]
    then
        RESULT=$TRUE
    fi
    
    return $RESULT
}

# ---------- Main ----------

# Check if "ntpdate" exists or not
if ! is_tool_exist "ntpdate"
then
    echo "Error: tool 'ntpdate' does not exist. Program exited." > /dev/stderr
    exit 1
fi

# Check if "nc" exists or not
if ! is_tool_exist "nc"
then
    echo "Error: tool 'nc' does not exist." > /dev/stderr
    exit 1
fi

# Check if "date" exists or not
if ! is_tool_exist "date"
then
    echo "Warning: tool 'date' does not exist." > /dev/stderr
fi

echo "Info: Checking Internet connection."

# Check the Internet connection
while true
do
    if ! is_internet_connected
    then
        # Try again after 2 seconds
        echo "Info: Trying again..."
        sleep 2
    else
        echo "Info: Connected to the Internet."
        break
    fi
done

echo "Info: Preparing to synchronize date and time from the Internet."

# TODO: Synchronize date and time with NTP server pool.ntp.org.
#   - This procedure must be performed by "ntpdate" utility.
#   - Using "ntpdate" and "ntpd" services to synchronize date and time is not ideal because it will take a long time
#     (<= 10 minutes) for the board to update date and time. Mean while, customers can use Docker client and meet some errors
#     because of this issue. Tool "ntpdate" will help users to update date and time immediately after docker service is started.
#    
# Why do I have to synchronize date and time?
#   - It's because Docker needs current date and time to pull containers from community.
#   - If not, Docker client will generate the below error:
#      | x509: failed to load system roots and no roots provided

ntpdate pool.ntp.org > /dev/null 2>&1
if [ $? -eq 0 ]
then
    echo "Info: Synchronize date and time from the Internet successfully."
    echo "Date: $( date )"
else
    echo "Error: Failed to update date and time." > /dev/stderr
    exit 1
fi

exit 0