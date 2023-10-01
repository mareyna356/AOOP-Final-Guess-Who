## README under construction

# AOOP-Final-Guess-Who
My final exam for my Advanced Object Oriented Programming course during my 3rd semester at the Autonomous University of Baja California (August - December, 2019): a Guess Who (in Spanish: Adivina Quién) with singleplayer and online multiplayer. Made with Java in Apache NetBeans. The game utilizes Swing, Threads, Sockets, and the Model-View-Controller design pattern.

The [AdivinaQuien](AdivinaQuien) folder contains the game itself, while the [Server](Server) folder contains the server that sets up and starts the matches. The Server needs to be running to be able to play. I utilize Apache NetBeans to execute the server and the game.

## Server
To run the server, execute [Server.java](Server/src/server/Server.java). The server doesn't contain any sort of UI nor requires any form of interaction from the user, it simply prints on console several of the actions it's executing. In a singleplayer match, where the user is playing against the server itself, the server prints on console the name of the character the user has to guess. By default, the server's port is ***5555***, but this can be changed in the main method.

The server will print "Esperando cliente." ("Waiting for client.") on console when it's ready to start games.

## AdivinaQuien (GuessWho)
To run the game, execute [Menu.java](AdivinaQuien/src/adivinaquien/Menu.java).

### Main Menu
When the game executes, you will see the main menu. Here, you can input your name and select the type of match to start: a singleplayer/PvE match against the server ("vs Bot" button) or a multiplayer/PvP match against another user ("Unirse a Server" button, Spanish for "Join Server").  
![image](https://github.com/mareyna356/AOOP-Final-Guess-Who/assets/116867368/5440147d-c59e-4a89-bce7-c8bda5af2251)

### Singleplayer/PvE
When starting a singleplayer match, the game automatically tries to connect to the local server through the server's default port: ***5555***. If you previously changed the server's port in [Server.java](Server/src/server/Server.java), then you'll need to specify that same port in [AdivinaQuien.java](AdivinaQuien/src/adivinaquien/AdivinaQuien.java) in the **connectLocal(javax.swing.JLabel jLabel)** method.

In a singleplayer game, the objective is to guess the server's character. Each character in the board can be pressed to hide it, and pressed again to reveal the character.  
![image](https://github.com/mareyna356/AOOP-Final-Guess-Who/assets/116867368/ebd22749-9c88-4a7d-9d0a-6fa48a120331)  
_The initial board._

You have to select a question from a predefined list of questions on the left side of the window, under "Tu:" ("You:"), and press the "Preguntar" ("Ask") button in order to ask the server the question. The server will always answer with a "Sí" ("Yes") or "No"; the asked question and the server's answer will be displayed in the text box on the right side of the window, under "Oponente:" ("Opponent:"). The asked question is automatically removed from the list of questions.

You can try to guess a character at any point in the match by selecting the name you want to guess in the "Adivinación" ("Guess") list in the bottom of the window and pressing "Adivina" ("Guess"). However, you only have one attempt; if you guess the wrong character, you lose. You can only ask the server a maximum of 6 questions; once you ask the 6th question, the game won't allow you to ask any more questions and you will be forced to guess a character. You can keep track of how many questions you've asked below the "Preguntar" button.  
![image](https://github.com/mareyna356/AOOP-Final-Guess-Who/assets/116867368/61b65789-6a6e-4b20-ab70-c16d7d2120a2)  
_The board mid match, with 2 questions asked ("Haz hecho 2 preguntas"; "You've asked 2 questions") and most characters hidden._

If you guess the character correctly, you will get a congratulatory message.  
![image](https://github.com/mareyna356/AOOP-Final-Guess-Who/assets/116867368/9e1833d3-7802-4671-b5c6-40d5b1b96d31)  
_"You guessed it!!!!"_

If you guess the wrong character, you will get a defeat message that reveals the correct character.  
![image](https://github.com/mareyna356/AOOP-Final-Guess-Who/assets/116867368/99cac891-a08c-4ed3-a05b-4775c13f5a02)  
_"You were wrong, my character is Eric"_

Once the match is over, you'll automatically return to the [Main Menu](#main-menu). The results of the match are stored in a file called "Resultados.txt" ("Results.txt") in the [AdivinaQuien](AdivinaQuien) folder. The file contains the name of the player, the amount of questions asked in the match and if the player won or lost.

### Multiplayer/PvP
