import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Launcher extends JFrame {

    private JFrame parent = new JFrame();
    private Icon icon;
    private URL image = getClass().getResource("image/winner.png");
    private MeilleurScore meilleurScore = new MeilleurScore();

    private JPanel jPanel, jPanel1, jPanel2, jPanel3, jPanel4, jPanel5, jPanel6, jPanel7,
            jPanel8 ;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4 ;
    private JTextField jTextField1, jTextField2, jTextField3;
    private JButton jButton1, jButton2, jButton3, jButton4;
    private JOptionPane erreur;
    private JDialog erreurDialog;

    private boolean multi = false, unPlayer = true, verification;

    public Launcher() {
        initComponent();
        afficheWidget();
        action();
        setSize(450,250);
        setLocation(400,200);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void launcherSolo() {
        initComponent();
        afficheWidget();
        action();
        setSize(450,250);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void launcherMulti() {
        initComponent();
        afficheWidgetMulti();
        action();
        setSize(450,300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void action() {
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                verification = true;
                if (multi && (jTextField2.getText().isEmpty() || jTextField3.getText().isEmpty()) || (!multi && jTextField1.getText().isEmpty())){
                    verification = false;
                    erreur.showMessageDialog(jButton1, "Veuillez entrer un pseudo", "Erreur",JOptionPane.ERROR_MESSAGE);
                    erreurDialog = erreur.createDialog(jButton1, "Erreur");
                }
                if(unPlayer && verification){
                    Snake frame = new Snake(1, jTextField1.getText(), "");
                    frame.pack();
                    frame.setVisible(true);
                }
                if (multi && verification){
                    Snake frame = new Snake(2, jTextField2.getText(), jTextField3.getText());
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        });


        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String[] options = {"OK"};
                JOptionPane jOptionPane = new JOptionPane();

                String messageMeilleurScore = "Meilleurs Scores : \n\n Premier de " + meilleurScore.getMeilleurPseudo().get(0) + " :    " + meilleurScore.getMeilleurScore().get(0) + " \n Deuxième de " + meilleurScore.getMeilleurPseudo().get(1) + " :   " + meilleurScore.getMeilleurScore().get(1) + " \n Troisième de " + meilleurScore.getMeilleurPseudo().get(2) + " :    " + meilleurScore.getMeilleurScore().get(2) + "\n";
                int x = jOptionPane.showOptionDialog(parent, messageMeilleurScore + "\n", "Meilleur Score",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);

                if (x == 0 ){
                    if (multi){
                        launcherMulti();
                    }else {
                        launcherSolo();
                    }
                }
            }
        });

        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                multi = true;
                unPlayer = false;
                launcherMulti();
            }
        });

        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                unPlayer = true;
                multi = false;
                launcherSolo();
            }
        });
    }

    private void initComponent() {

        icon = new ImageIcon(image);

        jPanel = new JPanel(new BorderLayout());

        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(36, 47, 65));
        jPanel2 = new JPanel();
        jPanel2.setBackground(new Color(36, 47, 65));
        jPanel3 = new JPanel();
        jPanel3.setBackground(new Color(36, 47, 65));
        jPanel4 = new JPanel();
        jPanel4.setBackground(new Color(36, 47, 65));
        jPanel5 = new JPanel();
        jPanel5.setBackground(new Color(36, 47, 65));
        jPanel6 = new JPanel(new BorderLayout());
        jPanel6.setBackground(new Color(36, 47, 65));

        jLabel1 = new JLabel();
        jLabel1.setFont(new Font("Century Gothic", 0, 35));
        jLabel1.setForeground(new Color(212, 212, 212));
        jLabel1.setText("             SNAKE             ");
        jLabel1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255)));

        jLabel2 = new JLabel();
        jLabel2.setFont(new Font("Century Gothic", 0, 20));
        jLabel2.setForeground(new Color(255,255,255));
        jLabel2.setText("Pseudo : ");

        jTextField1 = new JTextField();
        jTextField1.setColumns(10);
        jTextField1.setBackground(new Color(36,47,65));
        jTextField1.setFont(new Font("Century Gothic", 0, 13));
        jTextField1.setForeground(new Color(255,255,255));
        jTextField1.setText("");
        jTextField1.setOpaque(false);
        jTextField1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(166, 166, 166)));


        jLabel3 = new JLabel();
        jLabel3.setFont(new Font("Century Gothic", 0, 20));
        jLabel3.setForeground(new Color(255,255,255));
        jLabel3.setText("Pseudo Joueur 1 : ");

        jTextField3 = new JTextField();
        jTextField3.setColumns(10);
        jTextField3.setBackground(new Color(36,47,65));
        jTextField3.setFont(new Font("Century Gothic", 0, 13));
        jTextField3.setForeground(new Color(255,255,255));
        jTextField3.setText("");
        jTextField3.setOpaque(false);
        jTextField3.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(166, 166, 166)));


        jLabel4 = new JLabel();
        jLabel4.setFont(new Font("Century Gothic", 0, 20));
        jLabel4.setForeground(new Color(255,255,255));
        jLabel4.setText("Pseudo Joueur 2 : ");

        jTextField2 = new JTextField();
        jTextField2.setColumns(10);
        jTextField2.setBackground(new Color(36,47,65));
        jTextField2.setFont(new Font("Century Gothic", 0, 13));
        jTextField2.setForeground(new Color(255,255,255));
        jTextField2.setText("");
        jTextField2.setOpaque(false);
        jTextField2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(166, 166, 166)));


        jButton1 = new JButton();
        jButton1.setText("Jouer");
        jButton1.setFont(new Font("Century Gothic", 0, 22));
        jButton1.setForeground(new Color(191, 186, 186));
        jButton1.setBackground(new Color(105, 50, 104));
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        jButton1.setBorder(compound);
        jButton1.setFocusPainted(false);

        jButton2 = new JButton();
        jButton2.setText("Info");
        jButton2.setFont(new Font("Century Gothic", 0, 13));
        jButton2.setForeground(new Color(191, 186, 186));
        jButton2.setBackground(new Color(92, 44, 91));
        jButton2.setBorder(compound);
        jButton2.setFocusPainted(false);

        jButton3 = new JButton();
        jButton3.setText("Multi");
        jButton3.setFont(new Font("Century Gothic", 0, 13));
        jButton3.setForeground(new Color(191, 186, 186));
        jButton3.setBackground(new Color(92, 44, 91));
        jButton3.setBorder(compound);
        jButton3.setFocusPainted(false);

        jButton4 = new JButton();
        jButton4.setText("Solo ");
        jButton4.setFont(new Font("Century Gothic", 0, 13));
        jButton4.setForeground(new Color(191, 186, 186));
        jButton4.setBackground(new Color(92, 44, 91));
        jButton4.setBorder(compound);
        jButton4.setFocusPainted(false);


        erreur = new JOptionPane();
    }

    private void afficheWidget(){

        jPanel1.add(jLabel1);
        jPanel2.add(jLabel2);
        jPanel2.add(jTextField1);
        jPanel3.add(jButton2);
        jPanel3.add(jButton1);
        jPanel3.add(jButton3);

        jPanel.add(jPanel1, BorderLayout.NORTH);
        jPanel.add(jPanel2, BorderLayout.CENTER);
        jPanel.add(jPanel3, BorderLayout.SOUTH);

        setContentPane(jPanel);

    }

    private void afficheWidgetMulti(){


        jPanel1.add(jLabel1);
        jPanel2.add(jLabel3);
        jPanel2.add(jTextField2);
        jPanel4.add(jLabel4);
        jPanel4.add(jTextField3);
        jPanel3.add(jButton2);
        jPanel3.add(jButton1);
        jPanel3.add(jButton4);

        jPanel5.setLayout(new BoxLayout(jPanel5, BoxLayout.Y_AXIS));

        jPanel5.add(jPanel2);
        jPanel5.add(jPanel4);

        jPanel.add(jPanel1, BorderLayout.NORTH);
        jPanel.add(jPanel5, BorderLayout.CENTER);
        jPanel.add(jPanel3, BorderLayout.SOUTH);


        setContentPane(jPanel);

    }

    public static void main(String[] args) {
        System.out.println("run");
        Launcher f = new Launcher();
    }
}
