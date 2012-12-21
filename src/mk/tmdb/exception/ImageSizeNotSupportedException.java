package mk.tmdb.exception;

/**
 * Signals that the image size is not supported by that Image type.
 * 
 * @author Mirko Polato
 *
 */
public class ImageSizeNotSupportedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The mistake string.
	 */
	private String mistake;
	
	/**
	 * Default constructor: creates a new instance of ImageSizeNotSupportedException.
	 */
	public ImageSizeNotSupportedException() {
		super("Image size not supported");
		mistake = "Image size not supported";
	}
	
	/**
	 * Creates a new instance of ImageSizeNotSupportedException with the given mistake message.
	 * 
	 * @param exception The mistake message
	 */
	public ImageSizeNotSupportedException(String exception) {
		super(exception);
		mistake = exception;
	}
	
	/**
	 * Gets the mistake message.
	 * 
	 * @return The mistake message
	 */
	public String getError() {
		return mistake;
	}
	
}
