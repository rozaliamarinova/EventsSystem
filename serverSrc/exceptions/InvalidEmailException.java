package exceptions;

public class InvalidEmailException extends Exception{
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Invalid email.";
	}
}
