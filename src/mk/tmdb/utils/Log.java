package mk.tmdb.utils;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

public class Log {

	private static PrintStream defaultStream = System.out;
	
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
