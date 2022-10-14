package Monster;

import Entity.Entity;
import Main.GamePanel;

import java.util.Random;

public class Slime extends Entity {

    GamePanel gp;

    public Slime(GamePanel gp) {

        super(gp);
        this.gp = gp;
        name = "Slime";
        speed = 1;
        maxHP = 1;
        HP = 1;
        collision = true;
        type = 2;
        direction = "down";
        exp = 5;
        attack = 2;
        defense = 0;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultY = solidArea.y;
        solidAreaDefaultX = solidArea.x;

        getImage();
    }

    public void getImage()
    {
        up1 = setup("/Level 1/greenslime",gp.tileSize, gp.tileSize);
        up2 = setup("/Level 1/greenslime",gp.tileSize, gp.tileSize);
        down1 = setup("/Level 1/greenslime",gp.tileSize, gp.tileSize);
        down2 = setup("/Level 1/greenslime",gp.tileSize, gp.tileSize);
        left1 = setup("/Level 1/greenslime",gp.tileSize, gp.tileSize);
        left2 = setup("/Level 1/greenslime",gp.tileSize, gp.tileSize);
        right1 = setup("/Level 1/greenslime",gp.tileSize, gp.tileSize);
        right2 = setup("/Level 1/greenslime",gp.tileSize, gp.tileSize);
    }

    public void setAction()
    {
        actionLockCounter++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25)
                direction = "up";
            if (i > 25 && i <= 50)
                direction = "down";
            if (i > 50 && i <= 75)
                direction = "left";
            if (i > 75)
                direction = "right";

            actionLockCounter = 0;
        }
    }

    public void damageReaction()
    {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
