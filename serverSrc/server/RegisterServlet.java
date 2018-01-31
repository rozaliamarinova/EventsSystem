package server;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import exceptions.InvalidUserLoginException;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RegisterServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String lastname = request.getParameter("lastName");
		String pass = request.getParameter("password");
		String passConfirmation = request.getParameter("confirmPassword");
		String email = request.getParameter("email");
		String emailConfirmation = request.getParameter("confirmEmail");
		if (checkForErrors(name, lastname, pass, passConfirmation, email, emailConfirmation)) {
			RequestDispatcher view = request.getRequestDispatcher("register.html");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			User user = new User(name, lastname, pass, email);
			RequestDispatcher view = request.getRequestDispatcher("index.html");
			response.setStatus(200);
		}

	}

	private boolean checkForErrors(String name, String lastname, String pass, String passConfirmation, String email,
			String emailConfirmation) {
		return name == null || name.isEmpty() || name.length() > 45 || lastname == null || lastname.isEmpty()
				|| lastname.length() > 45 || pass == null || pass.length() < 8 || !pass.equals(passConfirmation)
				|| email == null || email.isEmpty() || !email.equals(emailConfirmation);
	}

}
