package Objects;

import Entity.Entity;
import Main.GamePanel;


public class Chest extends Entity {

    public Chest(GamePanel gp) {

        super(gp);
        name = "Chest";
        image = setup("/Level 1/Chest",gp.tileSize, gp.tileSize);
    }
}
