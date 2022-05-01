package tp.pro.dao;

import tp.pro.beans.Utilisateur;

public interface UtilisateurDAO {
	void creer( Utilisateur utilisateur ) throws DAOException;
    Utilisateur trouver( String email ) throws DAOException;
}
