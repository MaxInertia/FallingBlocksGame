package networking.ops;

/**
 * The Class that must be extended and provided to the method <i>setPassAction</i>
 * in the locally created instance of <i>ClientNet</i> class.
 * 
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 * @param <T> Class of the objects being transferred over the network.
 */
public abstract class PassOperation<T> implements Runnable{
	public T object;
}