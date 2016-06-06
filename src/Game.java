import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2i;
import org.jsfml.window.ContextSettings;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


class Game {
    static final int SCRN_WIDTH;
    static final int SCRN_HEIGHT;
    private RenderWindow window;
    private Font font;
    private static String playerName;
    private Status game_status;
    private Enum_game_status actualGame_status;
    private Sound sound;
    static Ranking rank;

    static {
        SCRN_HEIGHT = 568;
        SCRN_WIDTH = 1280;
    }

    enum Enum_game_status {
        MENU,
        RUN,
        END,
        GAME_OVER
    }

    Game() throws ResourcesException {
        ContextSettings settings = new ContextSettings(8);

        window = new RenderWindow();
        window.create(new VideoMode(SCRN_WIDTH, SCRN_HEIGHT), "Snake", WindowStyle.CLOSE, settings);
        window.setFramerateLimit(60);
        window.setPosition(new Vector2i(0, 0));

        font = new Font();
        SoundBuffer buffer = new SoundBuffer();
        sound = new Sound();

        rank = new Ranking();

        FileInputStream file_stream;


        try {
            file_stream = new FileInputStream("resources/font.ttf");
        } catch (FileNotFoundException e) {
            throw new ResourcesException("I have not found file font.ttf");
        }

        try {
            font.loadFromStream(file_stream);
        } catch (IOException e) {
            throw new ResourcesException("I have not loaded fonts");
        }

        try {
            file_stream = new FileInputStream("resources/loop.wav");
        } catch (FileNotFoundException e) {
            throw new ResourcesException("I have not found file loop.wav");
        }

        try {
            buffer.loadFromStream(file_stream);
        } catch (IOException e) {
            throw new ResourcesException("I have not loaded sounds");
        }

        sound.setBuffer(buffer);
        sound.setLoop(true);
        sound.setRelativeToListener(true);
        sound.setVolume(40.f);
        sound.play();

        actualGame_status = Game.Enum_game_status.MENU;
        game_status = new Menu(Enum_game_status.MENU, window, "Snake", font);
        game_status.init();
    }

    void start() {
        while (actualGame_status != Enum_game_status.END) {
            if (actualGame_status != game_status.getStatusType())
                changeActualGame_status();

            handleState();
        }

        try {
            rank.saveToFile();
        } catch (ResourcesException e) {
            System.out.println(e.getMessage());
        }
        sound.stop();
    }

    private void changeActualGame_status() {
        switch (actualGame_status) {
            case MENU:
                game_status = new Menu(Enum_game_status.MENU, window, "Menu", font);
                break;

            case RUN:
                game_status = new Play(Enum_game_status.RUN, window, "Play mode", font);
                break;

            case GAME_OVER:
                game_status = new Menu(Enum_game_status.MENU, window, "Game Over", font);
                break;
        }

        game_status.init();
    }

    private void handleState() {
        actualGame_status = game_status.getEvents();
        game_status.update();
        game_status.render();
    }


    void setPlayerName(String Name) {
        playerName = Name;
    }

    static String getPlayerName() {
        return playerName;
    }

}