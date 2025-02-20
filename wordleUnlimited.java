import java.util.Scanner;
public class WordleUnlimited{
    public static void main (String[] args)
    {
        // Will start off as a console application

        // A word is selected at random

        // A gameplay loop will start.

        // We get a word from the txt file at random.

        // * Guesses will be taken 5 letters at a time and those guesses have to be found within the file, otherwise it is an invalid word

        // In console, correct guesses will be denoted as 'C'. Existing characters will be 'E', and incorrect characters will be 'X'/

        // At the end of the game, we will prompt user if they would like to continue.
        
        // Main function will be the gameplay loop. The only things I want in here is a loop that keeps the game running. 

        // Create instance of game
      //new GUI();
        PlayGame game = new PlayGame();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello >.< !!");
       // System.out.println("A 5 letter word will be chosen at random\nYou will get 5 chances to guess the word correctly");
       // System.out.println("\n'X' Denotes incorrect\n'C' Denotes Correct\n'E' denotes an existing character\n\n Have fun!");

        boolean isPlaying = true;
        
        // Loop until user doesn't want to play.
        while(isPlaying)
        {
            game.play();
            System.out.println("Would you like to continue playing?(y/n)\n");
            String r = scanner.nextLine().trim().toLowerCase();
            if(r.equals("n"))
            {
                isPlaying = false;
            }
            
            
        }
        scanner.close();
        System.out.println("Thanks for playing!");

    }
}