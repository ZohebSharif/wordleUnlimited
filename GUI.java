import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI {
    private JLabel[][] letterBoxes = new JLabel[5][5];
    private StringBuilder currentInput = new StringBuilder();
    private int currentRow = 0;

    public GUI() {
        JFrame frame = new JFrame("Wordle Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 500);

        // create 5x5 box
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 5, 5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                letterBoxes[i][j] = new JLabel("_", SwingConstants.CENTER);
                letterBoxes[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                letterBoxes[i][j].setFont(new Font("SansSerif", Font.BOLD, 24));
                letterBoxes[i][j].setPreferredSize(new Dimension(50, 50));
                inputPanel.add(letterBoxes[i][j]);
            }
        }

        // visualize keyboard
        JPanel keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridLayout(3, 1, 0, 0));

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
                rowPanel.add(keyLabel);
            }
            keyboardPanel.add(rowPanel);
        }

        // dropdown menu
        JComboBox<String> dropdown = new JComboBox<>(new String[]{"Placeholder Message"});
        dropdown.setVisible(false);

        // add panels to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(keyboardPanel, BorderLayout.CENTER);
        frame.add(dropdown, BorderLayout.SOUTH);

        // get keyboard input
        JPanel inputCapturePanel = new JPanel();
        inputCapturePanel.setFocusable(true);
        inputCapturePanel.requestFocusInWindow();

        // key listen event
        inputCapturePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = Character.toUpperCase(e.getKeyChar());

                if (Character.isLetter(c) && currentInput.length() < 5) {
                    currentInput.append(c);
                } else if (c == '\b' && currentInput.length() > 0) { // backspace event
                    currentInput.deleteCharAt(currentInput.length() - 1);
                } else if (c == '\n' && currentInput.length() == 5) { // drop rows
                    if (currentRow < 4) {
                        currentInput.setLength(0);
                        currentRow++;
                    } else {
                        dropdown.setVisible(true);
                        currentInput.setLength(0);
                        currentRow = 0;
                        clearBoard();
                    }
                }

                updateBoxes();
            }
        });

        frame.add(inputCapturePanel, BorderLayout.EAST);
        frame.setVisible(true);
        inputCapturePanel.requestFocusInWindow(); // Make sure you are tabbed in when typing.
    }

    private void updateBoxes() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == currentRow && j < currentInput.length()) {
                    letterBoxes[i][j].setText(String.valueOf(currentInput.charAt(j)));
                } else if (letterBoxes[i][j].getText().equals("_")) {
                    letterBoxes[i][j].setText("_"); // Show underscore when empty
                }
            }
        }
    }

    private void clearBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                letterBoxes[i][j].setText("_");
            }
        }
    }

    // Main method that will be used to run the GUI from WordleUnlimited
    public static void main(String[] args) {

        new GUI();
    }
}
