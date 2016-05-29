import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;


class Menu extends Status {

    private Text header;
    private Text[] menuOptions;

    Menu(Game.Enum_game_status status_type, RenderWindow window, String window_title, Font font) {
        super(status_type, window, window_title, font);
    }

    void update() {
        Vector2f mousePosition = new Vector2f((float) Mouse.getPosition(pnt_window).x, (float) Mouse.getPosition(pnt_window).y);

        for (int i = 0; i < 2; i++) {
            if (menuOptions[i].getGlobalBounds().contains(mousePosition))
                menuOptions[i].setColor(new Color(255, 138, 0, 255));
            else menuOptions[i].setColor(new Color(255, 255, 255, 255));
        }
    }

    void render() {
        pnt_window.clear(new Color(178, 30, 0, 255));
        pnt_window.draw(header);

        for (int i = 0; i < 2; i++)
            pnt_window.draw(menuOptions[i]);

        pnt_window.display();
    }

    Game.Enum_game_status getEvents() {
        Vector2f mousePosition = new Vector2f((float) Mouse.getPosition(pnt_window).x, (float) Mouse.getPosition(pnt_window).y);

        for (Event event : pnt_window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    return Game.Enum_game_status.END;

                case MOUSE_BUTTON_RELEASED:
                    if (menuOptions[1].getGlobalBounds().contains(mousePosition))
                        return Game.Enum_game_status.END;
                    else if (menuOptions[0].getGlobalBounds().contains(mousePosition))
                        return Game.Enum_game_status.RUN;

                    break;

                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    String key = keyEvent.key.toString();

                    if (key.equals("ESCAPE"))
                        return Game.Enum_game_status.END;
                    break;
            }
        }
        return Game.Enum_game_status.MENU;
    }

    void init() {
        pnt_window.setMouseCursorVisible(true);

        header = new Text();
        header.setString(title);
        header.setFont(pnt_font);
        header.setCharacterSize(86);
        header.setColor(new Color(243, 245, 248, 255));
        header.setStyle(Text.BOLD);
        header.setPosition(Game.SCRN_WIDTH / 2 - header.getGlobalBounds().width / 2, 40);

        final int ile = 2;

        menuOptions = new Text[ile];
        String[] str = new String[ile];

        if (title.equals("Game Over"))
            str[0] = "Play again";
        else
            str[0] = "Play";

        str[1] = "Exit";

        for (int i = 0; i < ile; i++) {
            menuOptions[i] = new Text();
            menuOptions[i].setFont(pnt_font);
            menuOptions[i].setCharacterSize(65);
            menuOptions[i].setString(str[i]);
            menuOptions[i].setPosition(Game.SCRN_WIDTH / 2 - menuOptions[i].getGlobalBounds().width / 2, 250 + i * 120);
        }
    }

}

