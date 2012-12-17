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
	 * Prints the log string header
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

	public static synchronized void print(String text) {
		print(text, defaultStream);
	}

	public static synchronized void print(String text, PrintStream stream) {
		stream.println(header() + text);
	}

	public static synchronized void print(Throwable throwable) {
		print(throwable, defaultStream);
	}

	public static synchronized void print(Throwable throwable, PrintStream stream) {
		stream.print(header());
		throwable.printStackTrace(stream);
	}
	
	public static PrintStream getDefaultStream() {
		return defaultStream;
	}
	
	public static void setDefaultStream(PrintStream stream) {
		defaultStream = stream;
	}
}
