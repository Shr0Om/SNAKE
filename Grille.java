import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;


class Grille extends JPanel {

    private int modeJeu = 1;

    private MeilleurScore meilleurScore = new MeilleurScore();

    private Serpent snake, snake2;
    private Point tete1, tete2;
    private Point queue1, queue2;

    private Fruit pomme;
    private ArrayList<Obstacle> tabObstacle;

    // définie le niveau du joueur et défini en fonction de cela la vitesse du serpent
    private int niveau;
    private int vitesse;
    public int nbObstacle;
    public int tabNbObstacle[] = {0,10,15,20,25,7,8,9,10};
    private int tempPomme = 0;

    private Bonus bonus;
    private int apparitionBonus = 0;
    private int tempsBonus = 0;
    private int tempsInversion = 0;
    private boolean bonusOuPasBonus = false;
    private boolean malusInversion = false;
    private int dureeBonus = 0;
    private boolean priseBonus = false;


    boolean pause;
    private Timer timer;
    public enum EtatJeu {
        JOUER, PAUSE, GAMEOVER
    }
    public EtatJeu etat = EtatJeu.PAUSE;

    public ArrayList<String> messageMort;

    private Image imageBonus, imagePomme, imageTeteBas, imageTeteDroite, imageTeteGauche, imageTeteHaute,
            imageQueueHaut, imageQueueBas, imageQueueDroite, imageQueueGauche, imageCorpsHaut,
            imageCorpsCote, imageCoudeHaut, imageCoudeBas, imageCoudeDroit, imageCoudeGauche, imageTeteBas2,
            imageTeteDroite2, imageTeteGauche2, imageTeteHaute2, imageQueueHaut2, imageQueueBas2, imageQueueDroite2,
            imageQueueGauche2, imageCorpsHaut2, imageCorpsCote2, imageCoudeHaut2, imageCoudeBas2, imageCoudeDroit2,
            imageCoudeGauche2, imageObstacle;


    public Grille(int modeJeu, String pseudo){

        messageMort = new ArrayList<String>();
        this.modeJeu = modeJeu;

        setBackground(Color.white);
        loadImages();
        niveau = 1;
        vitesse = 100;
        nbObstacle = tabNbObstacle[niveau];

        // nombreAleatoire represente le temps pour l'apparition d'un bonus dans la grille
        apparitionBonus = 50 + (int)(Math.random() * ((500 - 50) + 1));
        System.out.println(apparitionBonus);

        pause = true;
        timer = new Timer();

        snake = new Serpent(pseudo);
        tete1 = new Point();         // deux points qui serviront à recuperer les
        queue1 = new Point();        // coordonnees de la tete et de la queue du serpent

        pomme = new Fruit();
        gestionObstacle(nbObstacle);

    }

    public Grille(int modeJeu, String pseudo, String pseudo2){

        this.modeJeu = modeJeu;

        messageMort = new ArrayList<String>();

        setBackground(Color.white);
        loadImages();
        niveau = 1;
        vitesse = 100;
        nbObstacle = tabNbObstacle[niveau];
        pause = true;
        int apparitionBonus = 0;
        // nombreAleatoire represente le temps pour l'apparition d'un bonus dans la grille
        apparitionBonus = 1000 + (int)(Math.random() * ((5000 - 1000) + 1));
        System.out.println(apparitionBonus);

        timer = new Timer();

        snake = new Serpent(pseudo);
        tete1 = new Point();         // deux points qui serviront à recuperer les
        queue1 = new Point();        // coordonnees de la tete et de la queue du serpent

        snake2 = new Serpent(pseudo2);
        tete2 = new Point();
        queue2 = new Point();

        pomme = new Fruit();
        gestionObstacle(nbObstacle);
    }



    public void resetGrille(int niv, String pseudo){

        // supprimer l'ancien timer
        timer.cancel();

        niveau = niv;
        vitesse = vitesse - 10 ;
        nbObstacle = tabNbObstacle[niveau];     // ressors d'un tableau le nombre d'obstacle à mettre dans le niveau
        pause = true;
        etat = EtatJeu.PAUSE;
        int apparitionBonus = 0;
        // nombreAleatoire represente le temps pour l'apparition d'un bonus dans la grille
        apparitionBonus = 1000 + (int)(Math.random() * ((5000 - 1000) + 1));
        System.out.println(apparitionBonus);

        timer = new Timer();

        snake = new Serpent(pseudo, snake.getScore());
        tete1 = new Point();         // deux points qui serviront à recuperer les
        queue1 = new Point();        // coordonnees de la tete et de la queue du serpent

        if (modeJeu == 2){
            snake2 = new Serpent(pseudo, snake2.getScore());
            tete2 = new Point();
            queue2 = new Point();
        }

        pomme = new Fruit();
        gestionObstacle(nbObstacle);
    }

    private void loadImages() {

        URL image1 = getClass().getResource("image/apple.png");
        URL image16 = getClass().getResource("image/obstacle.png");
        URL image17 = getClass().getResource("image/bonus.png");
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
        URL image12 = getClass().getResource("image/coudeDroit.png");
        URL image13 = getClass().getResource("image/coudeGauche.png");
        URL image14 = getClass().getResource("image/coudeJsp.png");
        URL image15 = getClass().getResource("image/coudeJsp2.png");

        URL image18 = getClass().getResource("image/teteHaut2.png");
        URL image19 = getClass().getResource("image/teteBas2.png");
        URL image20 = getClass().getResource("image/teteDroite2.png");
        URL image21 = getClass().getResource("image/teteGauche2.png");
        URL image22 = getClass().getResource("image/queueBas2.png");
        URL image23 = getClass().getResource("image/queueHaut2.png");
        URL image24 = getClass().getResource("image/queueDroite2.png");
        URL image25 = getClass().getResource("image/queueGauche2.png");
        URL image26 = getClass().getResource("image/corpsHaut2.png");
        URL image27 = getClass().getResource("image/corpsCote2.png");
        URL image28 = getClass().getResource("image/coudeDroit2.png");
        URL image29 = getClass().getResource("image/coudeGauche2.png");
        URL image30 = getClass().getResource("image/coudeJsp3.png");
        URL image31 = getClass().getResource("image/coudeJsp4.png");

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
            imageObstacle = ImageIO.read(image16);
            imageObstacle = imageObstacle.getScaledInstance(10, 10, imageObstacle.SCALE_DEFAULT);
            imageCoudeDroit = ImageIO.read(image12);
            imageCoudeDroit = imageCoudeDroit.getScaledInstance(10, 10, imageCoudeDroit.SCALE_DEFAULT);
            imageCoudeGauche = ImageIO.read(image13);
            imageCoudeGauche = imageCoudeGauche.getScaledInstance(10, 10, imageCoudeGauche.SCALE_DEFAULT);
            imageCoudeBas = ImageIO.read(image14);
            imageCoudeBas = imageCoudeBas.getScaledInstance(10, 10, imageCoudeBas.SCALE_DEFAULT);
            imageCoudeHaut = ImageIO.read(image15);
            imageCoudeHaut = imageCoudeHaut.getScaledInstance(10, 10, imageCoudeHaut.SCALE_DEFAULT);

            imageTeteHaute2 = ImageIO.read(image18);
            imageTeteHaute2 = imageTeteHaute2.getScaledInstance(10, 10, imageTeteHaute2.SCALE_DEFAULT);
            imageTeteBas2 = ImageIO.read(image19);
            imageTeteBas2 = imageTeteBas2.getScaledInstance(10, 10, imageTeteBas2.SCALE_DEFAULT);
            imageTeteDroite2 = ImageIO.read(image20);
            imageTeteDroite2 = imageTeteDroite2.getScaledInstance(10, 10, imageTeteDroite2.SCALE_DEFAULT);
            imageTeteGauche2 = ImageIO.read(image21);
            imageTeteGauche2 = imageTeteGauche2.getScaledInstance(10, 10, imageTeteGauche2.SCALE_DEFAULT);
            imageQueueBas2 = ImageIO.read(image22);
            imageQueueBas2 = imageQueueBas2.getScaledInstance(10, 10, imageQueueBas2.SCALE_DEFAULT);
            imageQueueHaut2 = ImageIO.read(image23);
            imageQueueHaut2 = imageQueueHaut2.getScaledInstance(10, 10, imageQueueHaut2.SCALE_DEFAULT);
            imageQueueDroite2 = ImageIO.read(image24);
            imageQueueDroite2 = imageQueueDroite2.getScaledInstance(10, 10, imageQueueDroite2.SCALE_DEFAULT);
            imageQueueGauche2 = ImageIO.read(image25);
            imageQueueGauche2 = imageQueueGauche2.getScaledInstance(10, 10, imageQueueGauche2.SCALE_DEFAULT);
            imageCorpsHaut2 = ImageIO.read(image26);
            imageCorpsHaut2 = imageCorpsHaut2.getScaledInstance(10, 10, imageCorpsHaut2.SCALE_DEFAULT);
            imageCorpsCote2 = ImageIO.read(image27);
            imageCorpsCote2 = imageCorpsCote2.getScaledInstance(10, 10, imageCorpsCote2.SCALE_DEFAULT);
            imageCoudeDroit2 = ImageIO.read(image28);
            imageCoudeDroit2 = imageCoudeDroit2.getScaledInstance(10, 10, imageCoudeDroit2.SCALE_DEFAULT);
            imageCoudeGauche2 = ImageIO.read(image29);
            imageCoudeGauche2 = imageCoudeGauche2.getScaledInstance(10, 10, imageCoudeGauche2.SCALE_DEFAULT);
            imageCoudeBas2 = ImageIO.read(image30);
            imageCoudeBas2 = imageCoudeBas2.getScaledInstance(10, 10, imageCoudeBas2.SCALE_DEFAULT);
            imageCoudeHaut2 = ImageIO.read(image31);
            imageCoudeHaut2 = imageCoudeHaut2.getScaledInstance(10, 10, imageCoudeHaut2.SCALE_DEFAULT);
            imageBonus = ImageIO.read(image17);
            imageBonus = imageBonus.getScaledInstance(10, 10, imageBonus.SCALE_DEFAULT);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void jouer(){

        TimerTask timerTask = new TimerTask() {
            //cette méthode est appelée par le timer chaque fois que celui-ci se déclenche
            public void run() {
                tempPomme ++;
                tempsBonus ++;
                tempsInversion ++;


                if (modeJeu == 1 ){
                    snake.avancerSerpent();
                    gereColision(snake);
                    gameOver();
                    repaint();           //on redessine tous les éléments de la grille
                } else {
                    snake.avancerSerpent();
                    snake2.avancerSerpent();
                    gereColision(snake);
                    gereColision(snake2);
                    collisionSnake(snake, snake2);
                    gameOver();
                    repaint();
                }

                if (tempPomme == 100){
                    pomme = new Fruit();
                    tempPomme = 0;
                }

                if (tempsInversion > 150){
                    malusInversion = false;
                    tempsInversion = 0;
                }
                if (tempsBonus == apparitionBonus){
                    bonus = new Bonus();
                    bonusOuPasBonus = true;
//                    priseBonus = true;
                }
                if (bonusOuPasBonus){
                    if (tempsBonus == 40){
                        bonusOuPasBonus = false;
                        tempsBonus = 0;
                    }
                }

                if (priseBonus){
                    dureeBonus ++;
                    System.out.println(dureeBonus);
                    if (dureeBonus == 50){
                        malusInversion = false;
                        dureeBonus = 0;
                        priseBonus = false;
                    }
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
            meilleurScore.ecrireScore(snake.getScore(), snake.getPseudoJoueur());
            if (modeJeu == 2){
                meilleurScore.ecrireScore(snake2.getScore(), snake2.getPseudoJoueur());
            }
            repaint();
            timer.cancel();
        }
    }

    public void touche(char t) {

        // commande pour le joueur 1 : les fleches
        if(t =='q' && pause == false){
            if (malusInversion && tempsInversion <150){
                snake.tourne(2);
            }else {
                snake.tourne(1);
                malusInversion = false;
                tempsInversion = 0;
            }
        }
        if(t =='d' && pause == false){
            if (malusInversion && tempsInversion <150){
                snake.tourne(1);
            }else {
                snake.tourne(2);
                malusInversion = false;
                tempsInversion = 0;
            }
        }

        // commande pour le joueur 2 : les nombres
        if(t =='4' && modeJeu == 2){
            snake2.tourne(1);
        }
        if(t =='6' && modeJeu == 2){
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
                        if(mur.p.x == serpent.x && mur.p.y == serpent.y){    // pas sur le snake, sinon on recommence
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

        Point tete = s.getList().get(0);
        Point queue = s.getList().get(s.getList().size()-1);

        boolean premier = true;               // variable permettant de ne pas exit lors de la création

        // pour vérifier si il ne se touche pas lui-même
        for (Point p : s.getList() ) {
            if (premier){
                premier = false;
            }
            else {
                if (p.x == tete.x && p.y == tete.y){
                    if (modeJeu == 1 ){
                        messageMort.add(s.getPseudoJoueur() + " s'est mangé la queue");
                        messageMort.add("Score : " + s.getScore());
                    }
                    if (modeJeu == 2 ){
                        messageMort.add(s.getPseudoJoueur() + " s'est mangé la queue");
                        messageMort.add(snake.getPseudoJoueur() + " : " + snake.getScore());
                        messageMort.add(snake2.getPseudoJoueur() + " : " + snake2.getScore());
                    }
                }
            }
        }

        // pour vérifier si il ne va pas a l'exterieur du plateau
        if (tete.x < 10 || tete.y > 510 || tete.x > 770 || tete.y < 10){
            if (modeJeu == 1 ){
                messageMort.add(s.getPseudoJoueur() + " s'est pris un mur");
                messageMort.add("Score : " + s.getScore());
            }
            if (modeJeu == 2 ){
                messageMort.add(s.getPseudoJoueur() + " s'est pris un mur");
                messageMort.add(snake.getPseudoJoueur() + " : " + snake.getScore());
                messageMort.add(snake2.getPseudoJoueur() + " : " + snake2.getScore());
            }
        }

        // pour vérifier si il n'est pas sur une pomme
        if(tete.x == pomme.p.x && tete.y == pomme.p.y){
            while(true){
                for(Point serpent : s.getList()){                   // condition pour vérifier si la pomme n'apparait
                    if(pomme.p.x == serpent.x && pomme.p.y == serpent.y){   // pas sur le snake, sinon on recommence
                        pomme = new Fruit();
                        tempPomme = 0;
                    }
                }
                for(Obstacle obstacle : tabObstacle ){                   // condition pour vérifier si la pomme n'apparait
                    if(pomme.p.x == obstacle.p.x && pomme.p.y == obstacle.p.y){   // pas sur un obstacle, sinon on recommence
                        pomme = new Fruit();
                        tempPomme = 0;
                    }
                }
                break;
            }
            s.getList().add(new Point(queue.x, queue.y));   // on ajoute un point a notre serpent a la fin de celui-ci
            s.setScore(s.getScore()+1);                                     // ajoute 1 au score
            s.getListeDirection().add(s.getListeDirection().get(s.getListeDirection().size()-1));
            System.out.println(s.getScore());
            niveauTermine(s);
        }

        // pour vérifier si il ne touche pas un obstacle
        for (Obstacle obstacle : tabObstacle ) {
            if(obstacle.p.x == tete.x && obstacle.p.y == tete.y){
                if (modeJeu == 1 ){
                    messageMort.add(s.getPseudoJoueur() + " s'est pris un obstacle");
                    messageMort.add("Score : " + s.getScore());
                }
                if (modeJeu == 2 ){
                    messageMort.add(s.getPseudoJoueur() + " s'est pris un obstacle");
                    messageMort.add(snake.getPseudoJoueur() + " : " + snake.getScore());
                    messageMort.add(snake2.getPseudoJoueur() + " : " + snake2.getScore());
                }
            }
        }

        // pour vérifier si il n'est pas sur un bonus
        if (bonusOuPasBonus){
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
    }

    public void collisionSnake(Serpent s1, Serpent s2){

        tete1 = s1.getList().get(0);
        tete2 = s2.getList().get(0);

        // vérifie si le serpent ne touche pas un autre serpent avec sa tête pour le Serpent 1
        for (Point p : s2.getList() ) {
            if(p.x == tete1.x && p.y == tete1.y){
                messageMort.add(s1.getPseudoJoueur() + " s'est pris l'ennemi");
                messageMort.add(s1.getPseudoJoueur() + " : " + s1.getScore());
                messageMort.add(s2.getPseudoJoueur() + " : " + s2.getScore());
            }
        }

        // vérifie si le serpent ne touche pas un autre serpent avec sa tête pour le Serpent 2
        for (Point p : s1.getList() ) {
            if(p.x == tete2.x && p.y == tete2.y){
                messageMort.add(s2.getPseudoJoueur() + " s'est pris l'ennemi");
                messageMort.add(s1.getPseudoJoueur() + " : " + s1.getScore());
                messageMort.add(s2.getPseudoJoueur() + " : " + s2.getScore());
            }
        }
    }

    public void effetBonus(int bonus){

        switch (bonus){
            case 1:
                vitesse += 10;
                System.out.println(vitesse);
                pause();
                jouer();
                break;
            case 2:
                malusInversion = true;
                break;
            case 3:
                vitesse += 10;
                System.out.println(vitesse);
                pause();
                jouer();
                break;
        }
    }


    public void niveauTermine(Serpent s){

        if (s.getScore() == niveau*10){
            resetGrille(niveau+1, s.getPseudoJoueur());
        }
    }

    //cette méthode dit comment on redessine
    //elle est appelée indirectement par un appel (repaint())
    //donc si on met repaint() dans le code, java appelle automatiquement
    //paintComponent
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        if(etat == EtatJeu.GAMEOVER){
//            ecrireScore();
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 15, 50));
            g.drawString("GAME OVER", 240 , 100);

            g.setColor(Color.black);
            g.setFont(new Font("Century Gothic", 15, 20));
            for (int i=0; i<messageMort.size(); i++){
                g.drawString(messageMort.get(i), 280 , 170+i*20);
            }

            g.setColor(Color.black);
            g.setFont(new Font("Century Gothic", 0, 20));
            g.drawString("Appuyer sur la touche Entrée pour retourner à la page d'accueil ", 80, 450);
            g.drawString("Appuyer sur Echap pour quitter", 225, 500);
        }

        if(etat == EtatJeu.PAUSE){
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 15, 50));
            g.drawString("PAUSE", 300 , 100);

            g.setColor(Color.black);
            g.setFont(new Font("Century Gothic", 15, 40));
            g.drawString("Niveau " + String.valueOf(niveau), 300 , 160);

            g.setColor(Color.red);
            g.setFont(new Font("Arial", 0, 19));
            g.drawString("Q pour aller à gauche", 180, 300);
            g.drawString("D pour aller à droite", 420, 300);

            g.setColor(Color.black);
            g.setFont(new Font("Century Gothic", 0, 20));
            g.drawString("Appuyer sur Espace pour démarrer", 225, 450);
            g.drawString("Appuyer sur Echap pour quitter le jeu", 225, 500);
        }

        tete1 = snake.getList().get(0);
        queue1 = snake.getList().get(snake.getList().size() - 1);

        if (etat == EtatJeu.JOUER) {

            g.setColor(Color.black);
            g.setFont(new Font("Century Gothic", 0, 19));
            g.drawString("Niveau : "+ niveau, 10, 30);
            g.setFont(new Font("Century Gothic", 0, 19));
            g.drawString("Score : "+ snake.getScore(), 10, 50);


            //parcours de la liste avec un itérateur
            //on dessine le serpent
            for (int i = 0; i < snake.getList().size() ; i++) {

                if (snake.getList().get(i) == tete1){
                    if (snake.getListeDirection().get(i) == 2){
                        g.drawImage(imageTeteHaute, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i) == 1){
                        g.drawImage(imageTeteGauche, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i) == 0){
                        g.drawImage(imageTeteBas, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i) == 3){
                        g.drawImage(imageTeteDroite, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }
                }

                if (snake.getList().get(i) == queue1) {
                    if (snake.getListeDirection().get(i-1) == 2){
                        g.drawImage(imageQueueBas, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i-1) == 1){
                        g.drawImage(imageQueueDroite, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i-1) == 0){
                        g.drawImage(imageQueueHaut, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i-1) == 3){
                        g.drawImage(imageQueueGauche, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }
                }

                if (snake.getList().get(i) != tete1 && snake.getList().get(i) != queue1) {
                    if (snake.getListeDirection().get(i) == 2 || snake.getListeDirection().get(i) == 0){
                        g.drawImage(imageCorpsHaut, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i) == 1 || snake.getListeDirection().get(i) == 3){
                        g.drawImage(imageCorpsCote, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i) == 1 && snake.getListeDirection().get(i-1) == 2 ){
                        g.drawImage(imageCoudeGauche, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }
                    if (snake.getListeDirection().get(i) == 1 && snake.getListeDirection().get(i-1) == 0 ){
                        g.drawImage(imageCoudeHaut, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i) == 2 && snake.getListeDirection().get(i-1) == 1 ){
                        g.drawImage(imageCoudeBas, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }
                    if (snake.getListeDirection().get(i) == 2 && snake.getListeDirection().get(i-1) == 3 ){
                        g.drawImage(imageCoudeHaut, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i) == 3 && snake.getListeDirection().get(i-1) == 0 ){
                        g.drawImage(imageCoudeBas, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }
                    if (snake.getListeDirection().get(i) == 3 && snake.getListeDirection().get(i-1) == 2 ){
                        g.drawImage(imageCoudeDroit, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }

                    if (snake.getListeDirection().get(i) == 0 && snake.getListeDirection().get(i-1) == 1 ){
                        g.drawImage(imageCoudeDroit, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }
                    if (snake.getListeDirection().get(i) == 0 && snake.getListeDirection().get(i-1) == 3 ){
                        g.drawImage(imageCoudeGauche, snake.getList().get(i).x, snake.getList().get(i).y, this);
                    }
                }
            }


            if (modeJeu == 2){

                tete2 = snake2.getList().get(0);
                queue2 = snake2.getList().get(snake2.getList().size() - 1);

                //parcours de la liste avec un itérateur
                //on dessine le serpent
                for (int i = 0; i < snake2.getList().size() ; i++) {

                    if (snake2.getList().get(i) == tete2){
                        if (snake2.getListeDirection().get(i) == 2){
                            g.drawImage(imageTeteHaute2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i) == 1){
                            g.drawImage(imageTeteGauche2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i) == 0){
                            g.drawImage(imageTeteBas2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i) == 3){
                            g.drawImage(imageTeteDroite2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }
                    }

                    if (snake2.getList().get(i) == queue2) {
                        if (snake2.getListeDirection().get(i-1) == 2){
                            g.drawImage(imageQueueBas2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i-1) == 1){
                            g.drawImage(imageQueueDroite2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i-1) == 0){
                            g.drawImage(imageQueueHaut2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i-1) == 3){
                            g.drawImage(imageQueueGauche2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }
                    }

                    if (snake2.getList().get(i) != tete2 && snake2.getList().get(i) != queue2) {
                        if (snake2.getListeDirection().get(i) == 2 || snake2.getListeDirection().get(i) == 0){
                            g.drawImage(imageCorpsHaut2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i) == 1 || snake2.getListeDirection().get(i) == 3){
                            g.drawImage(imageCorpsCote2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i) == 1 && snake2.getListeDirection().get(i-1) == 2 ){
                            g.drawImage(imageCoudeGauche2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }
                        if (snake2.getListeDirection().get(i) == 1 && snake2.getListeDirection().get(i-1) == 0 ){
                            g.drawImage(imageCoudeHaut2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i) == 2 && snake2.getListeDirection().get(i-1) == 1 ){
                            g.drawImage(imageCoudeBas2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }
                        if (snake2.getListeDirection().get(i) == 2 && snake2.getListeDirection().get(i-1) == 3 ){
                            g.drawImage(imageCoudeHaut2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i) == 3 && snake2.getListeDirection().get(i-1) == 0 ){
                            g.drawImage(imageCoudeBas2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }
                        if (snake2.getListeDirection().get(i) == 3 && snake2.getListeDirection().get(i-1) == 2 ){
                            g.drawImage(imageCoudeDroit2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }

                        if (snake2.getListeDirection().get(i) == 0 && snake2.getListeDirection().get(i-1) == 1 ){
                            g.drawImage(imageCoudeDroit2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }
                        if (snake2.getListeDirection().get(i) == 0 && snake2.getListeDirection().get(i-1) == 3 ){
                            g.drawImage(imageCoudeGauche2, snake2.getList().get(i).x, snake2.getList().get(i).y, this);
                        }
                    }
                }
            }
            //on dessine la pomme dans la fenetre
            g.drawImage(imagePomme, pomme.p.x, pomme.p.y, this);

            if (bonusOuPasBonus){
                g.drawImage(imageBonus,bonus.p.x,bonus.p.y,this);
            }

            for (Obstacle obstacle : tabObstacle ) {
                g.drawImage(imageObstacle,obstacle.p.x,obstacle.p.y,this);
            }
        }
    }


}
