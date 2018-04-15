package server;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.JDBC;
import exceptions.AlreadyRegisteredUserException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String lastname = request.getParameter("lastName");
		String pass = request.getParameter("password");
		String passConfirmation = request.getParameter("confirmPassword");
		String email = request.getParameter("email");
		String emailConfirmation = request.getParameter("confirmEmail");

		if (checkForErrors(name, lastname, pass, passConfirmation, email, emailConfirmation)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			String interests = "q00000000000000000000000";
			User user = new User(name, lastname, pass, email, interests);
			setUserSession(request, user);
			try(JDBC dbConnection = new JDBC()) {
				dbConnection.registerUser(user);
				response.setStatus(200);
			} catch (SQLException | AlreadyRegisteredUserException e) {
				// TODO: if the user is already registered show in the GUI appropriate error
				e.printStackTrace();
			}
		}
	}

	private void setUserSession(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setAttribute("username", user.getUsername());
		session.setAttribute("email", user.getEmail());
		session.setAttribute("interests", user.getInterests());
		session.setAttribute("isLogged", true);
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
