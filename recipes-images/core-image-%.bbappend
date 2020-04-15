update_issue() {
    # Set BSP version
    BSP_VERSION="1.0.4-beta"

    # Set SoC and Board info
    case "${MACHINE}" in
    hihope-rzg2h)
      BSP_SOC="RZG2H"
      BSP_BOARD="HIHOPE-RZG2H"
      ;;
    hihope-rzg2m)
      BSP_SOC="RZG2M"
      BSP_BOARD="HIHOPE-RZG2M"
      ;;
    hihope-rzg2n)
      BSP_SOC="RZG2N"
      BSP_BOARD="HIHOPE-RZG2N"
      ;;
    ek874)
      BSP_SOC="RZG2E"
      BSP_BOARD="EK874"
      ;;
    esac

    # Make issue file
    echo "BSP: ${BSP_SOC}/${BSP_BOARD}/${BSP_VERSION}" >> ${IMAGE_ROOTFS}/etc/issue
    echo "LSI: ${BSP_SOC}" >> ${IMAGE_ROOTFS}/etc/issue
    echo "Version: ${BSP_VERSION}" >> ${IMAGE_ROOTFS}/etc/issue
}
ROOTFS_POSTPROCESS_COMMAND += "update_issue; "
