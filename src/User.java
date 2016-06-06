import java.util.Comparator;

class User {
    private String Name;
    private int Score;

    User(String name, int score) {
        setName(name);
        setScore(score);
    }


    String getName() {
        return Name;
    }

    void setName(String name) {
        Name = name;
    }

    int getScore() {
        return Score;
    }

    void setScore(int score) {
        Score = score;
    }

    public String toString() {
        return this.Name + "\t" + this.Score + "\n";
    }

}

class UserComparator implements Comparator<User> {
    @Override
    public int compare(User A, User B) {
        return (A.getScore() > B.getScore()) ? 1 : -1;
    }

}
