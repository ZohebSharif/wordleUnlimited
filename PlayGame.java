import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class PlayGame {

    private final String filePath = "valid-wordle-words.txt";

    // generate a random number from 1 - 14855
    // whatever number is generated, go to file and get
    // the line associated with the number

    public String getRandomWord() {
        Random random = new Random();
        int num = random.nextInt(14855);
        return getLine(num);
    }

    // extract a 5 letter word from file
    // assumes file has one 5 letter word per line
    private String getLine(int randomNum) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            for (int i = 0; i <= randomNum; i++) {
                line = reader.readLine();
            }
            return line.toUpperCase(); // make words match 
        } catch (IOException e) {
            e.printStackTrace();
            return "APPLE"; 
        }
    }

    // read from file using a buffered reader
    // checks if given word is in file
    public boolean validateGuess(String word) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
