
/* applying concepts of multi-threading and graphics programming 
 * to create a gui based application that animates
 *  a clockwise revolving ball about some central point 
 *  
 *  data members:
 *  1>the coordinates of central pt- xOrigin and yOrigin
 *  2>the radius of bigger circle (on the periphery of which the ball revolves)
 *  -radius
 *  3>the angle which the ball makes with x-axis - angle
 *  
 *  for the position(ballOriginX,ballOriginY) of the ball at some time,
 *   we need to understand that 
 *  we will use the components of radius (cos and sin)(radius is the line that
 *  joins the ball and center)
 *  therefore, (x-component)[ballOriginX=radius*cos(angle)] & (y-component)[ballOriginY=radius*sin(angle)]
 *  
 *  correction :- our calculation ignores the fact that origin is not (0,0)
 *  for the smaller ball;
 *   but (xOrigin,yOrigin) and thats why our x and y components now become
 *   ballOriginX=xOrigin+radius*cos(angle)&
 *   ballOriginY=yOrigin+radius*sin(angle)
 *   [found later:- the adjustment simply tells the position of ball
 *   relative to the pivot and thats it]
 *   
 *   the coordinate system of screen :
 *   it has to be noted that the coordinate system here is an
 *    upside down fliped Cartesian , meaning X is same but Y is upside down 
 *    which means incrementing the 'angle' will move the ball in clockwise direction
 *    unlike in Cartesian plane where it moves it anti-clockwise. 
 *  
 */
import java.awt.*;
public class rotatingBallframe extends Frame 
implements Runnable{
	int xOrigin;
	int yOrigin;
	int radius=200;
	int ballradius=20;
	
	double currentAngle;//currently subtended ang by ball
	Thread thread;//as we will make animation , we need thread obj ,making reference variable
	
	//making constructor and inititializing values
	public rotatingBallframe( int xOrigin,int yOrigin,double initialAngle)
	{
		this.xOrigin=xOrigin;
		this.yOrigin=yOrigin;
		this.currentAngle=initialAngle;
		
		this.setBackground(Color.BLACK);//setting background of current object
		
		thread = new Thread(this, "Rotating ball");
		//(associating runnable implementing class's obj with thread obj)
		/* Note copied:By passing this as the first argument to the
		 *  Thread constructor, 
		 * it implies that the current object (which is an instance of a class
		 *  implementing Runnable or extending Thread) will be the target
		 * for the thread's execution(read again ). This assumes that the class
		 *  on which
		 * this statement appears implements the Runnable interface
		 * or extends the Thread class and overrides the run() method
		 *to define the desired behavior for the thread.
		 * The thread created by this statement will execute the 
		 * run() method of the object referenced by this in a separate thread 
		 * of execution. The thread can perform its designated task
		 *  concurrently with other parts of the program.

         The "Rotating ball" string is an optional name assigned to the thread,
         which can be useful for identification and debugging purposes.

         Note that after this statement, you will typically need to start
         the thread's execution by calling thread.start() to actually begin
          the separate thread's execution and invoke the 
          run() method of the current object.
		 */
		thread.start();
		/*The start() method of the Thread object is used to start
		 *  the execution of the associated run() method. 
		 *  When you call start(),
		 *  it internally invokes the run() 
		 * method of the Runnable instance in a separate thread of execution.
		 */
	}
		public void run()
		{
			while (true) {
				currentAngle+=2;
				this.repaint();
				try {
					Thread.sleep(100);//causing delay for smoothness
				}
				catch(InterruptedException ie) {
					System.out.println(ie.getLocalizedMessage());
				}
			}
		}
		
		public void paint(Graphics g)
		{
			//drawing the ball's path
			g.setColor(Color.WHITE);
			g.drawOval(xOrigin-radius, yOrigin-radius, radius*2, radius*2);
			/*
			 * xOrigin-radius is the x-coordinate of the top-left corner of the 
			 * oval's bounding rectangle.
			 * yOrigin-radius is the y-coordinate of the top-left corner of the
			 *  oval's bounding rectangle.
              width(radius*2) is the width of the oval.
              & height(radius*2) is the height of the oval
              (see associated diagram
               for better understanding of calculations in terms of RADIUS)
			 */
			
			//drawing ball
			int ballOriginX;
			int ballOriginY;
			
			ballOriginX=(int)(radius*Math.cos(currentAngle*Math.PI/180));
			//converting angle from degree to radian
			ballOriginY=(int)(radius*Math.sin(currentAngle*Math.PI/180));
			// remeber these values are considering origin as (0,0)
			g.setColor(Color.BLUE);
			g.drawOval(xOrigin+ballOriginX-ballradius, yOrigin+ballOriginY-ballradius,
					ballradius*2, ballradius*2);
			//complex looking format is as simple as the earlier drawOval
			//it just combines the offset adjustment and bounding rectangle calculation
			// offset calc- position of ball relative to pivot


	}
		
		public static void main(String []args)
		{
			rotatingBallframe rotatingBallframe=
					new rotatingBallframe(400,400,0);
			rotatingBallframe.setSize(400,400);
			rotatingBallframe.setVisible(true);
			rotatingBallframe.setTitle("Rotating ball");
			
			
		}

}
