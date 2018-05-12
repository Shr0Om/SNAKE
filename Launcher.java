import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher extends JFrame {

    private JLabel pseudoLabel, difficulteLabel, titre;
    private JFrame frame3;
    private JPanel panoTitre, panoInfo, panoBouton, panoHelp, panoGlobal;
    private JButton start, help, multi;
    private JTextField pseudoText;
    private JComboBox<Integer> difficulteBox;
    private JOptionPane erreur;
    private JDialog erreurDialog;
    private JPanel centreTitre;

    protected Launcher() {

        creerWidget();
        afficheWidget();
        action();
        setSize(500,400);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void action() {

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (pseudoText.getText().isEmpty()){
                    erreur.showMessageDialog(start, "Veuillez entrer un pseudo", "Erreur",JOptionPane.ERROR_MESSAGE);
                    erreurDialog = erreur.createDialog(start, "Erreur");
                }else {
                    Snake frame = new Snake(1);
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        });

        multi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Snake frame = new Snake(2);
                frame.pack();
                frame.setVisible(true);
            }
        });

        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame3 = new JFrame("HELP");
                frame3.setVisible(true);
                frame3.setSize(400,400);
                JLabel label = new JLabel("mettre les touches ici");
                JPanel panel = new JPanel();
                frame3.add(panel);
                panel.add(label);
            }
        });
    }

    private void creerWidget() {

        titre = new JLabel("SNAKE");
        centreTitre = new JPanel();
        centreTitre.add(titre);
        titre.setFont(new Font(titre.getFont().getName(), titre.getFont().getStyle(), 30));
        pseudoLabel = new JLabel("Pseudo");
        difficulteLabel = new JLabel("Difficulté");

        start = new JButton("Play");
        help = new JButton("Help");
        multi = new JButton("Mode multijoueur");

        pseudoText = new JTextField();
        pseudoText.setColumns(10);

        Integer[] tabInt = new Integer[]{1, 2, 3, 4, 5};
        difficulteBox = new JComboBox<>(tabInt);

        panoTitre = new JPanel(new BorderLayout());
        panoInfo = new JPanel();
        panoBouton = new JPanel();
        panoHelp = new JPanel();
        panoGlobal = new JPanel();

        erreur = new JOptionPane();
    }

    private void afficheWidget(){

        panoTitre.add(centreTitre, BorderLayout.NORTH);
        panoInfo.add(pseudoLabel);
        panoInfo.add(pseudoText);
        panoInfo.add(difficulteLabel);
        panoInfo.add(difficulteBox);
        panoTitre.add(panoInfo, BorderLayout.CENTER);
        panoBouton.add(start);
        panoBouton.add(help);
        panoBouton.add(multi);
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
