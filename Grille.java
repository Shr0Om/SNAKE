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

    private int pointJoueur1 = 0;
    private ArrayList<Integer> meilleurScore = new ArrayList<Integer>();
    private ArrayList<String> meilleurPseudo = new ArrayList<String>();
    private ArrayList<Point> listePoints = new ArrayList<Point>();

    public Serpent snake, snake2;
    public Point tete;
    public Point queue;
    public Fruit pomme;
    public ArrayList<Obstacle> tabObstacle;
    public Bonus bonus;
    public  int nombreMouv = 0;

    // définie le niveau du joueur et défini en fonction de cela la vitesse du serpent
    public int niveau;
    public int vitesse;
    public int sens = -1;
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


    public Grille(String pseudo){

        messageMort = new ArrayList<String>();

        setBackground(Color.white);
        loadImages();
        niveau = 1;
        vitesse = 100;
        nbObstacle = tabNbObstacle[niveau];

        pause = true;
        timer = new Timer();

        snake = new Serpent(pseudo);
        tete = new Point();         // deux points qui serviront à recuperer les
        queue = new Point();        // coordonnees de la tete et de la queue du serpent
        listePoints.add(snake.getList().get(0));

        pomme = new Fruit();
        bonus = new Bonus();
        gestionObstacle(nbObstacle);

    }

    public Grille(int modeJeu, String pseudo){

        this.modeJeu = modeJeu;

        messageMort = new ArrayList<String>();

        setBackground(Color.white);
        loadImages();
        niveau = 1;
        vitesse = 100;
        nbObstacle = tabNbObstacle[niveau];
        pause = true;
        timer = new Timer();

        snake = new Serpent(pseudo);
        tete = new Point();         // deux points qui serviront à recuperer les
        queue = new Point();        // coordonnees de la tete et de la queue du serpent

        snake2 = new Serpent(pseudo);
        tete = new Point();
        queue = new Point();
        listePoints.add(snake.getList().get(0));

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
//        pointJoueur1 = pJou1;
        pause = true;
        etat = EtatJeu.PAUSE;

        timer = new Timer();

        snake = new Serpent(snake.getScore());
        tete = new Point();         // deux points qui serviront à recuperer les
        queue = new Point();        // coordonnees de la tete et de la queue du serpent
        listePoints.add(snake.getList().get(0));

        if (modeJeu == 2){
            snake2 = new Serpent(snake2.getScore());
            tete = new Point();
            queue = new Point();
        }

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
        URL image12 = getClass().getResource("image/coudeDroit.png");
        URL image13 = getClass().getResource("image/coudeGauche.png");
        URL image14 = getClass().getResource("image/coudeJsp.png");
        URL image15 = getClass().getResource("image/coudeJsp2.png");
        URL image16 = getClass().getResource("image/obstacle.png");

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
            listePoints.add(snake.getList().get(0));
            nombreMouv++;

        }
        if(t =='d' && pause == false){
            snake.tourne(2);
            listePoints.add(snake.getList().get(0));
            nombreMouv++;
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
                    messageMort.add("score joueur : " + snake.getScore());

                    System.out.println("Le joueur s'est mangé la queue");
                    System.out.println("score joueur : "+ s.getScore());

                }
            }
        }

        // pour vérifier si il ne va pas a l'exterieur du plateau
        if (tete.x < 10 || tete.y > 560 || tete.x > 790 || tete.y < 10){
            messageMort.add("Le joueur s'est pris un mur");
            messageMort.add("score joueur : " + s.getScore());
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
            s.setScore(s.getScore()+1);                                     // ajoute 1 au score
            System.out.println(s.getScore());
            niveauTermine(s);
        }

        // pour vérifier si il ne touche pas un obstacle
        for (Obstacle obstacle : tabObstacle ) {
            if(obstacle.p.x == tete.x && obstacle.p.y == tete.y){
                messageMort.add("Le joueur s'est pris un obstacle");
                messageMort.add("score joueur : " + s.getScore());
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
                messageMort.add("score joueur 1 : " + s1.getScore());
                messageMort.add("score joueur 2 : " + s2.getScore());
            }
        }

        // vérifie si le serpent ne touche pas un autre serpent avec sa tête pour le Serpent 2
        for (Point p : s1.getList() ) {
            if(p.x == tete2.x && p.y == tete2.y){
                messageMort.add("Le joueur s'est pris l'ennemi");
                messageMort.add("score joueur 1 : " + s1.getScore());
                messageMort.add("score joueur 2 : " + s2.getScore());
            }
        }
    }

    public void effetBonus(int bonus){
        switch (bonus){
            case 1:
                vitesse += 50;
                System.out.println(vitesse);
                pause();
                jouer();
                break;
            case 2:
                vitesse += 50;
                System.out.println(vitesse);
                pause();
                jouer();
                break;
            case 3:
                vitesse += 50;
                System.out.println(vitesse);
                pause();
                jouer();
                break;
        }
    }


    public void niveauTermine(Serpent s){

        if (s.getScore() == niveau*10){
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
            ecrireScore();
            g.setColor(Color.black);
            g.setFont(new Font("Arial", 15, 50));
            g.drawString("GAME OVER", 200 , 100);

            g.setColor(Color.black);
            g.setFont(new Font("Arial", 15, 20));
            for (int i=0; i<messageMort.size(); i++){
                g.drawString(messageMort.get(i), 300 , 150+i*20);
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

            g.setColor(Color.black);
            g.setFont(new Font("Arial", 0, 19));
            g.drawString("Score : "+ snake.getScore(), 10, 30);

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
            }
            if (listePoints.size() > 1){
                mouvementSerpent(g, listePoints.get(nombreMouv), listePoints.get(nombreMouv -1));
            }else{
                mouvementSerpent(g, listePoints.get(nombreMouv), listePoints.get(nombreMouv));
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

    public void mouvementSerpent(Graphics g, Point point, Point point2){
        for (Point p:snake.getList()) {
            if (p == queue){
                if (snake.directionSerpent == 2){
                    if (p.x < point.x && p.y == point2.y){
                        g.drawImage(imageQueueGauche, p.x, p.y, this);
                        sens = 2;
                    }
                    if (p.x > point.x && p.y == point2.y){
                        sens = 1;
                        g.drawImage(imageQueueDroite, p.x, p.y, this);
                    }
                    if (p.x == point.x ||p.x == point2.x){
                        g.drawImage(imageQueueBas, p.x, p.y, this);
                    }
                    if (p.x == point2.x && p.y < point2.y){
                        g.drawImage(imageQueueHaut, p.x, p.y, this);
                    }
                }
                if (snake.directionSerpent == 0){
                    if (p.x < point.x && p.y == point2.y){
                        g.drawImage(imageQueueGauche, p.x, p.y, this);
                        sens = 4;
                    }
                    if (p.x > point.x && p.y == point2.y){
                        g.drawImage(imageQueueDroite, p.x, p.y, this);
                        sens = 3;
                    }
                    if (p.x == point.x ||p.x == point2.x){
                        g.drawImage(imageQueueHaut, p.x, p.y, this);
                    }
                    if (p.x == point2.x && p.y > point2.y){
                        g.drawImage(imageQueueBas, p.x, p.y, this);
                    }
                }
                if (snake.directionSerpent == 1){
                    if (p.y > point.y && p.x == point2.x){
                        g.drawImage(imageQueueBas, p.x, p.y, this);
                        sens = 6;
                    }
                    if (p.y < point.y && p.x == point2.x){
                        g.drawImage(imageQueueHaut, p.x, p.y, this);
                        sens = 5;
                    }
                    if (p.y == point.y || p.y == point2.y){
                        g.drawImage(imageQueueDroite, p.x, p.y, this);
                    }
                    if (p.y == point2.y && p.x < point2.x){
                        g.drawImage(imageQueueGauche, p.x, p.y, this);
                    }
                }
                if (snake.directionSerpent == 3){
                    if (p.y > point.y && p.x == point2.x){
                        g.drawImage(imageQueueBas, p.x, p.y, this);
                        sens = 8;
                    }
                    if (p.y < point.y && p.x == point2.x){
                        g.drawImage(imageQueueHaut, p.x, p.y, this);
                        sens = 7;
                    }
                    if (p.y == point.y || p.y == point2.y){
                        g.drawImage(imageQueueGauche, p.x, p.y, this);
                    }
                    if (p.y == point2.y && p.x > point2.x){
                        g.drawImage(imageQueueDroite, p.x, p.y, this);
                    }
                }
            }

            //Image Corps
            if (p != tete && snake.directionSerpent == 2 && p!= queue) {

                if (point.x == p.x || point2.x == p.x) {
                    g.drawImage(imageCorpsHaut, p.x, p.y, this);
                } else {
                    g.drawImage(imageCorpsCote, p.x, p.y, this);
                }
                if ((point == p || point2 == p) && sens == 1) {
                    g.drawImage(imageCoudeGauche, p.x, p.y, this);
                }
                if ((point == p || point2 == p) && sens == 2) {
                    g.drawImage(imageCoudeDroit, p.x, p.y, this);
                }
            }
            if (p != tete && snake.directionSerpent == 0 && p!= queue) {
                if (p.y == point.y) {
                    g.drawImage(imageCorpsCote, p.x, p.y, this);
                }
                else {
                    g.drawImage(imageCorpsHaut, p.x, p.y, this);
                }
                if ((point == p || point2 == p) && sens == 3){
                    g.drawImage(imageCoudeHaut, p.x, p.y, this);
                }
                if ((point == p || point2 == p) && sens == 4){
                    g.drawImage(imageCoudeBas, p.x, p.y, this);
                }
            }

            if (p != tete && snake.directionSerpent == 1 && p!= queue) {
                if (p.x == point.x) {
                    g.drawImage(imageCorpsHaut, p.x, p.y, this);
                }
                else {
                    g.drawImage(imageCorpsCote, p.x, p.y, this);
                }
                if ((point == p || point2 == p) && sens == 5){
                    g.drawImage(imageCoudeDroit, p.x, p.y, this);
                }
                if ((point == p || point2 == p) && sens == 6){
                    g.drawImage(imageCoudeBas, p.x, p.y, this);
                }
            }

            if (p != tete && snake.directionSerpent == 3 && p!= queue) {
                if (p.x == point.x) {
                    g.drawImage(imageCorpsHaut, p.x, p.y, this);
                }
                else{
                    g.drawImage(imageCorpsCote, p.x, p.y, this);
                }
                if ((point == p || point2 == p) && sens == 7){
                    g.drawImage(imageCoudeGauche, p.x, p.y, this);
                }
                if ((point == p || point2 == p) && sens == 8){
                    g.drawImage(imageCoudeHaut, p.x, p.y, this);
                }
            }
        }
    }

    public void ecrireScore(){

        File file1 = new File("src\\score\\meilleurScore.txt");
        File file2 = new File("src\\score\\pseudo.txt");
        File folder = new File("src\\score\\");

        readScore();

        try {
            PrintWriter writer = new PrintWriter(file1, "UTF-8");
            PrintWriter writerPseudo = new PrintWriter(file2, "UTF-8");
            int compteurAjoutMeilleurScore = 0;

            for (int i = 0; i < 3; i++) {
                if (meilleurScore.get(i)<snake.getScore() && compteurAjoutMeilleurScore == 0){
                    meilleurScore.add(i,snake.getScore());
                    meilleurPseudo.add(i, snake.getPseudoJoueur());
                    compteurAjoutMeilleurScore ++;
                }
                writer.println(meilleurScore.get(i));
                writerPseudo.println(meilleurPseudo.get(i));
            }
            writer.close();
            writerPseudo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readScore(){

        File file = new File("src\\score\\meilleurScore.txt");
        File file2 = new File("src\\score\\pseudo.txt");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            String line = reader.readLine();
            System.out.println(line);

            while (line != null){
                int meilleurscore = Integer.parseInt(line);
                meilleurScore.add(meilleurscore);
                line = reader.readLine();
            }
            reader.close();

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file2),"UTF-8"));
            line = reader.readLine();
            while (line != null){
                meilleurPseudo.add(line);
                line = reader.readLine();
            }
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
