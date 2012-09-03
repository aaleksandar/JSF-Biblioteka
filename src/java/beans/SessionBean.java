package beans;

import db.User;
import helpers.UserHelper;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@SessionScoped
public class SessionBean {

	private String username;
	private String password;
	private boolean loggedIn;

	private boolean admin;
	private boolean librarian;

	private User user;
	private UIComponent btnLogin;

	private UserHelper userHelper;


	/**
	 * Creates a new instance of SessionBean
	 */
	public SessionBean() {
		loggedIn = false;
		admin = false;
		librarian = false;
		userHelper = new UserHelper();
	}

	public String login() {
		User fetchUser = userHelper.getUser(username, password);

		if (fetchUser != null) {
			// success
			user = fetchUser;
			loggedIn = true;
			if (user.getType().equals("admin")) {
				admin = true;
				librarian = true;
			}
			if (user.getType().equals("librarian")) librarian = true;

			// ako je januar, pustaj sve
			if (Calendar.getInstance().get(Calendar.MONTH) == 0) return "index";

			// monthly awards trigger
			SystemBean.monthlyAwards();

			// unactive
			if (user.getActive() == null || !user.getActive()) {
				FacesMessage message = new FacesMessage("Nalog neaktivan. Morate platiti članarinu i sačekati odobrenje administratora.");
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(btnLogin.getClientId(context), message);

				return null;
			}

			return "search";
		}
		else {
			// invalid
            FacesMessage message = new FacesMessage("Podaci nisu ispravni");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(btnLogin.getClientId(context), message);

			return null;
		}
	}

	private void reset() {
		loggedIn = false;
		user = null;
		admin = false;
		librarian = false;
		username = null;
		password = null;
	}

	public String logout() {
		reset();
		return "login";
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the librarian
	 */
	public boolean isLibrarian() {
		return librarian;
	}

	/**
	 * @param librarian the librarian to set
	 */
	public void setLibrarian(boolean librarian) {
		this.librarian = librarian;
	}

	/**
	 * @return the btnLogin
	 */
	public UIComponent getBtnLogin() {
		return btnLogin;
	}

	/**
	 * @param btnLogin the btnLogin to set
	 */
	public void setBtnLogin(UIComponent btnLogin) {
		this.btnLogin = btnLogin;
	}

}