package tp.pro.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class RestrictionFilter
 */
@WebFilter("/restreint/*")
public class RestrictionFilter extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;
	
    public static final String ACCES_PUBLIC     = "/accesPublic.jsp";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

	/**
     * @see HttpFilter#HttpFilter()
     */
    public RestrictionFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		HttpSession session = request.getSession();
		if (session.getAttribute(ATT_SESSION_USER) == null) {
			// je redirige vers la page public et l'utilisateur le verra dans l'url
			response.sendRedirect(request.getContextPath() + ACCES_PUBLIC);
		} else {
			// je laisse passer requête et réponse jusqu'à l'espace d'accès restreint
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
