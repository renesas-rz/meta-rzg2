#!/bin/bash
source /home/root/.bashrc

TTY=`getUser`

printf "\n\e[32m[Info]:\e[0m\n" > ${TTY}
printf "\tRunning Glmask 3D Graphics demo\n" > ${TTY}
printf "\n\e[31mNOTE: Click on Exit icon to exit from running applications\e[0m\n\n" > ${TTY}

out_width=`expr $D_WIDTH`
out_height=`expr $D_HEIGHT`

OGLES3ColourGrading  &> /dev/null &

sleep 15s

killall -9 -q OGLES3ColourGrading &> /dev/null
sync
