@ECHO off

IF NOT EXIST .\node_modules (
	npm install
	npm start
	)

set /p user_choice=Do You need to rebuild client? (y/n):
if %user_choice%==y (
	npm install
	npm start
	) else (
	npm start
	)

pause