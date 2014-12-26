#!/bin/sh

#!/bin/sh
# resolve links - $0 may be a softlink
this="${BASH_SOURCE-$0}"
common_bin=$(cd -P -- "$(dirname -- "$this")" && pwd -P)
script="$(basename -- "$this")"
this="$common_bin/$script"

# convert relative path to absolute path
config_bin=`dirname "$this"`
script=`basename "$this"`
config_bin=`cd "$config_bin"; pwd`
this="$config_bin/$script"

export HAOLAP_PREFIX=`dirname "$this"`/..
export HAOLAP_HOME=${HAOLAP_PREFIX}
export HAOLAP_LOG_DIR=$HAOLAP_HOME/logs
export HAOLAP_PID_DIR=/tmp
#export HOLAP_OPTS=""
export HAOLAP_LIB_DIR=${HAOLAP_HOME}/lib
export HAOLAP_DEBUG_MODE=false
export HADOOP_DEBUG_MODE=false

export HADOOP_HOME=/Users/marc/software/hadoop-1.0.4
export HADOOP_LIB_DIR=${HADOOP_HOME}/lib
export HADOOP_CONF_DIR=${HADOOP_HOME}/conf
# export HAOLAP_LIBRARY_PATH=""