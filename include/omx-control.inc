## Additional configuration in OMX module, include
# USE_H264D_OMX for OMX Media Component H264 OMX Library
USE_H264D_OMX = "${@'1' if 'h264dec_lib' in '${DISTRO_FEATURES}' else '0'}"

# USE_H264E_OMX for OMX Media Component H264 Encoder Library
USE_H264E_OMX = "${@'1' if 'h264enc_lib' in '${DISTRO_FEATURES}' else '0'}"

# USE_H265D_OMX for OMX Media Component H265 OMX Library
USE_H265D_OMX = "${@'1' if 'h265dec_lib' in '${DISTRO_FEATURES}' else '0'}"

# OMX Video Decoder Flag list
LIST_OMX_VIDEO_DEC_FLAG = " \
    ${USE_H264D_OMX} \
    ${USE_H265D_OMX} \
"

# OMX Video Encoder Flag list
LIST_OMX_VIDEO_ENC_FLAG = " \
    ${USE_H264E_OMX} \
"

# USE_OMX_VIDEO_DEC for Video Decoder Common Libs
USE_VIDEO_DEC = "${@'1' if '1' in '${LIST_OMX_VIDEO_DEC_FLAG}' else '0'}"

# USE_OMX_VIDEO_DEC for Video Encoder Common Libs
USE_VIDEO_ENC = "${@'1' if '1' in '${LIST_OMX_VIDEO_ENC_FLAG}' else '0'}"

# USE_VIDEO_OMX for Enable/Disable OMX Video
USE_VIDEO_OMX = "${@'1' if '1' in '${USE_VIDEO_DEC} ${USE_VIDEO_ENC}' else '0'}"

# USE_OMX_COMMON for OMX Common Libs
USE_OMX_COMMON = "${@'1' if '1' in '${USE_VIDEO_OMX}' else '0'}"

# USE_OMX_USER_MODULE for OMX User Module
# Note) List of USE_OMX_COMMON = {USE_VIDEO_OMX}
USE_OMX_USER_MODULE = "${@'1' if '1' in '${USE_OMX_COMMON}' else '0'}"

USE_OMX_EVA_PKG = "${@'1' if 'use_eva_pkg' in '${DISTRO_FEATURES}' else '0'}"
