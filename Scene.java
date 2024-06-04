/* Tejas B and Nischay S
 * Scene.java file for RingFighter
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.File;

public class Scene extends JPanel {
    private Image background;
    private Image character1;
    private Image character2;
    private Player player1;
    private Player player2;
    private Random random;
    private JButton attackButton1;
    private JButton defendButton1;
    private JButton attackButton2;
    private JButton defendButton2;
    private boolean isPlayer1Turn;
    private String player1Action;
    private String player2Action;
    private int character1X;
    private int character2X;
    private boolean highlightCharacter1;
    private boolean highlightCharacter2;
    private Color highlightColor1;
    private Color highlightColor2;

    public Scene(Player player1, Player player2, boolean isPlayer1First) {
        System.out.println("Initializing Scene...");
        this.player1 = player1;
        this.player2 = player2;
        this.random = new Random();
        this.isPlayer1Turn = isPlayer1First;
        this.player1Action = null;
        this.player2Action = null;
        this.character1X = 50;
        this.character2X = 600; // Adjust for initial load
        this.highlightCharacter1 = false;
        this.highlightCharacter2 = false;
        setLayout(null);

        loadImages();
        createButtons();
        updateButtonVisibility();
        playBackgroundMusic("images/audio/introMUSIC.wav");
    }

    private void loadImages() {
        System.out.println("Loading images...");
        this.background = Toolkit.getDefaultToolkit().getImage("images/1023.jpg");
        this.character1 = Toolkit.getDefaultToolkit().getImage("images/character1.png");
        this.character2 = Toolkit.getDefaultToolkit().getImage("images/character2.png");
    }

    private void createButtons() {
        System.out.println("Creating buttons...");
        attackButton1 = new JButton("Attack");
        defendButton1 = new JButton("Defend");
        attackButton1.setBounds(10, 500, 100, 30);
        defendButton1.setBounds(120, 500, 100, 30);
        add(attackButton1);
        add(defendButton1);

        attackButton2 = new JButton("Attack");
        defendButton2 = new JButton("Defend");
        attackButton2.setBounds(670, 500, 100, 30);
        defendButton2.setBounds(780, 500, 100, 30);
        add(attackButton2);
        add(defendButton2);

        setButtonActions();
    }

    private void setButtonActions() {
        System.out.println("Setting button actions...");
        attackButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Player 1 Attack button clicked");
                player1Action = "attack";
                handlePlayerAction();
            }
        });

        defendButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Player 1 Defend button clicked");
                player1Action = "defend";
                handlePlayerAction();
            }
        });

        attackButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Player 2 Attack button clicked");
                player2Action = "attack";
                handlePlayerAction();
            }
        });

        defendButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Player 2 Defend button clicked");
                player2Action = "defend";
                handlePlayerAction();
            }
        });
    }

    private void handlePlayerAction() {
        if (isPlayer1Turn && player1Action != null) {
            System.out.println("Player 1 action: " + player1Action);
            isPlayer1Turn = false;
            updateButtonVisibility();
        } else if (!isPlayer1Turn && player2Action != null) {
            System.out.println("Player 2 action: " + player2Action);
            resolveActions();
            player1Action = null;
            player2Action = null;
            isPlayer1Turn = true;
            updateButtonVisibility();
        }
    }

    private void resolveActions() {
        System.out.println("Resolving actions...");
        if (player1Action != null) {
            executeAction(player1, player1Action, player2);
        }
        if (player2.getHealth() > 0 && player2Action != null) { // Only execute Player 2's action if they're not dead
            executeAction(player2, player2Action, player1);
        }
        repaint();
    }

    private void executeAction(Player player, String action, Player opponent) {
        if (action == null) return;

        if (action.equals("attack")) {
            performAttack(player, opponent);
        } else if (action.equals("defend")) {
            performDefense(player);
        }

        if (player1.getHealth() <= 0 || player2.getHealth() <= 0) {
            displayGameOver();
        }
    }

    private void performAttack(Player attacker, Player defender) {
        System.out.println(attacker.getName() + " is attacking " + defender.getName());
        int damage = random.nextInt(20) + 1;
        defender.takeDamage(damage);
        animateAttack(attacker, defender);
        JOptionPane.showMessageDialog(this, attacker.getName() + " dealt " + damage + " damage to " + defender.getName());
    }

    private void performDefense(Player defender) {
        System.out.println(defender.getName() + " is defending");
        int mitigation = random.nextInt(10) + 1;
        int newHealth = Math.min(defender.getHealth() + mitigation, 100); // Cap health at 100
        defender.setHealth(newHealth);
        JOptionPane.showMessageDialog(this, defender.getName() + " defended and healed " + mitigation + " health");
        animateDefense(defender);
    }

    private void animateAttack(Player attacker, Player defender) {
        System.out.println("Animating attack...");
        Timer timer = new Timer(50, null);
        final int[] step = {0};
        final int totalSteps = 10;
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int offset;
                if (step[0] < totalSteps / 2) {
                    offset = 5; 
                } else {
                    offset = -5; 
                }
    
                if (attacker == player1) {
                    character1X += offset;
                } else {
                    character2X -= offset;
                }
    
                if (step[0] == totalSteps) {
                    ((Timer) e.getSource()).stop();
                    highlightImage(defender, Color.RED); 
                }
                step[0]++;
                repaint();
            }
        });
        timer.start();
    }
    

    private void animateDefense(Player defender) {
        System.out.println("Animating defense...");
        highlightImage(defender, Color.BLUE);
    }

    private void highlightImage(Player defender, Color color) {
        System.out.println("Highlighting image...");
        if (defender == player1) {
            highlightCharacter1 = true;
            highlightColor1 = color;
        } else {
            highlightCharacter2 = true;
            highlightColor2 = color;
        }
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (defender == player1) {
                    highlightCharacter1 = false;
                } else {
                    highlightCharacter2 = false;
                }
                repaint();
                ((Timer) e.getSource()).stop();
            }
        });
        timer.setRepeats(false);
        timer.start();
        repaint();
    }

    private void updateButtonVisibility() {
        System.out.println("Updating button visibility...");
        attackButton1.setVisible(isPlayer1Turn);
        defendButton1.setVisible(isPlayer1Turn);
        attackButton2.setVisible(!isPlayer1Turn);
        defendButton2.setVisible(!isPlayer1Turn);
    }

    private void displayGameOver() {
        System.out.println("Displaying game over...");
        String winner;
        if (player1.getHealth() <= 0) {
            winner = player2.getName();
        } else {
            winner = player1.getName();
        }
        JOptionPane.showMessageDialog(null, "Game Over! " + winner + " wins!");
    
        JFrame gameOverFrame = new JFrame();
        gameOverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameOverFrame.setSize(300, 300);
        gameOverFrame.add(new JLabel("Game Over!"));
        gameOverFrame.setVisible(true);
    
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameOverFrame.dispose();
                showEndOptions();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void showEndOptions() {
        System.out.println("Showing end options...");
        int option = JOptionPane.showOptionDialog(this, "What would you like to do?", "Game Over", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Play Again", "Exit"}, "Play Again");
        if (option == JOptionPane.YES_OPTION) {
            GameUI gameUI = new GameUI((JFrame) SwingUtilities.getWindowAncestor(this));
            gameUI.initialize();
        } else {
            System.exit(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        int char1Width = character1.getWidth(this);
        int char1Height = character1.getHeight(this);
        int char2Width = character2.getWidth(this);
        int char2Height = character2.getHeight(this);

        if (highlightCharacter1) {
            g.setColor(highlightColor1);
            g.fillRect(character1X, getHeight() - char1Height - 50, char1Width, char1Height);
        }
        if (highlightCharacter2) {
            g.setColor(highlightColor2);
            g.fillRect(character2X, getHeight() - char2Height - 50, char2Width, char2Height);
        }

        g.drawImage(character1, character1X, getHeight() - char1Height - 50, this);
        g.drawImage(character2, character2X, getHeight() - char2Height - 50, this); // Adjust position

        int healthBarWidth = 200;
        int healthBarHeight = 10;

        int health1 = (int) ((double) player1.getHealth() / 100 * healthBarWidth);
        int health2 = (int) ((double) player2.getHealth() / 100 * healthBarWidth);

        g.setColor(Color.RED);
        g.fillRect(10, 10, health1, healthBarHeight);

        g.setColor(Color.BLUE);
        g.fillRect(getWidth() - healthBarWidth - 10, 10, health2, healthBarHeight);

        g.setColor(Color.WHITE);
        g.drawString(player1.getName() + "'s Health: " + player1.getHealth() + " / 100", 10, 30);
        g.drawString(player2.getName() + "'s Health: " + player2.getHealth() + " / 100", getWidth() - healthBarWidth - 10, 30);
    }

    private void playBackgroundMusic(String filePath) {
        System.out.println("Playing background music...");
        try {
            File musicFile = new File(filePath);
            if (musicFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                System.out.println("Playing background music: " + filePath);
            } else {
                System.out.println("File not found: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel drawGamePanel(Player player1, Player player2, boolean isPlayer1First) {
        System.out.println("Drawing game panel...");
        return new Scene(player1, player2, isPlayer1First);
    }
}
