# Environment setup, support building kernel modules with kernel src in SDK
export KERNELSRC="$SDKTARGETSYSROOT/usr/src/kernel"
export KERNELDIR="$SDKTARGETSYSROOT/usr/src/kernel"
export HOST_EXTRACFLAGS="--sysroot=$OECORE_NATIVE_SYSROOT -isysroot $OECORE_NATIVE_SYSROOT"
