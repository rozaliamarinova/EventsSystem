package server;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response){
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("password");

		if (pass.length() < 8) { //|| !getPassword(email).equals(pass)) {
			RequestDispatcher view = request.getRequestDispatcher("index.html");
			view.forward(request, response);
		} else {
			
			
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			
			
			//forward to index.html
			RequestDispatcher view = request.getRequestDispatcher("index.html");
			view.forward(request, response);
		}

		
	}

	private void setUserSession(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setAttribute("username", user.getUsername());
		session.setAttribute("email", user.getEmail());
		session.setAttribute("isLogged", true);
	}
}
