import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;


public class GUI {
    private JLabel[][] letterBoxes = new JLabel[5][5];
    private HashMap<Character, JLabel> keyboardKeys = new HashMap<>();
    private StringBuilder currentInput = new StringBuilder();
    private int currentRow = 0;
    private PlayGame game;
    private String targetWord;

    public GUI() {
        game = new PlayGame();
        targetWord = game.getRandomWord();

        JFrame frame = new JFrame("Wordle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 500);
        frame.setResizable(false);
        JPanel inputPanel = new JPanel(new GridLayout(5, 5, 5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {   
                letterBoxes[i][j] = new JLabel("_", SwingConstants.CENTER);
                letterBoxes[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                letterBoxes[i][j].setFont(new Font("SansSerif", Font.BOLD, 24));
                letterBoxes[i][j].setOpaque(true);
                letterBoxes[i][j].setBackground(Color.WHITE);
                inputPanel.add(letterBoxes[i][j]);
            }
        }

        JPanel keyboardPanel = new JPanel(new GridLayout(3, 1, 0, 0));
        String[] keyboardRows = {"QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM"};
        for (String row : keyboardRows) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
            for (char c : row.toCharArray()) {
                JLabel keyLabel = new JLabel(String.valueOf(c), SwingConstants.CENTER);
                keyLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
                keyLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                keyLabel.setOpaque(true);
                keyLabel.setBackground(Color.LIGHT_GRAY);
                keyLabel.setPreferredSize(new Dimension(35, 35));
                keyboardKeys.put(c, keyLabel);
                rowPanel.add(keyLabel);
            }
            keyboardPanel.add(rowPanel);
        }

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(keyboardPanel, BorderLayout.CENTER);
        
        // difficulty selection buttons
        //JPaneul mixed = new JPanel
        // listen for user input
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = Character.toUpperCase(e.getKeyChar());
                if (Character.isLetter(c) && currentInput.length() < 5) {
                    currentInput.append(c);
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && currentInput.length() > 0) {
                    currentInput.deleteCharAt(currentInput.length() - 1);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && currentInput.length() == 5) {
                    processWord();
                }
                // echo input to user diplay
                updateBoxes();
            }
        });

        frame.setFocusable(true);
        frame.setVisible(true);
        frame.requestFocus();
    }

    // echo input to display character by character
    private void updateBoxes() {
        for (int j = 0; j < 5; j++) {
            if (j < currentInput.length()) {
                letterBoxes[currentRow][j].setText(String.valueOf(currentInput.charAt(j)));
            } else {
                letterBoxes[currentRow][j].setText("_");
            }
        }
    }

// processes user input when user presses "Enter"
    private void processWord() {

        // takes user input, casts to string
        String guessedWord = currentInput.toString();

        // ensures guess is valid
        if (!game.validateGuess(guessedWord)) {
            JOptionPane.showMessageDialog(null, "Invalid word! Try again.");
            return;
        }
        colorChange();
        if (guessedWord.equals(targetWord)) {
            JOptionPane.showMessageDialog(null, "Congratulations! You guessed the word.");
            resetGame();
        } else if (currentRow < 4) {
            currentInput.setLength(0);
            currentRow++;
        } else {
            JOptionPane.showMessageDialog(null, "Game Over! The word was: " + targetWord);
            resetGame();
        }
    }
    // function that changes letter coloring to indicate correctness or otherwise
    
    private void colorChange() {
        for (int j = 0; j < 5; j++) {
            char inputChar = currentInput.charAt(j);
            char targetChar = targetWord.charAt(j);
            JLabel keyLabel = keyboardKeys.get(inputChar);

            if (inputChar == targetChar) {
                letterBoxes[currentRow][j].setBackground(Color.GREEN);
                if (keyLabel != null) keyLabel.setBackground(Color.GREEN);
            } else if (targetWord.contains(String.valueOf(inputChar))) {
                letterBoxes[currentRow][j].setBackground(Color.YELLOW);
                if (keyLabel != null && keyLabel.getBackground() != Color.GREEN) {
                    keyLabel.setBackground(Color.YELLOW);
                }
            } else {
                letterBoxes[currentRow][j].setBackground(Color.GRAY);
                if (keyLabel != null) keyLabel.setBackground(Color.GRAY);
            }
        }
    }
    // resets game, changes random word, clears display, reset visual keys
    private void resetGame() {
        currentRow = 0;
        currentInput.setLength(0);
        targetWord = game.getRandomWord();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                letterBoxes[i][j].setText("_");
                letterBoxes[i][j].setBackground(Color.WHITE);
            }
        }
        for (JLabel keyLabel : keyboardKeys.values()) {
            keyLabel.setBackground(Color.LIGHT_GRAY);
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}