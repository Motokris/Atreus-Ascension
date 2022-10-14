package Main;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];

    public EventHandler(GamePanel gp)
    {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;

            eventRect[col][row].width = 23;
            eventRect[col][row].height = 23;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;

            if (col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent()
    {
        if (hit(24,22,"any")) {
            damagePit(24,22,gp.dialogueState);
        }
        if (hit(21,12,"any"))
            healingPool(gp.dialogueState);
    }

    public boolean hit(int col, int row, String reqDirection)
    {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x - gp.tileSize;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y - gp.tileSize;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false)
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))
                hit = true;

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, int gameState)
    {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You take damage!";
        gp.player.HP--;
        eventRect[col][row].eventDone = true;
    }

    public void healingPool(int gameState)
    {
        if (gp.keyI.enter == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "HP restored";
            gp.player.HP = gp.player.maxHP;
            gp.asset.setMonster();
        }
        gp.keyI.enter = false;
    }
}
