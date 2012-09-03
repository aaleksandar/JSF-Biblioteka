package helpers;

import db.Const;
import db.MonthlyAward;
import db.SystemActivated;
import db.User;
import db.YearlyAward;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Aleksandar Abu-Samra
 */
public class SystemHelper extends GenericHelper {

    public SystemHelper() {
    }

	public Const getConsts(){
		return (Const)getObject("Const", "id", 1);
	}

	private int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public boolean isSystemActivated() {
		SystemActivated system = (SystemActivated) getObject("SystemActivated", "year", getYear());

		if (system == null) return false;
		return true;
	}

	public boolean activateSystem() {
		SystemActivated system = new SystemActivated(getYear(), Boolean.TRUE);
		return save(system);
	}

	public boolean setParameters(Const consts) {
		return update(consts);
	}

	public boolean yearlyAwardsAllowed(int year) {
		List<YearlyAward> yearlyAwards = (List<YearlyAward>) getList("YearlyAward", "year", year);

		if (yearlyAwards.isEmpty()) return true;
		return false;
	}

	public boolean giveYearlyAwards() {
		String query = "select t.user "
				+ "from Taken as t "
				+ "where t.hadPenalty = 0 "
				+ "and current_timestamp() - t.timestamp < 31556952000 "
				+ "and year(current_date()) * 12 + month(current_date()) > year(t.user.timestamp) * 12 + month(t.user.timestamp) + 12 "
				+ "group by t.user "
				+ "order by count(t) desc";

		int year = Calendar.getInstance().get(Calendar.YEAR);

		List<User> usersToAward = queryListLimit(query, 5);
		for (User user : usersToAward) {
			YearlyAward award = new YearlyAward(user, year);
			save(award);
		}

		return true;
	}

	public boolean monthlyAwardsAllowed() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);

		List<MonthlyAward> monthlyAwards = (List<MonthlyAward>) getList("MonthlyAward", "year", year, "month", month);

		if (monthlyAwards.isEmpty()) return true;
		return false;
	}


	public boolean giveMonthlyAwards() {
		String query = "select t.user "
				+ "from Taken as t "
				+ "where t.hadPenalty = 0 "
				+ "and current_timestamp() - t.timestamp < 2629743830 "
				+ "group by t.user "
				+ "order by count(t) desc";

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);

		List<User> usersToAward = queryListLimit(query, 3);
		for (User user : usersToAward) {
			MonthlyAward award = new MonthlyAward(user, year, month);
			save(award);
		}

		return true;
	}

}
