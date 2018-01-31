package server;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CategoriesServlet
 */
@WebServlet("/category")
public class CategoriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		String category = request.getParameter("category");
		HttpSession session = request.getSession();
		StringBuilder interests = new StringBuilder((String)session.getAttribute("interests"));
		session.setAttribute("interests", setCategories(category, interests));
	}

	private String setCategories(String categoryName, StringBuilder categories) {
		switch(categoryName) {
		case "communication":
			categories.setCharAt(1, '1');
			break;
		case "art":
			categories.setCharAt(2, '1');
			break;
		case "charity":
			categories.setCharAt(3, '1');
			break;
		case "food":
			categories.setCharAt(4, '1');
			break;
		case "drinks":
			categories.setCharAt(5, '1');
			break;
		case "shopping":
			categories.setCharAt(6, '1');
			break;
		case "books":
			categories.setCharAt(7, '1');
			break;
		case "music":
			categories.setCharAt(8, '1');
			break;
		case "party":
			categories.setCharAt(9, '1');
			break;
		case "sport":
			categories.setCharAt(10, '1');
			break;
		case "comedy":
			categories.setCharAt(11, '1');
			break;
		case "trades":
			categories.setCharAt(12, '1');
		}
		return categories.toString();
	}
}
