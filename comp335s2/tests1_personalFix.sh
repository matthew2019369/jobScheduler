#!/bin/bash
# to kill multiple runaway processes, use 'pkill runaway_process_name'
configDir="./configs"
diffLog="stage1diff.log"
if [ ! -d $configDir ]; then
	echo "No $configDir found!"
	exit
fi

if [ -f $configDir/$diffLog ]; then
	rm $configDir/*.log
fi

if [ $# -lt 1 ]; then
	echo "Usage: $0 your_client [-n]"
	exit
fi

if [ ! -f $1 ]; then
	echo "No $1 found!"
	echo "Usage: $0 your_client [newline]"
	exit
fi

if [ -f $configDir/$diffLog ]; then
	rm $configDir/$diffLog
fi

trap "kill 0" EXIT

for conf in $configDir/*.xml; do
	echo "$conf"
	echo "running the reference implementation (./ds-client)..."
	sleep 1
	if [ -z $2 ]; then
		./ds-server -c $conf -v all > $conf.ref.log&
		sleep 1
		./ds-client
	else
		./ds-server -c $conf -v all > $conf.ref.log&
		sleep 1
		./ds-client -a ff
	fi

	echo "running your implementation ($1)..."
	sleep 2

	./ds-server -c $conf -v all > $conf.your.log&


	sleep 1
	if [ -f $1 ] && [[ $1 == *".class" ]]; then
		if [ -z $2 ]; then
		java $(sed 's/\.class//' <<< $1)
		else
		java $(sed 's/\.class//' <<< $1) -a $2
		fi

	else
		./$1
	fi
	sleep 1
	diff $conf.ref.log $conf.your.log > $configDir/temp.log
	if [ -s $configDir/temp.log ]; then
		echo NOT PASSED!
	else
		echo PASSED!
	fi
	echo ============
	sleep 1
	cat $configDir/temp.log >> $configDir/$diffLog
done

echo "testing done! check $configDir/$diffLog"
