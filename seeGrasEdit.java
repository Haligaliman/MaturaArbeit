


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

		 public class seeGrasEdit extends JFrame{
			 
			 private boolean debug =false;
		   
		   private static final long serialVersionUID = 1L;
		
			 
		  private JPanel jP = new JPanel();
		   Random rand = new Random();

		   public int sizeX,sizeY,sizeP;
		   
		   public Thread t;
		   

		   ArrayList<ArrayList<Integer>> O = new ArrayList<ArrayList<Integer>>();
		   ArrayList<ArrayList<Integer>> O2 = new ArrayList<ArrayList<Integer>>();//Seegras									   0,1

 
		   JTextField pixelSize,xSize,ySize;
		   JFrame pixelFrame,guiFrame;
		   
		   JRadioButton rbPos= new JRadioButton("Seegras",false);
		   JRadioButton rbNeg= new JRadioButton("kein Seegras",false);
		   JRadioButton rbDisabled= new JRadioButton("Deaktivert",true);
		   
		   ButtonGroup grasPicker = new ButtonGroup();
		   
		    Dimension screenSize;
		   
		   
		   private ArrayList<Pixel> pA = new ArrayList<Pixel>(); //Array mit allen Feldern
		   
		   
		  
		   
		   
		 
		   public seeGrasEdit() {
			   getO();
			   
			   
			   screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			   t = new Thread(new updater());
			   t.start();
			   
			   
			pixelFrame = new JFrame();
			pixelFrame.setResizable(false);
			pixelFrame.setUndecorated(true);
			pixelFrame.getContentPane().setEnabled(true);
			
			jP.setPreferredSize(new Dimension(sizeX*sizeP,sizeX*sizeP));
			pixelFrame.add(jP);
			jP.setVisible(true);
			FlowLayout layout = (FlowLayout)jP.getLayout();
			layout.setVgap(0);
			layout.setHgap(0);
			
			
			
			
			
			
			

			   
				
				
				colorField();

				jP.repaint();
			
			
			
			   
			   
		    pixelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    pixelFrame.setSize(new Dimension(sizeX*sizeP,sizeX*sizeP));
			pixelFrame.setVisible(true);
		    

			   createGUI();
		   }
		   
		 
		   
		   
		   
		   public synchronized void colorField(){
			   int z=0;
			   pA.removeAll(pA);
			   for (ArrayList<Integer> i:O){
				   for (Integer k:i){
					   if (k==1){k=2;}
					  pA.add(z, new Pixel(k,sizeP));
					  pA.get(z).addMouseListener(new pixelClickMouseListener());
					   
					   z++;
				   }
			   }
			   
			   jP.removeAll();
			    for (Pixel p:pA){
			    	p.setPreferredSize(new Dimension(sizeP,sizeP));
			    	jP.add(p);
			    }
			    
			    jP.validate();
			    jP.grabFocus();
			    
		   }
		   
		   
		   
		   
		   public void getO(){
			   O=Main.m.getO();
			   
			   
		   }
		   
		   
		   
		   
		   
		   public void createGUI(){
			   JButton doneButton = new JButton("Fertig");
			   
			   
			   grasPicker.add(rbPos);
			   grasPicker.add(rbNeg);
			   grasPicker.add(rbDisabled);
			   
			   
			    
				doneButton.addActionListener(new doneButtonListener());
			   
			    
				guiFrame = new JFrame("Algensimulator");
				guiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				guiFrame.setLocation((int)screenSize.getWidth()-400, 0);
				guiFrame.addWindowListener(new onDisposeListener());
				JPanel hauptPanel = new JPanel();
				
				guiFrame.getContentPane().add(BorderLayout.CENTER, hauptPanel);
				
				
				//hauptPanel.add(ySize);

				hauptPanel.add(rbDisabled);
				hauptPanel.add(rbPos);
				hauptPanel.add(rbNeg);
				
				hauptPanel.add(doneButton);
				
				
				guiFrame.setSize(400,500);
				guiFrame.setVisible(true);
				}
		   
		   
		   
		   private int txttoint(JTextField textfield,int Standard){
			   try {
				   return Integer.parseInt(textfield.getText());
			   }catch (NumberFormatException|NullPointerException e){
				   return Standard;
			   }
		   }
		   
		   
		    
		   
		   
		   
		   private class updater implements Runnable {

			@Override
			public void run() {
				while(true){
					
					sizeX=txttoint(xSize,12);
					sizeY=sizeX;//strtoint(xSize.getText(),5);
					sizeP=txttoint(pixelSize,32);
						
					
					
					if(sizeP*sizeX>=120&&sizeP*sizeX<screenSize.getHeight()-20){
					
					//debug(pixelFrame.getWidth());
					
					
					try{
					pixelFrame.setSize(new Dimension(sizeX*sizeP+(int)pA.get(0).getLocationOnScreen().getX()*2,sizeX*sizeP+(int)pA.get(0).getLocationOnScreen().getY()));
					//debug((int)pA.get(0).getLocationOnScreen().getY());
					if (sizeX*sizeX>pA.size()){
						
					
					
					  colorField();
					  guiFrame.toFront();
					} 
					
					if (sizeX*sizeX<pA.size()){
						pixelFrame.setSize(new Dimension(sizeX*sizeP+(int)pA.get(0).getLocationOnScreen().getX()*2,sizeX*sizeP+(int)pA.get(0).getLocationOnScreen().getY()+sizeP));
						for (Pixel p:pA){
							jP.remove(p);
						}
						
						colorField();
						
						

						//debug("i did ma job");
						guiFrame.toFront();

						
					}
					   
					for(Pixel p:pA){
						p.setSize(sizeP);
						p.setPreferredSize(new Dimension(sizeP,sizeP));
						p.repaint();
					}
					jP.repaint();
					
					
					}catch(Exception e){
						debug(e.getMessage());
						
						
					}
					
					}
			   
		   }
		   
		  } 
		   
		   }   
		   
		   
		   
		   private class doneButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				int k=0,h=0,m=0;
				 for (ArrayList<Integer> al:O){
					   for (@SuppressWarnings("unused") int i:al){
						   if(pA.get(m).getMeColor()==2){O.get(h).set(k, 1);}else{
							   O.get(h).set(k, pA.get(m).getMeColor());
						   }
						   k++;
						   m++;
					   }
					   k=0;
					   h++;
				   }
				Main.m.setO(O);
				Main.m.jP.setEnabled(true);
				t.interrupt();
				jP.removeAll();
				pixelFrame.removeAll();
				guiFrame.removeAll();
				
				pixelFrame.dispose();
				guiFrame.dispose();
				
				
				
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
		   
		   
		   private class pixelClickMouseListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				((Pixel) arg0.getComponent()).setColor(4);
				
			}

			@Override
			public void mouseEntered(MouseEvent me) {						//Mouse auf Pixel
				
				int hashCode = grasPicker.getSelection().hashCode();
				//debug(hashCode +" + "+rbBrown.getModel().hashCode());
				
				if (hashCode == rbNeg.getModel().hashCode()) {
					((Pixel) me.getSource()).setColor(0);
				} else if (hashCode == rbPos.getModel().hashCode()) {
					((Pixel) me.getSource()).setColor(2);
				}
				}   
				   
			

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			   
		  }
		   
		   
		   
		   public  int getSizeP(){
			  return sizeP;
		   }
		   
		   public void debug(int i){
			   if (debug==true){
			  System.out.println(i);}
		   }
		   public void debug(String i){
			   if (debug==true){
			   System.out.println(i);}
		   }
		   
		 }
		    
		 

	



