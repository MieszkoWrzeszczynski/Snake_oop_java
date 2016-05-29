import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import static java.lang.Thread.sleep;

class Play extends Status {
    private int score;
    private int fraps;
    private boolean paused;
    private static final int FOOD_AMOUNT = 5;
    private Text title;
    private Snake snake;
    private Food[] tab;
    private SoundBuffer buffer;
    private Sound sound;

    Play(Game.Enum_game_status status_type, RenderWindow window, String window_title, Font font) {
        super(status_type, window, window_title, font);
        this.score = 0;
        this.fraps = 12;
        this.paused = false;

        String str_score = score + "";

        title = new Text();

        title.setString(str_score);
        title.setFont(pnt_font);
        title.setCharacterSize(86);
        title.setColor(new Color(255, 255, 255, 255));
        title.setStyle(Text.BOLD);
        title.setPosition(Game.SCRN_WIDTH / 2 - 3 * title.getGlobalBounds().width, 40);


        FileInputStream file_stream = null;
        try {
            file_stream = new FileInputStream("resources/eat.wav");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        buffer = new SoundBuffer();
        sound = new Sound();

        try {
            buffer.loadFromStream(file_stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sound.setBuffer(buffer);

    }

    public void init() {
        snake = new Snake(20, getRandomPosition());
        loadFood();
    }

    private Vector2f getRandomPosition() {
        Random rand = new Random();

        int x = (rand.nextInt((Game.SCRN_WIDTH - 60 - 60) + 1) + 60);
        int y = (rand.nextInt((Game.SCRN_HEIGHT - 60 - 60) + 1) + 60);

        return new Vector2f(x, y);
    }

    Game.Enum_game_status getEvents() {
        Clock resetClock = new Clock();
        pnt_window.setMouseCursorVisible(false);

        while (true) {
            for (Event event : pnt_window.pollEvents()) {
                switch (event.type) {
                    case CLOSED:
                        return Game.Enum_game_status.END;

                    case KEY_PRESSED:
                        KeyEvent keyEvent = event.asKeyEvent();
                        String key = keyEvent.key.toString();

                        switch (key) {
                            case "ESCAPE":
                                checkScoreInRank();
                                return Game.Enum_game_status.GAME_OVER;
                            case "RIGHT":
                                snake.changeDirection(Snake.enum_Direction.DIR_RIGHT);
                                break;
                            case "LEFT":
                                snake.changeDirection(Snake.enum_Direction.DIR_LEFT);
                                break;
                            case "DOWN":
                                snake.changeDirection(Snake.enum_Direction.DIR_DOWN);
                                break;
                            case "UP":
                                snake.changeDirection(Snake.enum_Direction.DIR_UP);
                                break;
                        }
                        if (key.equals("P") && !paused) {
                            title.setString("Pauza");
                            paused = true;
                        } else if (key.equals("P") && paused)
                            paused = false;

                        break;
                }

            }


            if (resetClock.getElapsedTime().asSeconds() >= 0.1 - (0.001 * fraps)) {
                if (!snake.exist()) {
                    checkScoreInRank();
                    return Game.Enum_game_status.GAME_OVER;
                }

                if (!paused)
                    update();

                resetClock.restart();
            }
            render();
        }
    }

    private void checkScoreInRank() {
        int best_score = Game.rank.getTheBestPlayerScore();

        if (best_score < score) {
            pnt_window.clear(new Color(178, 30, 0, 255));
            title.setPosition((float) (Game.SCRN_WIDTH / 2 - 1.5 * title.getGlobalBounds().width), 40);
            title.setString(Game.getPlayerName() + " masz nowy record!");
            pnt_window.draw(title);
            pnt_window.display();

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Game.rank.addToRanking(Game.getPlayerName(), score);
    }

    private void updateScore() {
        String str_score = score + "";
        title.setString("Poziom " + str_score);
    }

    private void ifSnakeAteFood() {
        for (int i = 0; i < FOOD_AMOUNT; i++) {
            if (tab[i].GetFoodBounds().intersection(snake.GetHeadFloatRect()) != null) {
                sound.play();
                snake.AddBodyPart(tab[i].getCurrentColor());
                food_respawn(tab[i]);
                levelUp();
            }
        }
    }

    void update() {
        ifSnakeAteFood();
        updateScore();
        snake.Move();
    }

    void render() {
        pnt_window.clear(new Color(255, 232, 143, 55));
        pnt_window.draw(title);
        snake.Render(pnt_window);
        drawFood();
        pnt_window.display();
    }

    private void levelUp() {
        if (score % 2 == 0)
            fraps += 4;
        score++;
    }

    private void food_respawn(Food food) {
        Vector2f randomPosition;

        do {
            randomPosition = getRandomPosition();
            food.respawn(randomPosition);
        } while (snake.contains(food));

    }

    private void loadFood() {
        tab = new Food[FOOD_AMOUNT];

        for (int i = 0; i < FOOD_AMOUNT; i++) {
            if (i % 2 == 0)
                tab[i] = new ExtraFood(20, getRandomPosition());
            else
                tab[i] = new Food(20, getRandomPosition());
        }
    }

    private void drawFood() {
        for (int i = 0; i < FOOD_AMOUNT; i++) {
            tab[i].draw(pnt_window);
        }
    }
}



