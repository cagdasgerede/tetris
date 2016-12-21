package runner;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CustomPanel extends JPanel {
	
	private BufferedImage image;
	private int width,height ;
	
	public CustomPanel(String filename,int w,int h) throws IOException {
		 image = ImageIO.read(new File(filename));
		// width=image.getWidth(); 
		// height=image.getHeight();
		 width=w;
		 height=h;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawImage(image, 0, 0, this);
		g.drawImage(image, 0, 0, width, height, this);	}

}
	/*
	@Override
	
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	*/

