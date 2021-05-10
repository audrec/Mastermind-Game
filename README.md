# Mastermind-Game
This Mastermind game program is a game for the player to guess the correct number combinations within the requested
attempts.

# Installation

- This program retrieves the true random numbers combinations from Random.org by HTTPRequest. To use the API tools from this
  website, follow the belows steps:
1. Add the [Gson](https://github.com/google/gson), [Commons Codec](http://commons.apache.org/proper/commons-codec/),
   and [JSON](https://mvnrepository.com/artifact/org.json/json/20140107) libraries to your project for operation. This
   process may differ depending on the IDE being used.

   (Example path from Intellij: File -> Project Structure -> Module -> '' + '' -> Add library)
2. Get the personal API Key from [Random.org](https://www.random.org/). API Key is required for calling APIs from
   this website. In RandomNumFactory.java file, put your API Key in the below code block to successfully run
   this program:
    ```bash
    HTTPRequestNumGenerator rn = new HTTPRequestNumGenerator("WHERE_TO_PUT_YOUR_API_KEY");
    ```
# Features

- [Game Levels](#game-levels)

- [Verify and Collect User Input](#User-Input)

- [Code Structure](#code-structure)

- [Creative Extension](#creative-extension)

# Game Levels
When the game is launched, it will first ask the user to choose a game level from easy, medium, to hard (Case-sensitive).
Different instruction will be given based on the level user chose. There are three levels to choose from, their default values 
for each URL parameter is as follows:

| Level | Length | Minimum Number | Maximum Number |
| :---: | :---: | :---: | :---:|
| `easy` | 4 | 0 | 7 |
| `medium` | 7 | 0 | 8 |
| `hard` | 10 | 0 | 9 |

Invalid input for level choosing will show error message, and ask the user to choose the level again.
!["Error message when invalid level input entered"](https://github.com/audrec/Mastermind-Game/blob/31fe247ab8ce1814f45ed2cfc055d2585d6f9424/ProgramScreenshots/ErrorLevelInput.png)

# Verify and Collect User Input
If the input fits the target number's length, the program will check and give the result of both total correct numbers 
and correct positions guessed. The history of each prompt will be record in a list and display as a reference for the user.
Attempts will be deducted by one if the input was not the target numbers combinations. 

!["Sample input on the console"](https://github.com/audrec/Mastermind-Game/blob/31fe247ab8ce1814f45ed2cfc055d2585d6f9424/ProgramScreenshots/SampleInput.png)

If the target numbers was successfully guessed by the user, the program will exit with the message: "You won!".
!["When the user won the game"](https://github.com/audrec/Mastermind-Game/blob/31fe247ab8ce1814f45ed2cfc055d2585d6f9424/ProgramScreenshots/Won.png)

If the target numbers was not guessed by the user, and all attempts were used, program exit with answer showing up and 
indicate that user had lost.

!["When the user lost the game"](https://github.com/audrec/Mastermind-Game/blob/31fe247ab8ce1814f45ed2cfc055d2585d6f9424/ProgramScreenshots/Lost.png)

Invalid input such as alphabets, incorrect length input, digits with alphabets will be handled by error input check.
!["Error handling for invalid user input"](https://github.com/audrec/Mastermind-Game/blob/31fe247ab8ce1814f45ed2cfc055d2585d6f9424/ProgramScreenshots/errorNumbersInput.png)

# Code Structure
This program consists of three packages: gameGenerators, gameLevels, and exceptionHandlers.

- gameGenerators:

    1. GameLauncher.java: The main entry class of this program, which contains three core APIs for launching this 
       game. 
        1. `getLevelParam()` - get the URL parameters for each input of HTTPRequest to get random numbers combinations from Random.org website.
        2. `buildGame()` - convert the target numbers to character array format and put into a hash map for validate function. Print the correct instruction based on the level chosen.
        3. `playGame()` - prompt the user to input a valid number combination while lives left, verify the correct numbers and index, and show the guess history.
    2. GameLauncherUtils.java: The utils class for storing the operation of each API that GameLauncher.java class used for cleaner code.
    3. RandomNumFactory.java: For input the API key and call the HTTPRequest class to get random numbers generated by random.org.
    4. HTTPRequestNumGenerator.java: The operation of calling HTTP request to retrieve the data from the website random.org

- gameLevels:

    1. GameLevel.java: The abstract class of each game level class. It can be used to store abstract methods for extension function.
    2. EasyGame.java: The class that contains the getter to get each URL parameter for the easy mode game.
    3. MediumGame.java: The class that contains the getter to get each URL parameter for the medium mode game.
    4. HardGame.java: The class that contains the getter to get each URL parameter for the hard mode game.

- exceptionHandlers:

    1. InvalidMethodCallException.java: handle the invalid method call error and show the corresponding error message.
    2. InvalidResponseException.java: handle the invalid response and show the corresponding error message.

# Creative Extension

There's a secret code "iamgod" that will trigger the god mode that direct the user to win the game automatically.

In gameGenerators.GameLauncherUtils.java : 

```bash
boolean isSecrectCodeEntered(String input, char[] targetCharArray){
        // If user enters the secret code, switch the god mode on and win the game directly
        if (input.equals("iamgod")) {
            return true;
        }
        return false;
    }
```
