## Overview:
**Summary:** A password manager with graphical user interface, using AES 256 cryptography and SQLite storage.
**Author:** Giovani Luigi R. B.
**Programming language:** Java 1.8 (*Netbeans project*)

## Dependencies:

 - **Database lib. (included)**
	 - Included a library to access SQLite files.

 - **Security: (required for 256-bit keys)**
	 - As per Oracle's documentation:
		 > Due to import control restrictions by the governments of a few countries, the jurisdiction policy files shipped with the JDK 5.0 from Sun Microsystems specify that "strong" but limited cryptography may be used.
	 - To install policy files:
		 - Copy/Replace the following files:
			 - **"\policy_file\US_export_policy.jar"** and **"\policy_file\local_policy.jar"**
		 - to:
			 - **\${java.home}/jre/lib/security/** and/or  **\${java.home}/jdk/jre/lib/security/**
 
 ## WARNING!
The scope of the project is to provide an encryption mechanism for personal data (password and usernames) in the database.
The GUI may be vulnerable to an attack and will offer no protection for the data while it is loaded in RAM memory.
**Use at your own risk!**