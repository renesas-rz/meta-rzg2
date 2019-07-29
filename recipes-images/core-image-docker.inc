require include/docker-control.inc

# Additional tools for supporting Docker
IMAGE_INSTALL_append = "\
  ${@base_conditional("USE_DOCKER", "1", " docker docker-contrib ca-certificates ntpdate kernel-module-nf-conntrack-netlink ", "", d)} \
"
