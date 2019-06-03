echo 'Start...............'
set PGPASSWORD=12345
set PSQL_HOME="%ProgramFiles%\PostgreSQL\10\bin\psql"
set DB_NAME=vskDB
%PSQL_HOME% -U postgres -c "drop database \"%DB_NAME%\";"
%PSQL_HOME% -U postgres -c "create database \"%DB_NAME%\";"
%PSQL_HOME% -U postgres -d %DB_NAME% -1 -f initscript.sql