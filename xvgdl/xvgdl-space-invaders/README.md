XVGDL SPACE INVADERS definition
Being a quite complex example, the classic Space Invaders videogame, has been used to demonstrate the use of XVGDL including concepts like projectiles and objects Artificial Intelligence.

**Requirements**
1. Java 11
2. Maven

**Generating the executable files**
To generate the jar file for executing the game, just run 
>`mvn clean package`

**Executing the game**
Once the jar file has been generated, it includes all necessary libraries to execute the game, simply do:
>`java -jar target/xvgdl-space-invaders-0.0.1-SNAPSHOT.jar` 

Being `java` the JVM pointing to a Java version 11.

This command will execute the pre-configured Space Invaders game.

**Executing a different XVGDL configuration for Space Invaders**
To run the game using a different configuration of the game, simply specify the game configuration as parameter as follows:
>`java -jar target/xvgdl-space-invaders-0.0.1-SNAPSHOT.jar <xvgdl-file-path>` 

