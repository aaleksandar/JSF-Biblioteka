package beans;

import helpers.BookHelper;
import javax.faces.bean.ManagedBean;
import db.Book;
import db.Comment;
import db.Reservation;
import db.User;
import helpers.UserHelper;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@ViewScoped
public class BookBean {
	private int id;

	@ManagedProperty(value="#{sessionBean}")
	private SessionBean sessionBean;

	private BookHelper bookHelper;
	private UserHelper userHelper;

	private User user;
	private Book book;
	private List<Comment> comments;

	private String reserveButtonValue;
	private boolean reservationButtonDisabled;
	private Reservation reservation;
	private boolean pdfAvailable;

	private int newGrade;
	private String newComment;

	/**
	 * Creates a new instance of BookBean
	 */
	public BookBean() {
		bookHelper = new BookHelper();
		userHelper = new UserHelper();
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		if (book == null) {
			setBook(bookHelper.getBookByID(id));
		}
		return book;
	}

	/**
	 * @return the commentList
	 */
	public List<Comment> getComments() {
		if (comments == null) {
			comments = bookHelper.getComments(id);
		}

		return comments;
	}

	public void init() {
		// reservationButtonDisabled?
		reservationButtonDisabled = false;
		if (isReservationOld()) reservationButtonDisabled = true;
		if (!getBook().getTakeAway()) reservationButtonDisabled = true;
		if (!canReserveBook(sessionBean.getUser())) reservationButtonDisabled = true;

		// pdfAvailable?
		if (getBook().getPdf() == null) pdfAvailable = false;
		else pdfAvailable = true;

		// reserveButtonValue ?
		if (!getBook().getTakeAway()) {
			reserveButtonValue = "Knjiga se ne može iznositi van biblioteke";
		}
		else if (getReservation() != null) {
			if (isReservationOld()) {
				reserveButtonValue = "Ne možete više rezervisati ovu knjigu";
			}
			else {
				reserveButtonValue = "Otkaži rezervaciju";
				reservationButtonDisabled = false;
			}
		}
		else if (!canReserveBook(sessionBean.getUser())) {
			reserveButtonValue = "Dostigli ste limit rezervisanih knjiga";
		}
		else {
			reserveButtonValue = "Rezerviši";
		}

		// comment grade default
		newGrade = 5;

	}

	/**
	 *
	 * @return
	 */
	public String toggleReservation() {
		if (getReservation() != null) {
			// rezervacija postoji, otkazi
			bookHelper.cancelReservation(getReservation());
			reserveButtonValue = "Rezerviši";
		}
		else {
			// rezervatija ne postoji, napravi novu
			reservation = bookHelper.reserveBook(getUser(), getBook());
			reserveButtonValue = "Otkaži rezervaciju";
		}

		return "/book.xhtml?faces-redirect=true&id=" + id;
	}


	/**
	 * @return the reserveButtonValue
	 */
	public String getReserveButtonValue() {
		return reserveButtonValue;
	}

	/**
	 * @param reserveButtonValue the reserveButtonValue to set
	 */
	public void setReserveButtonValue(String reserveButtonValue) {
		this.reserveButtonValue = reserveButtonValue;
	}

	public boolean isReservationButtonDisabled() {
		return reservationButtonDisabled;
	}

	public boolean isReservationOld() {
		if (getReservation() == null) return false; // nema rezervacije

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -2); // 2 dana traje rezervacija

		Date date = calendar.getTime();

		if (date.after(getReservation().getTimestamp())) {
			return true;
		}

		return false;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		if (user == null) {
			user = sessionBean.getUser();
		}

		return user;
	}

	/**
	 * @return the reservation
	 */
	public Reservation getReservation() {
		if (reservation == null) {
			reservation = bookHelper.getReservation(getUser(), getBook());
		}

		return reservation;
	}

	public boolean isPdfAvailable() {
		return pdfAvailable;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	public int numOfTaken(Book book) {
		return bookHelper.getTakenList(book).size();
	}

	public int numOfReserved(Book book) {
		return bookHelper.getReservedList(book).size();
	}

	/**
	 * @return the newGrade
	 */
	public int getNewGrade() {
		return newGrade;
	}

	/**
	 * @param newGrade the newGrade to set
	 */
	public void setNewGrade(int newGrade) {
		this.newGrade = newGrade;
	}

	/**
	 * @return the newComment
	 */
	public String getNewComment() {
		return newComment;
	}

	/**
	 * @param newComment the newComment to set
	 */
	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public String postComment() {
		Comment comment = new Comment();
		comment.setBook(getBook());
		comment.setGrade(newGrade);
		comment.setComment(newComment);

		comments.add(comment);
		bookHelper.addComment(comment);

		return "/book.xhtml?faces-redirect=true&id=" + id;
	}

	public boolean canPostComment(User user) {
		// ako ima elektronsko izdanje
		String pdf = getBook().getPdf();
		if (pdf != null && pdf.length() > 0) return true;

		// papirno izdanje, procitao knjigu
		return bookHelper.hasReadBook(user, getBook());
	}

	public int numOfBooksAllowedToReserve(User user) {
		int num =SystemBean.numBooksAllowedToReserve;

		if (userHelper.hasMonthlyAward(user)) {
			num *= 2;
		}

		return num;
	}

	public boolean canReserveBook(User user) {
		// da li sme da rezervise jos knjiga
		if (userHelper.getReservedBooks(user).size() >= numOfBooksAllowedToReserve(user)) return false;

		return true;
	}
}
