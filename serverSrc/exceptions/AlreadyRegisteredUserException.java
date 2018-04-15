package exceptions;

public class AlreadyRegisteredUserException  extends Exception{
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Invalid email.";
	}
}
