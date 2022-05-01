package tp.pro.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import tp.pro.beans.Utilisateur;
import tp.pro.dao.DAOException;
import tp.pro.dao.UtilisateurDAO;

public final class InscriptionForm {
	
	public static final String CHAMP_EMAIL = "email";
    public static final String CHAMP_PASS = "motdepasse";
    public static final String CHAMP_CONF = "confirmation";
    public static final String CHAMP_NOM = "nom";
    
    private static final String ALGO_CHIFFREMENT = "SHA-256";
    
    private UtilisateurDAO utilisateurDao;

    public InscriptionForm( UtilisateurDAO utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }
    
    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    /* Validation de l'adresse email */
    private void validationEmail( String email ) throws FormValidationException {
        if ( email != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new FormValidationException( "Merci de saisir une adresse mail valide." );
            } else if ( utilisateurDao.trouver( email ) != null ) {
                throw new FormValidationException( "Cette adresse email est déjà utilisée, merci d'en choisir une autre." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir une adresse mail." );
        }
	}
    
    private void validationMotsDePasse( String motDePasse, String confirmation ) throws FormValidationException{
    	if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
            if (!motDePasse.equals(confirmation)) {
                throw new FormValidationException("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
            } else if (motDePasse.trim().length() < 3) {
                throw new FormValidationException("Les mots de passe doivent contenir au moins 3 caractères.");
            }
        } else {
            throw new FormValidationException("Merci de saisir et confirmer votre mot de passe.");
        }
    }
    
    private void validationNom( String nom ) throws FormValidationException{
    	if ( nom != null && nom.trim().length() < 3 ) {
            throw new FormValidationException( "Le nom d'utilisateur doit contenir au moins 3 caractères." );
        }
    }
    
    private void traiterEmail(String email, Utilisateur utilisateur) {
    	try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
	}
    
    private void traiterMotsDePasse(String motDePasse, String confirmation, Utilisateur utilisateur) {
    	try {
            validationMotsDePasse( motDePasse, confirmation );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
            setErreur( CHAMP_CONF, null );
        }

        /*
         * Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe
         * efficacement.
         * 
         * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage
         * aléatoire et un grand nombre d'itérations de la fonction de hashage.
         * 
         * La String retournée est de longueur 56 et contient le hash en Base64.
         */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        String motDePasseChiffre = passwordEncryptor.encryptPassword( motDePasse );

        utilisateur.setMotDePasse( motDePasseChiffre );
    }

    private void traiterNom(String nom, Utilisateur utilisateur) {
    	try {
            validationNom( nom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        utilisateur.setNom( nom );
	}
    
    
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }
    
    public Utilisateur inscrireUtilisateur( HttpServletRequest request ) {
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );
        String confirmation = getValeurChamp( request, CHAMP_CONF );
        String nom = getValeurChamp( request, CHAMP_NOM );

        Utilisateur utilisateur = new Utilisateur();

        try {
            traiterEmail( email, utilisateur );
            traiterMotsDePasse( motDePasse, confirmation, utilisateur );
            traiterNom( nom, utilisateur );

            if ( erreurs.isEmpty() ) {
                utilisateurDao.creer( utilisateur );
                resultat = "Succès de l'inscription.";
            } else {
                resultat = "Échec de l'inscription.";
            }
        } catch ( DAOException e ) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return utilisateur;
    }


}
