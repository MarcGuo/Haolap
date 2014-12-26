#!/usr/bin/env bash

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

cygwin=false
case "`uname`" in
   CYGWIN*) cygwin=true;;
esac

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin"/haolap-config.sh

print_usage(){
  echo "Usage: haolap [<options>] <command> [<args>]"
  echo "Options:"
  echo ""
  echo "Commands:"
  echo "Some commands take arguments. Pass no args or -h for usage."
  echo "  schemaserver         Run a HaoLap Schema Server node"
  echo "  jobserver            Run a HaoLap Job Server node"
  echo "  metadataserver       Run a HaoLap Meta Data Server node"
}
if [ $# = 0 ]; then
  print_usage;
  exit 1;
fi

# get arguments
COMMAND=$1
shift

if [ "$COMMAND" = "--help" ]; then
  print_usage
  exit 0
elif [ "$COMMAND" = "--version" ]; then
  echo "1.0.0"
  exit 0
elif [ "$COMMAND" = "--conf" ]; then
  HAOLAP_CONF_DIR=$1
  shift
  COMMAND=$1
fi

add_to_cp_if_exists() {
  if [ -d "$@" ]; then
    CLASSPATH=${CLASSPATH}:"$@"
  fi
}



if [ -f "${HAOLAP_CONF_DIR}/haolap-env.sh" ]; then
  . "${HAOLAP_CONF_DIR}/haolap-env.sh"
fi

if [ "$JAVA_HOME" = "" ]; then
  echo "JAVA_HOME is not set"
  exit 2
fi
if [ "$HADOOP_HOME" = "" ]; then
  echo "HADOOP_HOME is not set"
  exit 3
fi

JAVA=$JAVA_HOME/bin/java
JAVA_HEAP_MAX=-Xmx1000m


HADOOP_CONF_DIR="${HADOOP_CONF_DIR:-$HADOOP_HOME/conf}"
HADOOP_LIB_DIR="${HADOOP_LIB_DIR:-$HADOOP_HOME/lib}"
HAOLAP_LIB_DIR="${HAOLAP_LIB_DIR:-$HAOLAP_HOME/lib}"

CLASSPATH="${HAOLAP_CONF_DIR}:${HADOOP_CONF_DIR}"
CLASSPATH=${CLASSPATH}:$JAVA_HOME/lib/tools.jar


# needed for execution
if [ ! -f ${HAOLAP_LIB_DIR}/haolap-core*.jar ]; then
  echo "Missing HaoLap Execution Jar: ${HAOLAP_LIB}/haolap-core*.jar"
  exit 1;
fi



for f in ${HAOLAP_LIB_DIR}/*.jar; do
    CLASSPATH=${CLASSPATH}:$f;
done

for f in ${HADOOP_HOME}/*.jar; do
    CLASSPATH=${CLASSPATH}:$f;
done

#Not suit for hadoop version 2
if [ -d "$HADOOP_LIB_DIR" ]; then
    for f in ${HADOOP_LIB_DIR}/*.jar; do
        CLASSPATH=${CLASSPATH}:$f;
    done
fi

# supress the HADOOP_HOME warnings in 1.x.x
export HADOOP_HOME_WARN_SUPPRESS=true 


if [ "$HADOOP_CLASSPATH" != "" ]; then
  CLASSPATH="${CLASSPATH}:${HADOOP_CLASSPATH}"
fi


function append_path() {
  if [ -z "$1" ]; then
    echo $2
  else
    echo $1:$2
  fi
}


# Add user-specified CLASSPATH last
if [ "$HAOLAP_CLASSPATH" != "" ]; then
  CLASSPATH=${CLASSPATH}:${HAOLAP_CLASSPATH}
fi

# Add user-specified CLASSPATH prefix first
if [ "$HAOLAP_CLASSPATH_PREFIX" != "" ]; then
  CLASSPATH=${HAOLAP_CLASSPATH_PREFIX}:${CLASSPATH}
fi

# default log directory & file
if [ "$HAOLAP_LOG_DIR" = "" ]; then
  HAOLAP_LOG_DIR="$HAOLAP_HOME/logs"
fi
if [ "$HAOLAP_LOGFILE" = "" ]; then
  HAOLAP_LOGFILE='haolap.log'
fi
HAOLAP_OPTS="$HAOLAP_OPTS -Dhaolap.log.dir=$HAOLAP_LOG_DIR"
HAOLAP_OPTS="$HAOLAP_OPTS -Dhaolap.log.file=$HAOLAP_LOGFILE"
HAOLAP_OPTS="$HAOLAP_OPTS -Dhaolap.home.dir=$HAOLAP_HOME"
HAOLAP_OPTS="$HAOLAP_OPTS -Dhaolap.root.logger=${HAOLAP_ROOT_LOGGER:-INFO,console}"
# cygwin path translation
if $cygwin; then
  CLASSPATH=`cygpath -p -w "$CLASSPATH"`
  HAOLAP_HOME=`cygpath -d "$HAOLAP_HOME"`
  HAOLAP_LOG_DIR=`cygpath -d "$HAOLAP_LOG_DIR"`
fi
CLASS=""

PROCESS_NAME=""

if [ "$COMMAND" = "jobserver" ] ; then
  PROCESS_NAME="JobServer"
  CLASS="cn.edu.neu.cloudlab.haolap.application.JobServerLauncher"
elif [ "$COMMAND" = "metadataserver" ] ; then
  PROCESS_NAME="MetaDataServer"
  CLASS="cn.edu.neu.cloudlab.haolap.application.MetaDataServerLauncher"
elif [ "$COMMAND" = "schemaserver" ] ; then
  PROCESS_NAME="SchemaServer"
  CLASS="cn.edu.neu.cloudlab.haolap.application.SchemaServerLauncher"
else
  PROCESS_NAME=$COMMAND
  CLASS=$COMMAND
fi

if [ "${HOLAP_NOEXEC}" != "" ]; then
  "$JAVA" -Dproc_$PROCESS_NAME $JAVA_HEAP_MAX $HAOLAP_OPTS -classpath "$CLASSPATH" $CLASS
else
  exec "$JAVA" -Dproc_$PROCESS_NAME $JAVA_HEAP_MAX $HAOLAP_OPTS -classpath "$CLASSPATH" $CLASS
fi


