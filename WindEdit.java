


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.JTextComponent;

		 public class WindEdit extends JFrame{
			 
			 private boolean debug =false;
		   
		   private static final long serialVersionUID = 1L;
		
			 
		  private JPanel jP = new JPanel();
		  private ArrayList<JTextField> tfAL = new ArrayList<JTextField>(9);
		  Random rand = new Random();
		  JFrame editFrame;
		  Pixel activePixel;
		  
		 
		   
		   public Thread t;
		   

		   ArrayList<ArrayList<ArrayList<Double>>> b = new ArrayList<ArrayList<ArrayList<Double>>>();		
		  private ArrayList<ArrayList<Pixel>> pAL = new ArrayList<ArrayList<Pixel>>();	
		  private ArrayList<Integer> empty = new ArrayList<Integer>(8);	
		  ArrayList<Integer> AL;
		  
		  
 
		   JFrame pixelFrame,guiFrame,detailFrame;
		   
		    Dimension screenSize;
		   
		   
		  
		   
		   
		 
		   public WindEdit() {
			   
			   
			   empty.removeAll(empty);
			   for(int x=0;x<9;x++){
				   empty.add(x, 0);
			   }
			   empty.set(4, 1);
			   
			   getb();
			   
			   
			   screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			   t = new Thread(new updater());
			   t.start();
			   
			   
			pixelFrame = new JFrame();
			pixelFrame.setResizable(false);
			pixelFrame.setUndecorated(true);
			pixelFrame.getContentPane().setEnabled(true);
			
			jP.setPreferredSize(new Dimension(Main.m.sizeX*Main.m.sizeP,Main.m.sizeX*Main.m.sizeP));
			pixelFrame.add(jP);
			jP.setVisible(true);
			FlowLayout layout = (FlowLayout)jP.getLayout();
			layout.setVgap(0);
			layout.setHgap(0);
			
			
			
			
			
			
			

			   
				
				
				colorField();

				jP.repaint();
			
			
			
			   
			   
		    pixelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    pixelFrame.setSize(new Dimension(Main.m.sizeX*Main.m.sizeP,Main.m.sizeX*Main.m.sizeP));
			pixelFrame.setVisible(true);
		    

			   createGUI();
		   }
		   
		 
		   
		   
		   
		   
		public synchronized void colorField(){
			   int g=0,a=0,c=0;
			   
			   pAL.removeAll(pAL);
			   for (ArrayList<ArrayList<Double>> i:b){
				   pAL.add(a, new ArrayList<Pixel>());
				   for (@SuppressWarnings("unused") ArrayList<Double> k:i){
					   
					  if(b.get(a).get(c).get(0)==-1){
						  pAL.get(a).add(c, new Pixel(a,c,-2,Main.m.sizeP));
					  }else{
					  pAL.get(a).add(c, new Pixel(a,c,g,Main.m.sizeP));}
					  if (g==0){g=-3;}else{g=0;}
					  pAL.get(a).get(c).addMouseListener(new pixelClickMouseListener());
					   
					   
					   c++;
				   }

				   a++;
				   c=0;
				   if (g==0){g=-3;}else{g=0;}
			   }
			   
			   jP.removeAll();
			    for (ArrayList<Pixel> pA:pAL){
			    	for (Pixel p:pA){
			    		p.setPreferredSize(new Dimension(Main.m.sizeP,Main.m.sizeP));
			    		jP.add(p);
			    	}
			    }
			    
			    jP.validate();
			    jP.grabFocus();
			    
		   }
		   
		   
		   
		   
		   public void getb(){
			   b=Main.m.getb();
			   
			   
		   }
		   
		   
		   
		   
		   
		   public void createGUI(){
			   JButton doneButton = new JButton("Fertig");
			   
			   
			   
				doneButton.addActionListener(new doneButtonListener());
			   
			    
				guiFrame = new JFrame("Algensimulator");
				guiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				guiFrame.setLocation((int)screenSize.getWidth()-400, 0);
				guiFrame.addWindowListener(new onDisposeListener());
				JPanel hauptPanel = new JPanel();
				
				guiFrame.getContentPane().add(BorderLayout.CENTER, hauptPanel);
				
				
				hauptPanel.add(doneButton);
				
				
				guiFrame.setSize(400,500);
				guiFrame.setVisible(true);
				}
		   
		   
		   
		   
		   public void createGrid(int x, int y, ArrayList<Integer> a){
			   Main.m.setb(b);
			  // System.out.println(b);
			   getb();
			   
			    editFrame = new JFrame();
				editFrame.setResizable(false);
				editFrame.setUndecorated(true);
				editFrame.setEnabled(true);
				editFrame.setVisible(true);
				editFrame.setLayout(new GridLayout(3,3,0,0));
				editFrame.setBounds((int) (screenSize.getWidth()/2-150), (int) (screenSize.getHeight()/2-150), 300, 300);
				editFrame.addWindowListener(new onDisposeListener());
				
				tfAL.removeAll(tfAL);
				for(int i=0;i<9;i++){
				tfAL.add(new JTextField(getBValue(x,y,i)));	
				
				tfAL.get(i).addFocusListener(new TextFieldFocusListener());
				center(tfAL.get(i));
				editFrame.add(tfAL.get(i));
				if (a.get(i)==1){tfAL.get(i).setEnabled(false);
				tfAL.get(i).setText("");}
				//System.out.println(empty);
				}
				
		   }
		   
		   
		   private String getBValue(int x,int y,int z){
			   switch(z){
				case 7: return b.get(y).get(x).get(4)+"";
				
				case 0: return b.get(y).get(x).get(7)+"";
				
				case 1: return b.get(y).get(x).get(0)+"";
				
				case 6: return b.get(y).get(x).get(5)+"";
				
				case 4: return "Auswahl";
				
				case 2: return b.get(y).get(x).get(1)+"";
				
				case 5: return b.get(y).get(x).get(2)+"";
				
				case 8: return b.get(y).get(x).get(3)+"";
				
				case 3: return b.get(y).get(x).get(6)+"";
				
				default: System.out.println("tfActionLIstener on WindEdit failed");
				return "error";
				
				}
		   }
		    
		   
		   
		   
		   private class updater implements Runnable {

			@Override
			public void run() {
				while(true){
					
					try{
						pixelFrame.setSize(new Dimension(Main.m.sizeX*Main.m.sizeP+(int)pAL.get(0).get(0).getLocationOnScreen().getX()*2,Main.m.sizeX*Main.m.sizeP+(int)pAL.get(0).get(0).getLocationOnScreen().getY()));
						

						
						   
						for(ArrayList<Pixel> pA:pAL){
							for(Pixel p:pA){
							p.setSize(Main.m.sizeP);
							p.setPreferredSize(new Dimension(Main.m.sizeP,Main.m.sizeP));
							p.repaint();
							}
						}
						jP.repaint();
						
						
						}catch(Exception e){
							debug(e.getMessage());
							
							
						}
			}
					}
			   
		   }
		   
		  
		   
		     
		   
		   
		   
		   private class doneButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Main.m.setb(b);
				Main.m.jP.setEnabled(true);
				t.interrupt();
				jP.removeAll();
				pixelFrame.removeAll();
				guiFrame.removeAll();
				
				
				pixelFrame.dispose();
				guiFrame.dispose();
				
				
				try {
					editFrame.dispose();
				} catch (Exception e) {
					
				}
				
				
				
				
			}
			   
		   }
		   
		   
		   
		   
		   
		   private class onDisposeListener implements WindowListener {

public void windowActivated(WindowEvent arg0) {}
public void windowClosed(WindowEvent arg0) {}

			@Override
			public void windowClosing(WindowEvent arg0) {
				doneButtonListener dBL = new doneButtonListener();
				dBL.actionPerformed(new ActionEvent(this, 1, "hallo"));
				
			}
			
public void windowDeactivated(WindowEvent arg0) {}
public void windowDeiconified(WindowEvent arg0) {}
public void windowIconified(WindowEvent arg0) {
	this.windowClosing(arg0);
}
public void windowOpened(WindowEvent arg0) {}

		   }
		   
		   
		   
		   
		   
		   public void setBatIaC(int x,int k,FocusEvent arg0){
			   b.get(activePixel.getMeY()).get(activePixel.getMeX()).set(k,Double.parseDouble(((JTextComponent) arg0.getSource()).getText()));
			   //System.out.println(b.get(Math.floorDiv(x,3)).get(Math.floorMod(x, 3)).get(k));
			   //System.out.println("B at "+(activePixel.getMeX())+","+(activePixel.getMeY())+"is now"+Double.parseDouble(((JTextComponent) arg0.getSource()).getText()));
		   }
		   
		   
		   private class pixelClickMouseListener implements MouseListener {

			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{editFrame.dispose();}catch(NullPointerException e){}
				int x,y,m;
				activePixel = ((Pixel) arg0.getSource());
				x=((Pixel) arg0.getSource()).getMeX();
				y=((Pixel) arg0.getSource()).getMeY();
				
				m=Main.m.sizeX;
				
				if(x<=1||y<=1||x>=m-2||y>=m-2){
					if(!(x==0)&&!(y==0)&&!(x==m-1)&&!(y==m-1)){
					
					if(x==1&&y==1){
					AL = (ArrayList<Integer>) empty.clone();
					for (int i=0;i<9;i++){
						if (i<=3||i==6){
							AL.set(i,1);
						}
					}
					createGrid(x,y,AL);
					
					}else if (y==1&&x==m-2){
						AL = (ArrayList<Integer>) empty.clone();
						for (int i=0;i<9;i++){
							if (i==0||i==3||i>5){
								AL.set(i,1);
							}
						}
						createGrid(x,y,AL);	
						
						
					}else if (x==1&&y==m-2){
						AL = (ArrayList<Integer>) empty.clone();
						for (int i=0;i<9;i++){
							if (i<3||i==5||i==8){
								AL.set(i,1);
							}
						}
						createGrid(x,y,AL);
						
						
						
						
					}else if (y==m-2&&x==m-2){
						AL = (ArrayList<Integer>) empty.clone();
						for (int i=0;i<9;i++){
							if (i>=5||i==2){
								AL.set(i,1);
							}
						}
						createGrid(x,y,AL);
						
						
						
					}else if (y==1){
						
						AL = (ArrayList<Integer>) empty.clone();
						for (int i=0;i<9;i++){
							if (i%3==0){
								AL.set(i,1);
							}
						}
						createGrid(x,y,AL);	
						
					}else if (y==m-2){
						
						AL = (ArrayList<Integer>) empty.clone();
						for (int i=0;i<9;i++){
							if (i%3==2){
								AL.set(i,1);
							}
						}
						createGrid(x,y,AL);
						
					}else if (x==1){
						
						AL = (ArrayList<Integer>) empty.clone();
						for (int i=0;i<9;i++){
							if (i<3){
								AL.set(i,1);
							}
						}
						createGrid(x,y,AL);
						
					}else if (x==m-2){
						
						AL = (ArrayList<Integer>) empty.clone();
						for (int i=0;i<9;i++){
							if (i>5){
								AL.set(i,1);
							}
						}
						createGrid(x,y,AL);
					}
					
					}
					
				}else{
					createGrid(x,y,empty);
				}
						
			}

			@Override
			public void mouseEntered(MouseEvent me) {}   
			
			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			   
		  }
		   
		   
		   
		   private class TextFieldFocusListener implements FocusListener {

				@Override
				public void focusGained(FocusEvent arg0) {
					
				}

				@Override
				public void focusLost(FocusEvent arg0) {
				
					//System.out.println("action performed");
					int x=0;	
					
					for(;((JTextComponent) arg0.getSource()).hashCode()!=tfAL.get(x).hashCode();x++){
						
					}
					switch(x){
					case 0: setBatIaC(x,7,arg0);
					//System.out.println("0/7");
					break;
					case 1: setBatIaC(x,0,arg0);
					break;
					case 2: setBatIaC(x,1,arg0);
					break;
					case 3: setBatIaC(x,6,arg0);
					break;
					case 4: System.out.println("there's no wind");
					break;
					case 5: setBatIaC(x,2,arg0);
					break;
					case 6: setBatIaC(x,5,arg0);
					break;
					case 7: setBatIaC(x,4,arg0);
					break;
					case 8: setBatIaC(x,3,arg0);
					break;
					default: System.out.println("tfActionLIstener on WindEdit failed");
					break;
					}
					
					
				}
					
				}
				   
			   
		   
		   
		   
		   
		   private void center(JTextField tf){

			   	tf.setHorizontalAlignment(JTextField.CENTER);
		   }
		   
		   
		   public  double getSizeP(){
			  return Main.m.sizeP;
		   }
		   
		   public void debug(double i){
			   if (debug==true){
			  System.out.println(i);}
		   }
		   public void debug(String i){
			   if (debug==true){
			   System.out.println(i);}
		   }
		   
		 }
		    
		 

	



