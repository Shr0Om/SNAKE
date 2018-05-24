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
    public Bonus bonus;

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

    private Image imagePomme, imageTeteBas, imageTeteDroite, imageTeteGauche, imageTeteHaute,
            imageQueueHaut, imageQueueBas, imageQueueDroite, imageQueueGauche, imageCorpsHaut,
            imageCorpsCote, imageCoudeHaut, imageCoudeBas, imageCoudeDroit, imageCoudeGauche, imageObstacle;


    public Grille(){

        messageMort = new ArrayList<String>();

        setBackground(Color.white);
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
        bonus = new Bonus();
        gestionObstacle(nbObstacle);

    }

    public Grille(int modeJeu){

        this.modeJeu = modeJeu;

        messageMort = new ArrayList<String>();

        setBackground(Color.white);
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
        bonus = new Bonus();
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
        bonus = new Bonus();
        gestionObstacle(nbObstacle);
    }

    private void loadImages() {

        URL image1 = getClass().getResource("image/apple.png");
        URL image2 = getClass().getResource("image/teteHaut.png");
        URL image3 = getClass().getResource("image/teteBas.png");
        URL image4 = getClass().getResource("image/teteDroite.png");
        URL image5 = getClass().getResource("image/teteGauche.png");
        URL image6 = getClass().getResource("image/queueBas.png");
        URL image7 = getClass().getResource("image/queueHaut.png");
        URL image8 = getClass().getResource("image/queueDroite.png");
        URL image9 = getClass().getResource("image/queueGauche.png");
        URL image10 = getClass().getResource("image/corpsHaut.png");
        URL image11 = getClass().getResource("image/corpsCote.png");
        URL image12 = getClass().getResource("image/obstacle.png");

        try {
            imagePomme = ImageIO.read(image1);
            imagePomme = imagePomme.getScaledInstance(10, 10, imagePomme.SCALE_DEFAULT);
            imageTeteHaute = ImageIO.read(image2);
            imageTeteHaute = imageTeteHaute.getScaledInstance(10, 10, imageTeteHaute.SCALE_DEFAULT);
            imageTeteBas = ImageIO.read(image3);
            imageTeteBas = imageTeteBas.getScaledInstance(10, 10, imageTeteBas.SCALE_DEFAULT);
            imageTeteDroite = ImageIO.read(image4);
            imageTeteDroite = imageTeteDroite.getScaledInstance(10, 10, imageTeteDroite.SCALE_DEFAULT);
            imageTeteGauche = ImageIO.read(image5);
            imageTeteGauche = imageTeteGauche.getScaledInstance(10, 10, imageTeteGauche.SCALE_DEFAULT);
            imageQueueBas = ImageIO.read(image6);
            imageQueueBas = imageQueueBas.getScaledInstance(10, 10, imageQueueBas.SCALE_DEFAULT);
            imageQueueHaut = ImageIO.read(image7);
            imageQueueHaut = imageQueueHaut.getScaledInstance(10, 10, imageQueueHaut.SCALE_DEFAULT);
            imageQueueDroite = ImageIO.read(image8);
            imageQueueDroite = imageQueueDroite.getScaledInstance(10, 10, imageQueueDroite.SCALE_DEFAULT);
            imageQueueGauche = ImageIO.read(image9);
            imageQueueGauche = imageQueueGauche.getScaledInstance(10, 10, imageQueueGauche.SCALE_DEFAULT);
            imageCorpsHaut = ImageIO.read(image10);
            imageCorpsHaut = imageCorpsHaut.getScaledInstance(10, 10, imageCorpsHaut.SCALE_DEFAULT);
            imageCorpsCote = ImageIO.read(image11);
            imageCorpsCote = imageCorpsCote.getScaledInstance(10, 10, imageCorpsCote.SCALE_DEFAULT);
            imageObstacle = ImageIO.read(image12);
            imageObstacle = imageObstacle.getScaledInstance(10, 10, imageObstacle.SCALE_DEFAULT);
        } catch (IOException e){
            e.printStackTrace();
        }
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
                    collisionSnake(snake, snake2);
                    gameOver();
                    repaint();
                }
            }
        };

        etat = EtatJeu.JOUER;
        timer.schedule(timerTask,0,vitesse);
        System.out.println(vitesse);
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
                    messageMort.add("Le joueur s'est mangé la queue");
                    messageMort.add("score joueur : " + snake.score);

                    System.out.println("Le joueur s'est mangé la queue");
                    System.out.println("score joueur : "+ s.score);

                }
            }
        }

        // pour vérifier si il ne va pas a l'exterieur du plateau
        if (tete.x < 10 || tete.y > 560 || tete.x > 790 || tete.y < 10){
            messageMort.add("Le joueur s'est pris un mur");
            messageMort.add("score joueur : " + s.score);
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
            vitesse -= 10;
            jouer();
        }

        // pour vérifier si il ne touche pas un obstacle
        for (Obstacle obstacle : tabObstacle ) {
            if(obstacle.p.x == tete.x && obstacle.p.y == tete.y){
                messageMort.add("Le joueur s'est pris un obstacle");
                messageMort.add("score joueur : " + s.score);
            }
        }

        // pour vérifier si il n'est pas sur un bonus
        if(tete.x == bonus.p.x && tete.y == bonus.p.y){
            while(true){
                for(Point serpent : s.getList()){                   // condition pour vérifier si la pomme n'apparait
                    if(bonus.p.x == serpent.x && bonus.p.y == serpent.y){   // pas sur le snake, sinon on recommence
                        bonus = new Bonus();
                    }
                }
                break;
            }
            effetBonus(bonus.typeBonus);
        }
    }

    public void collisionSnake(Serpent s1, Serpent s2){

        Point tete1 = new Point();
        Point tete2 = new Point();

        tete1 = s1.getList().get(0);
        tete2 = s2.getList().get(0);

        // vérifie si le serpent ne touche pas un autre serpent avec sa tête pour le Serpent 1
        for (Point p : s2.getList() ) {
            if(p.x == tete1.x && p.y == tete1.y){
                messageMort.add("Le joueur 1 s'est pris l'ennemi");
                messageMort.add("score joueur 1 : " + s1.score);
                messageMort.add("score joueur 2 : " + s2.score);
            }
        }

        // vérifie si le serpent ne touche pas un autre serpent avec sa tête pour le Serpent 2
        for (Point p : s1.getList() ) {
            if(p.x == tete2.x && p.y == tete2.y){
                messageMort.add("Le joueur s'est pris l'ennemi");
                messageMort.add("score joueur 1 : " + s1.score);
                messageMort.add("score joueur 2 : " + s2.score);
            }
        }
    }

    public void effetBonus(int bonus){
        switch (bonus){
            case 1:
                vitesse += 10;
                System.out.println(vitesse);
                jouer();
                break;
            case 2:
                vitesse -= 10;
                System.out.println(vitesse);
                jouer();
                break;
            case 3:
                vitesse += 30;
                System.out.println(vitesse);
                jouer();
                break;
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
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 15, 50));
            g.drawString("GAME OVER", 200 , 100);

            g.setColor(Color.black);
            g.setFont(new Font("Arial", 15, 20));
            for (int i=0; i<messageMort.size(); i++){
                g.drawString(messageMort.get(i), 300 , 150);
            }


            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, 20));
            g.drawString("Appuyer sur la touche Entrée pour retourner à la page d'accueil ", 80, 480);
            g.drawString("Appuyer sur Echap pour quitter", 225, 550);
        }

        if(etat == EtatJeu.PAUSE){
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 15, 50));
            g.drawString("PAUSE", 300 , 100);

            g.setColor(Color.black);
            g.setFont(new Font("Arial", 15, 40));
            g.drawString("Niveau " + String.valueOf(niveau), 300 , 150);

            g.setColor(Color.red);
            g.setFont(new Font("Arial", 0, 19));
            g.drawString("<- pour aller à gauche", 180, 300);
            g.drawString("-> pour aller à droite", 420, 300);

            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, 20));
            g.drawString("Appuyer sur Espace pour démarrer", 225, 500);
            g.drawString("Appuyer sur Echap pour quitter", 225, 550);
        }

        tete = snake.getList().get(0);
        queue = snake.getList().get(snake.getList().size() - 1);

        if (etat == EtatJeu.JOUER) {
            //parcours de la liste avec un itérateur
            //on dessine le serpent
            for (Point p:snake.getList() ) {

                if (p == tete) {
                    if (snake.directionSerpent == 2) {
                        g.drawImage(imageTeteHaute, p.x, p.y, this);
                    }
                    if (snake.directionSerpent == 0) {
                        g.drawImage(imageTeteBas, p.x, p.y, this);
                    }
                    if (snake.directionSerpent == 1) {
                        g.drawImage(imageTeteGauche, p.x, p.y, this);
                    }
                    if (snake.directionSerpent == 3) {
                        g.drawImage(imageTeteDroite, p.x, p.y, this);
                    }
                }

                if (p == queue) {
                    if (snake.directionSerpent == 2) {
                        g.drawImage(imageQueueBas, p.x, p.y, this);
                    }
                    if (snake.directionSerpent == 0) {
                        g.drawImage(imageQueueHaut, p.x, p.y, this);
                    }
                    if (snake.directionSerpent == 1) {
                        g.drawImage(imageQueueDroite, p.x, p.y, this);
                    }
                    if (snake.directionSerpent == 3) {
                        g.drawImage(imageQueueGauche, p.x, p.y, this);
                    }
                }

                if (p != tete && p!= queue){
                    if (snake.directionSerpent == 2) {
                        g.drawImage(imageCorpsHaut, p.x, p.y, this);
                    }
                    if (snake.directionSerpent == 0) {
                        g.drawImage(imageCorpsHaut, p.x, p.y, this);
                    }
                    if (snake.directionSerpent == 1) {
                        g.drawImage(imageCorpsCote, p.x, p.y, this);
                    }
                    if (snake.directionSerpent == 3) {
                        g.drawImage(imageCorpsCote, p.x, p.y, this);
                    }
                }
            }
            if (modeJeu == 2){
                for (Point p:snake2.getList() ) {
                    g.setColor(Color.red);
                    g.fillRect(p.x,p.y,10,10);
                }
            }
            //on dessine la pomme dans la fenetre
            g.drawImage(imagePomme, pomme.p.x, pomme.p.y, this);

            g.fillRect(bonus.p.x, bonus.p.y, 10,10);

            for (Obstacle obstacle : tabObstacle ) {
                g.drawImage(imageObstacle,obstacle.p.x,obstacle.p.y,this);
            }
        }
    }

}
