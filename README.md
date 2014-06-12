NLPTOSQL
========

This is the java based project which use ANTLR API in order to parser input query by user in natural language and create formal MySQL query which can be executed on standard MySQL database system.

REQUIRED APIs
=============

1. antlr-4.2.2-complete.jar
2. mysql-connector-java-5.1.18-bin.jar

Help to set up ANTLR 4 API
==========================
http://www.antlr.org/index.html. Here look for Quick Start section.


Setting up NLPTOSQL System
==========================

1. Compile ANTLR parser using antl-4.2.2-complete.jar.
2. This will result int severel generated java files
3. Copy those java files in the project folder
4. Compile all java files using JDK 
5. Run mainGUI module which the main interface of whole project.
