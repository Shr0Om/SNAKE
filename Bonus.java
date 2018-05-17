import java.awt.Point;
import java.util.Random;

public class Bonus {

    public Point p;
    private static final int longueur = 50;     // represente les dimensions de la fenetre
    private static final int largeur = 70;     // pour eviter que les pommes soient positionner a l'extérieur
    public int typeBonus;

    public Bonus(){
        Random r = new Random();            // on determine aleatoirement la position de la pomme
        int x = Math.abs(r.nextInt() % largeur);
        int y = Math.abs(r.nextInt() % longueur);
        x = x * 10 + 10;                    // *10 pour remettre au dimension de la fenetre
        y = y * 10 + 10;                    // +10 pour éviter que la pomme se colle au bord se qui rend difficile de la récuperer

        p = new Point(x,y);

        typeBonus();
    }


    public void typeBonus(){

        Random r = new Random();
        int valeur = 1 + r.nextInt(4 - 1);

        System.out.println("Valeur bonus" + valeur);

        this.typeBonus = valeur;
    }
}
