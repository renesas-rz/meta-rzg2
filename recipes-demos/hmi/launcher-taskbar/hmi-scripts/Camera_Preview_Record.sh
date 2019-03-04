#!/bin/bash
source /home/root/.bashrc

TTY=`getUser`

printf "\n\e[32m[Info]:\e[0m\n" > ${TTY}
printf "\tYou are running camera preview" > ${TTY}
printf "\n\e[31mNOTE: Click on Exit icon to exit from running applications\e[0m\n\n" > ${TTY}

/home/root/RZ_scripts/RZ_GST-demo_encoded-review.sh  &> /dev/null
