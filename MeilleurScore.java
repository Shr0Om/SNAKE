import java.io.*;
import java.util.ArrayList;

public class MeilleurScore {

    private ArrayList<Integer> meilleurScore = new ArrayList<Integer>();
    private ArrayList<String> meilleurPseudo = new ArrayList<String>();

    public void ecrireScore(int score, String pseudo){

        File file1 = new File("src/score/meilleurScore.txt");
        File file2 = new File("src/score/pseudo.txt");

        readScore();

        try {
            PrintWriter writer = new PrintWriter(file1, "UTF-8");
            PrintWriter writerPseudo = new PrintWriter(file2, "UTF-8");
            int compteurAjoutMeilleurScore = 0;     // variable permettant de n'ajouter qu'une seule fois le score si il est meilleur

            for (int i = 0; i < 3; i++) {
                if (meilleurScore.get(i) < score && compteurAjoutMeilleurScore == 0){
                    meilleurScore.add(i, score);
                    meilleurPseudo.add(i, pseudo);
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

        File file = new File("src/score/meilleurScore.txt");
        File file2 = new File("src/score/pseudo.txt");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            String line = reader.readLine();

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

    public ArrayList<Integer> getMeilleurScore() {
        readScore();
        return meilleurScore;
    }

    public ArrayList<String> getMeilleurPseudo () {
        readScore();
        return meilleurPseudo;
    }
}
