package Objects;

import Entity.Entity;
import Main.GamePanel;


public class Door extends Entity {

    public Door(GamePanel gp) {

        super(gp);
        name = "Door";
        direction = "down";
        image = setup("/Level 1/door",gp.tileSize, gp.tileSize);
        collision = true;
    }
}
