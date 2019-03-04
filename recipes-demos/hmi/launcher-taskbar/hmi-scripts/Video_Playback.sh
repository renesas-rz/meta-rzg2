#!/bin/bash
source /home/root/.bashrc

TTY=`getUser`

printf "\n\e[32m[Info]:\e[0m\n" > ${TTY}
printf "\tYou are watching Video playback on screen\n\
\twith default position (0, 0) & Video file\n\
\t(/home/root/videos/h264-fhd-60.mp4)\n" > ${TTY}
printf "\n\e[31mNOTE: Click on Exit icon to exit from running applications\e[0m\n\n" > ${TTY}

/home/root/RZ_scripts/RZ_GST-demo_video-playback.sh /home/root/videos/h264-fhd-60.mp4 $D_WIDTH $D_HEIGHT  > ${TTY}
