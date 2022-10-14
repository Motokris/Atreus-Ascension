package Objects;

import Entity.Entity;
import Main.GamePanel;

public class Sword extends Entity {

    public Sword(GamePanel gp) {
        super(gp);
        name = "Sword";
        attackValue = 1;
    }
}
