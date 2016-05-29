import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;

abstract class Status {

    Status(Game.Enum_game_status status_type, RenderWindow window, String window_title, Font font) {
        this.type = status_type;
        this.pnt_window = window;
        this.title = window_title;
        this.pnt_font = font;
    }

    Game.Enum_game_status getStatusType() {
        return type;
    }

    abstract void init();
    abstract void render();
    abstract void update();
    abstract Game.Enum_game_status getEvents();

    RenderWindow pnt_window;
    String title;
    Font pnt_font;
    private Game.Enum_game_status type;
}
