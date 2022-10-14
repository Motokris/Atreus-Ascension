package Main;

import Entity.NPC;
import Monster.King;
import Monster.Slime;
import Objects.Chest;
import Objects.Door;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }

    public void setObject()
    {

    }

    public void setNPC()
    {
        gp.npc[0] = new NPC(gp);
        gp.npc[0].worldX = gp.tileSize * 23;
        gp.npc[0].worldY = gp.tileSize * 23;
    }

    public void setMonster()
    {
        gp.monster[0] = new Slime(gp);
        gp.monster[0].worldX = gp.tileSize * 21;
        gp.monster[0].worldY = gp.tileSize * 21;

        gp.monster[1] = new Slime(gp);
        gp.monster[1].worldX = gp.tileSize * 13;
        gp.monster[1].worldY = gp.tileSize * 29;

        gp.monster[2] = new Slime(gp);
        gp.monster[2].worldX = gp.tileSize * 8;
        gp.monster[2].worldY = gp.tileSize * 9;

        gp.monster[3] = new Slime(gp);
        gp.monster[3].worldX = gp.tileSize * 10;
        gp.monster[3].worldY = gp.tileSize * 11;

        gp.monster[4] = new Slime(gp);
        gp.monster[4].worldX = gp.tileSize * 36;
        gp.monster[4].worldY = gp.tileSize * 10;

        gp.monster[5] = new Slime(gp);
        gp.monster[5].worldX = gp.tileSize * 39;
        gp.monster[5].worldY = gp.tileSize * 11;

        gp.monster[6] = new King(gp);
        gp.monster[6].worldX = gp.tileSize * 21;
        gp.monster[6].worldY = gp.tileSize * 39;
    }
}
