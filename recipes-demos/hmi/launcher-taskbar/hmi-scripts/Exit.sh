#!/bin/bash

killall -9 -q RZ_GST-demo_encoded-review.sh
killall -9 -q glmark2-es2-wayland
killall -9 -q RZ_GST-network-demo_transmit-video.sh
killall -9 -q RZ_GST-network-demo_video-wireless-streaming.sh
killall -9 -q Video-Streaming-From-Device-Wifi.sh
killall -9 -q run_receive-movie.sh

killall -9 -q fhd_hw_sw_playback.sh
killall -9 -q hd_hw_sw_playback.sh
killall -9 -q record.sh
killall -9 -q sleep
killall -9 -q record_fps_show
killall -9 -q replay_fps_show
killall -9 -q ip_show

killall -2 -q gst-launch-1.0
killall -9 -q banner
killall -9 -q fps_out
killall -9 -q fps_show
killall -9 -q glmark2-es2-wayland
killall -9 -q Demo3D
killall -q -9 OGLES2Water
killall -q -9 test-multicast2

sync

rm -rf /mnt/mmcblk1p1/record.mp4 > /dev/null
