package GUI;

import imageIO.Picture;
import imageIO.WebReader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class WebInputAction implements ActionListener{
	GUI g;
	public WebInputAction(GUI g){
		this.g = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		boolean matchesPattern = false;
		String url = "";
		while(!matchesPattern){
			url = JOptionPane.showInputDialog("Enter String: ");
			Pattern p = Pattern.compile("^(http|https|ftp)\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&amp;%\\$#\\=~])*$");
			Matcher m = p.matcher(url);
			if (m.find()){
				matchesPattern = true;
				WebReader wr = new WebReader();
				Picture pictureIn = wr.getWebImage(url);
				g.editPanels(pictureIn);
			}else{
				JOptionPane.showMessageDialog(null, "Please enter a URL ex: http://example.com/sample.jpg");
				matchesPattern = false;
			}
		}
	}	
}