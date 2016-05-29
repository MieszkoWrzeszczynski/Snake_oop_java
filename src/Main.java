public class Main {
    public static void main(String args[]) {
        Game game = new Game();
        System.out.print(getNameFromArgs(args));
        game.setPlayerName(getNameFromArgs(args));
        game.start();
    }

    private static String getNameFromArgs(String args[]) {
        if (args.length >= 1)
            return args[0];
        else
            return "Unnamed";
    }
}
