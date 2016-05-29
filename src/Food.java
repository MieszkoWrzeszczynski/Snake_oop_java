import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

import java.util.Random;

class Food {

    private RectangleShape body;
    Color current_color;

    Food(int food_size, Vector2f respawn_position) {
        current_color = rand_color();
        body = new RectangleShape();
        body.setFillColor(current_color);
        body.setSize(new Vector2f(food_size, food_size));
        body.setPosition(respawn_position);
        body.setOutlineThickness(-1.f);
    }

    Food() {
        this(20, new Vector2f(300, 300));
    }

    void respawn(Vector2f respawn_position) {
        current_color = rand_color();
        body.setFillColor(current_color);
        body.setPosition(respawn_position);
    }

    void set_position(Vector2f new_position) {
        body.setPosition(new_position);
    }

    Vector2f get_position() {
        return body.getPosition();
    }

    void draw(RenderTarget target) {
        target.draw(body);
    }

    FloatRect GetFoodBounds() {
        return body.getGlobalBounds();
    }

    Color rand_color() {
        Random rand = new Random();
        return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    Color getCurrentColor() {
        return this.current_color;
    }
}
