package library;

import beans.SessionBean;
import db.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aleksandar Abu-Samra
 */
public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        String page = req.getRequestURL().toString();

		SessionBean sessionBean = (SessionBean) session.getAttribute("sessionBean");
		if (sessionBean != null) {
			User user = sessionBean.getUser();
			if (user != null) {
				String role = user.getType();

				if (role.equals("member") && page.contains("admin.xhtml")) resp.sendRedirect("search.xhtml");
				if (role.equals("member") && page.contains("librarian.xhtml")) resp.sendRedirect("search.xhtml");
				if (role.equals("member") && page.contains("edit_book.xhtml")) resp.sendRedirect("search.xhtml");

				if (role.equals("librarian") && page.contains("admin.xhtml")) resp.sendRedirect("search.xhtml");
				if (role.equals("librarian") && page.contains("member.xhtml")) resp.sendRedirect("librarian.xhtml");
			}
			else {
				if (page.contains("admin.xhtml")
						|| page.contains("librarian.xhtml")
						|| page.contains("member.xhtml")) {
					resp.sendRedirect("login.xhtml");
				}
			}
		}

        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		//
	}

}
