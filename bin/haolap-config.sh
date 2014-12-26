#!/bin/sh
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

#
# processes --config option from command line
#

this="$0"
while [ -h "$this" ]; do
  ls=`ls -ld "$this"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    this="$link"
  else
    this=`dirname "$this"`/"$link"
  fi
done

# convert relative path to absolute path
bin=`dirname "$this"`
script=`basename "$this"`
bin=`cd "$bin"; pwd`
this="$bin/$script"

# the root of the HaoLap installation
if [[ -z $HAOLAP_HOME ]] ; then
  export HAOLAP_HOME=`dirname "$bin"`
fi

#check to see if the conf dir is given as an optional argument
while [ $# -gt 0 ]; do    # Until you run out of parameters . . .
  case "$1" in
    --conf)
        shift
        confdir=$1
        shift
        HAOLAP_CONF_DIR=$confdir
        ;;
    *)
        break;
        ;;
  esac
done


# Allow alternate conf dir location.
HAOLAP_CONF_DIR="${HAOLAP_CONF_DIR:-$HAOLAP_HOME/conf}"

export HAOLAP_CONF_DIR=$HAOLAP_CONF_DIR