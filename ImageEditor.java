import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.awt.event.*;

public class ImageEditor extends JFrame implements ActionListener{
	private ImageCanvas[][] murp =new ImageCanvas[3][3];
	String file = "flower.jpg";
	String kenny = "kenny.jpg";
	String[] ce = {"Red","Green","Blue","Yellow","Cyan","Violet","Negative","Blur","Sharpen","Mask"};
	boolean vikramiscoolio=true;
	double radians=0;
	
	private JMenuItem rotate,resize,stretchv,stretchh,transform,open,trick1,trick2,transparency,flipv,fliph,brighten,gs,em,contrast;
	private JMenuItem red, blue, green, yellow, cyan, violet,edgev,edgeh;
	private JMenu[][] pig = new JMenu[3][3];
	private JMenu change;
	private HashMap<JMenu,JMenuItem[]> choices = new HashMap();
	private JMenuItem[] ch = new JMenuItem[10];
	          
	
	
	public ImageEditor(){
		super("Image Editor");
		makeMenu();
		
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++){
				murp[r][c] = new ImageCanvas();
				murp[r][c].setImage(new File(file));
			if(r==2)
				murp[r][c].mirror(true);
			if(c==2)
				murp[r][c].mirror(false);
			
			}
		
		
		//murp[0][0].red();
		murp[0][1].contrast(150);
		murp[0][2].blue();
		murp[1][0].yellow();
		murp[1][1].cyan();
		murp[1][2].violet();
		murp[2][0].negative();
		murp[2][1].mask();
		//murp[2][2].sharpen();murp[2][2].emboss();
		
			
		
		
		
		JPanel stuff = new JPanel();
		stuff.setLayout(new GridLayout(3,3));
		
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++)
				stuff.add(new JScrollPane(murp[r][c]));
		
	
		this.add(stuff, BorderLayout.CENTER);
		//finishing up
		this.setSize(900,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		/*while(vikramiscoolio){
			murp[2][1].rotate(0.01);
		}*/
	}
	
	private void makeMenu(){
		/*ch[0] = new JMenuItem("Red");
		ch[0].addActionListener(this);
		ch[1] = new JMenuItem("Green");
		ch[1].addActionListener(this);
		ch[2] = new JMenuItem("Blue");
		ch[2].addActionListener(this);*/
		
		
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++){
				choices.put(pig[r][c], ch);
				
		}
		
		
		
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		open = new JMenuItem("Open");
		open.addActionListener(this);
		trick1 = new JMenuItem("Change direction");
		trick1.addActionListener(this);
		trick2 = new JMenuItem("Negative");
		trick2.addActionListener(this);
		JMenu test = new JMenu("Tester [2][2]");
		JMenu trans = new JMenu("Transformations");
		JMenu colors = new JMenu("Effects");
		rotate = new JMenuItem("Rotate");
		rotate.addActionListener(this);
		resize = new JMenuItem("Resize");
		resize.addActionListener(this);
		stretchv = new JMenuItem("Stretch vertically");
		stretchv.addActionListener(this);
		stretchh = new JMenuItem("Stretch horizontally");
		stretchh.addActionListener(this);
		transform = new JMenuItem("Transform");
		transform.addActionListener(this);
		flipv = new JMenuItem("Flip Vertically");
		flipv.addActionListener(this);
		fliph = new JMenuItem("Flip Horizontally");
		fliph.addActionListener(this);
		
		
		red = new JMenuItem("Red");
		red.addActionListener(this);
		green = new JMenuItem("Green");
		green.addActionListener(this);
		blue = new JMenuItem("Blue");
		blue.addActionListener(this);
		yellow = new JMenuItem("Yellow");
		yellow.addActionListener(this);
		cyan = new JMenuItem("Cyan");
		cyan.addActionListener(this);
		violet = new JMenuItem("Violet");
		violet.addActionListener(this);
		
		edgev = new JMenuItem("Vertical Edge Detection");
		edgev.addActionListener(this);
		edgeh = new JMenuItem("Horizontal Edge Detection");
		edgeh.addActionListener(this);
		brighten = new JMenuItem("Brighten/Darken");
		brighten.addActionListener(this);
		gs = new JMenuItem("Grayscale");
		gs.addActionListener(this);
		em = new JMenuItem("Emboss");
		em.addActionListener(this);
		contrast = new JMenuItem("Contrast");
		contrast.addActionListener(this);
		
		trans.add(rotate);
		trans.add(resize);
		trans.add(stretchv);
		trans.add(stretchh);
		trans.add(transform);
		trans.add(flipv);
		trans.add(fliph);
		
		colors.add(red);
		colors.add(blue);
		colors.add(green);
		colors.add(yellow);
		colors.add(cyan);
		colors.add(blue);
		colors.add(violet);
		colors.add(brighten);
		colors.add(gs);
		colors.add(em);
		colors.add(edgev);
		colors.add(edgeh);
		colors.add(contrast);
		
		test.add(trans);
		test.add(colors);
		bar.add(test);
		
		transparency = new JMenuItem("Transparency");
		transparency.addActionListener(this);
		
		JMenu change = new JMenu("Change");
		
		for(int r=0;r<3;r++)
			for(int c=0;c<3;c++){
				pig[r][c]=new JMenu("("+r+","+c+")");
				for(int x=0;x<10;x++){
					ch[x] = new JMenuItem(ce[x]);
					ch[x].addActionListener(this);
					pig[r][c].add(ch[x]);
					//System.out.println(ch[x]);
				}
				change.add(pig[r][c]);
				
				
				
		
		}
		
		/*pig = new JMenuItem("color");
		pig.addActionListener(this);
		
		p.add(pig);*/
		file.add(open);
		file.add(trick1);
		file.add(trick2);
		file.add(transparency);

		
		bar.add(file);
		bar.add(change);
		

		
		
		this.setJMenuBar(bar);
	}
	
	
	public static void main(String[] args) {new ImageEditor();}

	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==red){
				murp[2][2].red();
		}
		if(e.getSource()==green){
				murp[2][2].green();
		}
		if(e.getSource()==blue){
			murp[2][2].blue();
		}
		if(e.getSource()==yellow){
			murp[2][2].yellow();
		}
		if(e.getSource()==cyan){
			murp[2][2].cyan();
		}
		if(e.getSource()==violet){
			murp[2][2].violet();
		}
		
		if(e.getSource()==contrast){
			
			String ans = JOptionPane.showInputDialog(this,"Contrast by what percent? (100% is normal)");
			int percent = Integer.parseInt(ans);
			murp[2][2].contrast(percent);
		}
		
		if(e.getSource()==brighten){
			String ans = JOptionPane.showInputDialog(this,"Brighten by how much?");
			int m = Integer.parseInt(ans);
			murp[2][2].brighten(m);
		}
		if(e.getSource()==gs){
			murp[2][2].grayscale();
		}
		if(e.getSource()==em){
			murp[2][2].emboss();
		}
		
		if(e.getSource()==edgev){
			murp[2][2].edgeDetect(true);
	}
		if(e.getSource()==edgeh){
			murp[2][2].edgeDetect(false);
	}
		
		if(e.getSource()==flipv){
			murp[2][2].mirror(true);
		}
		if(e.getSource()==fliph){
			murp[2][2].mirror(false);
		}
	
		if(e.getSource()==rotate){
			String ans = JOptionPane.showInputDialog(this,"Rotate by how many degrees?");
			double angle = Double.parseDouble(ans);
			
			murp[2][2].rotate(angle);
		}
		
		if(e.getSource()==resize){
			String ans = JOptionPane.showInputDialog(this,"Resize by what ratio?");
			double ratio = Double.parseDouble(ans);
			murp[2][2].resize(ratio);
		}
		
		if(e.getSource()==stretchv){
			
			String ans = JOptionPane.showInputDialog(this,"Vertically stretch by what ratio?");
			double ratio = Double.parseDouble(ans);
			murp[2][2].stretch(true,ratio);
		}
		
		if(e.getSource()==stretchh){
			
			String ans = JOptionPane.showInputDialog(this,"Horizontally stretch by what ratio?");
			double ratio = Double.parseDouble(ans);
			murp[2][2].stretch(false,ratio);
		}
		
		if(e.getSource()==transform){
			double[][] a = new double[2][2];
			String ans;
			for(int m=0;m<a.length;m++)
				for(int n=0;n<a[0].length;n++){
					ans = JOptionPane.showInputDialog(this,"Enter ["+m+"]["+n+"]");
					double d = Double.parseDouble(ans);
					a[m][n]=d;
				}
			murp[2][2].transform(a);
		}
		
		
		if(e.getSource()==open){
			JFileChooser jfc = new JFileChooser();
			int result = jfc.showOpenDialog(this);
			if(result == JFileChooser.CANCEL_OPTION)
				return;
			File f = jfc.getSelectedFile();
			for(int r=0;r<3;r++)
				for(int c=0;c<3;c++)
					murp[r][c].setImage(f);
			
			
			this.repaint();
		}
		
		if(e.getSource()==change){
			System.out.println("in actionPerformed");
			
			
		}
		
		if(e.getSource()==trick1){
			for(int r=0;r<3;r++)
				for(int c=0;c<3;c++){
					murp[r][c].mirror(true);
					murp[r][c].mirror(false);}
			
			/*
			a1.mirror(true);
			a1.mirror(false);
			a2.mirror(true);
			a2.mirror(false);
			a3.mirror(true);
			a3.mirror(false);
			b1.mirror(true);
			b1.mirror(false);
			*/
		}
		
		if(e.getSource()==trick2){
			
			for(int r=0;r<3;r++)
				for(int c=0;c<3;c++)
					murp[r][c].negative();
			
		/*	a1.negative();
			
			a2.negative();
			
			a3.negative();
			
			b1.negative();
			a1.negative();
			
			a2.negative();
			
			a3.negative();
			
			b1.negative();
			a1.negative();
			
			a2.negative();
			

		*/
		}
		
		if(e.getSource()==transparency){
			
			
			
			
			
			
			
		}
		
		
	}
}
