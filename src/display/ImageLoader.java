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
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public interface ImageLoader {
	
	public final String IMAGE_FOLDER = "resources/";

	public String[] IMAGE_NAMES = { "sunTile.png","moonTile.png","winterTile.png","ironTile.png",
		"atomicTile.png","bioTile.png","laserTile.png","selected3.png"};
	
	/**
	 * Images used for the Blocks
	 */
	public BufferedImage[] IMAGES = new BufferedImage[8];
	
	/**
	 * Load images from resource folder in the form of BufferedImages
	 */
	public default void loadImages(){
		try{
			for(int i=1; i<=8; i++){
				IMAGES[i-1] = ImageIO.read(getClass().getResource(IMAGE_FOLDER+IMAGE_NAMES[i-1]));
			}
		} catch(IOException e){
			System.out.println("[IOException]\tThe relative paths given for the image files is wrong, or some images are missing");
			e.printStackTrace();
		}
	}	
}
