package beans;

import db.User;
import helpers.UserHelper;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@RequestScoped
public class ChangePasswordBean {
	private String username;
	private String oldPassword;
	private String newPassword;
	
	private UIComponent btnChangePass;
	
	UserHelper userHelper;
	
	/**
	 * Creates a new instance of ChangePasswordBean
	 */
	public ChangePasswordBean() {
		userHelper = new UserHelper();
		
	}
	
	public String changePassword() {	
		User user = userHelper.getUser(username, oldPassword);
		if (user != null) {
			user.setPassword(newPassword);
			userHelper.updateUser(user);
			
			return "msg_change_password_success";
		}
		else {
			// invalid
            FacesMessage message = new FacesMessage("Podaci nisu ispravni");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(btnChangePass.getClientId(context), message);
		}
		
		return null;
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
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the btnChangePass
	 */
	public UIComponent getBtnChangePass() {
		return btnChangePass;
	}

	/**
	 * @param btnChangePass the btnChangePass to set
	 */
	public void setBtnChangePass(UIComponent btnChangePass) {
		this.btnChangePass = btnChangePass;
	}
}
