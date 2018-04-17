import java.awt.Point;
import java.util.ArrayList;

public class Obstacle {

    private ArrayList<Point> listePoint;

    public Obstacle(){
        listePoint = new ArrayList<Point>();
        int x = 0;
        int y = 0;

    }

    // methode pour transmetttre les coordonnees des obstacles
    ArrayList<Point> getList(){
        return listePoint;
    }
}
