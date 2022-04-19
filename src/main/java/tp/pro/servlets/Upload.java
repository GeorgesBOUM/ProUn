package tp.pro.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tp.pro.beans.Fichier;
import tp.pro.forms.UploadForm;

/**
 * Servlet implementation class Upload
 */
@WebServlet(
		name = "upload", 
		urlPatterns = { "/upload" }, 
		initParams = { 
				@WebInitParam(name = "chemin", value = "/fichiers/")
		})

@MultipartConfig(
		fileSizeThreshold = 1048576, location = "C:/Users/geboum/fichiers",
		maxFileSize = 10485760, maxRequestSize = 52428800)
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String VUE = "/WEB-INF/upload.jsp";
	public static final String CHEMIN        = "chemin";
	public static final String ATT_FICHIER = "fichier";
    public static final String ATT_FORM    = "form";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher( VUE ).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
         * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
         * dans le web.xml
         */
        String chemin = this.getServletConfig().getInitParameter( CHEMIN );

        /* Préparation de l'objet formulaire */
        UploadForm form = new UploadForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Fichier fichier = form.enregistrerFichier( request, chemin );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_FICHIER, fichier );

        request.getRequestDispatcher( VUE ).forward( request, response );
    }
}