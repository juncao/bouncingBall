/*
 * @(#)BallPanel.java
 */
package bouncingball;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.Timer;


/**
 * This is a JPane and will run as a tread. 
 * There is a function to detect signal or double mouse click.
 * Each double click will create a new thread for a moving ball.
 * Signal click will pause a moving ball.
 * This JPane will display balls based on a given frame rate.
 * @author Jun
 * @version 1.0
 */
public class BallPanel extends JPanel implements Runnable {
    /*use the xSzize and ySize value to define panel size*/
    private int xSize;
    private int ySize;
    /*use the xP and yP value to define ball position*/
    private int xP;
    private int yP;
    private Ball ball ;
    /*balls arraylist will store all created balls*/
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    /*pauseballs arraylist will store all balls that will to be paused*/
    private ArrayList<Ball> pauseBalls = new ArrayList<Ball>();
    private int frameRate = 25;
    private ExecutorService threadExecutor = Executors.newCachedThreadPool();
    private boolean random;
    /**
     * Create an instance of a new BallPanel.
     * New BallPanel will use the same size as the parent JFrame
     * This instance will run as a thread.
     * @return	An instance of a Composition entity.
     * @param f parent JFrame
     */
    public BallPanel(JFrame f){

    xSize = f.getWidth();
    ySize = f.getHeight();
    
    this.setPreferredSize(new Dimension( xSize, ySize) );
    this.setBackground(Color.black);
    this.setLayout(new BorderLayout());    
    //this.addMouseListener(this);
    this.addMouseListener(new Mouse());
      threadExecutor.execute(this); 
    }

    /**
     * Create a new Ball instance.
     * Add this ball to balls array list.
     * Also run the ball in a new thread.
     */
    public void createBall(){
        ball = new Ball(xP, yP,xSize,ySize, random );
        balls.add(ball);
        threadExecutor.execute(ball);
    }
    
    /**
     * Draw all the balls on their current location.
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
           super.paintComponent(g);
            for (Ball ball : balls) {
                ball.draw(g);
           }
     }


    /**
     * Call repaint() at a certain rate.
     */
    @Override
        public void run(){

            while(true){

                repaint();
                try {
                    Thread.sleep(1000 / frameRate);
                } catch (InterruptedException e) {}
                
                
            }
        }
    /**
     * Private class.
     * This will create a mouseAdapter to check double or signal click.
     */
    private class Mouse extends MouseAdapter{
        private Boolean doubleClick = false;
        private final Integer waitTime = (Integer)Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
        
        /**
         * Track mouse event to define signal or double click.
         * Double click will create a new ball instance.
         * Signal click will pause clicked balls.
         * @param e MouseEvent
         */
         @Override
    public void mousePressed(MouseEvent e) {
        //Get the point that mouse clicked
        xP = e.getX();
        yP = e.getY();   
        /**Compare mouse pressed point with all balls position to see which balls 
        are clicked. This step will run before the double click check to avoid 
        * position error caused by the waiting time.
        */
        if (balls != null){
         for (Ball ball : balls) 
         {
            if (((ball.getX()+ball.getR())> xP)&&((ball.getX()-ball.getR())< xP)
             &&((ball.getY()+ball.getR())> yP)&&((ball.getY()-ball.getR())< yP)){
             pauseBalls.add(ball);}
         }
        }
        
        if (e.getClickCount() >= 2) {
                doubleClick = true;
            } else {
                Timer timer = new Timer(waitTime, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        /* we are the first click of a double click */
                        if (doubleClick) {
                            random = false;
                            createBall();  
                            
                            doubleClick = false;
                            pauseBalls.clear();
                        } else {
                            /* the second click never happened */
                             for (Ball ball : pauseBalls) {
                                ball.pause();
                             }
                             if (pauseBalls.isEmpty()){
                                 random = true;
                                 createBall(); 
                             }
                             
                             pauseBalls.clear();
                        }
                    }

                   
                });
                timer.setRepeats(false);
                timer.start();
            }
    }
        
        
    }
    
    

}
