package game.logic;

import java.io.Serializable;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class BasicBlock implements Serializable{
	
	public enum Type{
		SUN(0,1),MOON(1,4),WINTER(2,1),
		IRON(3,1),ATOMIC(4,1),BIO(5,1),
		LASER(6,1);
		
		public final int id;
		public final int range;
		
		Type(int typeID, int typeRange){
			id = typeID;
			range = typeRange;
		}
	}
	
	protected Type type;
	
	public int myColumn;
	public int myRow;
	public double yPosition;
	public static double yGround = 0;
	protected boolean falling;
	
	public BasicBlock(int column, int row){	
		this.myColumn = column;
		this.myRow = row;
		
		assignInitialType();
	}
	
	private void assignInitialType(){
		int rand = ((int)(Math.random()*63));
		if(rand<10){ type = Type.SUN; }
		else if(rand<20){ type = Type.MOON; }
		else if(rand<30){ type = Type.WINTER; }
		else if(rand<40){ type = Type.IRON; }
		else if(rand<50){ type = Type.ATOMIC; }
		else if(rand<60){ type = Type.BIO; }
		else if(rand<70){ type = Type.LASER; }
	}
	
	public void setType(Type t){
		type = t;
	}
	
	public Type getType(){
		return type;
	}
}
