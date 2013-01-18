#!/bin/bash
touch manifest.mf
echo -e "Manifest-Version: 1.0\nMain-Class: startMasterMind" > manifest.mf
sh compileme.sh
jar cmf manifest.mf MasterMind-PP1.jar *.class */*.class mastermind_gui/mastermind_templates/*.class 2>> mastermind_error.log
rm manifest.mf
if [ -e mastermind_error.log ] && [ $(wc -l mastermind_error.log | awk '{print $1}') -gt "0" ]; then
  echo "Es traten Fehler auf, weitere Infos in mastermind_error.log"
else
  echo "Jar-File wurde erstellt, führen Sie 'java -jar MasterMind-PP1.jar' aus um es zu starten"
fi
