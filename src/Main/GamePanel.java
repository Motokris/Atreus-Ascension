package Main;

import Entity.Entity;
import Entity.Player;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN
    int originalTileSize = 16;
    int scale = 3;

    public int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //WORLD
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    //FPS
    int fps = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyI = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter asset = new AssetSetter(this);
    public Player player = new Player(this,keyI);
    public Entity[] obj = new Entity[10];
    public UI ui = new UI(this);
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[10];
    public EventHandler eHandler = new EventHandler(this);
    ArrayList<Entity> entityList = new ArrayList<>(20);
    public Config config = new Config(this);
    public int difficulty = 0;

    //GameState
    public int gameState;
    public final int pauseState = -1;
    public final int titleState = 0;
    public final int level1State = 1;
    public final int loadGameState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int controlsState = 5;
    public final int difficultyState = 6;
    public final int gameOverState = 7;
    public final int victoryState = 8;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyI);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        asset.setObject();
        asset.setNPC();
        asset.setMonster();
        gameState = titleState;
        player.setDefaultPositions();
        player.restoreHP();
    }

    public void retry()
    {
        player.setDefaultPositions();
        player.restoreHP();
        asset.setMonster();
        asset.setNPC();
    }

    public void restart()
    {
        player.setDefaultPositions();
        player.setDefaultValue();
        asset.setObject();
        asset.setNPC();
        asset.setMonster();
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run()
    {
       double drawInterval = 1000000000 / fps;
        double delta = 0;
        long currentTime;
        long lastTime = System.nanoTime();

        while (gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) /drawInterval;
            lastTime = currentTime;

            if (delta >= -1)
            {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update()
    {
        if (gameState == level1State) {
            player.update();

            for (int i = 0; i < npc.length; i++)
               if (npc[i] != null)
                    npc[i].update();

            for (int i = 0; i < monster.length; i++)
                if (monster[i] != null) {
                    if(monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if(monster[i].HP <= 0) {
                        if (monster[6].HP <= 0) {
                            gameState = victoryState;
                        }
                        monster[i] = null;
                    }
                }
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState)
        {
            ui.draw(g2);
        }
        else if (gameState != difficultyState) {

            tileM.draw(g2);

            entityList.add(player);

            for(int i = 0; i < npc.length; i++)
                if(npc[i] != null)
                    entityList.add(npc[i]);

            for(int i = 0; i < monster.length; i++)
                if(monster[i] != null)
                    entityList.add(monster[i]);

            for(int i = 0; i < obj.length; i++)
                if(obj[i] != null)
                    entityList.add(obj[i]);

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);
                    return result;
                }
            });

            for (int i = 0; i < entityList.size(); i++)
            {
                entityList.get(i).draw(g2);
            }

            entityList.clear();

            ui.draw(g2);

            /*g2.setFont(new Font("Arial",Font.PLAIN,20));
            g2.setColor(Color.WHITE);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            g2.drawString("WorldX: " + player.worldX,x,y);
            y += lineHeight;
            g2.drawString("WorldY: " + player.worldY,x,y);
            y += lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize,x,y);
            y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize,x,y);

             */
        }
        else
        {
            ui.draw(g2);
        }

        g2.dispose();
    }
}
