package imageIO;

import java.awt.image.BufferedImage;

public interface DataAccess{
	BufferedImage getBufferedImage(String path);
	void setBufferedImage(String filepath, BufferedImage image);
}