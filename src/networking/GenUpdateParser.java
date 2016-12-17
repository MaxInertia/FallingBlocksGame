package networking;

import game.logic.Game;
import game.online.GameState;
import game.utilities.CanvasPainter;
import io.Standard;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class GenUpdateParser {
	
	private static Data update;
	
	public static void parse(GenUpdate updateObject){
		update = decompose(updateObject);
		
		for(int u=0; u<update.num; u++){
			switch(update.types[u]){
				case "ready":
					if(update.values[u].equals("true")){
						GameState.setReady(2, true);
					}else{
						GameState.setReady(2, false);
					}
					break;
					
				case "Lost":
					Game.gameover(true);
					break;
					
				case "BoardUpdate":
					CanvasPainter.paintOpponentBoard(updateObject.board);
					break;
					
				default:
					Standard.out("Unkonw type: "+update.types[u]);
			}
		}
	}	
	
	/**
	 * Breaks pulls the updates from the GenUpdate object; stores them into two String arrays: types and values.
	 * The type at index i is paired with the value at i, thus both arrays are the same length.
	 * Type refers to the data requested to change, while value refers to the new value of that data.
	 * @param updateObject the update sent from the server
	 * @return data object which contains the two String arrays: types and values.
	 */
	private static Data decompose(GenUpdate updateObject){
		String[] allData = updateObject.data.split("\\|");
		
		//System.out.println("allData[0] = "+allData[0]);
		//System.out.println("allData[1] = "+allData[1]);
		
		String[] types = new String[allData.length/2];
		String[] values = new String[allData.length/2];
		
		System.out.println(allData.length);
		for(int i=0; i<allData.length; i+=2){
			types[i] = allData[2*i];
			values[i] = allData[(2*i)+1];
		}
		
		return (new Data(types,values));
	}
	
	
	private static class Data {
		int num;
		String[] types;
		String[] values;
		
		public Data(String[] types, String[] values) {
			this.types = types;
			this.values = values;
			num = types.length;
		}
	}
}
