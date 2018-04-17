import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;


class Grille extends JPanel {

    public Serpent snake;
    public Point tete;
    public Point queue;
    public Fruit pomme;
    public Obstacle obstacle;
    boolean pause = true;
    private Timer timer;

    public Grille(){
        setBackground(Color.black);
        timer = new Timer();
        snake = new Serpent();
        pomme = new Fruit();
        obstacle = new Obstacle();
        tete = new Point();         // deux points qui serviront à recuperer les
        queue = new Point();        // coordonnees de la tete et de la queue du serpent
    }

    public void touche(char t) {

        // commande pour le joueur 1 : les fleches
        if(t =='q'){
            snake.tourne(1);
        }
        if(t =='d'){
            snake.tourne(2);
        }

        // commande pour le joueur 2 : les nombres
        if(t =='4'){
        }
        if(t =='6'){
        }

        // commande pour mettre en pause le jeu : touche espace
        if(t =='p'){
            if(pause){
                pause = false;
                //démarrage du jeu
                timer.scheduleAtFixedRate(new TimerTask(){
                    //cette méthode est appelée par le timer chaque fois que celui-ci se déclenche
                    public void run(){
                        snake.avancerSerpent();
                        gereColision(snake);
                        repaint();           //on redessine tous les éléments de la grille
                    }
                },100,100);
                repaint();
            }
            else {
                pause = true;
            }
        }
    }


    // methode qui teste si le serpent touche quelque chose
    public void gereColision(Serpent s){

        tete = s.getList().get(0);
        boolean premier = true;               // variable permettant de ne pas exit lors de la création

        // pour vérifier si il ne se touche pas lui-même
        for (Point p : snake.getList() ) {
            if (premier){
                premier = false;
            }
            else {
                if(p.x == tete.x && p.y == tete.y){
                    System.out.println("Le joueur s'est mangé la queue... c'est bon ?");
                    System.out.println("score joueur : "+ snake.score);

                    System.exit(0);
                }
            }
        }

        // pour vérifier si il ne va pas a l'exterieur du plateau
        if (tete.x < 10 || tete.y > 560 || tete.x > 790 || tete.y < 10){
            System.out.println("Le joueur s'est mangé un mur... c'est bon ?");
            System.out.println("score joueur : "+ snake.score);

            System.exit(0);
        }

        // pour vérifier si il n'est pas sur une pomme
        if(tete.x == pomme.p.x && tete.y == pomme.p.y){
            while(true){
                for(Point serpent:snake.getList()){                   // condition pour vérifier si la pomme n'apparait
                    if(pomme.p.x == serpent.x && pomme.p.y == serpent.y){   // pas sur le snake, sinon on recommence
                        pomme = new Fruit();
                    }
                }
                break;
            }
            s.getList().add(new Point(queue.x, queue.y));   // on ajoute un point a notre serpent a la fin de celui-ci
            s.score ++;                                     // ajoute 1 au score
            System.out.println(s.score);
        }

        // pour vérifier si il ne touche pas un obstacle
//        for (Point p : obstacle.getList() ) {
//            if(p.x == tete.x && p.y == tete.y){
//                System.out.println("Le joueur s'est mangé un obstacle... c'est bon ?");
//                System.out.println("score joueur : "+ snake.score);
//
//                System.exit(0);
//            }
//        }
    }

    //cette méthode dit comment on redessine
    //elle est appelée indirectement par un appel (repaint())
    //donc si on met repaint() dans le code, java appelle automatiquement
    //paintComponent
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(pause == true){
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 15, 50));
            g.drawString("PAUSE", 300 , 100);

            g.setColor(Color.red);
            g.setFont(new Font("Arial", 0, 19));
            g.drawString("<- pour aller à gauche", 180, 300);
            g.drawString("-> pour aller à droite", 420, 300);


            g.setColor(Color.white);
            g.setFont(new Font("Arial", 0, 20));
            g.drawString("Appuyer sur Espace pour démarrer", 225, 500);
            g.drawString("Appuyer sur Echap pour quitter", 225, 550);
        }
        else {
            //parcours de la liste avec un itérateur
            //on dessine le serpent
            for (Point p:snake.getList() ) {
                g.setColor(Color.white);
                g.fillRect(p.x,p.y,10,10);
            }

            //on dessine la pomme dans la fenetre
            g.setColor(Color.white);
            g.fillOval(pomme.p.x, pomme.p.y,10,10);
        }
    }

}
