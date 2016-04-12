package imageIO;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class WebReader implements DataAccess{
	public BufferedImage getBufferedImage(String url){
        URL imageUrl;
		try {
			imageUrl = new URL(url);
	        Image urlImage = ImageIO.read(imageUrl);
	        BufferedImage buffImage = (BufferedImage) urlImage;
	        return buffImage;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Picture getWebImage(String url){
		BufferedImage buffImage = getBufferedImage(url);
		String fileName = url.split("/")[url.split("/").length-1].split("\\.")[0];
		String id = fileName + (Math.random() * 400000 + 1);
		Picture p= new Picture(id,url,fileName,new ArrayList<String>(),new ArrayList<String>());
		p.setImage(buffImage);
		
		return p;
	}
	
	public void setBufferedImage(String filepath, BufferedImage image) {
		// TODO Auto-generated method stub
		
	}
}