import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

import java.util.Vector;

class Snake {
    private enum_Direction direction;

    enum enum_Direction {
        DIR_UP,
        DIR_DOWN,
        DIR_LEFT,
        DIR_RIGHT
    }
    private int size;
    private Vector<RectangleShape> snake_body;

    Snake(int size, Vector2f spawn_position) {
        snake_body = new Vector();
        this.size = size;
        RectangleShape head = new RectangleShape();
        direction = enum_Direction.DIR_UP;

        head.setFillColor(new Color(255, 138, 0, 255));
        head.setOutlineThickness(-1.f);
        head.setSize(new Vector2f(size, size));
        head.setPosition(spawn_position);

        snake_body.addElement(head);
    }

    FloatRect GetHeadFloatRect() {
        return snake_body.get(0).getGlobalBounds();
    }

    void changeDirection(enum_Direction direction) {
        enum_Direction past_direct = this.direction;

        if (past_direct == enum_Direction.DIR_UP && direction != enum_Direction.DIR_DOWN)
            this.direction = direction;

        if (past_direct == enum_Direction.DIR_DOWN && direction != enum_Direction.DIR_UP)
            this.direction = direction;

        if (past_direct == enum_Direction.DIR_LEFT && direction != enum_Direction.DIR_RIGHT)
            this.direction = direction;

        if (past_direct == enum_Direction.DIR_RIGHT && direction != enum_Direction.DIR_LEFT)
            this.direction = direction;
    }

    void Render(RenderTarget target) {
        snake_body.forEach(target::draw);
    }

    void Move() {
        Vector2f prevPos = snake_body.get(0).getPosition();
        Vector2f dir = getDirection();

        float x = dir.x * size;
        float y = dir.y * size;
        dir = new Vector2f(x, y);

        Vector2f offset = dir;
        snake_body.get(0).move(offset);

        for (int i = 1; i < snake_body.size(); i++) {
            Vector2f tmp = snake_body.get(i).getPosition();
            snake_body.get(i).setPosition(prevPos.x, prevPos.y);
            prevPos = tmp;
        }
    }

    boolean contains(Food food) {
        for (int i = 1; i < snake_body.size(); i++) {
            if (snake_body.get(i).getGlobalBounds().intersection(food.GetFoodBounds()) != null)
                return true;
        }

        return false;
    }

    void AddBodyPart(Color new_color) {

        RectangleShape bodyPart = new RectangleShape();

        Vector2f dir = getDirection();

        float x = dir.x * size;
        float y = dir.y * size;
        dir = new Vector2f(x, y);

        Vector2f offset = dir;

        bodyPart.setOutlineThickness(-1.f);
        bodyPart.setSize(new Vector2f(size, size));
        bodyPart.setFillColor(new_color);
        bodyPart.setPosition(offset);

        snake_body.addElement(bodyPart);
    }

    boolean exist() {
        return !(hit_the_wall() || selfEaten());

    }

    private boolean hit_the_wall() {
        return GetHeadPosition().x >= Game.SCRN_WIDTH - size ||
                GetHeadPosition().x <= 0 ||
                GetHeadPosition().y <= 0 ||
                GetHeadPosition().y >= Game.SCRN_HEIGHT - size;
    }

    private boolean selfEaten() {
        for (int i = 1; i < snake_body.size(); i++) {
            if (GetHeadFloatRect().intersection(snake_body.get(i).getGlobalBounds()) != null)
                return true;
        }

        return false;
    }

    private Vector2f GetHeadPosition() {
        return snake_body.get(0).getPosition();
    }

    private Vector2f getDirection() {
        Vector2f dir;
        int x = 0;
        int y = 0;

        switch (direction) {
            case DIR_LEFT:
                x = -1;
                break;

            case DIR_RIGHT:
                x = 1;
                break;

            case DIR_UP:
                y = -1;
                break;

            case DIR_DOWN:
                y = 1;
                break;
        }

        dir = new Vector2f(x, y);

        return dir;
    }


}












