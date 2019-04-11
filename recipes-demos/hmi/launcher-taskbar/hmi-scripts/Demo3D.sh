#!/bin/bash

source /home/root/.bashrc # get D_WIDTH, D_HEIGHT

TTY=`getUser`

printf "\n\e[32m[Info]:\e[0m\n" > ${TTY}
printf "\tRunning Demo3D Graphics demo\n" > ${TTY}

/home/root/Demo3D/Demo3D -width $D_WIDTH -height $D_HEIGHT &> /dev/null &
