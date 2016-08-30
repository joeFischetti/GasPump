# GasPump
# Copyright (c) 2016 Joe Fischetti

This software uses the jssc library created by @scream3r which can be found at 
https://github.com/scream3r/java-simple-serial-connector

I also make use of Taylor Hornby's "Password Hashing With PBKDF2" available from
http://crackstation.net/hashing-security.htm
For hashing and securely storing member passwords in the database.  Copyright info in src/PasswordHash.java

# SPBA Gas Pump and membership database

This is a membership and transaction database for the SPBA boat club in Saugerties NY.

The software has a basic sqlite database which stores all membership as well as transactional history.  Members have the ability to log in using either a drop down box or their (memorized) member ID number (generated when they get added to the database).  The physical gas pump on site has no serial interface, so the serial connection is only used for throwing a relay to power the pump on.  Meter readings are entered by the user and are stored in a table with a time stamp in the database file.  Transaction reports are automatically printed for the user.

The admin panel gives administrators the ability to add/remove/modify members, update gas prices based on membership status, as well as view and print transaction and disparity reports.
