
import javax.swing.JFrame;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.*;
import java.net.URL;
import java.util.ArrayList;

class Snake extends JFrame implements KeyListener {

    private Grille dessin;

    private JPanel jPanel1, jPanel2, jPanel3;
    private JLabel jLabel1, afficheScore, afficheMeilleurScore1, afficheMeilleurScore2, afficheMeilleurScore3,
            jLabel2, jLabel3, jLabel4, jLabel5;
    private MeilleurScore meilleurScore = new MeilleurScore();
    private Image imageSerpent1, imageSerpent2;
    private ImageIcon icon1, icon2;

    public Snake(int modeJeu, String pseudoJoueur, String pseudoJoueur2) {
        super("SNAKE");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        addKeyListener(this);

        if (modeJeu == 1){
            dessin = new Grille(1, pseudoJoueur);
        }
        if (modeJeu == 2){
            dessin = new Grille(2, pseudoJoueur, pseudoJoueur2);
        }

        URL image1 = getClass().getResource("image/serpentJoueur1.png");
        URL image2 = getClass().getResource("image/serpentJoueur2.png");

        try {
            imageSerpent1 = ImageIO.read(image1);
            imageSerpent1 = imageSerpent1.getScaledInstance(120, 50, imageSerpent1.SCALE_DEFAULT);
            icon1 = new ImageIcon(imageSerpent1);
            imageSerpent2 = ImageIO.read(image2);
            imageSerpent2 = imageSerpent2.getScaledInstance(120, 50, imageSerpent2.SCALE_DEFAULT);
            icon2 = new ImageIcon(imageSerpent2);
        } catch (IOException e){
            e.printStackTrace();
        }

        JPanel grille = new JPanel();

        jLabel1 = new JLabel();
        jLabel1.setFont(new Font("Century Gothic", 0, 35));
        jLabel1.setForeground(new Color(212, 212, 212));
        jLabel1.setText("             SNAKE             ");
        jLabel1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255)));

        jLabel2 = new JLabel();
        jLabel2.setFont(new Font("Century Gothic", 0, 15));
        jLabel2.setForeground(new Color(212, 212, 212));
        jLabel2.setText(pseudoJoueur + " : ");
        jLabel2.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));


        jLabel3 = new JLabel();
        jLabel3.setIcon(icon1);

        if (modeJeu == 2){
            jLabel4 = new JLabel();
            jLabel4.setFont(new Font("Century Gothic", 0, 15));
            jLabel4.setForeground(new Color(212, 212, 212));
            jLabel4.setText(pseudoJoueur2 + " : ");

            jLabel5 = new JLabel();
            jLabel5.setIcon(icon2);
        }


        jPanel2 = new JPanel();
        jPanel2.setBackground(new Color(36,47,65));

        jPanel2.add(jLabel1);

        grille.setLayout(new BorderLayout());
        grille.setBackground(new Color(36,47,65));
        grille.setPreferredSize(new Dimension(800, 600));
        grille.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        grille.add(dessin, BorderLayout.CENTER);
        grille.add(jPanel2, BorderLayout.NORTH);

        JPanel fenetreJeu = new JPanel();
        fenetreJeu.setLayout(new BorderLayout());

        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(36,47,65));
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));


        afficheScore = new JLabel();
        afficheScore.setFont(new Font("Century Gothic", 0, 20));
        afficheScore.setForeground(new Color(255,255,255));
        afficheScore.setText("Les meilleurs scores : ");

        afficheMeilleurScore1 = new JLabel();
        afficheMeilleurScore1.setFont(new Font("Century Gothic", 0, 20));
        afficheMeilleurScore1.setForeground(new Color(255,255,255));
        afficheMeilleurScore1.setText("        " + meilleurScore.getMeilleurPseudo().get(0) + " :  " + meilleurScore.getMeilleurScore().get(0));

        afficheMeilleurScore2 = new JLabel();
        afficheMeilleurScore2.setFont(new Font("Century Gothic", 0, 20));
        afficheMeilleurScore2.setForeground(new Color(255,255,255));
        afficheMeilleurScore2.setText("        " + meilleurScore.getMeilleurPseudo().get(1) + " :  " + meilleurScore.getMeilleurScore().get(1));

        afficheMeilleurScore3 = new JLabel();
        afficheMeilleurScore3.setFont(new Font("Century Gothic", 0, 20));
        afficheMeilleurScore3.setForeground(new Color(255,255,255));
        afficheMeilleurScore3.setText("        " + meilleurScore.getMeilleurPseudo().get(2) + " :  " + meilleurScore.getMeilleurScore().get(2));

        jPanel1.add(afficheScore);
        jPanel1.add(afficheMeilleurScore1);
        jPanel1.add(afficheMeilleurScore2);
        jPanel1.add(afficheMeilleurScore3);
        jPanel1.add(jLabel2);
        jPanel1.add(jLabel3);
        if (modeJeu == 2){
            jPanel1.add(jLabel4);
            jPanel1.add(jLabel5);
        }
        jPanel1.setBorder(BorderFactory.createEmptyBorder(100,20,0,15));

        jPanel3 = new JPanel();
        jPanel3.setBorder(BorderFactory.createMatteBorder(0,3,0,0,Color.white));
        jPanel3.setBackground(new Color(36,47,65));
        jPanel3.add(jPanel1);

        fenetreJeu.add(grille, BorderLayout.WEST);
        fenetreJeu.add(jPanel3, BorderLayout.EAST);

        setContentPane(fenetreJeu);
    }


    public void keyTyped(KeyEvent e) {

    }

    // methode renvoyant la touche pressee dans la méthode touche
    public void keyPressed(KeyEvent e) {
        // si on presse sur les fleches de direction, on se dirige en fonction
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            dessin.touche('q');
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            dessin.touche('d');
        }

        // commande pour le joueur 2
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            dessin.touche('4');
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            dessin.touche('6');
        }

        //si on presse sur espace, on met en pause
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            dessin.touche('p');
        }
        //si on presse sur echape, on quitte
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        //si on presse sur entré, on retourne à la page d'accueil
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            dispose();                        // fonction java suppriment une jframe pour en recréer une nouvelle
        }
    }

    public void keyReleased(KeyEvent e) {
    }


}

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.util.ArrayList;

class Snake extends JFrame implements KeyListener {

    private Grille dessin;

    private JPanel jPanel1, jPanel2, jPanel3;
//    private JMenuBar jMenuBar;
//    private JMenu game, help, level;
    private JLabel jLabel1, afficheScore, afficheMeilleurScore1, afficheMeilleurScore2, afficheMeilleurScore3;
    private MeilleurScore meilleurScore = new MeilleurScore();
//    private ArrayList<Integer> meilleurScore = new ArrayList<Integer>();
//    private ArrayList<String> meilleurPseudo = new ArrayList<String>();


    public Snake(int modeJeu, String pseudoJoueur, String pseudoJoueur2) {
        super("SNAKE");
        // créer menu bar avec des fonctionnalités
//        menuBar();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        addKeyListener(this);

        if (modeJeu == 1){
            dessin = new Grille(1, pseudoJoueur);
        }
        if (modeJeu == 2){
            dessin = new Grille(2, pseudoJoueur, pseudoJoueur2);
        }

        JPanel grille = new JPanel();

        jLabel1 = new JLabel();
        jLabel1.setFont(new Font("Century Gothic", 0, 35));
        jLabel1.setForeground(new Color(212, 212, 212));
        jLabel1.setText("             SNAKE             ");
        jLabel1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255)));


        jPanel2 = new JPanel();
        jPanel2.setBackground(new Color(36,47,65));

        jPanel2.add(jLabel1);

        grille.setLayout(new BorderLayout());
        grille.setBackground(new Color(36,47,65));
        grille.setPreferredSize(new Dimension(800, 600));
        grille.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        grille.add(dessin, BorderLayout.CENTER);
        grille.add(jPanel2, BorderLayout.NORTH);

        JPanel fenetreJeu = new JPanel();
        fenetreJeu.setLayout(new BorderLayout());

        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(36,47,65));
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));


        afficheScore = new JLabel();
        afficheScore.setFont(new Font("Century Gothic", 0, 20));
        afficheScore.setForeground(new Color(255,255,255));
        afficheScore.setText("Les meilleurs scores : ");

        afficheMeilleurScore1 = new JLabel();
        afficheMeilleurScore1.setFont(new Font("Century Gothic", 0, 20));
        afficheMeilleurScore1.setForeground(new Color(255,255,255));
        afficheMeilleurScore1.setText("          " + meilleurScore.getMeilleurPseudo().get(0) + " :  " + meilleurScore.getMeilleurScore().get(0));

        afficheMeilleurScore2 = new JLabel();
        afficheMeilleurScore2.setFont(new Font("Century Gothic", 0, 20));
        afficheMeilleurScore2.setForeground(new Color(255,255,255));
        afficheMeilleurScore2.setText("          " + meilleurScore.getMeilleurPseudo().get(1) + " :  " + meilleurScore.getMeilleurScore().get(1));

        afficheMeilleurScore3 = new JLabel();
        afficheMeilleurScore3.setFont(new Font("Century Gothic", 0, 20));
        afficheMeilleurScore3.setForeground(new Color(255,255,255));
        afficheMeilleurScore3.setText("          " + meilleurScore.getMeilleurPseudo().get(2) + " :  " + meilleurScore.getMeilleurScore().get(2));

        jPanel1.add(afficheScore);
        jPanel1.add(afficheMeilleurScore1);
        jPanel1.add(afficheMeilleurScore2);
        jPanel1.add(afficheMeilleurScore3);
        jPanel1.setBorder(BorderFactory.createEmptyBorder(100,20,0,15));

        jPanel3 = new JPanel();
        jPanel3.setBorder(BorderFactory.createMatteBorder(0,3,0,0,Color.white));
        jPanel3.setBackground(new Color(36,47,65));
        jPanel3.add(jPanel1);

        fenetreJeu.add(grille, BorderLayout.WEST);
        fenetreJeu.add(jPanel3, BorderLayout.EAST);

        setContentPane(fenetreJeu);
    }


    public void keyTyped(KeyEvent e) {

    }

    // methode renvoyant la touche pressee dans la méthode touche
    public void keyPressed(KeyEvent e) {
        // si on presse sur les fleches de direction, on se dirige en fonction
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            dessin.touche('q');
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            dessin.touche('d');
        }

        // commande pour le joueur 2
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            dessin.touche('4');
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            dessin.touche('6');
        }

        //si on presse sur espace, on met en pause
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            dessin.touche('p');
        }
        //si on presse sur echape, on quitte
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        //si on presse sur entré, on retourne à la page d'accueil
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            dispose();                        // fonction java suppriment une jframe pour en recréer une nouvelle
        }
    }

    public void keyReleased(KeyEvent e) {
    }


}
