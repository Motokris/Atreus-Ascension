package Monster;

import Entity.Entity;
import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;

public class King extends Entity {

    public King(GamePanel gp) {
        super(gp);
        name = "King";
        speed = 2;
        maxHP = 4;
        HP = 1;
        collision = true;
        type = 2;
        direction = "down";
        exp = 10;
        attack = 3;

        solidArea = new Rectangle(0,0,gp.tileSize,gp.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage()
    {
        UtilityTool uTool = new UtilityTool();
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/King Darius I/king_asp.png"));
            image = uTool.scaleImage(image,gp.tileSize,gp.tileSize * 3);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
        g2.setColor(Color.BLACK);
        g2.drawRect(screenX,screenY - 25,gp.tileSize,gp.tileSize);
    }
}
