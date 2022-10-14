package Main;

import java.io.*;
import java.sql.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {

        Connection c;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Stats.db");
            c.setAutoCommit(false);

            int difficulty = gp.difficulty;
            int score = gp.player.score;
            int level = gp.player.level;
            int maxHP = gp.player.maxHP;
            int HP = gp.player.HP;
            int exp = gp.player.exp;
            int nextLevelExp = gp.player.nextLevelExp;
            int strength = gp.player.strength;
            int dexterity = gp.player.dexterity;

            String sql = "INSERT INTO STATS (Difficulty,Score,Level,MaxHP,HP,EXP,NextLevelExp,Strength,Dexterity)" + "VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement update = c.prepareStatement(sql);
            update.setInt(1,difficulty);
            update.setInt(2,score);
            update.setInt(3,level);
            update.setInt(4,maxHP);
            update.setInt(5,HP);
            update.setInt(6,exp);
            update.setInt(7,nextLevelExp);
            update.setInt(8,strength);
            update.setInt(9,dexterity);

            update.executeUpdate();
            update.close();
            c.commit();
            c.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records saved");
    }

    public void loadConfig() {

        Connection c;
        Statement s;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Stats.db");
            c.setAutoCommit(false);
            s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM STATS");

            while (rs.next())
            {
                gp.difficulty = rs.getInt("Difficulty");
                gp.player.score = rs.getInt("Score");
                gp.player.level = rs.getInt("Level");
                gp.player.maxHP = rs.getInt("MaxHP");
                gp.player.HP = rs.getInt("HP");
                gp.player.exp = rs.getInt("EXP");
                gp.player.nextLevelExp = rs.getInt("NextLevelExp");
                gp.player.strength = rs.getInt("Strength");
                gp.player.dexterity = rs.getInt("Dexterity");
            }

            rs.close();
            s.close();

            c.commit();
            c.close();

        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records loaded");
    }
}
