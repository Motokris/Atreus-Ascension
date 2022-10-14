package Main;

import Entity.Entity;
import Objects.Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font arial_40;
    Graphics2D g2;
    public boolean messageOn = false;
    private BufferedImage background;
    private BufferedImage heart, heart_empty;
    public String message = "";
    public String currentDialogue = "";
    public int commandNum = 0;
    public int subState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        Entity HP = new Heart(gp);
        heart = HP.image;
        heart_empty = HP.image2;

        arial_40 = new Font("Arial", Font.PLAIN,40);
    }

    public void draw(Graphics2D g2)
    {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        if (gp.gameState == gp.titleState) {
             drawTitleScreen();
        }
        else if (gp.gameState == gp.difficultyState)
        {
            drawDifficultyScreen();
        }
        else if (gp.gameState == gp.level1State)
        {
            drawPlayerLife();
        }
        else if (gp.gameState == gp.pauseState)
        {
            drawPauseScreen();
            drawPlayerLife();
        }
        else if (gp.gameState == gp.dialogueState)
        {
            drawDialogueScreen();
            drawPlayerLife();

            if (gp.keyI.escape == true) {
                gp.gameState = gp.level1State;
            }
        }
        else if (gp.gameState == gp.characterState)
        {
            drawCharacterScreen();
        }

        else if (gp.gameState == gp.controlsState)
        {
            drawControls();
        }
        else if (gp.gameState == gp.gameOverState)
        {
            drawgameOver();
        } else if (gp.gameState == gp.victoryState)
        {
            drawVictory();
        }
    }

    public void drawPlayerLife()
    {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        while (i < gp.player.maxHP)
        {
            g2.drawImage(heart_empty,x,y,null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        while (i < gp.player.HP)
        {
            g2.drawImage(heart,x,y,null);
            i++;
            x += gp.tileSize;
        }
    }

    public void drawTitleScreen()
    {

        int x = 315;
        int y = 365;

        try {
            background = ImageIO.read(getClass().getResourceAsStream("/Misc/background1.gif"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        g2.drawImage(background, 0, 0, null);
        g2.setColor(Color.RED);
        g2.setFont(new Font("Shadow Into Light", Font.PLAIN, 28));
        String text = "Atreus' Ascension";
        g2.drawString(text, 300, 320);


        g2.setFont(new Font("Arial" , Font.PLAIN, 20));
        text = "Start Game";
        g2.drawString(text, x, y);
        if (commandNum == 0)
        {
            g2.drawString(">", 295,y);
        }


        text = "Load Game";
        y += 25;
        g2.drawString(text, x, y);
        if (commandNum == 1)
        {
            g2.drawString(">", 295,y);
        }

        text = "Quit";
        y += 25;
        g2.drawString(text, x, y);
        if (commandNum == 2)
        {
            g2.drawString(">", 295,y);
        }
    }

    public void drawDifficultyScreen()
    {
        int x = 315;
        int y = 365;

        try {
            background = ImageIO.read(getClass().getResourceAsStream("/Misc/background1.gif"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        g2.drawImage(background, 0, 0, null);
        g2.setColor(Color.RED);
        g2.setFont(new Font("Shadow Into Light", Font.PLAIN, 28));
        String text = "Atreus' Ascension";
        g2.drawString(text, 300, 320);


        g2.setFont(new Font("Arial" , Font.PLAIN, 20));
        text = "Easy";
        g2.drawString(text, x, y);
        if (commandNum == 0)
        {
            g2.drawString(">", 295,y);
        }


        text = "Hard";
        y += 25;
        g2.drawString(text, x, y);
        if (commandNum == 1)
        {
            g2.drawString(">", 295,y);
        }
    }

    public void drawPauseScreen()
    {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80));
        g2.setColor(Color.WHITE);
        String text = "PAUSE";

        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen()
    {
        int x = gp.tileSize * 2;
        int y = gp.tileSize + 20;
        int width = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize * 4;
        drawSubWindow(x,y,width,height);

        x += gp.tileSize;
        y += gp.tileSize;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        g2.drawString(currentDialogue,x,y);
    }

    public void drawCharacterScreen()
    {
        final int frameX = gp.tileSize
                 ,frameY = gp.tileSize
                 ,frameWidth = gp.tileSize * 5
                 ,frameHeight = gp.tileSize * 7;

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(24F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        g2.drawString("Level",textX,textY);
        textY += lineHeight;
        g2.drawString("HP",textX,textY);
        textY += lineHeight;
        g2.drawString("Strength",textX,textY);
        textY += lineHeight;
        g2.drawString("Dexterity",textX,textY);
        textY += lineHeight;
        g2.drawString("Attack",textX,textY);
        textY += lineHeight;
        g2.drawString("Defense",textX,textY);
        textY += lineHeight;
        g2.drawString("EXP",textX,textY);
        textY += lineHeight;
        g2.drawString("NextLvlEXP",textX,textY);
        textY += lineHeight;
        g2.drawString("Score",textX,textY);

        int tailX = frameX + frameWidth - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.HP + "/" + gp.player.maxHP);
        textX = getXforRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforRightText(value,tailX);
        g2.drawString(value,textX,textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.score);
        textX = getXforRightText(value,tailX);
        g2.drawString(value,textX,textY);

    }

    public void drawControls()
    {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        int frameX = gp.tileSize * 4,
                frameY = gp.tileSize,
                frameWidth = gp.tileSize * 9,
                frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        int textX;
        int textY;

        String text = "Controls + End";
        textX = getXforCenteredText(text) + 125;
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;

        g2.drawString("Move",textX,textY);
        textY += gp.tileSize;
        g2.drawString("Attack/Interact",textX,textY);
        textY += gp.tileSize;
        g2.drawString("Stats",textX,textY);
        textY += gp.tileSize;
        g2.drawString("Pause",textX,textY);
        textY += gp.tileSize;
        g2.drawString("End Game Now",textX,textY);
        if (gp.keyI.enter == true) {
            gp.gameState = gp.titleState;
        }

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;

        g2.drawString("WASD",textX,textY);
        textY += gp.tileSize;
        g2.drawString("Enter",textX,textY);
        textY += gp.tileSize;
        g2.drawString("C",textX,textY);
        textY += gp.tileSize;
        g2.drawString("Esc",textX,textY);
        textY += gp.tileSize;
        g2.drawString("Enter",textX,textY);
    }

    public void drawgameOver()
    {
       g2.setColor(new Color(0,0,0,150));
       g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

       int x;
       int y;
       String text;
       g2.setFont(g2.getFont().deriveFont(Font.BOLD,45F));

       text = "Game Over";
       g2.setColor(Color.black);
       x = gp.screenWidth / 2 - 150;
       y = gp.tileSize * 4;
       g2.drawString(text,x,y);

       g2.setColor(Color.WHITE);
       g2.drawString(text,x - 4,y - 4);

       y += 55;
       int x2 = x + 25;
       text = "Score: " + gp.player.score;
       g2.drawString(text,x2,y);

       g2.setFont(g2.getFont().deriveFont(40F));
       text = "Retry";
       x += 65;
       y += gp.tileSize * 3;
       g2.drawString(text,x,y);

       if (commandNum == 0)
       {
           g2.drawString(">",x - 40,y);
       }

       text = "Quit";
       y += gp.tileSize + 20;
       g2.drawString(text,x,y);

        if (commandNum == 1)
        {
            g2.drawString(">",x - 40,y);
        }
    }

    public void drawVictory()
    {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,45F));

        text = "Victory!";
        g2.setColor(Color.black);
        x = gp.screenWidth / 2 - 75;
        y = gp.tileSize * 4;
        g2.drawString(text,x,y);

        g2.setColor(Color.WHITE);
        g2.drawString(text,x - 4,y - 4);

        y += 55;
        int x2 = x + 25;
        text = "Score: " + gp.player.score;
        g2.drawString(text,x2,y);
    }

    public int getXforRightText(String text, int tailX) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    public int getXforCenteredText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x =  gp.screenWidth / 2 - length;
        return x;
    }

    public void drawSubWindow(int x, int y, int width, int height)
    {
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }
}
