Welcome to ElfVille, the ultimate social network for house elves.

==================
Directory contents
==================

root
* src - all files in here are *.java files, needed to compile the software
* bin - this is where generated *.class files can go, and also *.jar files
* README.txt - this file!
* TEAM.txt - our team, and who did what
* TESTPLAN.txt - a description of our test plan
* .classpath, .project - files needed if you use Eclipse to build
* server.jar - a precompiled Server
* client.jar - a precompiled Client

src (and bin)
* elfville - the code for both the server and the client
* testcases - source for junit testing code

elville
* client - code for the client; includes views which are the high level "pages" that you see (views is legacy name from when we were using a cardlayout); and views in turn has the sub package "subcomponents" which are generally JPanel elements on the view jpanel "pages".
* protocol - These are the classes sent back and forth between the client and the server (all are serializable). This has the subpackage "models" which contains classes that contain information that is data (and not merely a signalt to the server (or client) of what to do with the received message). It also has the subpackage utils, which is contains classes for cryptography.
* server - code for the server. Includes the database classes (storing and retrieving information), the model classes (the data objects), and the controller classes (deal with incoming requests (and sending responses back)).

==================================================
Compiling and running the software from source code
===================================================

1. Download and install "Eclipse IDE for Java Developers" from "http://www.eclipse.org/downloads/", if you do not already have it.

2. Start Eclipse. Click File -> Import -> General -> Existing Projects into Workspace. Click Next.

3. For "Select root directory", select the directory in which you downloaded ElfVille. Select to import ElfVille. Click Finish.

4. Eclipse will generate and compile all the .class files for you. Right-click the project and select Export. Select Java -> JAR file -> Next. Select where you want to save the JAR file (name this one server.jar) and click Next. Click Next again.

5. Under "Main class:", select "Server." Click Finish.

6. To build the client jar, repeat step 4 (but name it client.jar), and for step 5, select "Client."

Running the server
==================

1. Open up a command prompt and "cd" to the ElfVille directory to use our provided JAR, or to the directory where you exported a server JAR if you built from source.

2. Execute `java -jar server.jar 8444 /path/to/elfville.db /path/to/imitation_sysadmin.der`, where `/path/to/elfville.db` is where you would like to load an existing database or store a new one, and `/path/to/imitation_sysadmin.der` is the "very long secret" that our sysadmin will have memorized as a password of sorts (for simplification purposes). If the file cannot be found, the server will create it for you, but be sure the file location is writable! The `8444` refers to the port to listen on.

3. Ensure port 8444 is available for your server, and open firewalls if needed. You should now have a server running on "localhost", port "8444."

Running a client
================

1. Open up a command prompt and "cd" to the ElfVille directory to use our provided JAR, or to the directory where you exported a client JAR if you built from source.

2. Execute `java -jar client.jar localhost 8444 /path/to/elfville.pub.der`. The first two arguments refer to the server you started in the previous section. The final argument, `/path/to/elfville.pub.der` is the location of the stored public key for the server. A Swing window will appear.

===============
Client Tutorial
===============

1. Once you have a Client running, type in a username and click Register.

2. You will be taken to the Central Board, a place where Elves may post messages, and "like" or "dislike" posts with the entire social network. Go ahead and enter a post title and message. Click Post. You should see your post appear on the bottom of the screen!

3. Click on Clan Directory in the top navigation bar. That is where elves can create Clans, which are groups of like-minded elves. Go ahead and create a clan of your own, and you will see it appear below.

4. When you create a clan, you are the leader. Click on the Clan you created to be taken to the Clan Board.

5. On the Clan Board screen, you will see the Clan name and description at the top. You will see the create post form on the left, a list of message in the center, and a list of clan members in the right. As leader of the clan, you can disband the clan by clicking Disband.

6. You can join clans created by other users. Fire up another Client and register as a different user. Create a clan as that user. As the first user you created, go to the Clan Directory and click on a clan that isn't yours. You will see a "Request to Join" button on their clan board. Click that.

7. If someone applies to your clan, you will see their name at the bottom of your clan board. You can then press Accept or Decline for that user.

8. To log out, simply close the Client window.

=====================================
General description of code structure
=====================================

The toplevel packages are divided into "client", "server", and "protocol."

"elfville.server" and "elfville.client" are separate packages which will be delivered to server and client, respectively. "elfville.protocol" is common to both the server and client; it consists of Request and Response classes that define what the client can request, and what the server will respond with.

Server
======

The server follows the model-view-controller paradigm. The "elfville.server.model" package is server objects such as Posts, Clans, and Elves. The "elfville.server.database" package provides an interface to save these models to the database.

The "elfville.server.controller" package is an interface to the models, as in typical MVC. Routes.java redirects all requests to the corresponding controllers and retunes the responses.

At a high-level, the server starts running in Server.java, and kicks off multiple Session object to handle each new socket connection.

Client
======

The client follows a similar MVC pattern. All the communication to the server takes place in SocketController class, the only needed controller. To handle button clicks and the like, some of the views implement ActionListener, which invoke the SocketController. The client is implemented in Java Swing.
