package exceptions;

public class InvalidPasswordException extends Exception {
	private static final long serialVersionUID = 236115421352011863L;

	@Override
	public String getMessage() {
		return "Invalid password! Password must be between 6 and 20 characters long!";
	}

}
