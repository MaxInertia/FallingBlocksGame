/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.utilities;

import game.Swipe;
import java.net.URL;
import javafx.scene.image.Image;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public interface ImageLoader {
	
	public final String IMAGE_FOLDER = "src/game/utilities/resources/";

	public String[] IMAGE_NAMES = { "sunTile.png","moonTile.png","winterTile.png","ironTile.png",
		"atomicTile.png","bioTile.png","laserTile.png","FFFFFF-0.75.png","383838-0.75.png"};
	
	/**
	 * Images used for the Blocks
	 */
	public Image[] IMAGES = new Image[9];
	
	/**
	 * Load images from resource folder in the form of BufferedImages
	 */
	public default void loadImages(){
		for(int i=1; i<=9; i++){
			//try {
				System.out.println("[ImageLoader]\tLoading image #"+i);
				//File imageFile = new File(getClass().getResource(IMAGE_NAMES[i-1]).toURI().toString());
				URL imgUrl = getClass().getResource("resources/"+IMAGE_NAMES[i-1]);
				//ImageIcon imj = new ImageIcon(getClass().getResource("resources/"+IMAGE_NAMES[i-1]));
				IMAGES[i-1] = new Image(imgUrl.toString(),
						Swipe.CUBE_SIDE_LENGTH, Swipe.CUBE_SIDE_LENGTH, false, true);
				
				System.out.println("\t\t\tImage exists!");
			//} catch (URISyntaxException ex) {
				//System.out.println("\t\t\tImage does not exist...");
				//Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
			//}
		}
	}	
}
