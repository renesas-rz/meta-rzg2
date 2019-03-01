#!/bin/bash
source /home/root/.bashrc

TTY=$(grep "\/bin\/start_getty" /etc/inittab | grep 'ttyS' | awk '{ print $3 }')
TTY=/dev/${TTY}

printf "\n\e[32m[Info]:\e[0m\n" > ${TTY}
printf "\tYou are watching Video playback on screen\n\
\twith default position (0, 0) & Video file\n\
\t(/home/root/videos/h264-fhd-60.mp4)\n" > ${TTY}
printf "\n\e[31mNOTE: Click on Exit icon to exit from running applications\e[0m\n\n" > ${TTY}

#detect the width to set the value of scale on waylandsink
D_WIDTH=$(weston-info | grep -B 1 "current"| grep "width" | cut -d" " -f2 | head -1)

if [ $D_WIDTH -le 799 ]; then 
   D_SCALE=0.25               # screen LVDS:480x272
elif [ $D_WIDTH -le 1200 ]; then
   D_SCALE=0.5                # screen LVDS:800x480
else
   D_SCALE=1                  # screen HDMI
fi

/home/root/RZ_scripts/RZ_GST-demo_video-playback.sh /home/root/videos/h264-fhd-60.mp4 0 0 $D_SCALE  > ${TTY}
