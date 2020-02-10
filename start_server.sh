#!/bin/sh

if ! command -v mvn >/dev/null 2>/dev/null; then
	echo "ERROR: mvn does not exist in Your system."
	exit 1
fi

if [ ! -d "./server" ]; then
	echo "ERROR: Folder server does not exist."
	exit 2
fi

cd ./server

if [ ! -d "./target" ]; then
	mvn clean compile exec:java
	exit 0
fi

read -p 'Do You need to rebuild client? (y/n)' user_choice
if [ "$user_choice" == "y" ]; then
	mvn clean compile exec:java
	exit 0
else
	mvn exec:java
	exit 0
fi

