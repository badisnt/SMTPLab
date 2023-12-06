DAI lab Report: SMTP
=============

Description
----------
This repository is an implementation of the SMTP lab for the DAI course at HEIG-VD. It focuses on understanding and implementing the SMTP protocol at a fundamental level using the Socket API. 
The project aims to create a TCP client application in Java that interacts with an SMTP server to execute email pranks on a list of victims. The user inputs the email addresses, prank messages and other parameters. The program generates groups and sends a random prank email to a randomly generated group of email addresses.

Requirements
-----------
* **Needed Tools:** You need to have the following tools installed and functional to run this program
  - JDK (17.0 or higher)
  - Docker
  - Maven

* **Config Folder:** The "config.txt" file under "/config" provides some necessary information for the functioning of the program.
  -serverAddress: SMTP server port (localhost by default if using maildev)
  -serverPort: SMTP server port (1025 by default)
  -nb: number of groups to generate, i.e number of emails to send (5 by default)

* **Messages List**

The list of prank messages to send, found in the "messages.txt" file under "/config", should following this format, using *** as delimiters:
```
    Subject: <subject of the email>
    <body of the email>
    ***
```

* **Victims List**

The list of email addresses, found in the "victims.txt" file under "/config", should contain one email address per line.

Mock SMTP Server
-----------------------
If you want to experiment before sending pranks, you can use [MailDev](https://github.com/maildev/maildev) as a mock SMTP server for your tests.

Use docker to start the server:

    docker run -d -p 1080:1080 -p 1025:1025 maildev/maildev

This provides a Web interface on localhost:1080 and a SMTP server on localhost:1025.

Instructions
-----------
1. Clone the repository
2. If needed, change the parameters in the "config.txt" file and write your own email addresses and pranks in the "victims.txt" and "messages.txt" files (ChatGPT is useful for generating some unfunny pranks)
3. Use Maven to compile and run the java project by running the following commands in the same directory as the "pom.xml" file:
```
mvn clean package
cd target
java -jar sprankster-1.0.jar
``` 
4. If using MailDev, go to "localhost:1080" on your prefered browser and check that the prank emails have been sent.


Implementation
-------


