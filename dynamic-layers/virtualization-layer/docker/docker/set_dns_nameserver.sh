#!/bin/bash

#***********************************************************************
#* FILENAME: set_dns_nameservers.sh
#*
#* DESCRIPTION:
#*   Add DNS nameserver 8.8.8.8 and 8.8.4.4 using "connmanctl" utility.
#* 
#* AUTHOR: RVC       START DATE: 28/04/2019
#*
#* CHANGES:
#*
#* NOTE:
#*   This service should be run right after docker has started.
#* 
#***********************************************************************

# ---------- Define constant global variables ----------

# These values are used as return values of functions
TRUE=0
FALSE=1

DNS_SERVERS="8.8.8.8 8.8.4.4"

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

# ---------- Main ----------

# Check if "connmanctl" exists or not
if ! is_tool_exist "connmanctl"
then
    echo "Error: tool 'connmanctl' does not exist. Program exited." > /dev/stderr
    exit 1
fi

echo "Info: Finding Internet connection."

while true
do
    # Connection Manager documentation:
    # ConnMan presents network connections as services.
    # The existing services can be shown using the ConnMan command line utility connmanctl
    #
    # > connmanctl services
    # *AO Wired                ethernet_3d871eafa37e_cable
    # *AR eca                  wifi_38502dce5bcf_656361_managed_psk
    # *   Guest                wifi_38502dce5bcf_4775657374_managed_none
    # *   ABC Phone            bluetooth_5C886ED67C1D_327100D49DFA
    #
    # The symbols in the output above are: '*' favorite (saved) network, 'A' autoconnectable, 'O' online and 'R' ready.
    # If no letter is shown in the O/R column, the network is not connected. 
    # In addition, temporary states include 'a' for association, 'c' configuration and 'd' disconnecting. 
    # When any of these three letters are showing, the network is in the process of connecting or disconnecting.
    #
    # A network is in state 'ready' once it has obtained an IPv4 or IPv6 address or both.
    # A network is in state 'online' if ConnMan has verified connectivity to Internet
    # The 'ready' state gives and indication that the network might need a proxy set up to get connected from 
    # a company intranet, airport WiFi or similar. In the worst case the network really has limited outside connectivity
    # for one or another reason.
    #
    # ConnMan will have only one service at a time in state 'online' while the other services are kept in state 'ready'.
    #
    # Reference: https://01.org/connman/documentation
    
    INTERNET_SERVICE="$( connmanctl services | awk '/^\*AO/ { print $NF }' )"
    if [ "$INTERNET_SERVICE" = "" ]
    then
        # Try again after 2 seconds
        echo "Info: Trying again..."
        sleep 2
    else
        echo -e "Info: Internet connection at service $INTERNET_SERVICE."
        
        # TODO: Add DNS nameserver 8.8.8.8 and 8.8.4.4 using "connmanctl" utility.
        #   - This procedure must be performed by "connmanctl" because network configuration
        #     on EK874 board is managed by Connection Manager (https://en.wikipedia.org/wiki/ConnMan).
        #   - So, this means the below steps will NOT WORK:
        #     1. Add "dns-nameservers 8.8.8.8 8.8.4.4" under "iface eth0 inet dhcp" in file /etc/network/interfaces.
        #     2. Directly add "nameserver 8.8.8.8" and "nameserver 8.8.4.4" in /etc/resolv.conf file.
        #        When the board restarts or the Ehternet port is plugged in, ConnMan will remove these DNS values 
        #        and replaced with default values.
        #
        # Why do I have to set these DNS nameservers?
        #   - It's because Docker needs these DNS servers to pull containers from community.
        #   - If not, Docker client will generate the below error:
        #       | docker: error pulling image configuration:
        #       | Get https://registry-1.docker.io/v2/: net/http: dial tcp: i/o timeout
        
        connmanctl config $INTERNET_SERVICE --nameservers $DNS_SERVERS > /dev/null 2>&1
        if [ $? -eq 0 ]
        then
            echo "Info: Set DNS server $DNS_SERVERS successfully."
            exit 0
        else
            echo "Error: Failed to set DNS server $DNS_SERVERS."  > /dev/stderr
            exit 1
        fi
        
        break
    fi
done