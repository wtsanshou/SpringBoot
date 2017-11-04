# BetGame Project

This application is to reads a comma delimited file containing bets, and generates a
report.

## Prerequisites

* Java8

## Objectives

* Write an application that reads a comma delimited file containing bets, and generates a
report, which is output to the console.
* The report provides totals for "Number of Bets", "Total Stakes" and "Total Payout".
* The data should be grouped by Selection Name and Currency, and sorted by Total Payout, taking the following currency exchange rate into account 1GBP = 1.14EUR.
* Source and format of the data, e.g. change from reading a csv file to perhaps requesting json data over HTTP
* The output format, e.g. from text printed to console to an XML file.
* The sorting and grouping strategy, e.g. grouped Selection Name, sorted by Total Stakes (EUR value)
* Exchange rate

## How to use

1. run `./gradlew build` to build a jar
2. run the Application (a jar was build in the folder `build/lib/`)
3. input a file path (e.g `app/testFiles/test1.txt`. assume the format of the file is legal)
4. input "exit" to end the program.
5. log is in the folder `app/logs/`
6. The main class is at `ie/dublin/ppb/betgame/BetGameApplication.java`
7. Download address is `https://github.com/wtsanshou/BetGame.git`

