package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public GamePanel gp;
    public int worldX, worldY;
    public int speed;

    public String name;
    public boolean collision = false;
    public BufferedImage image, image2,
            idleR1, idleR2, idleR3, idleR4, normal,
            idleL1, idleL2, idleL3, idleL4,
            up1, up2, down1, down2, left1, left2, right1, right2,
            attackR1, attackR2, attackR3, attackR4,
            attackL1, attackL2, attackL3, attackL4;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public String[] dialogues = new String[25];
    int dialogueIndex = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int type;
    public boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    public boolean HPBarOn = false;
    public int HPBarCounter = 0;

    public int maxHP, HP;
    public int level, strength, dexterity, attack, defense, exp, nextLevelExp, score;
    public Entity currentWeapon, currentShield;
    public int attackValue, defenseValue;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}

    public void damageReaction() {}

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

    }

    public void update() {

        setAction();
        collisionOn = false;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);

        if (this.type == 2 && contactPlayer == true)
            if (gp.player.invincible == false) {
                int damage = attack - gp.player.defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.player.HP -= damage;
                gp.player.invincible = true;
            }

        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                default:
                    break;
            }
        }
        spriteCounter++;
        if (spriteCounter > 7) {
            switch (spriteNumber) {
                case 1, 2, 3:
                    spriteNumber++;
                    break;
                case 4:
                    spriteNumber = 1;
                    break;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
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
        g2.setColor(Color.red);
        g2.drawRect(screenX,screenY,gp.tileSize,25);
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0F);
        }

        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1F);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0F);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1F);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0F);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1F);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0F);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1F);
        }
        if (dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
