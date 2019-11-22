#!/bin/bash

if [ "$(hostname)" == "hihope-rzg2n" ] || [ "$(hostname)" == "hihope-rzg2m" ]; then
	export CAM_DEV="/dev/video5"
	media-ctl -d /dev/media0 -r
	media-ctl -d /dev/media0 -l "'rcar_csi2 fea80000.csi2':1 -> 'VIN5 output':0 [1]"
	media-ctl -d /dev/media0 -V "'rcar_csi2 fea80000.csi2':1 [fmt:UYVY8_2X8/1280x960 field:none]"
	media-ctl -d /dev/media0 -V "'ov5645 2-003c':0 [fmt:UYVY8_2X8/1280x960 field:none]"
elif [ "$(hostname)" == "ek874" ]; then
	export CAM_DEVICE="/dev/video0"
	media-ctl -d /dev/media0 -r
	media-ctl -d /dev/media0 -l "'rcar_csi2 feaa0000.csi2':1 -> 'VIN5 output':0 [1]"
	media-ctl -d /dev/media0 -V "'rcar_csi2 feaa0000.csi2':1 [fmt:UYVY8_2X8/1280x960 field:none]"
	media-ctl -d /dev/media0 -V "'ov5645 3-003c':0 [fmt:UYVY8_2X8/1280x960 field:none]"
else
	echo "Unsupported platform: $(hostname)"
fi
