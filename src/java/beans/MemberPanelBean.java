package beans;

import db.Reservation;
import db.Taken;
import db.User;
import helpers.BookHelper;
import helpers.UserHelper;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@RequestScoped
public class MemberPanelBean {

	@ManagedProperty(value="#{sessionBean.user}")
	private User user;
	
	private List<Taken> taken;
	private List<Reservation> reservations;
	
	private UserHelper userHelper;
	private BookHelper bookHelper;
	
	/**
	 * Creates a new instance of MemberPanelBean
	 */
	public MemberPanelBean() {
		userHelper = new UserHelper();
		bookHelper = new BookHelper();
	}
	
		/**
	 * @return the takenBooks
	 */
	public List<Taken> getTaken() {
		if (taken == null) {
			taken = userHelper.getTakenBooks(getUser());
		}
		
		return taken;
	}
	
	public List<Reservation> getReservations() {
		if (reservations == null) {
			reservations = userHelper.getReservedBooks(getUser());
		}
		
		return reservations;
	}
	
	public void cancelReservation(Reservation res) {
		bookHelper.cancelReservation(res);
		reservations.remove(res);
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
}
