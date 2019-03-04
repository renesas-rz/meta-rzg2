#!/bin/bash

source /home/root/.bashrc
TTY=`getUser`

printf "\n\e[32m[Info]:\e[0m\n" > ${TTY}

IP_STREAM=$(cat /home/root/launcher-taskbar/hmi-scripts/IP_STREAM.info)

if [ -z $IP_STREAM]; then
	printf "\t\e[32mPlease write the IP address of target board into file\n\
	/home/root/launcher-taskbar/hmi-scripts/IP_STREAM.info\e[0m\n" > ${TTY}
else
	printf "\tStreaming file /home/root/videos/h264-hd-30.mp4 to address\n\
	${IP_STREAM}, port 5000\n" > ${TTY}
	printf "\n\e[31mNOTE: Click on Exit icon to exit from running applications\e[0m\n\n" > ${TTY}
	/home/root/RZ_scripts/RZ_GST-network-demo_transmit-video.sh /home/root/videos/h264-hd-30.mp4 ${IP_STREAM}  > ${TTY}
fi
