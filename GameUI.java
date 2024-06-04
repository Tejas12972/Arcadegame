/* Tejas B and Nischay S
 * GameUI file for RingFighter
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameUI {
    private JFrame frame;
    private JTextField nameField1;
    private JTextField nameField2;
    private Player player1;
    private Player player2;
    private Random random;

    public GameUI(JFrame frame) {
        System.out.println("Initializing GameUI...");
        this.frame = frame;
        this.random = new Random();
    }

    public void initialize() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Initializing UI components...");
            frame.getContentPane().removeAll();

            BackgroundPanel backgroundPanel = new BackgroundPanel("images/IMG_1209.png");
            backgroundPanel.setLayout(new GridBagLayout());

            JPanel panel = new JPanel();
            panel.setOpaque(false);
            panel.setLayout(new GridLayout(3, 2));

            JLabel nameLabel1 = new JLabel("Player 1, enter your name:");
            nameLabel1.setForeground(Color.BLACK);
            nameField1 = new JTextField(20);

            JLabel nameLabel2 = new JLabel("Player 2, enter your name:");
            nameLabel2.setForeground(Color.BLACK);
            nameField2 = new JTextField(20);

            JButton startButton = new JButton("Start Game");
            startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Start button clicked");
                    startGame();
                }
            });

            panel.add(nameLabel1);
            panel.add(nameField1);
            panel.add(nameLabel2);
            panel.add(nameField2);
            panel.add(new JLabel());
            panel.add(startButton);

            backgroundPanel.add(panel);
            frame.setContentPane(backgroundPanel);
            frame.revalidate();
            frame.repaint();
            System.out.println("UI components initialized.");
        });
    }

    private void startGame() {
        System.out.println("Starting game...");
        String name1 = nameField1.getText().trim();
        String name2 = nameField2.getText().trim();

        if (name1.isEmpty() || name2.isEmpty()) {
            System.out.println("Invalid names entered.");
            JOptionPane.showMessageDialog(frame, "Please enter valid names for both players.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        player1 = new Player(name1);
        player2 = new Player(name2);

        JOptionPane.showMessageDialog(frame, "Welcome " + name1 + " and " + name2 + "!\n" +
                "Player 1 has been assigned Character 1.\nPlayer 2 has been assigned Character 2.");

        performCoinFlip();
    }
    private void performCoinFlip() {
        System.out.println("Performing coin flip...");
        JFrame coinFlipFrame = new JFrame();
        coinFlipFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        coinFlipFrame.setSize(300, 300);
        coinFlipFrame.add(new JLabel(new ImageIcon("images/coin_flip.gif")));
        coinFlipFrame.setVisible(true);
    
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coinFlipFrame.dispose();
                int firstPlayer = random.nextInt(2);
                String playerName;
                if (firstPlayer == 0) {
                    playerName = player1.getName();
                } else {
                    playerName = player2.getName();
                }
                JOptionPane.showMessageDialog(frame, playerName + " goes first!");
                drawGamePanel(firstPlayer == 0);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    

    private void drawGamePanel(boolean isPlayer1First) {
        System.out.println("Drawing game panel...");
        JPanel gamePanel = Scene.drawGamePanel(player1, player2, isPlayer1First);
        frame.setContentPane(gamePanel);
        frame.revalidate();
        frame.repaint();
    }
}
