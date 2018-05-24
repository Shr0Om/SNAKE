import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher extends JFrame {

    private JLabel pseudoLabel, titre, pseudo2Label, titreHelp;
    private JFrame frame3;
    private JPanel panoTitre, panoInfo, panoInfo2, panoInfo3, panoBouton, panoGlobal, panoCentral;
    private JButton start, help, multijoueur, unJoueur;
    private JTextField pseudoText, pseudo2Text;
    private JOptionPane erreur;
    private JDialog erreurDialog;
    private JPanel centreTitre, centreTitreHelp;
    private boolean multi = false, unPlayer = true, verification;

    public Launcher() {
        creerWidget();
        afficheWidget();
        action();
        setSize(500,400);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void launcherSolo() {
        creerWidget();
        afficheWidget();
        action();
        setSize(500,400);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void launcherMulti() {
        creerWidget();
        afficheWidgetMulti();
        action();
        setSize(500,400);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void help(){
        creerWidgetHelp();
        afficheWidgetHelp();
        action();
        setSize(500,400);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void action() {
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                verification = true;
                if (multi && (pseudoText.getText().isEmpty() || pseudo2Text.getText().isEmpty()) || (!multi && pseudoText.getText().isEmpty())){
                    verification = false;
                    erreur.showMessageDialog(start, "Veuillez entrer un pseudo", "Erreur",JOptionPane.ERROR_MESSAGE);
                    erreurDialog = erreur.createDialog(start, "Erreur");
                }
                if(unPlayer && verification){
                    Snake frame = new Snake(1, pseudoText.getText());
                    frame.pack();
                    frame.setVisible(true);
                }
                if (multi && verification){
                    Snake frame = new Snake(2, pseudoText.getText());
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        });

        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                help();
            }
        });

        multijoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                multi = true;
                unPlayer = false;
                launcherMulti();
            }
        });

        unJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                unPlayer = true;
                multi = false;
                launcherSolo();
            }
        });
    }

    private void creerWidget() {

        titre = new JLabel("SNAKE");
        centreTitre = new JPanel();
        centreTitre.add(titre);
        titre.setFont(new Font(titre.getFont().getName(), titre.getFont().getStyle(), 30));
        pseudoLabel = new JLabel("Joueur 1");
        pseudo2Label = new JLabel("Joueur 2");

        start = new JButton("Jouer");
        help = new JButton("Aide");
        multijoueur = new JButton("Multijoueur");
        unJoueur = new JButton("Retour");

        pseudoText = new JTextField();
        pseudoText.setColumns(10);
        pseudo2Text = new JTextField();
        pseudo2Text.setColumns(10);

        panoTitre = new JPanel(new BorderLayout());
        panoInfo = new JPanel();
        panoInfo2 = new JPanel();
        panoInfo3 = new JPanel();
        panoBouton = new JPanel();
        panoCentral = new JPanel();
        panoGlobal = new JPanel();

        erreur = new JOptionPane();
    }

    private void creerWidgetHelp(){
        titreHelp = new JLabel("Aide");
        centreTitre = new JPanel();
        centreTitre.add(titreHelp);
        titreHelp.setFont(new Font(titreHelp.getFont().getName(), titreHelp.getFont().getStyle(), 30));

        unJoueur = new JButton("Retour");

        panoTitre = new JPanel(new BorderLayout());
        panoBouton = new JPanel();
        panoGlobal = new JPanel(new BorderLayout());
    }

    private void afficheWidgetHelp(){

        panoTitre.add(centreTitre, BorderLayout.NORTH);
        panoBouton.add(unJoueur);
        panoGlobal.setLayout(new BoxLayout(panoGlobal, BoxLayout.Y_AXIS));
        panoGlobal.add(panoTitre);
        panoGlobal.add(panoBouton);
        setContentPane(panoGlobal);
    }

    private void afficheWidget(){

        panoTitre.add(centreTitre, BorderLayout.NORTH);
        panoInfo.add(pseudoLabel);
        panoInfo.add(pseudoText);
        panoCentral.setLayout(new BoxLayout(panoCentral, BoxLayout.Y_AXIS));
        panoCentral.add(panoInfo);
        panoCentral.add(panoInfo3);
        panoTitre.add(panoCentral, BorderLayout.CENTER);

        panoBouton.add(multijoueur);
        panoBouton.add(start);
        panoBouton.add(help);
        panoGlobal.setLayout(new BoxLayout(panoGlobal, BoxLayout.Y_AXIS));
        panoGlobal.add(panoTitre);
        panoGlobal.add(panoBouton);

        setContentPane(panoGlobal);
    }

    private void afficheWidgetMulti(){

        panoTitre.add(centreTitre, BorderLayout.NORTH);
        panoInfo.add(pseudoLabel);
        panoInfo2.add(pseudo2Label);
        panoInfo.add(pseudoText);
        panoInfo2.add(pseudo2Text);

        panoCentral.setLayout(new BoxLayout(panoCentral, BoxLayout.Y_AXIS));
        panoCentral.add(panoInfo);
        panoCentral.add(panoInfo2);
        panoCentral.add(panoInfo3);
        panoTitre.add(panoCentral, BorderLayout.CENTER);

        panoBouton.add(unJoueur);
        panoBouton.add(start);
        panoBouton.add(help);

        panoGlobal.setLayout(new BoxLayout(panoGlobal, BoxLayout.Y_AXIS));
        panoGlobal.add(panoTitre);
        panoGlobal.add(panoBouton);

        setContentPane(panoGlobal);
    }

    public static void main(String[] args) {
        System.out.println("run");
        Launcher f = new Launcher();
    }
}
