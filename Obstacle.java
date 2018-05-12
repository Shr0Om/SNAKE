import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Obstacle {

    public Point p;
    private static final int longueur = 50;     // represente les dimensions de la fenetre
    private static final int largeur = 70;     // pour eviter que les pommes soient positionner a l'extérieur

    public Obstacle(){
        Random r = new Random();
        //on determine aleatoirement la position du serpent
        int x = Math.abs(r.nextInt() % largeur);
        int y = Math.abs(r.nextInt() % longueur);
        x = x * 10;
        y = y * 10;
        p = new Point(x,y);

    }
}

//import java.awt.Point;
//import java.util.ArrayList;
//import java.util.Random;
//
//public class Obstacle {
//
//    public Point p;
//    private ArrayList<Point> listeObstacle;
//    private static final int longueur = 50;     // represente les dimensions de la fenetre
//    private static final int largeur = 70;     // pour eviter que les pommes soient positionner a l'extérieur
//
//    public Obstacle(){
//        listeObstacle = new ArrayList<Point>();
//        int min = 5;
//        int max = 10;
//        int taille;
//        //on determine aleatoirement la position de l'obstacle
//
//        // creation des obstacles par une liste de point
//        for (int j = 0; j < 15; j++) {
//            Random r = new Random();
//            Random m = new Random();
//            int x = Math.abs(r.nextInt() % largeur);
//            int y = Math.abs(r.nextInt() % longueur);
//            x = x * 10;
//            y = y * 10;
//            taille = m.nextInt(max-min) + min;
//
//            for (int i = 1; i < taille; i++) {
//                listeObstacle.add(new Point(x + i*10,y));
//                listeObstacle.add(new Point(x,y));
//            }
//            for (int i = 1; i < taille; i++) {
//                listeObstacle.add(new Point(x,y + i*10));
//                listeObstacle.add(new Point(x,y));
//            }
//        }
//    }
//
//    ArrayList<Point> getList(){
//        return listeObstacle;
//    }
//}
