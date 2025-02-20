 // Class that will handle file parsing and gameplay logic.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class PlayGame {

    private String currWord;
    private String currGuess;
    private final String filePath = "valid-wordle-words.txt";
    private int chances;
    
    public void play()
    {
        // Get word

        // Give 5 chances

        // Prompt guesses

        // Parse input strings

        // Perform a binary search on all inputted String types to ensure the inputted word is valid.

        chances = 5;
        setWord();
        while(chances > 0)
        {
            System.out.println("GUESS!!!!!!!!");
            
            fishGuess();
          
        for (int i = 0; i < 5; i++) {
    if (currGuess.charAt(i) == currWord.charAt(i)) {
        System.out.print("C");
    } else if (currWord.indexOf(currGuess.charAt(i)) != -1) {
        System.out.print("O"); 
    } else {
        System.out.print("X");
    }
}

            
            if(currWord.equals(currGuess))
            {
                System.out.println("\n\nYou guessed correctly! The word was: " + currWord);
                break;
            }
            

            chances--;

            if (chances == 0) {
                System.out.println("\nYou lost! The word was: " + currWord);
            } else {
                System.out.println("\nYou have " + chances + " chances left.");
            }
        }



    }
   
    public void setWord()
    {
        Random random = new Random();
        int num = random.nextInt(14855);
        currWord = getLine(num);
    }

    public String getLine(int randomNum)
    {
     try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
     {
            String line = null;
            for (int i = 0; i <= randomNum; i++)
             {
                // Read until the target line
                line = reader.readLine();
            }
            return line;
        } 
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean validateGuess(String word) {
        int left = 0;
        int right = 14855;
    
        while (left <= right) {
            int mid = left + (right - left) / 2;  // Move inside loop
            String midWord = getLine(mid);
    
            if (midWord.equals(word)) {
                return true;
            } else if (midWord.compareTo(word) > 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
    
        return false;
    }
    

void fishGuess() {
   
    Scanner scan = new Scanner(System.in);
    while (true) {
        String r = "";
        if(scan.hasNextLine())
        r = scan.nextLine().trim().toLowerCase();    
        
         else
         {
           // System.out.println("No more input available.");
            break;
        }
        
        // Case: invalid length
        if (r.length() != 5) {
            System.out.println("Incorrect length, try again.");
        }
        // Case: invalid guess
        else if (!validateGuess(r)) {
            System.out.println("Guess not found in list of words, try again.");
        }
        // Case: valid guess
        else {
            currGuess = r;
            if(chances == 0)
           scan.close();
            break;
        }
    }
}

}
