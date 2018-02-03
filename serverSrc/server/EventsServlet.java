package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		
		//TEST EVENTS
		events.add(new Event(2, "Sofia Art Book Fest", "St Kliment Ohridski", "12 March 2018","http://www.programata.bg/?p=180&l=2&c=1&id=2085", "http://www.programata.bg/img/gallery/festival_2085.jpg?141237680", 103));
		events.add(new Event(3, "Festival of Ethnographic Film", "Vlaikova", "14 November 2018", "http://www.programata.bg/?p=180&l=2&c=1&id=2130", "http://www.programata.bg/img/gallery/festival_2130.jpg?1269908820", 30));
		events.add(new Event(1, "Lili Ivanova Concert", "Arena Armeec", "14 July 2018","", "https://arhiv.marica.bg/f/news/833/640_925d81ab260a0085aed509ea3008e5bf.jpg", 930));
		events.add(new Event(4, "Apolonia 2017", "Sozopol", "5th September 2018","http://www.programata.bg/?p=180&l=2&c=1&id=2130", "http://www.programata.bg/img/gallery/festival_2070.jpg?1401406094", 425));
		try {
			events = JDBC.read("PATH/TO/YOUR/FILE");
		} catch (SQLException e) {
			System.out.println("error");
		}
		request.getSession().setAttribute("events", events);
		response.sendRedirect("categories.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
