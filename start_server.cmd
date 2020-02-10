@ECHO off

WHERE mvn 1> nul 2> nul
IF %ERRORLEVEL% NEQ 0 (
	echo ERROR: maven does not exist at Your system.
	goto EXIT
	)

IF NOT EXIST .\server (
	echo ERROR: Folder "server" not found. 
	goto EXIT
	)
	
cd .\server

IF NOT EXIST .\target (
	mvn clean compile exec:java
	goto EXIT
	)

set /p user_choice=Do You need to rebuild server? (y/n):
if %user_choice%==y (
	mvn clean compile exec:java
	) else (
	mvn exec:java
	)

:EXIT
cd ..