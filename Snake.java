
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.util.ArrayList;

class Snake extends JFrame implements KeyListener {

    private Grille dessin;

    JPanel p1, p2;
    JTextArea s;
    JMenuBar mymbar;
    JMenu game, help, level;
    private int score1;
    private String pseudo1;
    private int score2;
    private String pseudo2;
    private int score3;
    private String pseudo3;
    public JLabel afficheScore;
    ArrayList<Integer> records = new ArrayList<Integer>();
    ArrayList<String> pseudo = new ArrayList<String>();


    public Snake(int modeJeu, String pseudoJoueur, String pseudoJoueur2) {
        super("SNAKE");
        // créer menu bar avec des fonctionnalités
        menuBar();
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

        JLabel label = new JLabel("Le Jeu: SNAKE");
        JPanel centreTitre = new JPanel();

        centreTitre.add(label);
        grille.setLayout(new BorderLayout());
        grille.setPreferredSize(new Dimension(800, 600));
        grille.add(dessin, BorderLayout.CENTER);
        grille.add(centreTitre, BorderLayout.NORTH);

        JPanel fenetreJeu = new JPanel();
        fenetreJeu.setLayout(new BorderLayout());

        p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));

        // appel de la fonction pour lire les meilleurs scores et les sauvegarder dans les variables
        readScore();

        afficheScore = new JLabel("Les meilleurs scores :      ");

        JLabel afficheMeilleurScore1 = new JLabel(" 1 : " + pseudo1 + " : " + score1);
        JLabel afficheMeilleurScore2 = new JLabel(" 2 : " + pseudo2 + " : " + score2);
        JLabel afficheMeilleurScore3 = new JLabel(" 3 : " + pseudo3 + " : " + score3);

        p1.add(afficheScore);
        p1.add(afficheMeilleurScore1);
        p1.add(afficheMeilleurScore2);
        p1.add(afficheMeilleurScore3);
        p1.setSize(300,600);

        fenetreJeu.add(grille, BorderLayout.WEST);
        fenetreJeu.add(p1, BorderLayout.EAST);

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


    public void menuBar() {
        mymbar = new JMenuBar();

        game = new JMenu("Jeu");

        JMenuItem newgame = new JMenuItem("Nouvelle Partie");
        JMenuItem exit = new JMenuItem("Quitter");

        newgame.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
//                        reset();
                    }
                });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        game.add(newgame);
        game.addSeparator();
        game.add(exit);
        mymbar.add(game);

        level = new JMenu("Niveau");
        mymbar.add(level);
        help = new JMenu("Help");

        JMenuItem creator = new JMenuItem("Information");
        JMenuItem instruction = new JMenuItem("Commande");

        creator.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(p2, "CREATEURS :\n NORMANT Thomas C1\n PAILLARD Tanguy C1\n HASNAOUI Lounèse C2\n ROVICQUE Marco C2\n LONGECHAMPS Clément C1\n VIEILLE Chloé C1 BIG BOSS\n Pour les réalisations de notre projet tuteuré de S2");
            }
        });
        instruction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(p2, "Les commandes sont  :\n Joueur 1 ; <- pour aller à gauche et -> pour aller à droite\n Joueur 2 ; \n touche espace pour la pause ");
            }
        });

        help.add(creator);
        help.add(instruction);
        mymbar.add(help);

        setJMenuBar(mymbar);
    }



    public void readScore(){

        File file1 = new File("src\\score\\meilleurScore.txt");
        File file2 = new File("src\\score\\pseudo.txt");

        try {
            // lecture des meilleurs scores dans fichier
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"UTF-8"));
            String line = reader.readLine();
            System.out.println(line);
            while (line != null){
                int meilleurscore = Integer.parseInt(line);
                records.add(meilleurscore);
                line = reader.readLine();
            }
            score1 = records.get(0);
            score2 = records.get(1);
            score3 = records.get(2);
            reader.close();

            // lecture des pseudos associé aux meilleurs scores
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file2),"UTF-8"));
            line = reader.readLine();
            System.out.println(line);
            while (line != null){
                pseudo.add(line);
                line = reader.readLine();
            }
            pseudo1 = pseudo.get(0);
            pseudo2 = pseudo.get(1);
            pseudo3 = pseudo.get(2);

            reader.close();
            } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
