
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;

public class Snake extends JFrame implements KeyListener {

    private Grille dessin;

    JPanel p1, p2;
    JTextArea s;
    JMenuBar mymbar;
    JMenu game, help, level;
    JButton jenesaispas;
    private String score1;
    private String score2;
    private String score3;


    public Snake(int modeJeu) {
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
            dessin = new Grille();
        }else {
            dessin = new Grille(2);
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
        jenesaispas = new JButton("Super bouton trop cool !");

        JLabel afficheScore = new JLabel("Le score du joueur 1 :" + dessin.snake.score);
        p1.add(afficheScore);
        p1.setSize(300,600);

        fenetreJeu.add(grille, BorderLayout.WEST);
        fenetreJeu.add(p1, BorderLayout.EAST);

        setContentPane(fenetreJeu);
    }

    public void ecrireScore(){
        File file = new File("score/.txt");
        File folder = new File("score/");
        if (!folder.exists()){
            folder.mkdir();

            }
        if (!file.exists()) {
            try{
                file.createNewFile();

            }catch (IOException e){
                e.printStackTrace();
            }


        }else{
            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write(dessin.snake.score);
                bw.newLine();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void readScore(){
        File file = new File("score/.txt");
        File folder = new File("score/");
        if (!folder.exists()){
            folder.mkdir();

        }
        if (!file.exists()) {
            try{
                file.createNewFile();

            }catch (IOException e){
                e.printStackTrace();
            }


        }else{
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
                String line = reader.readLine();

                    if (line == null){

                    }else{
                        score1=line;
                        line = reader.readLine();
                        score2=line;
                        line = reader.readLine();
                        score3=line;
                        reader.close();

                    }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
    public void keyTyped(KeyEvent e) {

    }

    // methode renvoyant la touche pressee dans la méthode touche
    public void keyPressed(KeyEvent e) {
        // si on presse sur les fleches de direction, on se dirige en fonction
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            dessin.touche('q');
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
}
