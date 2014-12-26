#!/bin/sh

sbin=`dirname "$0"`
sbin=`cd "$sbin"; pwd`

. "$sbin/stop-jobserver.sh"
. "$sbin/stop-metadataserver.sh"
. "$sbin/stop-schemaserver.sh"
