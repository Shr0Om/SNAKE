import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

class Serpent{

    private String pseudoJoueur;
    private ArrayList<Point> listePoint;        //liste contenant les coordonnées des points
    private ArrayList<Integer> listeDirection;        //liste contenant de la direction du serpent
    //qui composent le serpent
    private static final int longueur = 50;
    private static final int largeur = 70;
    private int score;
    public int directionSerpent;
    private int tourne;

    public Serpent(String pseudo){

        pseudoJoueur = pseudo;
        listePoint = new ArrayList<Point>();
        listeDirection = new ArrayList<Integer>();
        Random r = new Random();
        //on determine aleatoirement la position du serpent
        int x = Math.abs(r.nextInt() % largeur);
        int y = Math.abs(r.nextInt() % longueur);
        x = x * 10;
        y = y * 10;

        directionSerpent = 3; // par defaut le serpent part à DROITE
        //0 = BAS
        //1 = GAUCHE
        //2 = HAUT
        //3 = DROITE

        // creation du serpent par une liste de point
        listePoint.add(new Point(x + 50,y));
        listePoint.add(new Point(x + 40,y));
        listePoint.add(new Point(x + 30,y));
        listePoint.add(new Point(x + 20,y));
        listePoint.add(new Point(x + 10,y));
        listePoint.add(new Point(x,y));

        for (int i = 0; i < 6 ; i++) {
            listeDirection.add(directionSerpent);
        }

        score = 0;
    }

    public Serpent(String pseudo, int score){

        pseudoJoueur = pseudo;
        this.score = score;
        listePoint = new ArrayList<Point>();
        listeDirection = new ArrayList<Integer>();
        Random r = new Random();
        //on determine aleatoirement la position du serpent
        int x = Math.abs(r.nextInt() % largeur);
        int y = Math.abs(r.nextInt() % longueur);
        x = x * 10;
        y = y * 10;

        directionSerpent = 3; // par defaut le serpent part à DROITE
        //0 = BAS
        //1 = GAUCHE
        //2 = HAUT
        //3 = DROITE

        // creation du serpent par une liste de point
        listePoint.add(new Point(x + 50,y));
        listePoint.add(new Point(x + 40,y));
        listePoint.add(new Point(x + 30,y));
        listePoint.add(new Point(x + 20,y));
        listePoint.add(new Point(x + 10,y));
        listePoint.add(new Point(x,y));

        for (int i = 0; i < 6 ; i++) {
            listeDirection.add(directionSerpent);
        }

    }

    void tourne(int touche) {

        tourne = touche;
        if(tourne == 1) {
            directionSerpent --;
            if (directionSerpent < 0)  // ainsi on passerait de -1
                directionSerpent = 3;  // à 3 = DROITE
        }
        if(tourne == 2) {
            directionSerpent++;
            if(directionSerpent > 3)
                directionSerpent = 0; // passe a 0 = BAS
        }
    }

    // methode pour transmetttre les coordonnees des points du serpent
    ArrayList<Point> getList(){
        return listePoint;
    }

    ArrayList<Integer> getListeDirection(){
        return listeDirection;
    }

    // gere le deplacement du serpent et les changements de direction
    public void avancerSerpent(){

        Point tete = new Point(listePoint.get(0));

        switch(directionSerpent) {
            case 0 :
                tete.y = tete.y + 10;
                break;
            case 1 :
                tete.x = tete.x - 10;
                break;
            case 2 :
                tete.y = tete.y - 10;
                break;
            case 3 :
                tete.x = tete.x + 10;
                break;
        }

        listeDirection.add(0, directionSerpent);
        listeDirection.add(listeDirection.size()-1);

        listePoint.add(0,new Point (tete.x, tete.y));
        listePoint.remove(listePoint.size()-1);

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPseudoJoueur() {
        return pseudoJoueur;
    }
}
