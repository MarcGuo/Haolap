#!/bin/sh

action=$1;
shift
app=$1;
shift
hadoop_rotate_log ()
{
    log=$1;
    num=5;
    if [ -n "$2" ]; then
	num=$2
    fi
    if [ -f "$log" ]; then # rotate logs
	while [ $num -gt 1 ]; do
	    prev=`expr $num - 1`
	    [ -f "$log.$prev" ] && mv "$log.$prev" "$log.$num"
	    num=$prev
	done
	mv "$log" "$log.$num";
    fi
}

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin/haolap-config.sh"
if [ -f "$HAOLAP_CONF_DIR/haolap-env.sh" ]; then
  . "${HAOLAP_CONF_DIR}/haolap-env.sh"
fi

HAOLAP_HOME=${HAOLAP_HOME};

if [ "$HAOLAP_HOME" = "" ]; then
  HAOLAP_HOME=`dirname ${bin}`
fi

export HAOLAP_PID_DIR=${HAOLAP_PID_DIR:-/tmp}
export HAOLAP_LOG_DIR=${HAOLAP_LOG_DIR:-$HAOLAP_HOME/logs}
export HAOLAP_IDENT_STRING=$USER

mkdir -p "$HAOLAP_LOG_DIR"
touch $HAOLAP_LOG_DIR/.hadoop_test > /dev/null 2>&1
TEST_LOG_DIR=$?
if [ "${TEST_LOG_DIR}" = "0" ]; then
  rm -f $HAOLAP_LOG_DIR/.hadoop_test
else
  chown $HAOLAP_IDENT_STRING:$HAOLAP_IDENT_STRING $HAOLAP_HOME
fi


# some variables
export HAOLAP_LOGFILE=hadoop-$HAOLAP_IDENT_STRING-$app-$HOSTNAME.log
export HAOLAP_ROOT_LOGGER="INFO,console"
log=$HAOLAP_LOG_DIR/hadoop-$HAOLAP_IDENT_STRING-$app-$HOSTNAME.out
pid=$HAOLAP_PID_DIR/hadoop-$HAOLAP_IDENT_STRING-$app.pid

case $action in

  (start)

    mkdir -p "$HAOLAP_PID_DIR"

    if [ -f $pid ]; then
      if kill -0 `cat $pid` > /dev/null 2>&1; then
        echo $command running as process `cat $pid`.  Stop it first.
        exit 1
      fi
    fi

    hadoop_rotate_log $log
    echo starting $app, logging to $log
    $HAOLAP_HOME/bin/haolap.sh "$app" > "$log" 2>&1 < /dev/null &
    echo $! >$pid
    echo "$app is started"
    sleep 1; head "$log"
    ;;

  (stop)

    if [ -f $pid ]; then
      if kill -9 `cat $pid` > /dev/null 2>&1; then
        echo stopping $app
        kill `cat $pid`
        #rm $pid
      else
        echo no $app to stop
      fi
    else
      echo no $app to stop
    fi
    ;;

  (*)
    exit 1
    ;;
esac
