package game.utilities;

import game.Swipe;
import game.logic.BasicBlock;
import game.logic.Block;
import game.logic.Game;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class CanvasPainter implements ImageLoader{
	
	private static CanvasPainter instance;
	private final GraphicsContext[] graphics;
	
	public CanvasPainter(final Canvas c){
		System.out.println("[CanvasPainter]\tConstructor called.");
		this.loadImages();
		graphics = new GraphicsContext[1];
		graphics[0] = c.getGraphicsContext2D();
		setup(0);
		
		//drawImages();
		instance = this;
	}
	
	public CanvasPainter(final Canvas myC, final Canvas oppC){
		System.out.println("[CanvasPainter]\tConstructor called.");
		this.loadImages();
		graphics = new GraphicsContext[2];
		graphics[0] = myC.getGraphicsContext2D();
		graphics[1] = oppC.getGraphicsContext2D();
		setup(0);
		setup(1);
		
		//drawImages();
		instance = this;
	}
	
	private void setup(int playerNumber){
		graphics[playerNumber].setFill(Color.BLACK);
		graphics[playerNumber].fillRect(0, 0, Swipe.COLUMNS*Swipe.CUBE_SIDE_LENGTH, Swipe.ROWS*Swipe.CUBE_SIDE_LENGTH);
	}
	
	public static void repaint() {
		instance.paintBlocks();
	}
	
	private void paintBlocks() {
		setup(0);
		//for(int p=0; p<graphics.length; p++){
		//for(int p=0; p<1; p++){
		
		int p=0;
		
		for(int c=0; c<Game.columnCount; c++){
			for(int r=0; r<Game.rowCount+1; r++){
				Block b = Game.blocks[p][c][r];
				if(b!=null){
					graphics[p].drawImage(IMAGES[b.getType().id], 
					b.myColumn*Game.blockLength,
					(b.myRow*Game.blockLength - b.yPosition), 
					Game.blockLength,
					Game.blockLength);

					if(b.myRow==Game.rowCount){
						graphics[p].drawImage(IMAGES[8], 
						b.myColumn*Game.blockLength,
						(b.myRow*Game.blockLength - b.yPosition), 
						Game.blockLength,
						Game.blockLength);
					}

					/* // Uncomment to add info to tiles
					graphics.strokeText("("+b.myColumn+","+b.myRow+","+b.yPosition+")",
						b.myColumn*Game.blockLength, (b.myRow*Game.blockLength - b.yPosition+10));
					graphics.strokeText(""+b.isBlocked(),
						b.myColumn*Game.blockLength, (b.myRow*Game.blockLength - b.yPosition+20));
					*/
				}
			}
			//}

			
			graphics[p].drawImage(IMAGES[7],
					Game.selectedCol*Game.blockLength,
					(Game.selectedRow*Game.blockLength)-Block.yGround,
					Game.blockLength,
					Game.blockLength);

			// For testing if the images do not appear
			/*
			graphics.setFill(Color.ALICEBLUE);
			Game.blocks.stream().forEach((aBlock) -> {
				graphics.fillRect(aBlock.myColumn*Game.blockLength,
						(aBlock.myRow*Game.blockLength - aBlock.yPosition), 
						Game.blockLength,
						Game.blockLength);
			});*/
		}
	}
	
	public static void paintOpponentBoard(BasicBlock[][][] board){
		instance.setup(1);
		
		int p=1;
		int blockCount = 0;
		for(int c=0; c<Game.columnCount; c++){
			for(int r=0; r<Game.rowCount+1; r++){
				BasicBlock b = board[0][c][r];
				if(b!=null){
					blockCount++;
					
					instance.graphics[p].drawImage(IMAGES[b.getType().id], 
					b.myColumn*Game.blockLength,
					(b.myRow*Game.blockLength - b.yPosition), 
					Game.blockLength,
					Game.blockLength);

					if(b.myRow==Game.rowCount){
						instance.graphics[p].drawImage(IMAGES[8], 
						b.myColumn*Game.blockLength,
						(b.myRow*Game.blockLength - b.yPosition), 
						Game.blockLength,
						Game.blockLength);
					}
				}
			}
		}
		//System.out.println("Drew opponent board on Thread: "+Thread.currentThread().getName());
		//System.out.println("Opponent blocks painted: "+blockCount);
	}
	
	private void drawImages(){
		System.out.println("[CanvasPainter]\tDrawing example images");
		for(int p=0; p<graphics.length; p++){
			graphics[p].drawImage(IMAGES[0], 0.0, 0.0, 60.0, 60.0);
			graphics[p].drawImage(IMAGES[1], 0.0, 60.0, 60.0, 60.0);
			graphics[p].drawImage(IMAGES[2], 0.0, 120.0, 60.0, 60.0);
			graphics[p].drawImage(IMAGES[3], 0.0, 180.0, 60.0, 60.0);
			graphics[p].drawImage(IMAGES[4], 0.0, 240.0, 60.0, 60.0);
			graphics[p].drawImage(IMAGES[5], 0.0, 300.0, 60.0, 60.0);
			graphics[p].drawImage(IMAGES[6], 0.0, 360.0, 60.0, 60.0);
		}
	}	
	
}
