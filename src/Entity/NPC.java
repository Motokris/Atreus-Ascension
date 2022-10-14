package Entity;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC extends Entity
{

    public NPC(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }

    public void getImage()
    {
        up1 = setup("/Level 1/oldup1",gp.tileSize, gp.tileSize);
        up2 = setup("/Level 1/oldup2",gp.tileSize, gp.tileSize);
        down1 = setup("/Level 1/olddown1",gp.tileSize, gp.tileSize);
        down2 = setup("/Level 1/olddown2",gp.tileSize, gp.tileSize);
        left1 = setup("/Level 1/oldleft1",gp.tileSize, gp.tileSize);
        left2 = setup("/Level 1/oldleft2",gp.tileSize, gp.tileSize);
        right1 = setup("/Level 1/oldright1",gp.tileSize, gp.tileSize);
        right2 = setup("/Level 1/oldright2",gp.tileSize, gp.tileSize);
    }

    public void setDialogue()
    {
        dialogues[0] = "Hello there.";
        dialogues[1] = "The enemy presence isn't that large.";
        dialogues[2] = "I believe u can handle it.";
        dialogues[3] = "Good luck!";
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

    public void speak()
    {
        super.speak();
    }


    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize > gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize > gp.player.worldY + gp.player.screenY) {
            switch (direction) {
                case "right":
                    switch (spriteNumber) {
                        case 1, 2:
                            image = right1;
                            break;
                        case 3, 4:
                            image = right2;
                            break;
                    }
                    break;
                case "left":
                    switch (spriteNumber) {
                        case 1, 2:
                            image = left1;
                            break;
                        case 3, 4:
                            image = left2;
                            break;
                    }
                    break;
                case "up":
                    switch (spriteNumber) {
                        case 1, 2:
                            image = up1;
                            break;
                        case 3, 4:
                            image = up2;
                            break;
                    }
                    break;
                case "down":
                    switch (spriteNumber) {
                        case 1, 2:
                            image = down1;
                            break;
                        case 3, 4:
                            image = down2;
                            break;
                    }
                    break;
            }

            if (type == 2 && HPBarOn == true) {

                double oneScale = (double) gp.tileSize / maxHP;
                double HPBarValue = oneScale * HP;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) HPBarValue, 10);

                HPBarCounter++;

                if (HPBarCounter > 300) {
                    HPBarCounter = 0;
                    HPBarOn = false;
                }
            }

            if (invincible == true) {
                HPBarOn = true;
                HPBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }

            if (dying == true) {
                dyingAnimation(g2);
            }

            changeAlpha(g2, 1F);
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        g2.setColor(Color.blue);
        g2.drawRect(screenX + 10,screenY - 15,gp.tileSize,25);
    }
}
