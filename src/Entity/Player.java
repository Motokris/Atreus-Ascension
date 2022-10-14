package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Objects.Shield;
import Objects.Sword;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    public KeyHandler keyI;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler keyI) {

        super(gp);
        this.keyI = keyI;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle(0, 0, 16, 10);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValue();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValue() {

        setDefaultPositions();
        speed = 4;

        if (gp.difficulty == 1) {
            maxHP = 4;
            HP = 4;
            level = 1;
            strength = 1;
            dexterity = 1;
            exp = 0;
            nextLevelExp = 5;
            score = 0;
        } else {
            maxHP = 6;
            HP = 6;
            level = 2;
            strength = 2;
            dexterity = 2;
            score = 0;
            exp = 0;
            nextLevelExp = 5;
        }
        currentWeapon = new Sword(gp);
        currentShield = new Shield(gp);
        attack = getAttack();
        defense = getDefense();

    }

    public void setDefaultPositions()
    {
        worldX = gp.tileSize * 22;
        worldY = gp.tileSize * 21;
        direction = "down";
    }

    public void restoreHP()
    {
        HP = maxHP;
        invincible = false;
    }

    public int getAttack()
    {
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense()
    {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {
        try {
            idleR1 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_idle1.png"));
            idleR2 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_idle2.png"));
            idleR3 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_idle3.png"));
            idleR4 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_idle4.png"));
            normal = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus.png"));
            idleL1 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_idle1_flip.png"));
            idleL2 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_idle2_flip.png"));
            idleL3 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_idle3_flip.png"));
            idleL4 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_idle4_flip.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPlayerAttackImage() {
        try {
            attackR1 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_attack1.png"));
            attackR2 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_attack2.png"));
            attackR3 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_attack3.png"));
            attackR4 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_attack4.png"));
            attackL1 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_attack1_flip.png"));
            attackL2 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_attack2_flip.png"));
            attackL3 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_attack3_flip.png"));
            attackL4 = ImageIO.read(getClass().getResourceAsStream("/Atreus/atreus_attack4_flip.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (attacking == true) {
            attacking();
        } else if (keyI.up == true || keyI.down == true || keyI.left == true || keyI.right == true || keyI.enter == true) {
            if (keyI.up) {
                direction = "up";
            } else if (keyI.down) {
                direction = "down";
            } else if (keyI.left) {
                direction = "left";
            } else if (keyI.right) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            gp.eHandler.checkEvent();

            if (!collisionOn && keyI.enter == false) {
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

            if (keyI.enter == true && attackCanceled == false)
            {
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;

            gp.keyI.enter = false;
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
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (HP <= 0)
            gp.gameState = gp.gameOverState;

    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNumber = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 15) {
            spriteNumber = 2;
        }
        if (spriteCounter > 15 && spriteCounter <= 25) {
            spriteNumber = 3;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction)
            {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25 && spriteCounter <= 35) {
            spriteNumber = 4;
        }
        if (spriteCounter > 35) {
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void pickUpObject(int i) {
        if (i != 999) {

        }
    }

    public void interactNPC(int i) {
        if (gp.keyI.enter == true) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false) {
                if (gp.difficulty == 1)
                    HP -= (gp.monster[i].attack + 1);
                else
                    HP -= gp.monster[i].attack;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i)
    {
        if (i != 999)
        {
            if (gp.monster[i].invincible == false)
            {
                gp.monster[i].HP -= gp.player.attack;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].HP <= 0)
                {
                    score++;
                    gp.monster[i].dying = true;
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp()
    {
        if (exp >= nextLevelExp)
        {
            level++;
            nextLevelExp += 3;
            maxHP++;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            exp = nextLevelExp - exp;

            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You leveled up!";
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = normal;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "right","up":
                if (attacking == false) {
                    if (spriteNumber == 1)
                        image = idleR1;
                    if (spriteNumber == 2)
                        image = idleR2;
                    if (spriteNumber == 3)
                        image = idleR3;
                    if (spriteNumber == 4)
                        image = idleR4;
                }
                if (attacking == true) {
                    if (spriteNumber == 1)
                        image = attackR1;
                    if (spriteNumber == 2)
                        image = attackR2;
                    if (spriteNumber == 3)
                        image = attackR3;
                    if (spriteNumber == 4)
                        image = attackR4;
                }
                break;
            case "left","down":
                if (attacking == false) {
                    if (spriteNumber == 1)
                        image = idleL1;
                    if (spriteNumber == 2)
                        image = idleL2;
                    if (spriteNumber == 3)
                        image = idleL3;
                    if (spriteNumber == 4)
                        image = idleL4;
                }
                if (attacking == true) {
                    tempScreenX = screenX - 11;
                    if (spriteNumber == 1)
                        image = attackL1;
                    if (spriteNumber == 2)
                        image = attackL2;
                    if (spriteNumber == 3)
                        image = attackL3;
                    if (spriteNumber == 4)
                        image = attackL4;
                }
                break;
            default:
                switch (spriteNumber) {
                    case 1:
                        image = idleR1;
                        break;
                    case 2:
                        image = idleR2;
                        break;
                    case 3:
                        image = idleR3;
                        break;
                    case 4:
                        image = idleR4;
                        break;
                }
                break;
        }

        int x = screenX;
        int y = screenY;

        if (screenX > worldX)
            x = worldX;
        if (screenY > worldY)
            y = worldY;

        int rightOff = gp.screenWidth - screenX;
        if (rightOff > gp.worldWidth - worldX)
            x = gp.screenWidth - gp.worldWidth + worldX;

        int botOff = gp.screenHeight - screenY;
        if (botOff > gp.worldHeight - worldY)
            y = gp.screenHeight - gp.worldHeight + worldY;

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, x, y, gp.tileSize * 3, gp.tileSize, null);
       // g2.setColor(Color.red);
        //g2.drawRect(x,y,gp.tileSize,gp.tileSize);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
