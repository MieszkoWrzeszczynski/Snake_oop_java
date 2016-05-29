import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

class ExtraFood extends Food {
    private CircleShape extraFoodBody;

    ExtraFood(int food_size, Vector2f respawn_position) {
        current_color = rand_color();
        extraFoodBody = new CircleShape();
        extraFoodBody.setFillColor(current_color);
        extraFoodBody.setRadius(food_size);
        extraFoodBody.setPosition(respawn_position);
        extraFoodBody.setOutlineThickness(-1.f);
    }

    void respawn(Vector2f respawn_position) {
        current_color = rand_color();
        extraFoodBody.setFillColor(current_color);
        extraFoodBody.setPosition(respawn_position);
    }

    void draw(RenderTarget target) {
        target.draw(extraFoodBody);
    }

    void set_position(Vector2f new_position) {
        extraFoodBody.setPosition(new_position);
    }

    Vector2f get_position() {
        return extraFoodBody.getPosition();
    }

    FloatRect GetFoodBounds() {
        return extraFoodBody.getGlobalBounds();
    }
}