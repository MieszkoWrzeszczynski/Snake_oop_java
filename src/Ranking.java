import java.io.*;
import java.util.*;


class Ranking {
    Ranking() {
        user = new TreeSet<>(new UserComparator());
        getRankingFromFile();
    }

    int getTheBestPlayerScore() {
        if (!user.isEmpty())
            return user.last().getScore();

        return -1;
    }

    void addToRanking(String playerName, int playerScore) {
        user.add(new User(playerName, playerScore));
    }

    void saveToFile() {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(DB_NAME, false));

            for (User e : user)
                bw.write(e.getName() + "\t" + e.getScore() + "\n");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (bw != null) try {
                bw.close();
            } catch (IOException ioe2) {

            }
        }
    }

    private void getRankingFromFile() {

        try {
            FileInputStream fstream = new FileInputStream(DB_NAME);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] words = strLine.split("\t");
                user.add(new User(words[0], Integer.parseInt(words[1])));

            }

            in.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }


    }


    private TreeSet<User> user;
    private static final String DB_NAME = "resources/ranking.csv";
}










