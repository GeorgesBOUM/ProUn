package tp.pro.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Restriction
 */
@WebServlet(name = "restriction", urlPatterns = { "/restriction" })
public class Restriction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public static final String ACCES_PUBLIC     = "/accesPublic.jsp";
    public static final String ACCES_RESTREINT  = "/WEB-INF/accesRestreint.jsp";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Restriction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		HttpSession session = request.getSession();

        /*
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connecté.
         */
        if ( session.getAttribute( ATT_SESSION_USER ) == null ) {
            /* Redirection vers la page publique */
        	// la redirection peut atteindre des pages externes
        	// il faut donc pour cela préciser le contexte
        	response.sendRedirect( request.getContextPath() + ACCES_PUBLIC );
        } else {
            /* Affichage de la page restreinte */
        	// le forwarding n'est possible qu'avec des pages internes à l'appli
        	// pas besoin donc de préciser le contexte
        	this.getServletContext().getRequestDispatcher( ACCES_RESTREINT ).forward( request, response );
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
