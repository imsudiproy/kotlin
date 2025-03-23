#!/usr/bin/env bash

KONAN_TOOLCHAIN_VERSION=1
TARBALL_s390x=target-sysroot-$KONAN_TOOLCHAIN_VERSION-linux-s390x
OUT=`pwd`

if [ -z "$LINUX_S390X_SDK_INSTALL_DIR" ]; then
    echo "Using default Linux s390x SDK install location"
    export LINUX_S390X_SDK_INSTALL_DIR="/opt/s390x-sdk"
fi

sdk=s390x-linux-gnu
p=$LINUX_S390X_SDK_INSTALL_DIR/sysroots/$sdk/usr
echo "Packing SDK $sdk as $OUT/$TARBALL_s390x.tar.gz..."
tar -czvf $OUT/$TARBALL_s390x.tar.gz -C $p .
