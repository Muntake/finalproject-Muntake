

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author lalim9800
 */


public class Game extends JComponent implements KeyListener, MouseMotionListener, MouseListener { 

    // Height and Width of our game
    static final int WIDTH = 600;
    static final int HEIGHT = 700;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    //create a int to stote the first pipe x value 
    int x=300;
     ArrayList<Rectangle> blocks = new ArrayList<>();
    
    //create player 
     Rectangle player = new Rectangle(60, 597, 50, 50);
     
     //ceate a integer for the y value 
    int movey=0;
     //create gravity integer 
     int gravity=1;
     int thispipe=0;
     int counter=0;
   //create jump variable (keyboard variables)
    boolean jump=false;        
   //previous jump boolean 
    boolean pjump=false;
    boolean done = false; 
    //create new font
    Font newFont= new Font("Helvetica",Font.BOLD,50);
    

    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        Color ground=new Color(232,205,190);
        // GAME DRAWING GOES HERE 
        g.setColor(Color.GREEN);
         for(Rectangle block: blocks){
            // draw the block
            g.fillRect(block.x, block.y, block.width, block.height);
        
         }
        
        
        //set color to yellow 
        g.setColor(Color.yellow);
        //draw the flappy cube 
        g.fillRect(player.x, player.y, player.width,player.height);        
        //make ground
        g.setColor(Color.GREEN);
        g.drawRect(0, 647, 600, 1);
        g.setColor(ground);
        g.setColor(Color.black);
        if(done){
            //set new font created 
            g.setFont(newFont);
           g.drawString("GAMEOVER",player.x,player.y);
        }
             
     
       
        
       
           
            
            
            
   // GAME DRAWING ENDS HERE
    }
    
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run(){
        //do intail things 
        blocks.add(new Rectangle(500,0,100,200));
        blocks.add(new Rectangle(1000,0,100,400));
        blocks.add(new Rectangle(1500,0,100,100));
        blocks.add(new Rectangle(2000,0,100,300));
        blocks.add(new Rectangle(2500,0,100,350));
        blocks.add(new Rectangle(3000,0,100,100));
        
         blocks.add(new Rectangle(500,400,100,647-400));
         blocks.add(new Rectangle(1000,600,100,647-600));
         blocks.add(new Rectangle(1500,300,100,647-300));
         blocks.add(new Rectangle(2000,500,100,647-500));
         blocks.add(new Rectangle(2500,550,100,647-550));
         blocks.add(new Rectangle(3000,300,100,647-300));
        
      
        
        //end initail things 
    
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
         
        // the main game loop section
        // game will end if you set done = false;
        
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
             
            
            //gravity pulling player down
            movey = movey + gravity; 
            //jumping 
            if (jump && !pjump) {
                //make a big change in y direction
                movey = -15;
                pjump=true;
               
            }
                
                //move player in y direction 
                 player.y = player.y + movey;
            //if feet of playet become lower than floor
            if (player.y + player.height > 647) {
                player.y = 647 - player.height;
                movey = 0;
              
                
                
            }
            
             // go through all blocks and decrase the x value by 5 (moving function)
            for(Rectangle block: blocks){
                block.x=block.x-5;
                thispipe=thispipe-5;
                System.out.println(counter);
                if (thispipe==-500){
                    thispipe=0;
                }     
                 
                
                
                
            }
             
             for(Rectangle block: blocks){
                
                if(player.intersects(block)){
                 player.y=597;
                    done = true;
                    
                }
            }
           
            
            
            
            
            
           
               
                
                    
                    
            
            
            // GAME LOGIC ENDS HERE 
            //
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        
    }    
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        Game game = new Game();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_SPACE){
            jump=true;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
         int key = e.getKeyCode();
        if(key==KeyEvent.VK_SPACE){
            jump=false;
            pjump=false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
