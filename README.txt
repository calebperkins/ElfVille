Welcome to ElfVille, the ultimate social network for house elves.

Directory contents
==================

* src - all files in here are *.java files, needed to compile the software
* bin - this is where generated *.class files can go, and also *.jar files
* README.txt - this file!
* TEAM.txt - our team, and who did what
* TESTPLAN.txt - a description of our test plan
* .classpath, .project - files needed if you use Eclipse to build
* server.jar - a precompiled Server
* client.jar - a precompiled Client

Compiling and running the software from source code
===================================================

1. Download and install "Eclipse IDE for Java Developers" from "http://www.eclipse.org/downloads/", if you do not already have it.

2. Start Eclipse. Click File -> Import -> General -> Existing Projects into Workspace. Click Next.

3. For "Select root directory", select the directory in which you downloaded ElfVille. Select to import ElfVille. Click Finish.

4. Eclipse will generate and compile all the .class files for you. Right-click the project and select Export. Select Java -> JAR file -> Next. Select where you want to save the JAR file and click Next. Click Next again.

5. Under "Main class:", select "Server." Click Finish.

6. To build the client jar, repeat step 4, and for step 5, select "Client."

Running the server
==================

1. Open up a command prompt and "cd" to the ElfVille directory to use our provided JAR, or to the directory where you exported a server JAR if you built from source.

2. Execute `java -jar server.jar /path/to/elfville.db`, where `/path/to/elfville.db` is where you would like to load an existing database or store a new one. If the file cannot be found, the server will create it for you, but be sure the file location is writable!

3. Ensure port 8444 is available for your server, and open firewalls if needed.

Running a client
================

1. Open up a command prompt and "cd" to the ElfVille directory to use our provided JAR, or to the directory where you exported a client JAR if you built from source.

2. Execute `java -jar client.jar`. A Swing window will appear.

Client Tutorial
===============

1. Once you have a Client running, type in a username and click Register. You will be taken to the Central Board, a place where Elves may post messages, and "like" or "dislike" posts.

2. FINISH ME....
