import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;


class Grille extends JPanel {

    private int modeJeu = 1;

    private int pointJoueur1 = 0;

    public Serpent snake, snake2;
    public Point tete;
    public Point queue;
    public Fruit pomme;
    public ArrayList<Obstacle> tabObstacle;

    // définie le niveau du joueur et défini en fonction de cela la vitesse du serpent
    public int niveau;
    public int vitesse;

    public int nbObstacle;
    public int tabNbObstacle[] = {0,10,15,20,25,7,8,9,10};

    boolean pause;
    private Timer timer;
    public enum EtatJeu {
        JOUER, PAUSE, GAMEOVER
    }
    public EtatJeu etat = EtatJeu.PAUSE;

    public ArrayList<String> messageMort;

    private Image imagePomme;
    private Image imageTete;
    private Image imageCorps;


    public Grille(){

        messageMort = new ArrayList<String>();

        setBackground(Color.black);
        loadImages();
        niveau = 1;
        vitesse = 100;
        nbObstacle = tabNbObstacle[niveau];
        pause = true;
        timer = new Timer();

        snake = new Serpent();
        tete = new Point();         // deux points qui serviront à recuperer les
        queue = new Point();        // coordonnees de la tete et de la queue du serpent

        pomme = new Fruit();
        gestionObstacle(nbObstacle);

    }

    public Grille(int modeJeu){

        this.modeJeu = modeJeu;

        messageMort = new ArrayList<String>();

        setBackground(Color.black);
        loadImages();
        niveau = 1;
        vitesse = 100;
        nbObstacle = tabNbObstacle[niveau];
        pause = true;
        timer = new Timer();

        snake = new Serpent();
        tete = new Point();         // deux points qui serviront à recuperer les
        queue = new Point();        // coordonnees de la tete et de la queue du serpent

        snake2 = new Serpent();
        tete = new Point();
        queue = new Point();

        pomme = new Fruit();
        gestionObstacle(nbObstacle);

    }

    public void resetGrille(int niv, int pJou1){

        // supprimer l'ancien timer
        timer.cancel();

        niveau = niv;
        vitesse = vitesse - 10 ;
        nbObstacle = tabNbObstacle[niveau];     // ressors d'un tableau le nombre d'obstacle à mettre dans le niveau
        pointJoueur1 = pJou1;
        pause = true;
        etat = EtatJeu.PAUSE;

        timer = new Timer();
        snake = new Serpent();
        tete = new Point();         // deux points qui serviront à recuperer les
        queue = new Point();        // coordonnees de la tete et de la queue du serpent

        snake2 = new Serpent();
        tete = new Point();
        queue = new Point();

        pomme = new Fruit();
        gestionObstacle(nbObstacle);
    }

    private void loadImages() {

        URL image1 = getClass().getResource("image/apple.png");
/*        URL image2 = getClass().getResource("image/snake.png");
        URL image3 = getClass().getResource("image/dot.png");*/
        try {
            imagePomme = ImageIO.read(image1);
            imagePomme = imagePomme.getScaledInstance(10, 10, imagePomme.SCALE_DEFAULT);
/*            imageTete = ImageIO.read(image2);
            imageTete = imageTete.getScaledInstance(10, 10, imageTete.SCALE_DEFAULT);
            imageCorps = ImageIO.read(image3);
            imageCorps = imageCorps.getScaledInstance(10, 10, imageCorps.SCALE_DEFAULT);*/
        } catch (IOException e){
            e.printStackTrace();
        }

//        ImageIcon iid = new ImageIcon("image/dot.png");
//        imageCorps = iid.getImage();
//        imageCorps = imageCorps.getScaledInstance(20, 20, imageCorps.SCALE_DEFAULT);
//        ImageIcon iia = new ImageIcon("image/apple.png");
//        imagePomme = iia.getImage();
//        imagePomme = imagePomme.getScaledInstance(20, 20, imagePomme.SCALE_DEFAULT);
//        ImageIcon iih = new ImageIcon("image/snake.png");
//        imageTete = iih.getImage();
//        imageTete = imageTete.getScaledInstance(20, 20, imageTete.SCALE_DEFAULT);
    }

    public void jouer(){

        TimerTask timerTask = new TimerTask() {
            //cette méthode est appelée par le timer chaque fois que celui-ci se déclenche
            public void run() {
                if (modeJeu == 1 ){
                    snake.avancerSerpent();
                    gereColision(snake);
                    gameOver();
                    repaint();           //on redessine tous les éléments de la grille
                }else {
                    snake.avancerSerpent();
                    snake2.avancerSerpent();
                    gereColision(snake);
                    gereColision(snake2);
                    gameOver();
                    repaint();
                }

            }
        };

        etat = EtatJeu.JOUER;
        timer.schedule(timerTask,0,vitesse);
    }

    public void pause(){

        etat = EtatJeu.PAUSE;
        repaint();
        timer.cancel();
        timer = new Timer();
    }

    public void gameOver(){

        if (messageMort.size() > 1){
            etat = EtatJeu.GAMEOVER;
            repaint();
            timer.cancel();
        }
    }

    public void touche(char t) {

        // commande pour le joueur 1 : les fleches
        if(t =='q' && pause == false){
            snake.tourne(1);
        }
        if(t =='d' && pause == false){
            snake.tourne(2);
        }

        // commande pour le joueur 2 : les nombres
        if(t =='4'){
            snake2.tourne(1);
        }
        if(t =='6'){
            snake2.tourne(2);
        }

        // commande pour mettre en pause le jeu : touche espace
        if(t =='p'){

            if(pause){
                jouer();
            } else {
                pause();
            }
            pause = !pause;
        }
    }

    public void gestionObstacle(int nbObstacle){

        tabObstacle = new ArrayList<Obstacle>();
        Obstacle mur;

        for (int i=0; i < nbObstacle ; i++){
            while(true){
                mur = new Obstacle();
                for(Point serpent : snake.getList()){                   // condition pour vérifier si la pomme n'apparait
                    if(mur.p.x == serpent.x && mur.p.y == serpent.y){   // pas sur le snake, sinon on recommence
                        mur = new Obstacle();
                    }
                }
                if (modeJeu == 2){
                    for(Point serpent : snake2.getList()){                   // condition pour vérifier si la pomme n'apparait
                        if(mur.p.x == serpent.x && mur.p.y == serpent.y){   // pas sur le snake, sinon on recommence
                            mur = new Obstacle();
                        }
                    }
                }
                if (mur.p.x == pomme.p.x && mur.p.y == pomme.p.y){
                    mur = new Obstacle();
                }
                break;
            }
            tabObstacle.add(mur);
        }
    }


    // methode qui teste si le serpent touche quelque chose
    public void gereColision(Serpent s){

        tete = s.getList().get(0);
        boolean premier = true;               // variable permettant de ne pas exit lors de la création

        // pour vérifier si il ne se touche pas lui-même
        for (Point p : s.getList() ) {
            if (premier){
                premier = false;
            }
            else {
                if(p.x == tete.x && p.y == tete.y){
                    messageMort.add("Le joueur s'est mangé la queu");
                    messageMort.add("score joueur : " + snake.score);

                    System.out.println("Le joueur s'est mangé la queue");
                    System.out.println("score joueur : "+ snake.score);

//                    System.exit(0);
                }
            }
        }

        // pour vérifier si il ne va pas a l'exterieur du plateau
        if (tete.x < 10 || tete.y > 560 || tete.x > 790 || tete.y < 10){
            messageMort.add("Le joueur s'est pris un mur");
            messageMort.add("score joueur : " + snake.score);

            System.out.println("Le joueur s'est pris un mur");
            System.out.println("score joueur : " + snake.score);

//            System.exit(0);
        }

        // pour vérifier si il n'est pas sur une pomme
        if(tete.x == pomme.p.x && tete.y == pomme.p.y){
            while(true){
                for(Point serpent : s.getList()){                   // condition pour vérifier si la pomme n'apparait
                    if(pomme.p.x == serpent.x && pomme.p.y == serpent.y){   // pas sur le snake, sinon on recommence
                        pomme = new Fruit();
                    }
                }
                break;
            }
            s.getList().add(new Point(queue.x, queue.y));   // on ajoute un point a notre serpent a la fin de celui-ci
            s.score ++;                                     // ajoute 1 au score
            System.out.println(s.score);
            niveauTermine(s);
        }

        // pour vérifier si il ne touche pas un obstacle
        for (Obstacle obstacle : tabObstacle ) {
            if(obstacle.p.x == tete.x && obstacle.p.y == tete.y){
                messageMort.add("Le joueur s'est pris un obstacle");
                messageMort.add("score joueur : " + snake.score);

                System.out.println("Le joueur s'est pris obstacle");
                System.out.println("score joueur : "+ s.score);

//                System.exit(0);
            }
        }
    }

    public void niveauTermine(Serpent s){

        if (s.score == 5){
            resetGrille(niveau+1, pointJoueur1+1);
        }
    }

    //cette méthode dit comment on redessine
    //elle est appelée indirectement par un appel (repaint())
    //donc si on met repaint() dans le code, java appelle automatiquement
    //paintComponent
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        if(etat == EtatJeu.GAMEOVER){
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 15, 50));
            g.drawString("GAME OVER", 200 , 100);

//            g.setColor(Color.white);
//            g.setFont(new Font("Arial", 15, 40));
//            g.drawString("Niveau " + String.valueOf(niveau), 300 , 150);

            g.setColor(Color.white);
            g.setFont(new Font("Arial", 0, 20));
            g.drawString("Appuyer sur la touche Entrée pour retourner à la page d'accueil ", 80, 480);
            g.drawString("Appuyer sur Echap pour quitter", 225, 550);
        }

        if(etat == EtatJeu.PAUSE){
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 15, 50));
            g.drawString("PAUSE", 300 , 100);

            g.setColor(Color.white);
            g.setFont(new Font("Arial", 15, 40));
            g.drawString("Niveau " + String.valueOf(niveau), 300 , 150);

            g.setColor(Color.red);
            g.setFont(new Font("Arial", 0, 19));
            g.drawString("<- pour aller à gauche", 180, 300);
            g.drawString("-> pour aller à droite", 420, 300);

            g.setColor(Color.white);
            g.setFont(new Font("Arial", 0, 20));
            g.drawString("Appuyer sur Espace pour démarrer", 225, 500);
            g.drawString("Appuyer sur Echap pour quitter", 225, 550);
        }

        tete = snake.getList().get(0);
        if (etat == EtatJeu.JOUER) {
            //parcours de la liste avec un itérateur
            //on dessine le serpent
//            for (Point p:snake.getList() ) {
////                if (p == tete) {
////                    g.drawImage(imageTete, p.x, p.y, this);
////                }else {
////                    g.drawImage(imageCorps, p.x, p.y, this);
//////                    g.setColor(Color.green);
//////                    g.fillOval(p.x,p.y,10,10);
////                }
//             }
            for (Point p:snake.getList() ) {
                g.setColor(Color.white);
                g.fillRect(p.x,p.y,10,10);
            }

            if (modeJeu == 2){
                for (Point p:snake2.getList() ) {
                    g.setColor(Color.red);
                    g.fillRect(p.x,p.y,10,10);
                }
            }
            //on dessine la pomme dans la fenetre
//            g.setColor(Color.white);
//            g.fillOval(pomme.p.x, pomme.p.y,10,10);
            g.drawImage(imagePomme, pomme.p.x, pomme.p.y, this);

            for (Obstacle obstacle : tabObstacle ) {
                g.setColor(Color.gray);
                g.fillRect(obstacle.p.x,obstacle.p.y,10,10);
            }


        }
    }

}
