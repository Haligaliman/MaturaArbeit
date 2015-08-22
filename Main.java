

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.JTextComponent;

		 public class Main extends JFrame{
			 
			 public static Main m;
			 
			 private boolean debug =false,playing=false;
		   
		   private static final long serialVersionUID = 1L;
		
			 
		  public JPanel jP = new JPanel();
		   Random rand = new Random();

		   public int sizeX,sizeY,sizeP,fps;
		   public double g,o,t=0;
		   
		   public Graphics graphics;
		   
		   public String textfieldTemp;
		   
		   public Thread updater;
		   
		   ArrayList<ArrayList<Integer>> B = new ArrayList<ArrayList<Integer>>();		//Ursprünglicher Zustand	Vorheriger		-1,0,1,2
		   //Integer[][] B = new Integer[sizeX][sizeX]; 								
		   ArrayList<ArrayList<Integer>> B2 = new ArrayList<ArrayList<Integer>>(); 		//Zustand bei T+1			Jetziger		-1,0,1,2
		   ArrayList<ArrayList<Integer>> O = new ArrayList<ArrayList<Integer>>();  		//Seegras									   0,1
		   ArrayList<ArrayList<ArrayList<Double>>> b = new ArrayList<ArrayList<ArrayList<Double>>>(); 	//Wind in Koordinaten in 6 Richtungen		   0->1
		   
		   JTextField pixelSize,xSize,ySize,grasFaktor,algenFaktor,FPS;
		   JFrame pixelFrame,guiFrame;
		   
		   
		   JRadioButton rbDisabled= new JRadioButton("Deaktiviert",true);
		   JRadioButton rbBrown= new JRadioButton("Land",false);
		   JRadioButton rbBlue= new JRadioButton("Wasser",false);
		   JRadioButton rbLGreen= new JRadioButton("wenig Algen",false);
		   JRadioButton rbDGreen= new JRadioButton("viele Algen",false);
		   
		   JButton playButton = new JButton("Abspielen");
		   
		   ButtonGroup colorPicker = new ButtonGroup();
		   
		    Dimension screenSize;
		   
		   
		   private ArrayList<Pixel> pA = new ArrayList<Pixel>(); //Array mit allen Feldern
		   
		   
		   
		   
		   public static void main(String[] args) {
			     m = new Main();
			   }
		   
		   
		 
		   public Main() {
			   
			   
			   screenSize = Toolkit.getDefaultToolkit().getScreenSize();

			   updater = new Thread(new updater());
			   updater.start();
			   
			   
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
			
			
			
			
			
			
			

			   
			   generateField();
				
				
				colorField();

				jP.repaint();
			
			
			
			   
			   
		    pixelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    pixelFrame.setSize(new Dimension(sizeX*sizeP,sizeX*sizeP));
			pixelFrame.setVisible(true);
		    

			   createGUI();
		   }
		   
		   
		   
		   
		   public synchronized void generateField(){
			   
			   B.clear();
			   O.clear();
			   b.clear();
			   B2.clear();
			   
			   for (int y=0;y<sizeX;y++){
				   B.add(new ArrayList<Integer>(sizeX));
				   O.add(new ArrayList<Integer>(sizeX));
				   b.add(new ArrayList<ArrayList<Double>>(sizeX));
				   
				   for (int x=0;x<sizeX;x++){
					   b.get(y).add(new ArrayList<Double>(sizeX));			
					   try{
					   B.get(y).set(x, rand.nextInt(3));
					   O.get(y).set(x, rand.nextInt(2));
					   if (y==0||y==sizeX-1||x==0||x==sizeX-1){
						   	B.get(y).set(x, -2);
					   		O.get(y).set(x, -2);}
					   } catch (IndexOutOfBoundsException e){
						   B.get(y).add(x, rand.nextInt(3));
						   O.get(y).add(x, rand.nextInt(2));
						   if (y==0||y==sizeX-1||x==0||x==sizeX-1){
							   B.get(y).set(x, -2);
							   O.get(y).set(x, -2);}
					   }
					  					
					   						
					 /*  for(int x=0;x<O.size();x++){
						   for (int y=0;y<O.get(x).size();y++){
							   if(B.get(y).get(x)==-2){O.get(y).set(x, -2);}
						   }
					   }			*/
					   						
					   						
					   						for (int i=0;i<8;i++){
					   							try{
					   							b.get(y).get(x).get(i);}catch(Exception e){
					   							b.get(y).get(x).add((double)0.5);}
					   						}
					   						
					   						
					   						/*for (int i=0;i<4;i++){
					   							int j=(int) Math.round(Math.random());
					   							if (y==0||y==sizeX-1||x==0||x==sizeX-1){
					   								b.get(y).get(x).set(i+4, (double)-1);
   													b.get(y).get(x).set(i,(double)-1);
					   							}else{
					   												 if (j==1){
					   													b.get(y).get(x).set(i, Math.random()/6);
					   													b.get(y).get(x).set(i+4,(double) 0);
					   												 }
					   												 if (j==0){
					   													b.get(y).get(x).set(i+4, Math.random()/6);
					   													b.get(y).get(x).set(i,(double) 0);
					   												 }
					   							}				
					   												
				   							}*/
										}
			   
		   }
			   
		   }   
		 
		   
		   
		   
		   
		   public synchronized void colorField(){
			   timer.start(2);
			   pA.removeAll(pA);
			   
			   int x=0,y=0,z=0;
			   for (ArrayList<Integer> i:B){
				   for (Integer k:i){
					  pA.add(z, new Pixel(y,x,k,sizeP));
					  pA.get(z).addMouseListener(new pixelClickMouseListener());
					   
					   z++;
					   y++;
				   }
				   x++;
				   y=0;
			   }
			   System.out.println(timer.end(2));
			   
			   
			   jP.removeAll();
			    
			   for (Pixel p:pA){
			    	p.setPreferredSize(new Dimension(sizeP,sizeP));
			    	jP.add(p);
			    }
			    
			    jP.validate();
			    jP.grabFocus();
			    
		   }
		   
		   
		   
		   
		   
		   
		   public void createGUI(){
			   JButton randomButton = new JButton("zufällig generieren");
			   JButton nextButton = new JButton("nächste Generation generieren");
			   JButton editSGButton = new JButton("Seegrasvorkommnisse festlegen");
			   JButton editWindButton = new JButton("Windverhältnisse festlegen");
			   
			   
			   
			   colorPicker.add(rbDisabled);
			   colorPicker.add(rbBrown);
			   colorPicker.add(rbBlue);
			   colorPicker.add(rbLGreen);
			   colorPicker.add(rbDGreen);
			   
			   
			   
			  
			   
			   
			   pixelSize = new JTextField("Pixelgrösse");
			   xSize = new JTextField("Pixel pro Seite");
			   ySize = new JTextField("Vertikale Menge Pixel");
			   grasFaktor = new JTextField("Seegrasgewichtung");
			   algenFaktor = new JTextField("Wachstumsfaktor");
			   FPS = new JTextField("Bilder pro Minute");
			   
			   
			   FPS.addFocusListener(new TextFieldFocusListener());
			   	FPS.setHorizontalAlignment(JTextField.CENTER);	
			   xSize.addFocusListener(new TextFieldFocusListener());
			   	xSize.setHorizontalAlignment(JTextField.CENTER);
			   	//xSize.addPropertyChangeListener(new gridPropertyChangeListener());
			   	ySize.addFocusListener(new TextFieldFocusListener());
			   	ySize.setHorizontalAlignment(JTextField.CENTER);
			    pixelSize.addFocusListener(new TextFieldFocusListener());
			    pixelSize.setHorizontalAlignment(JTextField.CENTER);
			    grasFaktor.addFocusListener(new TextFieldFocusListener());
			    grasFaktor.setHorizontalAlignment(JTextField.CENTER);
			    algenFaktor.addFocusListener(new TextFieldFocusListener());
			    algenFaktor.setHorizontalAlignment(JTextField.CENTER);
			    
			    nextButton.addActionListener(new nextButtonListener());
				randomButton.addActionListener(new randomButtonListener());
				editSGButton.addActionListener(new editSGButtonListener());
				editWindButton.addActionListener(new editWindButtonListener());
				playButton.addActionListener(new playButtonListener());
			   
			    
				guiFrame = new JFrame("Algensimulator");
				guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
				guiFrame.setLocation((int)screenSize.getWidth()-400, 0);
				JPanel buttonPanel = new JPanel();
				JPanel LPanel = new JPanel();
				JPanel hauptPanel = new JPanel();

				
				
				guiFrame.add(buttonPanel,BorderLayout.NORTH);
				guiFrame.add(hauptPanel,BorderLayout.CENTER);
				guiFrame.add(LPanel,BorderLayout.SOUTH);
				
				buttonPanel.setLayout(new GridLayout(0, 1, 0, 5));
				
				buttonPanel.add(randomButton);
				buttonPanel.add(nextButton);
				buttonPanel.add(editWindButton);
				buttonPanel.add(editSGButton);
				buttonPanel.add(playButton);
				
				hauptPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
				
				hauptPanel.setLayout(new GridLayout(0, 1, 0, 5));
				
				
				hauptPanel.add(FPS);
				hauptPanel.add(pixelSize);
				hauptPanel.add(xSize);
				//hauptPanel.add(ySize);
				hauptPanel.add(grasFaktor);
				hauptPanel.add(algenFaktor);
				
				
				LPanel.setLayout(new BoxLayout(LPanel, BoxLayout.PAGE_AXIS));
				
				rbDisabled.setAlignmentX(Component.CENTER_ALIGNMENT);
				LPanel.add(rbDisabled);
				rbBrown.setAlignmentX(Component.CENTER_ALIGNMENT);
				LPanel.add(rbBrown);
				rbBlue.setAlignmentX(Component.CENTER_ALIGNMENT);
				LPanel.add(rbBlue);
				rbLGreen.setAlignmentX(Component.CENTER_ALIGNMENT);
				LPanel.add(rbLGreen);
				rbDGreen.setAlignmentX(Component.CENTER_ALIGNMENT);
				LPanel.add(rbDGreen);
				
				guiFrame.setSize(400,500);
				guiFrame.setVisible(true);
				}
		   
		   
		   
		   public synchronized void getNextState(){
//timer.start(1);
			   int l=0;
			   for (Pixel P:pA){
				   B.get(Math.round((long)(Math.floor(l/sizeX)))).set(l%sizeX,P.getMeColor());
				   l++;
			   } 
//debug(l);
					//int a=0,k=0,c=0,d=0,e=0;
			   B2.clear();
			for (int y=0;y<sizeX;y++){
				   B2.add(new ArrayList<Integer>(sizeX));
				   for (int x=0;x<sizeX;x++){
					   B2.get(y).add(x,x);
					   if (B.get(y).get(x)<0){
						   switch(B.get(y).get(x)){
						   case -1:B2.get(y).set(x,-1); 
						   break;
						   case -2:B2.get(y).set(x,-2);
						   
						   }
					   }
					   else if (g*Math.abs(Math.sin((t+5)*Math.PI/6))+0.4+o*O.get(y).get(x)<0)
					   {
						   /*if (Math.round((int) ((g*Math.sin((t+5)*Math.PI/6)+0.4+o*O.get(y).get(x))*B.get(y).get(x)))<0){
							   B2.get(y).set(x,0);
							   debug("seefehler "+(Math.round((int) ((g*Math.sin((t+5)*Math.PI/6)+0.4+o*O.get(y).get(x))*B.get(y).get(x))))+" mit 0 beglichen");
						   }else*/{
						   B2.get(y).set(x,Math.round((int) (((g*Math.sin((t+5)*Math.PI/6)+0.4)+o*O.get(y).get(x))*B.get(y).get(x))));
						   }
						   //debug(B.get(y).get(x));
					   }
					   else
					   {
					   						//debug(B[y][x]);
					   						//B2[y][x]=(int) (((g*Math.sin((5)/6)+0.4)+o*O[y][x])*B[y][x]*2.5);
					   						
						   B2.get(y).set(x,Math.round((int)(((g*Math.sin((t+5)*Math.PI/6)+0.4)+o*O.get(y).get(x))*B.get(y).get(x)
					   								
					   								+ b.get(y).get(x).get(6)*B.get(y).get(x+1) //rechts
					   								+ b.get(y).get(x).get(2)*B.get(y).get(x-1) //links
					   								+ b.get(y).get(x).get(0)*B.get(y+1).get(x) //unten
					   								+ b.get(y).get(x).get(4)*B.get(y-1).get(x) //ob.get(y).get(x).get(en
					   										
					   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(7)*B.get(y+1).get(x+1)
					   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(5)*B.get(y-1).get(x+1)
					   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(1)*B.get(y+1).get(x-1)
					   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(3)*B.get(y-1).get(x-1)
					   								
					   								
					   								)));
						   
					   						
						   /*if (B2.get(y).get(x)==-1){
					   							a++;
					   						}
					   						if (B2.get(y).get(x)==0){
					   							k++;}
					   						if (B2.get(y).get(x)==1){
					   							c++;}
					   						if (B2.get(y).get(x)==2){
					   							d++;}
					   						if (B2.get(y).get(x)==0){
					   							
					   							debug("B:"+B.get(y).get(x));
					   							debug("B2:"+(((g*Math.sin((t+5)*Math.PI/6)+0.4)+o*O.get(y).get(x))*B.get(y).get(x)
						   								
						   								+ b.get(y).get(x).get(6)*B.get(y).get(x+1) //rechts
						   								+ b.get(y).get(x).get(2)*B.get(y).get(x-1) //links
						   								+ b.get(y).get(x).get(0)*B.get(y+1).get(x) //unten
						   								+ b.get(y).get(x).get(4)*B.get(y-1).get(x) //ob.get(y).get(x).get(en
						   										
						   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(7)*B.get(y+1).get(x+1)
						   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(5)*B.get(y-1).get(x+1)
						   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(1)*B.get(y+1).get(x-1)
						   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(3)*B.get(y-1).get(x-1))
						   								);
					   							debug("x:"+x);
					   							debug("y:"+y);
					   							debug("alpha:"+g*Math.sin((t+5)*Math.PI/6));
					   							debug("seegras:"+o*O.get(y).get(x));
					   							debug("beta:"+(
					   									b.get(y).get(x).get(6)*B.get(y).get(x+1) //rechts
						   								+ b.get(y).get(x).get(2)*B.get(y).get(x-1) //links
						   								+ b.get(y).get(x).get(0)*B.get(y+1).get(x) //unten
						   								+ b.get(y).get(x).get(4)*B.get(y-1).get(x) //ob.get(y).get(x).get(en
						   										
						   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(7)*B.get(y+1).get(x+1)
						   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(5)*B.get(y-1).get(x+1)
						   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(1)*B.get(y+1).get(x-1)
						   			 + (1/Math.sqrt(2)) * b.get(y).get(x).get(3)*B.get(y-1).get(x-1)));
					   							
					   							
					   							e++;
					   						}*/
					   						
					   						
					   						
					   						
					   						
					   }				
					   						//debug(((g*Math.sin((5)/6)+0.4)+o*O[y][x])*B[y][x]*2.5);
					   
				   							}
										}
					//debug("-1:"+a+" + "+"0:"+k+" + "+"1:"+c+" + "+"2:"+d+" + "+">3:"+e);
			   
			   //t++;
					
			   B=B2;
			 // System.out.println(timer.end(1)); 
		   }
			   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   private int txttoint(JTextField textfield,int Standard){
			   try {
				   return Integer.parseInt(textfield.getText());
				   
			   }catch (NumberFormatException|NullPointerException e){
				   return Standard;
			   }
		   }
		   private double txttodbl(JTextField textfield,double Standard){
			   try {
				   return Double.parseDouble(textfield.getText());
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
					o=txttodbl(grasFaktor,1);
					g=txttoint(algenFaktor,1);
					fps=txttoint(FPS,60);
						
					
					
					if(sizeP*sizeX>=120&&sizeP*sizeX<screenSize.getHeight()-20&&sizeP>2){
					
					//debug(pixelFrame.getWidth());
					
						
						
					try{
						/*for(Pixel p:pA){
							p.setSize(sizeP);
							p.setPreferredSize(new Dimension(sizeP,sizeP));
							p.repaint();
						}*/
						
						
						
					
					//debug((int)pA.get(0).getLocationOnScreen().getY());
					if (sizeX*sizeX>pA.size()){
						
					generateField();
					
					  colorField();
					  guiFrame.toFront();
					  
					  
					} 
					
					if (sizeX*sizeX<pA.size()){
						//pixelFrame.setSize(new Dimension(sizeX*sizeP,sizeX*sizeP));
						/*for (Pixel p:pA){
							jP.remove(p);
						}*/
						jP.removeAll();
						generateField();
						colorField();
						
						

						//debug("i did ma job");
						guiFrame.toFront();

						
					}
					pixelFrame.setSize(new Dimension(sizeX*sizeP,sizeX*sizeP));   
					  
					//jP.repaint();
					
					
					}catch(Exception e){
						debug(e.getMessage());
						
						
					}
					
					
					
					}
			   
		   }
		   
		  } 
		   
		   }   
		   
		   
		   
		   private class editSGButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				new seeGrasEdit();
				jP.setEnabled(false);
				
			}
			   
		   }
		   
		   private class editWindButtonListener implements ActionListener{

				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					new WindEdit();
					jP.setEnabled(false);
					
				}
				   
			   }
		   
		   
		   private class randomButtonListener implements ActionListener {
				public void actionPerformed(ActionEvent ev) {
					t=0;
					generateField();
					colorField();
					jP.repaint();
					guiFrame.toFront();
					
			}
			}
		   
		   private class nextButtonListener implements ActionListener {
				public void actionPerformed(ActionEvent ev) {
					getNextState();
					colorField();
					
					jP.repaint();
			}
			}
		   
		   private class playButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread t2 = new Thread(new videoPlayer());
				playing=!playing;
				if (playing){
				t2.start();	
				}
				
			}
			   
		   }
		   
		   private class videoPlayer implements Runnable{

			@Override
			public void run() {
				playButton.setText("stop");
				while(true){
					if (!playing){
						playButton.setText("abspielen");
					break;
					
					}
					try {
						getNextState();
					colorField();
					jP.repaint();
					Thread.sleep(100*60/fps);
					} catch (InterruptedException e) {
						
					}
					
			}
			   
		   }
		   } 
		   private class TextFieldFocusListener implements FocusListener {

			@Override
			public void focusGained(FocusEvent arg0) {
				textfieldTemp=((JTextComponent) arg0.getSource()).getText();
				if (((JTextComponent) arg0.getSource()).getText().length()>10){((JTextComponent) arg0.getSource()).setText("");}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (((JTextComponent) arg0.getSource()).getText().length()<1){((JTextComponent) arg0.getSource()).setText(textfieldTemp);}
				else{
					textfieldTemp=null;
				}
			}
			   
		   }
		   
		   
		   private class pixelClickMouseListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				((Pixel) arg0.getComponent()).setColor(3);
			}

			@Override
			public void mouseEntered(MouseEvent me) {						//Mouse auf Pixel
				if (((Pixel) me.getSource()).getMeColor()!=-2){
				int hashCode = colorPicker.getSelection().hashCode();
				//debug(hashCode +" + "+rbBrown.getModel().hashCode());
				
				if (hashCode == rbBrown.getModel().hashCode()) {
					((Pixel) me.getSource()).setColor(-2);
				} else if (hashCode == rbBlue.getModel().hashCode()) {
					((Pixel) me.getSource()).setColor(0);
				} else if (hashCode == rbLGreen.getModel().hashCode()) {
					((Pixel) me.getSource()).setColor(1);
				} else if (hashCode == rbDGreen.getModel().hashCode()) {	//Radiobutton Grün -> setze Farbe auf grün
					((Pixel) me.getSource()).setColor(2);
				}
				}   
				   
			}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			   
		  }
		   
		   
		   public ArrayList<ArrayList<Integer>> getO(){
			   return O;
		   }
		   
		   public ArrayList<ArrayList<Integer>> getB(){
			   return B;
		   }
		   
		   public ArrayList<ArrayList<ArrayList<Double>>> getb(){
			   
			   return b;
			   
		   }
		   
		   
		   
		   
		   public void setb(ArrayList<ArrayList<ArrayList<Double>>> aL){
			   //System.out.println("b has been set");
			   b=aL;
		   }
		   
		   public void setO(ArrayList<ArrayList<Integer>> aL){
			   O=aL;
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
		    
		 

	


