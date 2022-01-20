This is a Yocto layer to build RZ/Five board

## Dependencies

This layer depends on:

* URI: git://github.com/openembedded/openembedded-core.git
  * branch: dunfell
  * revision: 44b1970c40e9d73f6e63fb10cdc55837a26f5921
* URI: git://github.com/openembedded/meta-openembedded.git
  * branch: dunfell
  * revision: 7889158dcd187546fc5e99fd81d0779cad3e8d17

* URI: git://github.com/openembedded/bitbake.git
* revision: 7889158dcd187546fc5e99fd81d0779cad3e8d17

## Quick Start

```text
git clone git://github.com/openembedded/meta-openembedded.git
cd meta-openembedded/
git checkout -f 7889158dcd187546fc5e99fd81d0779cad3e8d17
git clone git://github.com/openembedded/openembedded-core.git
cd openembedded-core/
git checkout -f 44b1970c40e9d73f6e63fb10cdc55837a26f5921

git clone git://github.com/openembedded/bitbake.git
cd bitbake
git checkout -f c0348de8121c3a842bf44906f7e2f79e93f7275b
cd ..
mv bitbake openembedded-core/

git clone https://github.com/renesas-rz/meta-rzg2-private.git -b develop/rzfive_init meta-rzfive

source openembedded-core/oe-init-build-env
cp ../docs/template/conf/rzfive-dev/* ./conf/
bitbake core-image-minimal
```


