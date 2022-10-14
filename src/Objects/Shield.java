package Objects;

import Entity.Entity;
import Main.GamePanel;

public class Shield extends Entity {

    public Shield(GamePanel gp) {
        super(gp);
        name = "Shield";
        defenseValue = 1;
    }
}
