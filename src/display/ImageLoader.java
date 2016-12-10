/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Doria
 */
public interface ImageLoader {
	
	public final String IMAGE_FOLDER = "resources/";
		//"C:\\Users\\Doria\\Desktop\\Projects\\ClickQuick\\src\\resources\\";
	public String[] IMAGE_NAMES = { "sunTile.png","moonTile.png","winterTile.png","ironTile.png",
		"atomicTile.png","bioTile.png","laserTile.png"};
	
	public BufferedImage[] IMAGES = new BufferedImage[7];
	
	public default void loadImages(){
		try{
			for(int i=1; i<=7; i++){
				IMAGES[i-1] = ImageIO.read(getClass().getResource(IMAGE_FOLDER+IMAGE_NAMES[i-1]));
				
				//IMAGES[i-1]= ImageIO.read(new File(IMAGE_FOLDER+IMAGE_NAMES[i-1]));
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}	
}
