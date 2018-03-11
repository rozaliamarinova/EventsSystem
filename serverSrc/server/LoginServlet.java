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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("password");
		
		
		//String passConfirmation = request.getParameter("confirmPassword");

		if (pass.length() < 8) { //|| !getPassword(email).equals(pass)) {
			RequestDispatcher view = request.getRequestDispatcher("index.html");
			view.forward(request, response);
		} else {
			
			//set session - email, 
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			
			
			//forward to index.html
			RequestDispatcher view = request.getRequestDispatcher("index.html");
			view.forward(request, response);
		}

		doGet(request, response);
	}

}
