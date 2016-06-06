import java.io.*;
import java.util.TreeSet;


class Ranking {

    private TreeSet<User> user;
    private static final String DB_NAME = "resources/ranking.csv";

    Ranking() throws ResourcesException {
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

    void saveToFile() throws ResourcesException {
        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(DB_NAME, false));

            for (User e : user)
                bw.write(e.getName() + "\t" + e.getScore() + "\n");

            bw.close();

        } catch (IOException ioe) {
            throw new ResourcesException("I can not save ranking to file ranking.csv");
        }

    }

    private void getRankingFromFile() throws ResourcesException {

        try {
            FileInputStream fstream = new FileInputStream(DB_NAME);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] user_info = strLine.split("\t");
                user.add(new User(user_info[0], Integer.parseInt(user_info[1])));
            }

            in.close();

        } catch (Exception e) {
            throw new ResourcesException("I can not load ranking from file ranking.csv");
        }


    }

}










