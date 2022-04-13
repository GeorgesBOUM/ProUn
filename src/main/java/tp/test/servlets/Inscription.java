package tp.test.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Inscription
 */
@WebServlet(name = "inscription", urlPatterns = { "/inscription" })
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String CHAMP_EMAIL = "email";
    public static final String CHAMP_PASS = "motdepasse";
    public static final String CHAMP_CONF = "confirmation";
    public static final String CHAMP_NOM = "nom";
    
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";
	
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
		
		String resultat;
        Map<String, String> erreurs = new HashMap<String, String>();
		
		String email = request.getParameter( CHAMP_EMAIL );
		String motDePasse = request.getParameter( CHAMP_PASS );
		String confirmation = request.getParameter( CHAMP_CONF );
		String nom = request.getParameter( CHAMP_NOM );
		
        try {
            validationEmail( email );
        } catch (Exception e) {
            erreurs.put(CHAMP_EMAIL, e.getMessage());
        }
        try {
        	validationMotsDePasse( motDePasse, confirmation );
		} catch (Exception e) {
			erreurs.put(CHAMP_PASS, e.getMessage());
		}
        try {
        	validationNom( nom );
		} catch (Exception e) {
			erreurs.put(CHAMP_NOM, e.getMessage());
		}
        
        /* Initialisation du résultat global de la validation. */
        if ( erreurs.isEmpty() ) {
            resultat = "Succès de l'inscription.";
        } else {
            resultat = "Échec de l'inscription.";
        }

        /* Stockage du résultat et des messages d'erreur dans l'objet request */
        request.setAttribute( ATT_ERREURS, erreurs );
        request.setAttribute( ATT_RESULTAT, resultat );
        
        doGet(request, response);
        
	}
	
	private void validationEmail( String email ) throws Exception{
		if ( email != null && email.trim().length() != 0 ) {
	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "Merci de saisir une adresse mail valide." );
	        }
	    } else {
	        throw new Exception( "Merci de saisir une adresse mail." );
	    }
	}
    private void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception{
    	if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
            if (!motDePasse.equals(confirmation)) {
                throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
            } else if (motDePasse.trim().length() < 3) {
                throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
            }
        } else {
            throw new Exception("Merci de saisir et confirmer votre mot de passe.");
        }
    }
    private void validationNom( String nom ) throws Exception{
    	if ( nom != null && nom.trim().length() < 3 ) {
            throw new Exception( "Le nom d'utilisateur doit contenir au moins 3 caractères." );
        }
    }

}
