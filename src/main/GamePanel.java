package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS//
    final int orginalTileSize = 16; //16x16
    final int scale = 3; //how much our tile has been scale
    public final int tileSize= orginalTileSize*scale;
    public final int maxScreenCol =16;
    public final int maxScreenRow =12;
    final int screenWidth= tileSize*maxScreenCol;
    final int screenHight = tileSize*maxScreenRow;
    int FPS = 60;
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);

    //Player default position
    int playerX=100;
    int playerY=100;
    int playerSpeed=4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this. setFocusable(true);

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(gameThread !=null){

            currentTime = System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;
            timer+= (currentTime-lastTime);
            lastTime=currentTime;
            if(delta>=1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer>=1000000000){
                System.out.println("FPS: "+drawCount);
                drawCount=0;
                timer = 0;
            }
        }
    }
    public void update(){
    player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);
        player.draw(g2);
         g2.dispose();
    }
}