#!/bin/bash
touch manifest.mf
echo -e "Manifest-Version: 1.0\nMain-Class: startMasterMind" > manifest.mf
sh compileme.sh
jar cmf manifest.mf MasterMind-PP1.jar *.class */*.class mastermind_gui/mastermind_templates/*.class >> mastermind_error.log 2>&1
rm manifest.mf
echo "Jar-File wurde erstellt, führen Sie 'java -jar MasterMind-PP1.jar' aus um es zu starten"
echo "Fehler werden in mastermind_error.log dokumentiert!"
