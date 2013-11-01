/*
 *@(#)Ball.java
 */
package bouncingball;

import java.awt.Color;
import java.awt.Graphics;



/**
 * This class is runnable and includes all information for a moving ball. 
 * Two kinds of ball included with different settings.
 * Ball will move when the instance running as a thread.
 * @author Jun
 * @version 1.0
 */
public class Ball implements Runnable{
    /**
     * Use more calculation to get a more smooth distributed random value.
     * @return	An integer number.
     * @param maxValue integer number for the max random value.
     */
    public static int Random(int maxValue) {
    return (int) Math.round((Math.random() * maxValue));
    }

    private int x ;
    private int y ;
    private int dx ;
    private int dy ;
    private int time= Random(50000) ;
    private int radius;
    private int R= Random(255);
    private int G= Random(255);
    private int B= Random(255);

    int xBound;
    int yBound;
    private boolean run = true;

    /**
     * Create an instance of a new Ball.
     * New Ball has two different behaviors.
     * One is only move up and down.
     * Another kind will move randomly.
     * @return	An instance of Ball.
     * @param x ball x position 
     * @param y ball y position 
     * @param xB parent panel x bound
     * @param yB parent panel y bound
     * @param r true for a radom moving ball
     */
    public Ball(int x, int y, int xB, int yB, boolean r){

        this.x = x ;
        this.y = y ;
        this.xBound = xB;
        this.yBound = yB;
        if (r){
            while((dx==0)&&(dy==0)){
            dx = 7-Random(14);
            dy = 7-Random(14);
            }
            radius = 15;
        }else{
            dy = 5+Random(5);
            radius = 30;
        }
         
    }
    
    /**
     * get ball ball x position 
     * @return ball x position 
     */
    public int getX(){
        return x;
    }
    
    /**
     * get ball y position 
     * @return ball y position 
     */
    public int getY(){
        return y;
    }
    
    /**
     * get ball radius
     * @return ball radius
     */
    public int getR(){
        return radius;
    }
    
    /**
     * Draw ball at position x, y with radius
     * @param g graphics
     */
    public void draw(Graphics g){
        g.setColor(new Color(R,G,B));
        g.fillOval((x-radius) , (y-radius), (2 * radius), (2 * radius));
    }   
    
        
    /**
     * change the "run" to false to pause the ball
     */
    public void pause(){
        run = false;
    }
    
    /**
     * Control ball moving.
     * Use speed on x and y to update ball position for every 500 milliseconds.
     * Will pause ball for 5 seconds when "run" is false.
     */
    @Override
    public void run() {
        while(true){
                if(run){
               
                    x+=dx;
                    y+=dy;
                    time -= 20;

                    if (dx ==0 )// If the ball is a vertically moving ball
                    {
                         if ((y - radius)<0){
                        dy = -dy;
                        y = radius;
                        }else if ((y+ radius)>yBound){
                        if (time<0){
                            dy = 0;
                             y = yBound -  radius;
                        }else{
                        dy = -dy;
                        y = yBound -  radius;
                        }
                        }

                    }else{// If the ball is a random moving ball

                         if (x- radius<0){
                            dx = -dx;
                            x = radius;
                        }else if ((x+ radius)>xBound){
                            dx = -dx;
                            x = xBound -  radius;
                        }
                        if ((y - radius)<0){
                            dy = -dy;
                            y = radius;
                        }else if ((y+ radius)>yBound){
                            dy = -dy;
                            y = yBound -  radius;
                        }
                        /**
                        if (x- radius<0){
                            if (time <0){
                                dx = 0;
                                dy = 0 ;
                                x =  radius;
                            }else{
                            dx = -dx;
                            x = radius;
                            }
                        }else if ((x+ radius)>xBound){
                            if (time <0){
                                dx = 0;
                                dy = 0 ;
                                x = xBound -  radius;
                            }else{
                            dx = -dx;
                            x = xBound -  radius;
                            }
                        }
                        if ((y - radius)<0){
                             if (time <0){
                                dx = 0;
                                dy = 0 ;
                                y =  radius;
                            }else{
                            dy = -dy;
                            y = radius;
                             }
                        }else if ((y+ radius)>yBound){
                            if (time<0){
                                dx = 0 ;
                                dy = 0;
                                 y = yBound -  radius;
                            }else{
                            dy = -dy;
                            y = yBound -  radius;
                            }
                        }
                        */
                }    
                try {
                    Thread.sleep(1000 / 20);
                } catch (InterruptedException e) {}
                }else{
                    try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {}
                    run = true;
                }
                
            }  
    }
}
