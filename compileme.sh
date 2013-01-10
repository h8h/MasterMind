#!/bin/bash
javac -encoding utf-8 *.java mastermind_core/*.java mastermind_save_load/*.java mastermind_gui/mastermind_templates/*.java mastermind_gui/*.java > mastermind_error.log 2>&1
echo "MasterMind wurde erfolgreich erstellt, Fehler werden in mastermind_error.log dokumentiert!"
