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

* URI:  https://github.com/riscv/meta-riscv.git 
  * branch: dunfell
  * revision: 6f039004c2fa2be041c73d6ff711c86ae139faca

## Quick Start

```text
git clone https://github.com/openembedded/meta-openembedded.git
cd meta-openembedded/
git checkout -f 7889158dcd187546fc5e99fd81d0779cad3e8d17
cd ../

git clone https://github.com/openembedded/openembedded-core.git
cd openembedded-core/
git checkout -f 44b1970c40e9d73f6e63fb10cdc55837a26f5921
cd ../

git clone https://github.com/openembedded/bitbake.git
cd bitbake
git checkout -f c0348de8121c3a842bf44906f7e2f79e93f7275b
cd ../
mv bitbake openembedded-core/

git clone https://github.com/riscv/meta-riscv.git -b dunfell
cd meta-riscv
git checkout -f 6f039004c2fa2be041c73d6ff711c86ae139faca 
cd ../

# Checkout meta-rzfive folder from meta-rzg2 repo.
# User can select the corresponding tag, for example "rzfive_v0.8".
# It's better to select the latest tag. Or just use the latest commitID from branch if you don't checkout tag.
git clone https://github.com/renesas-rz/meta-rzg2.git -b dunfell/rzfive meta-rzfive
git checkout <tag>

source openembedded-core/oe-init-build-env
cp ../meta-rzfive/docs/template/conf/smarc-rzfive/* ./conf/
bitbake core-image-minimal
```


