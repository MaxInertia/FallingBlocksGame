package game.online.netops;

import networking.GenUpdate;
import networking.GenUpdateParser;
import networking.ops.PassOperation;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class PassUpdateOperation extends PassOperation<GenUpdate>{

	@Override
	public void run() {
		GenUpdateParser.parse(object);
	}

}
