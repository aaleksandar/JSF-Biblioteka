package beans;

import db.Const;
import db.User;
import helpers.SystemHelper;
import helpers.UserHelper;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@RequestScoped
public class AdminBean {
	UserHelper userHelper;
	SystemHelper systemHelper;

	private int numBooksAllowedToTake;
	private int numBooksAllowedToReserve;
	private int numDaysAllowedToHold;
	private int penaltyPerDay;

	/**
	 * Creates a new instance of AdminBean
	 */
	public AdminBean() {
		userHelper = new UserHelper();
		systemHelper = new SystemHelper();

		numBooksAllowedToTake = SystemBean.numBooksAllowedToTake;
		numBooksAllowedToReserve = SystemBean.numBooksAllowedToReserve;
		numDaysAllowedToHold = SystemBean.numDaysAllowedToHold;
	    penaltyPerDay = SystemBean.penaltyPerDay;
	}

	/**
	 * @return the unactiveUsers
	 */
	public List<User> getUnactiveUsers() {
		return userHelper.getUnactiveUsers();
	}

	public void activateUser(User user) {
		userHelper.activateUser(user);
	}

	public void deleteUser(User user) {
		userHelper.deleteUser(user);
	}

	/**
	 * @return the allUsers
	 */
	public List<User> getAllUsers() {
		return userHelper.getUsers();
	}

	/**
	 * @return all members
	 */
	public List<User> getMembers() {
		return userHelper.getMembers();
	}


	public List<User> getLibrarians() {
		return userHelper.getLibrarians();
	}

	/**
	 * @return the numBooksAllowedToTake
	 */
	public int getNumBooksAllowedToTake() {
		return numBooksAllowedToTake;
	}

	/**
	 * @param numBooksAllowedToTake the numBooksAllowedToTake to set
	 */
	public void setNumBooksAllowedToTake(int numBooksAllowedToTake) {
		this.numBooksAllowedToTake = numBooksAllowedToTake;
	}

	/**
	 * @return the numBooksAllowedToReserve
	 */
	public int getNumBooksAllowedToReserve() {
		return numBooksAllowedToReserve;
	}

	/**
	 * @param numBooksAllowedToReserve the numBooksAllowedToReserve to set
	 */
	public void setNumBooksAllowedToReserve(int numBooksAllowedToReserve) {
		this.numBooksAllowedToReserve = numBooksAllowedToReserve;
	}

	/**
	 * @return the numDaysAllowedToHold
	 */
	public int getNumDaysAllowedToHold() {
		return numDaysAllowedToHold;
	}

	/**
	 * @param numDaysAllowedToHold the numDaysAllowedToHold to set
	 */
	public void setNumDaysAllowedToHold(int numDaysAllowedToHold) {
		this.numDaysAllowedToHold = numDaysAllowedToHold;
	}

	/**
	 * @return the penaltyPerDay
	 */
	public int getPenaltyPerDay() {
		return penaltyPerDay;
	}

	/**
	 * @param penaltyPerDay the penaltyPerDay to set
	 */
	public void setPenaltyPerDay(int penaltyPerDay) {
		this.penaltyPerDay = penaltyPerDay;
	}

	public void updateSystemParameters() {
		Const consts = new Const();
		consts.setId(1);
		consts.setM(numBooksAllowedToTake);
		consts.setN(numBooksAllowedToReserve);
		consts.setS(numDaysAllowedToHold);
		consts.setP(penaltyPerDay);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO,"Sačuvano","")
				);

		systemHelper.setParameters(consts);
	}
}
