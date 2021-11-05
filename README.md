# COSC2657-Android Development
#### Assignment 1 - Nguyen Phuoc Nhu Phuc


This Tic Tac Toe app has **4 activities**, including:
1. Menu Activity.
2. Register Activity.
3. Main Game Activity.
4. Settings Activity.

##### Menu Activity
This activity is the launcher screen of the app. From here, users can go to either click the button Start or Settings. If the user clicked on Start, they will be redirected to the Register Activity, else if the user clicked on Settings, they will be taken to the Settings Activity.

##### Register Activity
This is where the users register their name so that they can be used to denote player's turn in the game. There are a few scenarios for this activity:
1. If the player names are filled and a match was completed, they can be modified by going to Settings after the game ends.
2. If the player names' edit texts are left empty, they will be assigned as default "Player 1" and "Player 2". In this case, there are 2 sub-scenarios where the players choose to or not to edit their player names' in Settings. If they choose to edit their player names in Settings, they will not be asked to enter new names when they click Start on menu. Else, they will be asked to enter new names if they haven not modified player names in Settings.

##### Main Game Activity
The Main Game Acitivity has 2 buttons: Reset and Home. By clicking Reset, the entire board will be empty and the game will restart. By clicking Home, the users will be taken back to Menu. The name of each player is displayed on top of the Tic Tac Toe board to indicate the current player's turn.

##### Settings Activity
This activity provides users with options to modify:
1. Dark UI.
2. Color theme.
3. Edit Players' names.
4. Change language (English and Vietnamese).
5. Modify board grid (3x3, 4x4 or 5x5).
