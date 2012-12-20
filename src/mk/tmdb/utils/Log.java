package mk.tmdb.utils;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

/**
 * This class provides static methods to print to the log.
 * To deactivate the log call the setActive method with false as parameter.
 * In the same way you can reactivate the log, it's enough calling 
 * the setActive method with true as parameter
 * 
 * @author Mirko Polato
 *
 */
public final class Log {

	/**
	 * Default stream, initially sets to the system console.
	 */
	private static PrintStream defaultStream = System.out;
	
	/**
	 * Whether the logger is active or not.
	 */
	private static boolean active = true;
	
	/**
	 * Prints the log header. Each log line starts with this header that has the form
	 * "dd/mm/yy MM:HH:SS THREAD: Thread_Name :". 
	 * 
	 * @return The header string
	 */
	private static String header() {
		Date d = new Date();
		return DateFormat.getDateInstance(DateFormat.SHORT).format(d)
				+ " "
				+ DateFormat.getTimeInstance(DateFormat.LONG).format(d)
				+ " THREAD: "
				+ Thread.currentThread().getName()
				+ " : ";
	}

	/**
	 * Prints the given text appended to the header to the default stream.
	 * 
	 * @param text The text to print.
	 */
	public static synchronized void print(String text) {
		print(text, defaultStream);
	}

	/**
	 * Prints the given text appended to the header to the specified stream.
	 * 
	 * @param text The text to print
	 * @param stream The stream where to print
	 */
	public static synchronized void print(String text, PrintStream stream) {
		if (active)
			stream.println(header() + text);
	}

	/**
	 * Prints the given throwable object information to the default stream.
	 * 
	 * @param throwable The throwable object
	 */
	public static synchronized void print(Throwable throwable) {
		print(throwable, defaultStream);
	}

	/**
	 * Prints the given throwable object information to the given stream.
	 * 
	 * @param throwable The throwable object
	 * @param stream The stream where to print
	 */
	public static synchronized void print(Throwable throwable, PrintStream stream) {
		if (active) {
			stream.print(header());
			throwable.printStackTrace(stream);
		}
	}
	
	/**
	 * Gets the default stream.
	 * 
	 * @return the default stream
	 */
	public static PrintStream getDefaultStream() {
		return defaultStream;
	}
	
	/**
	 * Sets the default stream.
	 * 
	 * @param stream The new stream
	 */
	public static void setDefaultStream(PrintStream stream) {
		defaultStream = stream;
	}

	/**
	 * Gets whether the logger is active or not.
	 * 
	 * @return Whether the logger is active or not.
	 */
	public static boolean isActive() {
		return active;
	}

	/**
	 * Sets the activation status
	 * 
	 * @param active Whether the logger is active or not.
	 */
	public static void setActive(boolean active) {
		Log.active = active;
	}
	
}
