@echo off 
set list=account klant adres artikel
(for %%a in (%list%) do ( 
   echo[
   echo Inlezen testdata voor :  %%a
   echo[
   "C:\Program Files\MongoDB\Server\4.0\bin\mongoimport.exe" -d mattenshop -c %%a --drop --type csv --headerline --file mongodb_%%a_testdata.csv
))
