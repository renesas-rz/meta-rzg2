#!/bin/bash

killall -9 -q RZ_GST-demo_encoded-review.sh
killall -9 -q RZ_GST-network-demo_transmit-video.sh
killall -9 -q RZ_GST-network-demo_video-wireless-streaming.sh

killall -9 -q sleep

killall -2 -q gst-launch-1.0
killall -9 -q Demo3D
killall -q -9 test-multicast2

sync
