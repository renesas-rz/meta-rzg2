#!/bin/bash

source /home/root/.bashrc
TTY=`getUser`

printf "\n\e[32m[Info]:\e[0m\n" > ${TTY}
printf "\tStreaming file /home/root/videos/h264-wvga-30.mp4\n\
\tvia WiFi Connection." > ${TTY}
printf "\n\e[31mNOTE:\n\
\t* Please make sure that you have set up board & \n\
\t  device for WiFi connection with the same router\n\
\t* Please Click on IP icon to show the path of streaming\n\
\t* Click on Exit icon to exit from running applications\e[0m\n\n" > ${TTY}

/home/root/IPShow/IPShow &
/home/root/RZ_scripts/test-multicast2 &

gst-launch-1.0 -e filesrc location=/home/root/videos/h264-hd-30.mp4 ! \
qtdemux ! h264parse ! omxh264dec no-copy=true ! vspfilter ! \
video/x-raw,format=NV12,width=640,height=480 ! omxh264enc \
target-bitrate=5242880 ! h264parse ! video/x-h264,\
stream-format=avc,alignment=au ! rtph264pay pt=96 name=pay0 \
config-interval=3 ! udpsink host=127.255.255.255 port=5000

killall -9 -q test-multicast2
killall -9 -q IPShow
