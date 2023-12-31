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
To start a multiplayer match, you need to input the server's IP ("Host:") and its port ("Puerto:"). As I've mentioned previously, the server's default port is 5555. The match will start when two different players have joined the server.  
![image](https://github.com/mareyna356/AOOP-Final-Guess-Who/assets/116867368/f951baf2-8195-4781-b55b-481380b018d9)

The objective and rules of a multiplayer match are the same as in the traditional tabletop Guess Who. Each player takes turns asking each other questions about their characters in order to guess the opponent's character. The player that guesses the opponent's character correctly wins, but if the guess is wrong then the opponent wins.

Both players will have similar boards. The board will contain the characters to hide and reveal by clicking on them, the player's randomly-selected character that the opponent has to guess, a text field where the player can type questions for the opponent and answer the opponent's questions, a text field that shows the opponent's answers and questions, and the list of names with which to try to guess the opponent's character.  
![image](https://github.com/mareyna356/AOOP-Final-Guess-Who/assets/116867368/22f8337f-fa27-447a-98c1-2aba72565857)  
_The initial board._

The player that gets to ask the first question at the beginning of the match is the player that joined the server first. To ask a question, you have to type it in the text field below "Tu:" ("You:") and press the "Preguntar" ("Ask") button. When the opponent answers the question, the answer will appear in the text field below "Oponente:" ("Opponent:"), followed by the opponent's question. To answer the opponent's question, you have to type your answer in the same text field where you make your questions (the one below "Tu:") and press the "Responder" ("Answer") button; the "Responder" button is the same button as the "Preguntar" button, but its name changes depending on the action that you have to do at the time. Once you answer the opponent's question, you can make your next question the same way you did it the first time, and so on. Unlike in singleplayer, in multiplayer there is no limit to the amount of questions any player can ask.

In the bottom of the window, next to "Adivinación:" ("Guess:"), is the list of names used to guess the opponent's character. When you're ready to try to guess the opponent's character, select the desired name and press the "Adivina" ("Guess") button. The "Adivina" button is only enabled after answering the opponent's question but before making your own question; if you ask the opponent your next question, the button becomes disabled until you answer the opponent's next question, so everytime it's your turn you have to choose between making a guess and asking another question.

If you guess your opponent's character incorrectly, you will get a message informing you of that and revealing the correct character. Your opponent will also get a message informing them about your incorrect guess and revealing your character.  
![272111399-6af6f68b-2886-4584-ab23-cebb7b9dc00e](https://github.com/mareyna356/AOOP-Final-Guess-Who/assets/116867368/0a65276c-5c3e-4323-84df-cdde04999308)  
_Top (player guessing): You didn't guess it correctly! Their character was Anne._  
_Bottom (opponent): Your opponent guessed your character incorrectly! You win by default. Their character was Tom._

If you guess your opponent's character correctly, you will get a message informing you of that. Your opponent will also get a message informing them about your guess and revealing your character.  
![272111435-518fee4e-6caf-4c6f-a1d5-65557b555f85](https://github.com/mareyna356/AOOP-Final-Guess-Who/assets/116867368/a1bc77d0-eada-4736-aa90-d2b76c5c37dd)  
_Top (player guessing): You guessed correctly! You're the winner._  
_Bottom (opponent): Your opponent guessed your character correctly! Their character was Anita._

Just like in a singleplayer match, once the match is over you'll automatically return to the [Main Menu](#main-menu) and your results in the match are stored in the "Resultados.txt" file. The file contains your player name, the amount of questions you asked in the match and if you won or lost.
