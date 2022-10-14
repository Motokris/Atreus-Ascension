package Objects;

import Entity.Entity;
import Main.GamePanel;


public class Heart extends Entity
{

    public Heart(GamePanel gp) {

        super(gp);

        name = "Heart";
        image = setup("/Atreus/Heart",gp.tileSize, gp.tileSize);
        image2 = setup("/Atreus/heart_blank",gp.tileSize, gp.tileSize);
    }
}
