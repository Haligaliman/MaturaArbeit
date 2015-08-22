import java.awt.*;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Pixel extends JPanel {

	private int color,size,x,y;
	
	public Pixel(/*int x,int y,*/ int color,int size) {       
		//this.x=x;
		//this.y=y;
		this.color=color;
		this.size=size;
		//System.out.println(x+"," + y + "," +color);
	   }
	
	public Pixel(int x,int y, int color,int size) {       
		this.x=x;
		this.y=y;
		this.color=color;
		this.size=size;
		//System.out.println(x+"," + y + "," +color);
	   }
	 
	   public void paintComponent(Graphics g) {
		   Graphics2D g2d = (Graphics2D) g;
		   
		   
		   switch(color){
		   /*case -3:{g2d.setColor(new Color(0,0,255));
			   break;}*/
		   case -2:{g2d.setColor(new Color(83,51,42)); //braun
		       break;}
		   case -1:g2d.setColor(new Color(83,51,42)); //braun
			   break;
		   case 0: g2d.setColor(new Color(83,145,244)); //türkis
			   break;
		   case 1: g2d.setColor(new Color(83,181,153)); //hellgrün
		       break;
		   case 2: g2d.setColor(new Color(2,120,7)); //dunkelgrün
			   break;
		   default: g2d.setColor(new Color(2,120,7));//dunkelgrün
		   }
		   
		   
		   	 g2d.fillRect(0, 0, size,size);
			 
		     //System.out.println("painted at " + x + "/" + y);
			 //System.out.println(this.getLocationOnScreen());
	   }
	   
	  public void setSize(int size){
		  this.size=size;
		  //this.setSize(new Dimension(size,size));
		  this.setSize(size, size);
		  //this.setPreferredSize(new Dimension(size,size));
		  
	  }
	  
	  public void setColor(int color){
		  switch(color){
		   case -2:this.color=-2;
		   //System.out.println("im -2 brown");
			   break;
		   case -1:this.color=-1;
			   break;
		   case 0:this.color=0;
			   break;
		   case 1:this.color=1;
		       break;
		   case 2:this.color=2;
			   break;
		   case 3:if(this.color!=2){this.color+=1;}else{this.color=-1;}
		   	   break;
		   case 4:if(this.color==0){this.color=2;}else{this.color=0;}
	   	   	   break;
		   case 5:this.color=-3;
   	   	   	   break;
		   default:System.out.println("setColor(int) in Pixel.java failed");
		   }
		  }
	  
	   
	   
	   public int getMeX(){
		   return x;
	   }
	   
	   public int getMeY(){
		   return y;
	   }
	   
	   public int getMeColor(){
		   return color;
	   }
}

