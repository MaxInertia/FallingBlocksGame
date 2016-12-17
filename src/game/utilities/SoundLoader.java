package game.utilities;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public interface SoundLoader {
	
	final String INTRO = "src/game/utilities/resources/intro_to_game.mp3";
	final String DESERT_LORD = "src/game/utilities/resources/desertlord.mp3";
	final String MUSIC_PATHNAME = "src/game/utilities/resources/space_exploration03a.mp3";
	
	public default MediaPlayer setupMusic(String soundLoader){
		File musicFile = null;
		if(soundLoader.equals("intro")){
			musicFile = new File(INTRO);
		}else if(soundLoader.equals("offline")){
			musicFile = new File(MUSIC_PATHNAME);
		}else if(soundLoader.equals("online")){
			musicFile = new File(DESERT_LORD);
		}
		if( (musicFile != null) && musicFile.exists()) {
			System.out.println("[SoundLoader]\tSound file exists!");
			
			Media music = new Media(musicFile.toURI().toString());
			
			MediaPlayer mp = new MediaPlayer(music);
			return mp;
		}
		return null;
	}

}
