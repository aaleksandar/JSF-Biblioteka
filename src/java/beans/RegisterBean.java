package beans;

import db.User;
import helpers.UserHelper;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@RequestScoped
public class RegisterBean {

	private User user;

	private String confirmPassword;
	UserHelper userHelper;


	/**
	 * Creates a new instance of RegisterBean
	 */
	public RegisterBean() {
		user = new User();
		user.setType("member");
		user.setActive(Boolean.FALSE);

		userHelper = new UserHelper();
	}

	public String register() {
		String result = "msg_register_success";

		userHelper.registerUser(user);

		return result;
	}

	public String registerLibrarian() {
		String result = "msg_register_success";
		user.setActive(Boolean.TRUE);
		user.setType("librarian");

		userHelper.registerUser(user);

		return result;
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
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
