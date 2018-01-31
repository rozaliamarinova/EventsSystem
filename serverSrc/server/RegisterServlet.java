package server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			//RequestDispatcher view = request.getRequestDispatcher("register.html");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			
		} else {
			String categories = "q00000000000000000000000";
			HttpSession session = request.getSession();
			session.setAttribute("username", name);
			session.setAttribute("email", email);
			session.setAttribute("interests", categories);
			session.setAttribute("isLogged", true);
			User user = new User(name, lastname, pass, email, categories);
			//UserDAO 
			response.setStatus(200);
			
		}

	}

	private boolean checkForErrors(String name, String lastname, String pass, String passConfirmation, String email,
			String emailConfirmation) {
		boolean result = name == null || name.isEmpty() || name.length() > 45;
		result = result || lastname == null || lastname.isEmpty() || lastname.length() > 45;
		result = result || pass == null || pass.length() < 8 || !pass.equals(passConfirmation);
		result = result || email == null || email.isEmpty() || !email.equals(emailConfirmation);
		return result;
	}

}
