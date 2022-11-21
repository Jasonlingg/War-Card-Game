// Jason Ling
// Grade 11 CompSci
// Description: War card game, each player draws a card one at a time and the person that loses all their cards loses the game and the game is over.
package com.company;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

public class Main extends JPanel implements KeyListener, ActionListener {
    static JFrame frame;
    JPanel cardPanel1, cardPanel2;
    JLabel picLabel1, picLabel2;
    ImageIcon[] deck, deck1, deck2;
    int cards1 = 26, cards2 = 26;
    JLabel[] warLabel1 = new JLabel[5], warLabel2 = new JLabel[5];
    Image warPic;
    Clip background1, background2, background3, cardSlide, war, cardShuffle, victory, boo;
    ImageIcon backOfCard = new ImageIcon("00.gif");

    //constructor
    public Main() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        // Start menu popup
        ImageIcon welcome = new ImageIcon("start.jpg");
        JOptionPane.showConfirmDialog(null,
                "", "Welcome!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, welcome);

        // The Main Panel
        setPreferredSize(new Dimension(400, 500));
        setLayout(new BorderLayout());

        // Setting up the Menu
        // Set up the Game MenuItems
        JMenuBar mainMenu = new JMenuBar();
        JMenuItem newOption, exitOption, subMenuOne, subMenuTwo, subMenuThree, subMenuFour, controls, objective, creator, shuffle;

        // Game Menu
        JMenu gameMenu = new JMenu("Game");
        newOption = new JMenuItem("New");
        exitOption = new JMenuItem("Exit");
        // shuffle card on menu
        shuffle = new JMenuItem("re-shuffle");
        // Set up the Game Menu
        gameMenu.add(newOption);
        gameMenu.add(shuffle);
        gameMenu.addSeparator();
        gameMenu.add(exitOption);

        // Song Menu
        JMenu songMenu = new JMenu("Songs");
        // Add the song choices into songMenu
        subMenuOne = new JMenuItem("Giorno's theme");
        subMenuTwo = new JMenuItem("ya like Jazz");
        subMenuThree = new JMenuItem("Simple music");
        subMenuFour = new JMenuItem("No music :(");
        songMenu.add(subMenuOne);
        songMenu.add(subMenuTwo);
        songMenu.add(subMenuThree);
        songMenu.add(subMenuFour);

        // How to play menu
        JMenu playMenu = new JMenu("How To Play");
        controls = new JMenuItem("Controls");
        objective = new JMenuItem("Objective");
        creator = new JMenuItem("Creator info");
        //add to playMenu
        playMenu.add(controls);
        playMenu.add(objective);
        playMenu.add(creator);

        // Set up the Menu Bar and add the above Menus
        mainMenu.add(gameMenu);
        mainMenu.add(songMenu);
        mainMenu.add(playMenu);
        // Set the menu bar for this frame to mainMenu
        frame.setJMenuBar(mainMenu);

        // Get the audio for the game
        AudioInputStream sound = AudioSystem.getAudioInputStream(new File("background1.wav"));
        background1 = AudioSystem.getClip();
        background1.open(sound);
        sound = AudioSystem.getAudioInputStream(new File("background2.wav"));
        background2 = AudioSystem.getClip();
        background2.open(sound);
        sound = AudioSystem.getAudioInputStream(new File("background3.wav"));
        background3 = AudioSystem.getClip();
        background3.open(sound);
        sound = AudioSystem.getAudioInputStream(new File("cardSlide.wav"));
        cardSlide = AudioSystem.getClip();
        cardSlide.open(sound);
        sound = AudioSystem.getAudioInputStream(new File("swordraw.wav"));
        war = AudioSystem.getClip();
        war.open(sound);
        sound = AudioSystem.getAudioInputStream(new File("victory.wav"));
        victory = AudioSystem.getClip();
        victory.open(sound);
        sound = AudioSystem.getAudioInputStream(new File("boo.wav"));
        boo = AudioSystem.getClip();
        boo.open(sound);
        sound = AudioSystem.getAudioInputStream(new File("cardShuffle.wav"));
        cardShuffle = AudioSystem.getClip();
        cardShuffle.open(sound);


        // load the cards into an array called deck
        deck = new ImageIcon[52];
        // for cards 1-9
        for (int imageNo = 1; imageNo < 10; imageNo++) {
            String imageFileName1 = "0" + imageNo + "c.gif";
            String imageFileName2 = "0" + imageNo + "d.gif";
            String imageFileName3 = "0" + imageNo + "h.gif";
            String imageFileName4 = "0" + imageNo + "s.gif";
            deck[imageNo - 1] = new ImageIcon(imageFileName1);
            deck[imageNo + 12] = new ImageIcon(imageFileName2);
            deck[imageNo + 25] = new ImageIcon(imageFileName3);
            deck[imageNo + 38] = new ImageIcon(imageFileName4);
        }
        // for cards 10-13
        for (int imageNo = 10; imageNo <= 13; imageNo++) {
            String imageFileName1 = imageNo + "c.gif";
            String imageFileName2 = imageNo + "d.gif";
            String imageFileName3 = imageNo + "h.gif";
            String imageFileName4 = imageNo + "s.gif";
            deck[imageNo - 1] = new ImageIcon(imageFileName1);
            deck[imageNo + 12] = new ImageIcon(imageFileName2);
            deck[imageNo + 25] = new ImageIcon(imageFileName3);
            deck[imageNo + 38] = new ImageIcon(imageFileName4);
        }
        // Shuffle cards and divide deck into 2 with method
        shuffleCards(deck);
        divideInto2();

        // picture for the middle
        warPic = ImageIO.read(new File("war-img.png"));

        // Create two panels to hold cards for each player
        // in the program, 1 is for the player and 2 is for the robot
        cardPanel1 = new JPanel();
        add(cardPanel1, BorderLayout.SOUTH);
        cardPanel2 = new JPanel();
        add(cardPanel2, BorderLayout.NORTH);

        // Make JLabels to hold card
        picLabel1 = new JLabel(backOfCard);
        picLabel2 = new JLabel(backOfCard);
        // add to respective panels
        cardPanel1.add(picLabel1, BorderLayout.CENTER);
        cardPanel2.add(picLabel2, BorderLayout.CENTER);

        // add 4 additional cards to the panel for the total war but set it invisible
        for (int i = 1; i < 6; i++) {
            JLabel picLabel = new JLabel(deck1[i]);
            warLabel1[i - 1] = picLabel;
            cardPanel1.add(warLabel1[i - 1]);
            // hide until there is total war
            warLabel1[i - 1].setVisible(false);
            JLabel picLabel1 = new JLabel(deck2[i]);
            // hide until there is total war
            warLabel2[i - 1] = picLabel1;
            cardPanel2.add(warLabel2[i - 1]);
            warLabel2[i - 1].setVisible(false);
        }

        // action command and listeners
        newOption.setActionCommand("New");
        newOption.addActionListener(this);
        exitOption.setActionCommand("Exit");
        exitOption.addActionListener(this);
        subMenuOne.setActionCommand("JoJo");
        subMenuOne.addActionListener(this);
        subMenuTwo.setActionCommand("Jazz");
        subMenuTwo.addActionListener(this);
        subMenuThree.setActionCommand("Simple Music");
        subMenuThree.addActionListener(this);
        subMenuFour.setActionCommand("No music");
        subMenuFour.addActionListener(this);
        controls.setActionCommand("Controls");
        controls.addActionListener(this);
        objective.setActionCommand("Objective");
        objective.addActionListener(this);
        creator.setActionCommand("Creator");
        creator.addActionListener(this);
        shuffle.setActionCommand("shuffle");
        shuffle.addActionListener(this);

        setFocusable(true);
        addKeyListener(this);
        setFocusTraversalKeysEnabled(false);

        // music playing in background
        background2.setFramePosition(0); //<-- play sound file again from beginning
        background2.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void newGame() {
        // resets the game; it calls shuffle cards, divides it in two, resets scores and the table
        // no parameters
        // no return statement
        shuffleCards(deck);
        divideInto2();
        cards1 = 26;
        cards2 = 26;
        picLabel1.setIcon(backOfCard);
        picLabel2.setIcon(backOfCard);
        for (int i = 1; i < 6; i++) {
            warLabel1[i - 1].setVisible(false);
            warLabel2[i - 1].setVisible(false);
        }
        Border border = BorderFactory.createLineBorder(null, 2);
        picLabel1.setBorder(border);
        repaint();
    }

    public void checkWin() {
        // Checks if a player has won by the number of cards in deck and displays message. The message gives an option to
        // play again or to exit
        // no parameters
        // no return statement
        if (cards2 == 52 || cards1 < 1) {
            boo.setFramePosition(0); //<-- play sound file again from beginning
            boo.start();
            ImageIcon icon = new ImageIcon("loseIcon.jpg");
            int input = JOptionPane.showConfirmDialog(null,
                    "", "GAMEOVER -YOU LOSE",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
            if (input == 0)
                newGame();
            else
                System.exit(0);

        } else if (cards1 == 52 || cards2 < 1) {
            victory.setFramePosition(0); //<-- play sound file again from beginning
            victory.start();
            ImageIcon icon = new ImageIcon("winIcon.png ");
            int input = JOptionPane.showConfirmDialog(null,
                    " Play Again?", "GAMEOVER -YOU WIN",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon );
            if (input == 0)
                newGame();
            else
                System.exit(0);
        }
    }

    public void actionPerformed(ActionEvent event) {
        // Gives an action to a event
        // requires a event name to find which action to perform
        // no return statement
        String eventName = event.getActionCommand();
        if (eventName.equals("New")) {
            newGame();
        } else if (eventName.equals("Exit")) {
            System.exit(0);
        } else if (eventName.equals("JoJo")) {
            // Stop other sounds to play the certain song
            background2.stop();
            background3.stop();
            background1.setFramePosition(0); //<-- play sound file again from beginning
            background1.loop(Clip.LOOP_CONTINUOUSLY);
        } else if (eventName.equals("Jazz")) {
            // Stop other sounds to play the certain song
            background1.stop();
            background3.stop();
            background2.setFramePosition(0); //<-- play sound file again from beginning
            background2.loop(Clip.LOOP_CONTINUOUSLY);
        } else if (eventName.equals("Simple Music")) {
            // Stop other sounds to play the certain song
            background1.stop();
            background2.stop();
            background3.setFramePosition(0); //<-- play sound file again from beginning
            background3.loop(Clip.LOOP_CONTINUOUSLY);
        } else if (eventName.equals("No music")) {
            // stop all music
            background1.stop();
            background2.stop();
            background3.stop();
        } else if (eventName.equals("shuffle")){
            cardShuffle.setFramePosition(0); //<-- play sound file again from beginning
            cardShuffle.start();
            shuffleCards(deck1);
            shuffleCards(deck2);
            deck1 = moveNull(deck1);
            deck2 = moveNull(deck2);

            // if want to test the deck reshuffled
            //System.out.println(Arrays.toString(deck1));
            //System.out.println(Arrays.toString(deck2));
        }
        else if (eventName.equals("Controls")) {
            JOptionPane.showMessageDialog(this, "Use space-bar to draw cards and mouse to select menu items and to select if you want to continue the game.",
                    "How to Play", JOptionPane.INFORMATION_MESSAGE);
        } else if (eventName.equals("Objective")) {
            JOptionPane.showMessageDialog(this, "The objective is to win all the cards in the deck by comparing the value of each card turned over." +
                            " If the Cards are equal in value, 4 additional cards will be drawn.",
                    "How to Play", JOptionPane.INFORMATION_MESSAGE);
        } else if (eventName.equals("Creator")) {
            JOptionPane.showMessageDialog(this, "The creator of this game is Jason Ling :) ",
                    "How to Play", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void paintComponent(Graphics g) {
        // paint component draws and colors components
        // only parameter is graphics
        // no return statement
        super.paintComponent(g);
        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD, 15));
        g.drawString("Cards left in your deck: " + cards1, 0, 340);
        g.setFont(new Font("serif", Font.BOLD, 15));
        g.drawString("Cards left in opponent's deck: " + cards2, 0, 150);
        g.drawImage(warPic, 0, 180, null);

    }

    public void shuffleCards(ImageIcon[] shuffle) {
        //  shuffles or randomizes an array, specifically a deck of cards
        // requires an ImageIcon array to run
        // no return statement - passes by reference
        Random rand = new Random();
        for (int i = 0; i < shuffle.length; i++) {
            int index = rand.nextInt(shuffle.length);
            ImageIcon temp = shuffle[index];
            shuffle[index] = shuffle[i];
            shuffle[i] = temp;
        }
    }


    public ImageIcon[] moveNull(ImageIcon[] deck) {
        // moves all the null values in a specific array to the end of the array
        // parameters constitute just one ImageIcon array
        // returns the new organized deck because the original is unchanged
        ImageIcon[] nullMoved = new ImageIcon[deck.length];
        int j = 0;
        for (int i = 0; i < deck.length; i++) {
            if (deck[i] != null)
                nullMoved[i - j] = deck[i];
            else
                j++;

        }
        return nullMoved;
    }

    public void wartime() {
        // compares the values of the two cards on the table
        // no parameters
        // no return statement
        String str1 = deck1[0].toString(); // convert player 1 value to string
        str1 = str1.substring(0, 2);
        int value1 = Integer.parseInt(str1); // then to int

        String str2 = deck2[0].toString(); // convert player 2 value to string
        str2 = str2.substring(0, 2);
        int value2 = Integer.parseInt(str2); // then to int

        if (value1 > value2) { // 1 is bigger than 2
            for (int i = 1; i < deck1.length; i++) {
                if (deck1[i] == null) { // find empty array key
                    deck1[i] = deck2[0]; // put opponent's card in the key
                    deck1[i + 1] = deck1[0]; // put own card in adjacent key
                    cards1++;
                    cards2--;
                    break;
                }
            }
            deck1[0] = null; // remove the played card from the start of array
            deck2[0] = null;

            // border changes bc of win
            Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
            // set the border of this component
            picLabel1.setBorder(border);

        } else if (value1 < value2) { // 2 is bigger than 1
            for (int i = 1; i < deck2.length; i++) {
                if (deck2[i] == null) { // find empty array key
                    deck2[i] = deck1[0]; // put opponent's card in the key
                    deck2[i + 1] = deck2[0]; // put own card in adjacent key
                    cards1--;
                    cards2++;
                    break;
                }
            }
            deck2[0] = null; // remove the played card from the start of array
            deck1[0] = null;

            // border changes bc of lose
            Border border = BorderFactory.createLineBorder(Color.RED, 2);
            // set the border of this component
            picLabel1.setBorder(border);

        } else { // total war is activated when values are the same
            war.setFramePosition(0); //<-- play sound file again from beginning
            war.start();
            int warValue1 = 0;
            int warValue2 = 0;
            for (int i = 0; i < 5; i++) { // for the player 1 deck
                if (deck1[i + 1] == null)
                    break;
                warLabel1[i].setIcon(deck1[i + 1]); // sets the next card in deck
                cardPanel1.add(warLabel1[i]); // adds to panel
                warLabel1[i].setVisible(true); // set the cards visible
                str1 = deck1[i + 1].toString();  // conversion to string
                str1 = str1.substring(0, 2);
                warValue1 += Integer.parseInt(str1); // add the values of each card to an accumulator
            }
            for (int i = 0; i < 5; i++) { // for the player 1 deck
                if (deck2[i + 1] == null)
                    break;
                warLabel2[i].setIcon(deck2[i + 1]);  // sets the next card in deck
                cardPanel2.add(warLabel2[i]); // adds to panel
                warLabel2[i].setVisible(true); // set the cards visible
                str2 = deck2[i + 1].toString(); // conversion to string
                str2 = str2.substring(0, 2);
                warValue2 += Integer.parseInt(str2); // add the values of each card to an accumulator
            }
            if (warValue1 > warValue2) { // compare totals; 1 is bigger than 2
                for (int i = 1; i < deck1.length; i++) {
                    if (deck1[i] == null) { // same as before but there are four changes for each deck
                        for (int j = 0; j < 5; j++) {
                            deck1[i + j] = deck2[j]; // cards that are won from opponent
                            if (i + 6 + j > 56) // if exceeded break immediately so does not crash
                                break;
                            deck1[i + 6 + j] = deck1[j]; // own cards
                            deck1[j] = null;
                            deck2[j] = null;
                            cards1++;
                            cards2--;
                            checkWin(); // check if a player has won so system does not crash

                            // border bc of win for each card
                            Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
                            // set the border of this component
                            warLabel1[j].setBorder(border);
                        }
                        Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
                        // set the border of this component
                        picLabel1.setBorder(border);
                        break;
                    }
                }
            } else if (warValue1 < warValue2) { // compare totals; 1 is bigger than 2
                for (int i = 1; i < deck2.length; i++) {
                    if (deck2[i] == null) { // same as before but there are four changes for each deck
                        for (int j = 0; j < 5; j++) {
                            deck2[i + j] = deck1[j]; // cards that are won from opponent
                            if (i + 6 + j > 56) // if exceeded break immediately so does not crash
                                break;
                            deck2[i + 6 + j] = deck2[j]; // own cards
                            deck1[j] = null;
                            deck2[j] = null;
                            cards1--;
                            cards2++;
                            checkWin(); // check if a player has won so system does not crash
                            // border changes bc of win for each card
                            Border border = BorderFactory.createLineBorder(Color.RED, 2);
                            // set the border of this component
                            warLabel1[j].setBorder(border);
                        }
                        // border change bc of lose for each card
                        Border border = BorderFactory.createLineBorder(Color.RED, 2);
                        // set the border of this component
                        picLabel1.setBorder(border);
                        break;
                    }

                }
            } else { // if values still equal then it is a tie
                for (int i = 1; i < deck1.length; i++) {
                    if (deck1[i] == null) {
                        for (int j = 0; j < 5; j++) {
                            deck1[i + j] = deck1[j]; // return to own deck
                            deck1[j] = null;
                        }
                        break;
                    }
                }
                for (int i = 1; i < deck2.length; i++) {
                    if (deck2[i] == null) {
                        for (int j = 0; j < 5; j++) {
                            deck2[i + j] = deck2[j]; // return to own deck
                            deck2[j] = null;
                        }
                        break;
                    }
                }
            }
        }
    }

    public void divideInto2() {
        // divides the shuffled deck into 2
        // no parameter
        // no return statement
        deck1 = new ImageIcon[58];
        deck2 = new ImageIcon[58];
        for (int i = 0; i < 26; i++) {
            deck1[i] = this.deck[i];
            deck2[i] = this.deck[i + 26];
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // key pressed method
        // needs a key event
        // no return statement
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // when player presses space
            for (int i = 1; i < 6; i++) {
                warLabel1[i - 1].setVisible(false);
                warLabel2[i - 1].setVisible(false);
            }
            cardSlide.setFramePosition(0); //<-- play sound file again from beginning
            cardSlide.start();
            picLabel1.setIcon(deck1[0]);
            picLabel2.setIcon(deck2[0]);
            wartime();
            deck1 = moveNull(deck1);
            deck2 = moveNull(deck2);
            checkWin();
            repaint();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    // main method
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        frame = new JFrame("WAR Card Game");
        frame.setLocation(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image iconImage = Toolkit.getDefaultToolkit().getImage("war.jpg");
        frame.setIconImage(iconImage);
        frame.add(new Main());
        frame.pack();
        frame.setVisible(true);
    }

}
