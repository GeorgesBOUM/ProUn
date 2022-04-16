package tp.pro.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Deconnexion
 */
@WebServlet(name = "deconnexion", urlPatterns = { "/deconnexion" })
public class Deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String URL_REDIRECTION = "https://openclassrooms.com/";
	public static final String URL_FORWARDING = "/connexion";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deconnexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// récupération de la session
		HttpSession session = request.getSession();
		// annulation de la session
		session.invalidate();
		// redirection vers une page externe
		// response.sendRedirect(URL_REDIRECTION);
		
		// forwarding vers une page interne
		// ici, contrairement à la redirection l'utilisateur n'est pas informé des changements opérés
		request.getRequestDispatcher(URL_FORWARDING).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
