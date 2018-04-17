
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class Snake extends JFrame implements KeyListener{
    protected Grille dessin;

    public Snake() {
        super("SNAKE");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(this);
        dessin = new Grille();

        JPanel grille = new JPanel();
        JLabel label = new JLabel("Le Jeu: SNAKE");
        JPanel centreTitre = new JPanel();
        centreTitre.add(label);
        grille.setLayout(new BorderLayout());
        grille.setPreferredSize(new Dimension(800, 600));
        grille.add(dessin, BorderLayout.CENTER);
        grille.add(centreTitre, BorderLayout.NORTH);
        setContentPane(grille);
    }

    public void keyTyped(KeyEvent e) {

    }

    // methode renvoyant la touche pressee dans la méthode touche
    public void keyPressed(KeyEvent e) {
        // si on presse sur les fleches de direction, on se dirige en fonction
        if(e.getKeyCode()== KeyEvent.VK_LEFT) {
            dessin.touche('q');
        }
        if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
            dessin.touche('d');
        }

        // commande pour le joueur 2
        if(e.getKeyCode()== KeyEvent.VK_NUMPAD4) {
            dessin.touche('4');
        }
        if(e.getKeyCode()==KeyEvent.VK_NUMPAD6) {
            dessin.touche('6');
        }

        //si on presse sur espace, on met en pause
        if(e.getKeyCode()== KeyEvent.VK_SPACE) {
            dessin.touche('p');
        }
        //si on presse sur echape, on quitte
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    public void keyReleased(KeyEvent e) {
    }

}


