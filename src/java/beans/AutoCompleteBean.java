/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.User;
import helpers.UserHelper;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@RequestScoped
public class AutoCompleteBean {

	private User selectedUser;
	private List<User> users;
	private UserHelper userHelper;

	/**
	 * Creates a new instance of AutoCompleteBean
	 */
	public AutoCompleteBean() {
		userHelper = new UserHelper();
		users = userHelper.getUsers();
		//users = UserConverter.userDB;
	}

	public List<User> completeUser(String query) {
		List<User> suggestions = new ArrayList<User>();

		for (User p : users) {
			if (p.getUsername().startsWith(query)) {
				suggestions.add(p);
			}
		}

		return suggestions;
	}

	/**
	 * @return the selectedUser
	 */
	public User getSelectedUser() {
		return selectedUser;
	}

	/**
	 * @param selectedUser the selectedUser to set
	 */
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
}
