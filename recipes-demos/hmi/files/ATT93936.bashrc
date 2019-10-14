getWestonWidth() {
  echo $(weston-info | grep -wB1 'current' | head -n 1 | awk '{print $2}')
}
#export -f getWestonWidth

getWestonHeight() {
  echo $(weston-info | grep -wB1 'current' | head -n 1 | awk '{print $5}')
}
#export -f getWestonHeight

getUser() {
	list_usr=$(who | grep tty | awk '{print $2}')
	TTY=${list_usr[0]}
	if [ -z $TTY ]; then
		list_usr=$(who | awk '{print $2}')
		TTY=${list_usr[0]}
	fi
	if [ -z $TTY ]; then
		TTY=/null
	fi
	echo /dev/$TTY
}
export D_WIDTH=`getWestonWidth`
export D_HEIGHT=`getWestonHeight`

./setup_ov5645.sh
