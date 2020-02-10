@ECHO off

WHERE npm 1> nul 2> nul
IF %ERRORLEVEL% NEQ 0 (
	echo ERROR: "npm" does not exist at Your system.
	goto EXIT
	)

IF NOT EXIST .\client (
	echo ERROR: Folder "client" not found. 
	goto EXIT
	)

cd .\client

IF NOT EXIST .\node_modules (
	npm install
	npm start
	goto EXIT
	)

set /p user_choice=Do You need to rebuild client? (y/n):
if %user_choice%==y (
	npm install
	npm start
	) else (
	npm start
	)

:EXIT
cd ..