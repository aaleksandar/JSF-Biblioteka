package beans;

import db.Book;
import db.Taken;
import db.User;
import helpers.BookHelper;
import helpers.UserHelper;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@RequestScoped
public class TakenBean {

	private BookHelper bookHelper;
	private UserHelper userHelper;

	/**
	 * Creates a new instance of TakenBean
	 */
	public TakenBean() {
		bookHelper = new BookHelper();
		userHelper = new UserHelper();
	}

	/**
	 * @return the takenList
	 */
	public List<Taken> getTakenList() {
		return bookHelper.getTakenList();
	}


	public long penalty(Taken taken) {
		long penalty = 0;

		// samo ako nije vec vracena
		if (!taken.getBack()) {
			Date now = new Date();

			long numDaysBetween = (now.getTime() - taken.getTimestamp().getTime()) / SystemBean.DAY_IN_MILLISECONDS;
			long numDaysAhead = numDaysBetween - SystemBean.numDaysAllowedToHold;

			if (numDaysAhead > 0) {
				penalty = numDaysAhead * SystemBean.penaltyPerDay;
			}
		}

		return penalty;
	}

	public int numOfBooksTaken(User user) {
		return bookHelper.getHoldList(user).size();
	}


	public int numOfBooksAllowedToTake(User user) {
		int num =SystemBean.numBooksAllowedToTake;

		if (userHelper.hasMonthlyAward(user)) {
			num *= 2;
		}

		return num;
	}

	// TODO update
	public boolean canTakeBook(User user, Book book) {
		// da li sme da se uzme jos knjiga
		if (numOfBooksTaken(user) >= numOfBooksAllowedToTake(user)) return false;

		// ako su sve uzete
		int available = book.getNumOfCopies() - bookHelper.getTakenList(book).size();
		if (available <= 0) return false;

		// ako nisu sve uzete, gledamo rezervacije
		available -= bookHelper.getReservedList(book).size();
		if (available <= 0) {
			// nema rezervaciju
			if (bookHelper.getReservation(user, book) == null) return false;
		}

		return true;
	}

	public String giveBook(User user, Book book) {
		bookHelper.giveBook(user, book);

		return "/librarian.xhtml?faces-redirect=true";
	}

	public void returnBook(Taken taken) {
		if (penalty(taken) > 0) taken.setHadPenalty(Boolean.TRUE);
		taken.setBack(Boolean.TRUE);

		bookHelper.returnBook(taken);
	}

}
