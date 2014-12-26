#!/bin/sh
sbin=`dirname "$0"`
sbin=`cd "$sbin"; pwd`

. "$sbin/daemon.sh" start metadataserver