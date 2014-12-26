#!/bin/sh

sbin=`dirname "$0"`
sbin=`cd "$sbin"; pwd`

. "$sbin/start-jobserver.sh"
. "$sbin/start-metadataserver.sh"
. "$sbin/start-schemaserver.sh"
