package Objects;

import Main.GamePanel;
import Entity.Entity;


public class Key extends Entity {

    public Key(GamePanel gp) {

       super(gp);

        name = "Key";
        image = setup("/Level 1/key",gp.tileSize, gp.tileSize);
    }
}
