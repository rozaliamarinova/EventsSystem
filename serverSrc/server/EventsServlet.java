package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/events")
public class EventsServlet extends HttpServlet {

	private static final long serialVersionUID = -4358929838765828236L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Event> events = new ArrayList<>();
		events.add(new Event(1, "Lili Ivanova", "Vkushti", "12/12/12", "http://haha.com", "http://haha.com", 3));
		events.add(new Event(2, "Lili Ivanova", "Vkushti", "12/12/12", "http://haha.com", "http://haha.com", 3));
		events.add(new Event(3, "Lili Ivanova", "Navun", "12/12/12", "http://haha.com", "http://haha.com", 3));

		request.getSession().setAttribute("events", events);
		response.sendRedirect("categories.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
