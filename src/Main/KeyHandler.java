package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean up, down, left, right, enter, escape, fkey;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            titleState(code);
        } else if (gp.gameState == gp.difficultyState) {
            difficultyState(code);
        } else if (gp.gameState == gp.level1State) {
            level1State(code);
        } else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        } else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        } else if (gp.gameState == gp.characterState) {
            characterState(code);
        } else if (gp.gameState == gp.loadGameState) {
            loadGameState(code);
        } else if (gp.gameState == gp.controlsState) {
            controlsState(code);
        }
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        } else if (gp.gameState == gp.victoryState)
            victoryState(code);
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0)
                gp.ui.commandNum = 2;
        } else if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2)
                gp.ui.commandNum = 0;
        }

        if (code == KeyEvent.VK_ENTER) {
            switch (gp.ui.commandNum) {
                case 0:
                    gp.gameState = gp.difficultyState;
                    break;
                case 1:
                    gp.gameState = gp.loadGameState;
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    public void level1State(int code) {
        if (code == KeyEvent.VK_W)
            up = true;
        else if (code == KeyEvent.VK_S)
            down = true;
        else if (code == KeyEvent.VK_A)
            left = true;
        else if (code == KeyEvent.VK_D)
            right = true;

        if (code == KeyEvent.VK_ESCAPE) {
            escape = true;
            if (gp.gameState == gp.level1State)
                gp.gameState = gp.pauseState;
        }

        if (code == KeyEvent.VK_C)
            gp.gameState = gp.characterState;

        if (code == KeyEvent.VK_F)
            gp.gameState = gp.controlsState;

        if (code == KeyEvent.VK_ENTER) {
            enter = true;
        }
    }

    public void difficultyState(int code)
    {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0)
                gp.ui.commandNum = 1;
        } else if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1)
                gp.ui.commandNum = 0;
        }

        if (code == KeyEvent.VK_ENTER)
        {
            gp.difficulty = gp.ui.commandNum;
            gp.gameState = gp.level1State;
            gp.restart();
        }

        if (code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gp.titleState;
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            escape = true;
            gp.gameState = gp.level1State;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            escape = true;
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.level1State;
        }
        if (code == KeyEvent.VK_ENTER) {
            enter = true;
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C)
            gp.gameState = gp.level1State;
    }

    public void loadGameState(int code)
    {
        if (code == KeyEvent.VK_ENTER) {
            gp.config.loadConfig();
            gp.gameState = gp.level1State;
        }
    }

    public void controlsState(int code)
    {
        if (code == KeyEvent.VK_F)
            gp.gameState = gp.level1State;

        if (code == KeyEvent.VK_ENTER)
        {
            gp.config.saveConfig();
            enter = true;
        }
    }

    public void gameOverState(int code)
    {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0)
                gp.ui.commandNum = 1;
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1)
                gp.ui.commandNum = 0;
        }

        if (code == KeyEvent.VK_ENTER)
        {
            switch(gp.ui.commandNum)
            {
                case 0:
                    gp.gameState = gp.level1State;
                    gp.retry();
                    break;
                case 1:
                    gp.gameState = gp.titleState;
                    gp.restart();
                    break;
            }
        }
    }

    public void victoryState(int code)
    {
        gp.config.saveConfig();
        if (code == KeyEvent.VK_ENTER)
        {
            gp.gameState = gp.titleState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W)
            up = false;
        else if (code == KeyEvent.VK_S)
            down = false;
        else if (code == KeyEvent.VK_A)
            left = false;
        else if (code == KeyEvent.VK_D)
            right = false;

    }
}
