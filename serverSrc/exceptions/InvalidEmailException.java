package exceptions;

public class InvalidEmailException extends Exception{
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Invalid email.";
	}
}
