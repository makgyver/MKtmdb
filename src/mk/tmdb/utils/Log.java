package mk.tmdb.utils;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

/**
 * This class provides static methods to print to the log.
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
	 * Prints the log header. Each log line start with this header that has the form
	 * "dd/mm/yy MM:HH:SS THREAD: Thread_Name :". 
	 * @return
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
	 * @param text The text to print.
	 */
	public static synchronized void print(String text) {
		print(text, defaultStream);
	}

	/**
	 * Prints the given text appended to the header to the specified stream.
	 * @param text The text to print
	 * @param stream The stream where to print
	 */
	public static synchronized void print(String text, PrintStream stream) {
		stream.println(header() + text);
	}

	/**
	 * Prints the given throwable object information to the default stream.
	 * @param throwable The throwable object
	 */
	public static synchronized void print(Throwable throwable) {
		print(throwable, defaultStream);
	}

	/**
	 * Prints the given throwable object information to the given stream.
	 * @param throwable The throwable object
	 * @param stream The stream where to print
	 */
	public static synchronized void print(Throwable throwable, PrintStream stream) {
		stream.print(header());
		throwable.printStackTrace(stream);
	}
	
	/**
	 * Gets the default stream.
	 * @return the default stream
	 */
	public static PrintStream getDefaultStream() {
		return defaultStream;
	}
	
	/**
	 * Sets the default stream.
	 * @param stream The new stream
	 */
	public static void setDefaultStream(PrintStream stream) {
		defaultStream = stream;
	}
}
