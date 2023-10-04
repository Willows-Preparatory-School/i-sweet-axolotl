@echo off
REM Stage all files
git add -A

REM Commit the changes with the commit message "coffee"
git commit -m "Automated GitHub Script Force Push"

REM Push the changes to the remote repository
git push origin
pause