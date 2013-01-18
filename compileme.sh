#!/bin/bash
javac -target 1.6 -source 1.6 -encoding utf-8 *.java mastermind_core/*.java mastermind_save_load/*.java mastermind_gui/mastermind_templates/*.java mastermind_gui/*.java  -Xlint:-options 2> mastermind_error.log
if [ -e mastermind_error.log ] && [ $(wc -l mastermind_error.log | awk '{print $1}') -gt "0" ]; then
  echo "Es traten Fehler auf, weitere Infos in mastermind_error.log"
else
  echo "MasterMind wurde erfolgreich erstellt!"
fi
