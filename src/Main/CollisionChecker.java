package Main;

import Entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int LeftX = entity.worldX + entity.solidArea.x + gp.tileSize;
        int RightX = entity.worldX + entity.solidArea.x + entity.solidArea.width + gp.tileSize;
        int TopY = entity.worldY + entity.solidArea.y + 10;
        int BottomY = entity.worldY + entity.solidArea.y + entity.solidArea.height + 32;

        int LeftCol = LeftX / gp.tileSize;
        int RightCol = RightX / gp.tileSize;
        int TopRow = TopY / gp.tileSize;
        int BottomRow = BottomY / gp.tileSize;

        int tile1, tile2;

        switch (entity.direction) {
            case "up":
                TopRow = (TopY - entity.speed) / gp.tileSize;
                tile1 = gp.tileM.mapTileNum[LeftCol][TopRow];
                tile2 = gp.tileM.mapTileNum[RightCol][TopRow];
                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision)
                    entity.collisionOn = true;
                break;

            case "down":
                BottomRow = (BottomY + entity.speed) / gp.tileSize;
                tile1 = gp.tileM.mapTileNum[RightCol][BottomRow];
                tile2 = gp.tileM.mapTileNum[LeftCol][BottomRow];
                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision)
                    entity.collisionOn = true;
                break;

            case "right":
                RightCol = (RightX + entity.speed) / gp.tileSize;
                tile1 = gp.tileM.mapTileNum[RightCol][TopRow];
                tile2 = gp.tileM.mapTileNum[RightCol][BottomRow];
                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision)
                    entity.collisionOn = true;
                break;

            case "left":
                LeftCol = (LeftX - entity.speed) / gp.tileSize;
                tile1 = gp.tileM.mapTileNum[LeftCol][TopRow];
                tile2 = gp.tileM.mapTileNum[LeftCol][BottomRow];
                if (gp.tileM.tile[tile1].collision || gp.tileM.tile[tile2].collision)
                    entity.collisionOn = true;
                break;
        }
    }

    public int checkObject(Entity e, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++)
            if (gp.obj[i] != null) {
                e.solidArea.x = e.worldX + e.solidArea.x;
                e.solidArea.y = e.worldY + e.solidArea.y;

                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x - gp.tileSize;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y - gp.tileSize;

                switch (e.direction) {
                    case "up":
                        e.solidArea.y -= e.speed;
                        break;
                    case "down":
                        e.solidArea.y += e.speed;
                        break;
                    case "right":
                        e.solidArea.x += e.speed;
                        break;
                    case "left":
                        e.solidArea.x -= e.speed;
                        break;
                }

                if (e.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        e.collisionOn = true;
                        if (player)
                            index = i;
                    }
                }

                e.solidArea.x = e.solidAreaDefaultX;
                e.solidArea.y = e.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        return index;
    }

    public int checkEntity(Entity e, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++)
            if (target[i] != null) {
                e.solidArea.x = e.worldX + e.solidArea.x;
                e.solidArea.y = e.worldY + e.solidArea.y;

                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x - gp.tileSize;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y - gp.tileSize;

                switch (e.direction) {
                    case "up":
                        e.solidArea.y -= e.speed;
                        break;
                    case "down":
                        e.solidArea.y += e.speed;
                        break;
                    case "right":
                        e.solidArea.x += e.speed;
                        break;
                    case "left":
                        e.solidArea.x -= e.speed;
                        break;
                }

                if (e.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != e) {
                        e.collisionOn = true;
                        index = i;
                    }
                }

                e.solidArea.x = e.solidAreaDefaultX;
                e.solidArea.y = e.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        return index;
    }

    public boolean checkPlayer(Entity e) {

        boolean contactPlayer = false;

        e.solidArea.x = e.worldX + e.solidArea.x - gp.tileSize;
        e.solidArea.y = e.worldY + e.solidArea.y - gp.tileSize;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (e.direction) {
            case "up":
                e.solidArea.y -= e.speed;
                break;
            case "down":
                e.solidArea.y += e.speed;
                break;
            case "right":
                e.solidArea.x += e.speed;
                break;
            case "left":
                e.solidArea.x -= e.speed;
                break;
        }

        if (e.solidArea.intersects(gp.player.solidArea)) {
            e.collisionOn = true;
            contactPlayer = true;
        }

        e.solidArea.x = e.solidAreaDefaultX;
        e.solidArea.y = e.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
