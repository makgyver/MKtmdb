package mk.tmdb.exception;

public class ImageSizeNotSupportedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String mistake;
	
	public ImageSizeNotSupportedException() {
		super("Image size not supported");
		mistake = "Image size not supported";
	}
	
	public ImageSizeNotSupportedException(String exception) {
		super(exception);
		mistake = exception;
	}
	
	public String getError() {
		return mistake;
	}
}
