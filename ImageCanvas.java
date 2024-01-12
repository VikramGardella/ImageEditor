import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
public class ImageCanvas extends JPanel{
	private BufferedImage img; //the image that appears on this canvas

	private int w, h;
	private int tran =2;
	private static final int TYPE = BufferedImage.TYPE_INT_ARGB_PRE;
	private int[][] blurreda;
	private int[][] almostmask;
	


	/** ***************** PIXEL FUNCTIONS ****************** **/
	public static final int A=0, R=1, G=2, B=3;

	//returns only the red value of the pixel
	//   EX: pixel = 0x004f2ca5 --> returns 4f
	public int howRed(int pixel){
		pixel = (pixel & 0xFF0000);
		//System.out.println("Test" + (pixel >> 16));
		return pixel>>16;
	}

	//returns only the green value of the pixel
	//   EX: pixel = 0x004f2ca5 --> returns 2c 
	public int howGreen(int pixel){return (pixel&0x00ff00)>>8;}

	//returns only the blue value of the pixel
	//   EX: pixel = 0x004f2ca5 --> returns a5
	public int howBlue(int pixel){return (pixel&0x0000ff);}

	//returns a new pixel with the specified alpha
	//    red, green, and blue values

	//color intensity values subtracted from their maximum possible value
	//this changes the pixels in the negative funtion
	public int negate(int a, int r, int g, int b){



		r=255-r;
		g=255-g;
		b=255-b;
		return(this.combine(0,r,g,b));
	}

	//combines the color values to get a integer which represents a pixel
	public int combine(int a, int r, int g, int b){
		if(r>255)
			r=255;
		if(g>255)
			g=255;
		if(b>255)
			b=255;
		if(r<0)
			r=0;
		if(g<0)
			g=0;
		if(b<0)
			b=0;
		
		r=r<<16;//Math.max((r&0xff),0)<<16;
		g=g<<8;//Math.max((g&0xff),0)<<8;
		b=b;//Math.max((b&0xff),0);
		/*
		if(r>this.howRed(0xff0000))
			r=0xff0000;
		if(g>this.howGreen(0x00ff00))
			g=0x00ff00;
		if(b>this.howBlue(0xff))
			b=0x0000ff;
		if(r<0)
			r=0;
		if(g<0)
			g=0;
		if(b<0)
			b=0;
		*/
		//System.out.println(r+","+g+","+b);
		return (a|r|g|b);
	}
	/** ***************************************************   **/
	public ImageCanvas(){
		super();
		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(400,400));
		img = new BufferedImage(100,100,TYPE);
		w = img.getWidth();
		h = img.getHeight();
		almostmask=this.imgToArray();
	}

	public BufferedImage getImage(){return img;}
	public void setImage(File file){
		try{ 
			img = ImageIO.read((file));
			MediaTracker mt = new MediaTracker(new Component(){});
			mt.addImage(img, 0);
			mt.waitForAll();
		}
		catch(Exception ex){ex.printStackTrace();}
		w = img.getWidth();
		h = img.getHeight();
		//pix = imgToArray();
		this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
	}


	// *********************Easy pixel manips************************

	public void red(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				//System.out.println(cat[c][r]);
				cat[r][c]=cat[r][c]&0x00ff0000;
			}
		this.arrayToImg(cat);

	}
	public void green(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				//System.out.println(cat[c][r]);
				cat[r][c]=cat[r][c]&0x0000ff00;
			}
		this.arrayToImg(cat);	
	}

	public void blue(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				//System.out.println(cat[c][r]);
				cat[r][c]=cat[r][c]&0x000000ff;
			}
		this.arrayToImg(cat);
	}

	public void yellow(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				//System.out.println(cat[c][r]);
				cat[r][c]=cat[r][c]|0x00ff0000;
				cat[r][c]=cat[r][c]|0x0000ff00;
			}
		this.arrayToImg(cat);
	}

	public void violet(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				//System.out.println(cat[c][r]);
				cat[r][c]=cat[r][c]|0x00ff0000;
				cat[r][c]=cat[r][c]|0x000000ff;
			}
		this.arrayToImg(cat);
	}
	public void cyan(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				//System.out.println(cat[c][r]);
				cat[r][c]=cat[r][c]|0x000000ff;
				cat[r][c]=cat[r][c]|0x0000ff00;
			}
		this.arrayToImg(cat);
	}


	public void gray(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				int red=this.howRed(cat[r][c]);
				int g=this.howGreen(cat[r][c]);
				int b=this.howBlue(cat[r][c]);



			}
		this.arrayToImg(cat);
	}

	public void brighten(int f){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				int red,green,blue;
				red=this.howRed(cat[r][c]);
				green=this.howGreen(cat[r][c]);
				blue=this.howBlue(cat[r][c]);
				cat[r][c]=this.combine(0,red+f,green+f,blue+f);
			}
		this.arrayToImg(cat);
	}
	
	public void grayscale(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				int a = (this.howRed(cat[r][c])+this.howGreen(cat[r][c])+this.howBlue(cat[r][c]))/3;
				cat[r][c]= this.combine(0,a,a,a);
			}
		
		this.arrayToImg(cat);
	}

	public void contrast(int percent){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				int red = this.howRed(cat[r][c]);
				int g = this.howGreen(cat[r][c]);
				int b = this.howBlue(cat[r][c]);
				if((int)(Math.abs(128-red)*percent/100.)<128)
					red=128-(int)((128-red)*percent/100.);
				if((int)(Math.abs(128-g)*percent/100.)<128)
					g=128-(int)((128-g)*percent/100.);
				if((int)(Math.abs(128-b)*percent/100.)<128)
					b=128-(int)((128-b)*percent/100.);
				
				cat[r][c]=this.combine(0, red, g, b);
			}
		this.arrayToImg(cat);
		
	}
	

	//goes through each pixel and sends it to the negate function
	public void negative(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				//System.out.println(this.howRed(cat[r][c])+","+this.howGreen(cat[r][c])+","+this.howBlue(cat[r][c]));
				//System.out.println(cat[c][r]);
				cat[r][c]=this.negate(0,this.howRed(cat[r][c]), this.howGreen(cat[r][c]), this.howBlue(cat[r][c]));
				
			}
		this.arrayToImg(cat);

	}
	
	
	public void cut(int r, int c){
		//r is 0 to 2 and c is 0 to 2
		//int [][] orignial
		
	}

	public void mirror(boolean vert){
		//true=vert
		//false=hori
		int[][] mirp = this.imgToArray();
		w = mirp[0].length;
		h = mirp.length;
		int[][] fin = new int[h][w];
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++)
				if(vert==true)
					fin[h-r-1][c]=mirp[r][c];	
				else
					fin[r][w-c-1]=mirp[r][c];

		this.arrayToImg(fin);

	}
	// *********************END Easy pixel manips************************






	// ********************KERNEL STUFF *********************************
	public void blur(){
		int[][] orig = this.imgToArray();
		int[][] fin = new int[h][w];
		almostmask= orig;
		//System.out.println(this.howRed(orig[50][50]));
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++)
				if(r!=0&&r!=h-1&&c!=0&&c!=w-1){
					int red=0,g=0,b=0;
					for(int rd=-1;rd<=1;rd++)
						for(int cd=-1;cd<=1;cd++){
							red+=this.howRed(orig[r+rd][c+cd]);
							g+=this.howGreen(orig[r+rd][c+cd]);
							b+=this.howBlue(orig[r+rd][c+cd]);
						}
					red/=9;
					g/=9;
					b/=9;

					

					fin[r][c]=this.combine(0,red,g,b);
					//fin[r][c]=(red/9)+(g/9)+(b/9);
				}
				else{
					fin[r][c]=orig[r][c];
				}
		blurreda = fin;
		this.arrayToImg(fin);
	}

	public void edgeDetect(boolean vert){
		int[][] orig = this.imgToArray();
		int[][] fin = new int[h][w];
		almostmask= orig;
		//System.out.println(this.howRed(orig[50][50]));
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++)
				if(r!=0&&r!=h-1&&c!=0&&c!=w-1){
					int red=0,g=0,b=0;
					for(int rd=-1;rd<2;rd++)
						for(int cd=-1;cd<2;cd++){
							if(vert==true){
							red+=this.howRed(orig[r+rd][c+cd])*cd;
							g+=this.howGreen(orig[r+rd][c+cd])*cd;
							b+=this.howBlue(orig[r+rd][c+cd])*cd;
							}
							else{
								red+=this.howRed(orig[r+rd][c+cd])*rd;
								g+=this.howGreen(orig[r+rd][c+cd])*rd;
								b+=this.howBlue(orig[r+rd][c+cd])*rd;
							}
						}
					//System.out.println(red+","+g+""+b);
					fin[r][c]=this.combine(0,red,g,b);
					//fin[r][c]=(red/9)+(g/9)+(b/9);
				}
				else{
					fin[r][c]=orig[r][c];
				}
		blurreda = fin;
		this.arrayToImg(fin);
	}
	
	public void emboss(){
		int[][] orig = this.imgToArray();
		int[][] fin = new int[h][w];
		almostmask= orig;
		//System.out.println(this.howRed(orig[50][50]));
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++)
				if(r!=0&&r!=h-1&&c!=0&&c!=w-1){
					int red=0,g=0,b=0;
					
					for(int rd=-1;rd<2;rd++)
						for(int cd=-1;cd<2;cd++){
							if((rd==-1&&cd!=1)||(cd==-1&&rd==0)){
								red+=this.howRed(orig[r+rd][c+cd])*-2;
								g+=this.howGreen(orig[r+rd][c+cd])*-2;
								b+=this.howBlue(orig[r+rd][c+cd])*-2;
							}
							if((rd==1&&cd!=-1)||(rd==0&&cd==1)){	
								red+=this.howRed(orig[r+rd][c+cd])*2;
								g+=this.howGreen(orig[r+rd][c+cd])*2;
								b+=this.howBlue(orig[r+rd][c+cd])*2;
							}
						}
					int a = (red+g+b)/3+128;
					//System.out.println(red+","+g+""+b);
					fin[r][c]=this.combine(0,a,a,a);
					//fin[r][c]=(red/9)+(g/9)+(b/9);
				}
				else{
					fin[r][c]=orig[r][c];
				}
		blurreda = fin;
		this.arrayToImg(fin);
	}
	
	
	public void blend(ImageCanvas ic){
		int[][] cat = this.imgToArray();
		int[][] dog = ic.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				if(r%tran==0&&c%tran==0&&dog.length>r&&dog[0].length>c)
					cat[r][c]=dog[r][c];
			}
		this.arrayToImg(cat);	

	}
	
	public void shade(){
		int[][] cat = this.imgToArray();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				//if(r%tran==0&&c%tran==0)
					cat[r][c]=cat[r][c]|0x00333333;
			}
		this.arrayToImg(cat);	

	}
	

	public void mask(){
		this.blur();
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				almostmask[r][c]-=blurreda[r][c];
			}
		this.arrayToImg(almostmask);


	}

	public void sharpen(){
		int[][] turtle = this.imgToArray();
		this.mask();
		
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
			
				//System.out.print(turtle[r][c]+", ");
			turtle[r][c]=this.pixelAddition(turtle[r][c],almostmask[r][c]);
			//System.out.println(turtle[r][c]);
			}
		this.arrayToImg(turtle);
	}
	
	public int pixelAddition(int x, int y){
		int r1=this.howRed(x);
		int g1=this.howGreen(x);
		int b1=this.howBlue(x);
		int r2=this.howRed(y);
		int g2=this.howGreen(y);
		int b2=this.howBlue(y);
		if(r2>10)
			r2=10;
		if(g2>10)
			g2=10;
		if(b2>10)
			b2=10;
		int[] c= {0,(r1+r2),(g1+g2),(b1+b2)};
		
		for(int i=0;i<c.length;i++){
			if(c[i]>255)
				c[i]=255;
			if(c[i]<0)
				c[i]=0;
		}

		x=this.combine(c[0],c[1],c[2],c[3]);
		
		return x;
	}

	/* **************MATRIX STUFF ********************************* */
	public void resize(double ratio){
		int nh = (int)(h*ratio);
		int nw = (int)(w*ratio);
		int[][] old = this.imgToArray();
		int[][] nuu = new int[nh][nw];
		for(int r=0;r<nh;r++)
			for(int c=0;c<nw;c++){
			nuu[r][c]=old[(int)(r/(ratio))][(int)(c/(ratio))];
			}
		this.arrayToImg(nuu);
	}

	
	/*public int getXshift(double angle){
		int cprime=0;
		
		int one=(int)(-h*Math.sin(angle)+(w*Math.cos(angle)));
		int two=(int)(-h*Math.sin(angle));
		int three=(int)((w*Math.cos(angle)));
		
			cprime=(Math.min(Math.min(one,two),three));
		
		if(cprime>0)
			cprime=0;
		//System.out.println("x-shift: "+cprime);
		return cprime;
	}*/
	public int[] transform( double[][] matrix){//(double angle){
		int prime[]= new int[2];
		int toprx=(int) (matrix[0][0]*(w-1));
		int topry=(int) (matrix[1][0]*(w-1));	
		int botlx=(int) (matrix[0][1]*(h-1));
		int botly=(int) (matrix[1][1]*(h-1));
		int botrx=(int) (matrix[0][0]*(w-1)+matrix[0][1]*(h-1));
		int botry=(int) (matrix[1][0]*(w-1)+matrix[1][1]*(h-1));
		int[][] barn = this.imgToArray();
		prime[0]=Math.min(Math.min(Math.min(toprx,botlx),botrx),0);
		prime[1]=Math.min(Math.min(Math.min(topry,botly),botry),0);
		//System.out.println("shifts: "+prime[0]+", "+prime[1]);
		int bigx=Math.max(Math.max(0,toprx),Math.max(botlx,botrx));
		int smallx=Math.min(Math.min(0,toprx),Math.min(botlx,botrx));
		int bigy=Math.max(Math.max(0,topry),Math.max(botly,botry));
		int smally=Math.min(Math.min(0,topry),Math.min(botly,botry));
		
		int[][] ret = new int[bigy-smally+1][bigx-smallx+1];
		
			// where would (0, h-1) go to?
			for(int r=0;r<h;r++)
				for(int c=0;c<w;c++){
					
					int newr = (int) (matrix[1][0]*c+matrix[1][1]*r)-prime[1];
					int newc = (int) (matrix[0][0]*c+matrix[0][1]*r)-prime[0];
					
					ret[newr][newc]=barn[r][c];
				}
			
			
			for(int x=0;x<prime.length;x++)
				if(prime[x]>0)
					prime[x]=0;
			//System.out.println(prime);
			this.arrayToImg(ret);
		return prime;
	} 
	
	public void stretch(boolean vert, double ratio){
		int[][] orig = this.imgToArray();
		int[][] n;
		//System.out.println(vert);
		if(vert==true){
			//System.out.println("vert=true");
			n = new int[(int)(h*ratio)][w];
			for(int r=0;r<(int)(h*ratio);r++)
				for(int c=0;c<w;c++)
					n[r][c]=orig[(int)(r/ratio)][c];
			
		}
		
		else{
			//System.out.println("vert=false");
			n = new int[h][(int)(w*ratio)];
			for(int r=0;r<h;r++)
				for(int c=0;c<(int)(w*ratio);c++)
					n[r][c]=orig[r][(int)(c/ratio)];
			
			
		}
		
		this.arrayToImg(n);
		
	}
	
	public void rotate(double angle){
		angle*=(180/3.14);
		double[][] m = new double[2][2];
		
		m[0][0] = Math.cos(angle);
		m[0][1] = -1*Math.sin(angle);
		m[1][0] = Math.sin(angle);
		m[1][1] = Math.cos(angle);
		
		this.transform(m);
		}
	

	public void paint(Graphics g){
		super.paint(g);
		((Graphics2D)g).drawImage(img,null,0,0);
		//g.drawImage(img, 0, 0, null);
	}


	/**  **************** START WITH THESE!  ************** **/
	//have kids do this first!  just take the pixels and replace them
	public void tester(){
		/*int[][] blah = imgToArray();		
		arrayToImg(blah);
		System.out.println(blah);
		this.mirror(true);
		this.green();
		this.red();
		this.blue();
		this.mirror(true);
		this.mirror(false);
		this.negative();*/
	}

	//Postconditions:  all of the pixels from the original image have been stored
	//  into a 2d array and that 2d array has been returned
	private int[][] imgToArray(){
		//this puts the pixels into a 1d array.  You want to move them into a 2d array
		int[] pix = img.getRGB(0, 0, w, h, null, 0, w);
		int[][] blurp = new int[h][w];
		int pixind =0;
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				blurp[r][c]=pix[pixind++];

			}

		//System.out.println(blurp);
		return blurp;

	}

	//Postconditions:  the pixel values from the given 2d array have been loaded onto
	//  the image
	//HINT:  use this function--> img.setRGB(x,y,val);
	private void arrayToImg(int[][] pix){
		w = pix[0].length;
		h = pix.length;
		this.setPreferredSize(new Dimension(w,h));
		img = new BufferedImage(w,h,img.getType());
		for(int r=0;r<h;r++)
			for(int c=0;c<w;c++){
				//pix[r][c]
				img.setRGB(c,r,pix[r][c]);
			}

		this.repaint();
	}
	/**  ***************************************************  **/
}
