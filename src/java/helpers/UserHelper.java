package helpers;

import db.MonthlyAward;
import db.Reservation;
import db.Taken;
import db.User;
import db.YearlyAward;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Aleksandar Abu-Samra
 */
public class UserHelper extends GenericHelper {

    public UserHelper() {
    }

	public User getUser(int userId){
		return (User)getObject("User", "id", userId);
	}

	public User getUser(String username, String password) {
		return (User)getObject("User", "username", username, "password", password);
	}


	/**
	 * Get all users
	 *
	 * @return
	 */
	public List<User> getUsers() {
		return getList("User");
	}

	/**
	 * Get all members
	 *
	 * @return
	 */
	public List<User> getMembers() {
		return getList("User", "type", "member");
	}

	/**
	 * Get all librarians
	 *
	 * @return
	 */
	public List<User> getLibrarians() {
		return getList("User", "type", "librarian");
	}


	public List<User> getUnactiveUsers() {
		return getList("User", "active", 0);
	}

	public boolean registerUser(User user) {
		return save(user);
	}

	public boolean updateUser(User user) {
		return update(user);
	}

	public boolean deleteUser(User user) {
		return delete(user);
	}

	public boolean activateUser(User user) {
		user.setActive(Boolean.TRUE);
		return update(user);
	}

	public List<Taken> getTakenBooks(User user) {
		return getList("Taken", "user", user.getId());
	}

	public List<Reservation> getReservedBooks(User user) {
		return getList("Reservation", "user", user.getId());
	}

	public boolean hasYearlyAward(User user) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		YearlyAward award = (YearlyAward)getObject("YearlyAward", "year", year, "user", user.getId());

		if (award != null) return true;
		return false;
	}

	public boolean hasMonthlyAward(User user) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);

		MonthlyAward award = (MonthlyAward)queryObject(
				"from MonthlyAward as a where a.year = " +  year
				+ " and a.user = " + user.getId()
				+ " and a.month = " + month
				);

		if (award != null) return true;
		return false;
	}
}
