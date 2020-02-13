#!/bin/sh

if ! command -v npm >/dev/null 2>/dev/null; then
	echo "ERROR: npm does not exist in Your system."
	exit 1
fi

if [ ! -d "./client" ]; then
	echo "ERROR: Folder client does not exist."
	exit 2
fi

cd ./client

if [ ! -d "./node_modules" ]; then
	npm install
	npm start
	exit 0
fi

read -p 'Do You need to rebuild client? (y/n)' user_choice
if [ "$user_choice" == "y" ]; then
	npm install
	npm start
	exit 0
else
	npm start
	exit 0
fi

