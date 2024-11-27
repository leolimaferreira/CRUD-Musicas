package controller;

public class MusicaException extends Exception{

	public MusicaException(String message) {
		super(message);
	}
	
	public MusicaException() {
		super();
	}
	
	public MusicaException(Throwable t) {
		super(t);
	}

}
