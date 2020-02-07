@ECHO off

IF NOT EXIST .\target mvn clean compile exec:java

set /p user_choice=Do You need to rebuild server? (y/n):
if %user_choice%==y (
	mvn clean compile exec:java
	) else (
	mvn exec:java
	)

pause