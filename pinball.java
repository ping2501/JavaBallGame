import java.applet.*;
import java.awt.*;
import java.util.*;
import java.awt.Event.*;



/*
<APPLET
CODE=pinball.class
WIDTH=400
HEIGHT=400 >
</APPLET>
*/

public class pinball extends Applet implements Runnable
{
        // Initializing variables
        int x_pos = 30;                          //current  x - Position for Ball
        int y_pos = 100;                          //current  y - Position for Ball
        int x_speed = 2;                      // speed  along x axis
        int radius = 10;                      //Radius of ball
        int y_speed=2;                           //speed along y axis
        int appletsize_x = 400;            // size of applet
        int appletsize_y = 400;
        int x_rect=200;                 // current x postion of rectangle
        int y_rect=400;                  //current y postion of rectangle
        int x_speed1=2;              //speed along x axis
        int y_speed1=0;              //speed along y axis
        int w=30, h=15;                 //width and height
        AudioClip mysound,mysound1,mysound2,mysound3;
       // boolean collide;
        Image backimage;
        private Player player;
        // Variables 
	private Image dbImage;
	private Graphics dbg;
       

	public void init()
               {         player= new Player();
                         
                        setBackground (Color.black);
                try
                {
                  mysound=getAudioClip(getCodeBase(),"mp3.au");
          //      mysound1=getAudioClip(getCodeBase(),"gun.au");
                }
                catch(Exception e) {}

        }


       public void start ()
	{
                // thread intailization
		Thread th = new Thread (this);
                // Start thread
		th.start ();
        } 

	public void stop()
	{
          Thread th = new Thread (this);
          th.stop ();
	}

	public void destroy()
	{

	}

       public boolean mouseEnter(Event e,int x ,int y)
        {

       
       mysound.play();
        return(true) ;
        }
        public boolean mouseExit(Event e,int x,int y)
        {
       mysound.stop();
        return(true);
        }
        public boolean keyDown(Event e,int key)
        {
        if(key==1006)
        {     
           x_rect += x_speed1= -4;
        }
        else if(key==1007)
        {
        x_rect += x_speed1= +4;
        }
        else if(key==32)
        {
        x_speed1=0;
        y_speed1=0;
        }
    /*    else if(key==1004)
        {
              y_speed1 = -1;
        }
        else if(key==1005)
        {
        y_speed1= +1;
        }   */

        if(key==49)  
        {
         mysound.play();
        
        }
        if(key==50)    
        {
          mysound.stop();
        
        }
        return true;
        }
	public void run ()
	{
                
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

                
                while (true)
		{
                         //if ball move out of appletsize limit
			if (x_pos > appletsize_x - radius)
			{
                                
                                x_speed = -3;
			}

                        else if (x_pos < radius)
			{
				
                                x_speed = +4;
			}
                        else if(y_pos > appletsize_y - radius)
                        {
                       // y_speed = -2;
                        x_pos=0;
                        y_pos=10;
                        player.looseLife();
                        }
                        else if(y_pos <radius)
                        {
                        y_speed = +2;
                        }
                        else if(x_rect>appletsize_x)
                        {
                          x_speed1= -1;

                       }
                       else if(x_rect<w)
                       {
                       x_speed1 = +1;
                       }
                       else if(y_rect>appletsize_y)
                       {
                       y_speed1 = -1;
                       }
                       else if(y_rect<h)
                       {
                       y_speed1= +1;
                       }
               
                        if(y_pos+radius >=y_rect-h  && x_pos >=x_rect-w   && x_pos+radius  <= x_rect+h  && y_pos+radius<=y_rect)  
                                                                                         
                       {
                                                                                                      
                        y_speed= -2;
                        x_speed= -1;
                     //   x_speed= +1;
                        
                        player.addScore(5);

                        }


                        if(player.getLives() <=0)
                        {
                        x_speed=0;
                        y_speed=0;
                        x_speed1=0;
                       
                        
                        }


                        
                        x_pos += x_speed+2;
                        y_pos += y_speed;
                      //  x_rect += x_speed1 ;
                      //  y_rect += y_speed1 ;
                        
			repaint();

			try
			{
                               
				Thread.sleep (20);
			}
			catch (InterruptedException ex)
			{
				// do nothing
			}

                       
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}

        /** Update - Method */
	public void update (Graphics g)
	{
		// DoubleBuffers
		if (dbImage == null)
		{
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

                
		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);

                
		dbg.setColor (getForeground());
		paint (dbg);

                
		g.drawImage (dbImage, 0, 0, this);
	}

	public void paint (Graphics g)
	{
		g.setColor  (Color.red);

                g.fillOval(x_pos-radius , y_pos-radius , 2 * radius, 2 * radius);
                g.setColor(Color.green);
                g.fillRect(x_rect-w ,y_rect-h ,w,h);
            
            if(player.getLives() > 0)
              { g.setColor(Color.gray);
              g.drawString("Scores" + player.getScore(),10,40);
              g.drawString("Lives" +  player.getLives(),10,360);

            }
              
     else if(player.getLives() <= 0)
             {


              g.drawString("Game Is Over",180,180);
              
               
              if (player.getScore() < 50) g.drawString ("Well, it could be better!", 100, 190);
			else if (player.getScore() < 100 && player.getScore() >= 50) g.drawString ("That was not so bad", 100, 190);
			else if (player.getScore() < 200 && player.getScore() >= 100) g.drawString ("That was really good", 100, 190);
			else if (player.getScore() < 400 && player.getScore() >= 200) g.drawString ("You seem to be very good!", 90, 190);
			else if (player.getScore() < 700 && player.getScore() >= 400) g.drawString ("That was nearly perfect!", 90, 190);
			else if (player.getScore() >= 1000) g.drawString ("You are the Champingon!",100, 190);
                
              }  
	}

}
