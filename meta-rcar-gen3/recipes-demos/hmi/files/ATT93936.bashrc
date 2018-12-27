getWestonWidth() {
  echo $(weston-info | grep -wB1 'current' | head -n 1 | awk '{print $2}')
}
#export -f getWestonWidth

getWestonHeight() {
  echo $(weston-info | grep -wB1 'current' | head -n 1 | awk '{print $5}')
}
#export -f getWestonHeight

export D_WIDTH=`getWestonWidth`
export D_HEIGHT=`getWestonHeight`
export CAM_DEV=/dev/video0
