package tp.pro.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tp.pro.beans.Utilisateur;
import tp.pro.forms.InscriptionForm;

/**
 * Servlet implementation class Inscription
 */
@WebServlet(name = "inscription", urlPatterns = { "/inscription" })
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	  public static final String CHAMP_EMAIL = "email";
//    public static final String CHAMP_PASS = "motdepasse";
//    public static final String CHAMP_CONF = "confirmation";
//    public static final String CHAMP_NOM = "nom";
    
    public static final String ATT_FORM  = "form";
    public static final String ATT_USER = "utilisateur";
	
	public static final String VUE = "/WEB-INF/inscription.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm();
		
        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        Utilisateur utilisateur = form.inscrireUtilisateur( request );
		
        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );
        
        request.getRequestDispatcher(VUE).forward(request, response);
        
	}

}
