package helpers;

import java.util.List;
import db.Book;
import db.Comment;
import db.Reservation;
import db.Taken;
import db.User;

/**
 *
 * @author Aleksandar Abu-Samra
 */
public class BookHelper extends GenericHelper {

	public BookHelper() {
	}

	/**
	 *
	 * @return
	 */
	public List<Book> getBooks() {
		return getList("Book");
	}

	public Book getBookByID(int bookId) {
		return (Book)getObject("Book", "id", bookId);
	}

	public boolean addBook(Book book) {
		return save(book);
	}

	public List<Comment> getComments(int bookId) {
		return getList("Comment", "book", bookId);
	}

	public Reservation getReservation(User user, Book book) {
		return (Reservation)getObject("Reservation", "user", user.getId(), "book", book.getId());
	}

	public Reservation reserveBook(User user, Book book) {
		Reservation reservation = new Reservation();
		reservation.setUser(user);
		reservation.setBook(book);

		save(reservation);

		return reservation;
	}


	public boolean cancelReservation(Reservation reservation) {
		return delete(reservation);
	}

	public boolean updateBook(Book book) {
		return update(book);
	}

	public List<Taken> getTakenList() {
		return getList("Taken", "back", 0);
	}

	public List<Taken> getTakenList(Book book) {
		return getList("Taken", "book", book.getId());
	}

	public List<Taken> getTakenList(User user) {
		return getList("Taken", "user", user.getId());
	}

	public List<Taken> getHoldList(User user) {
		return getList("Taken", "user", user.getId(), "back", 0);
	}


	public List<Reservation> getReservedList(Book book) {
		return getList("Reservation", "book", book.getId());
	}

	public Taken giveBook(User user, Book book) {
		Taken taken = new Taken();
		taken.setUser(user);
		taken.setBook(book);
		taken.setBack(Boolean.FALSE);

		// remove reservation
		Reservation res = getReservation(user, book);
		if (res != null) {
			cancelReservation(res);
		}

		save(taken);

		return taken;
	}

	public boolean returnBook(Taken taken) {
		taken.setBack(Boolean.TRUE);

		return update(taken);
	}

	public boolean addComment(Comment comment) {
		return save(comment);
	}


	public List<Book> getNewestBooks() {
		return queryListLimit("from Book as book order by book.id desc", 10);
	}

	public List<Book> getMostReadBooks() {
		String query = "select t.book "
				+ "from Taken as t "
				+ "group by t.book "
				+ "order by count(t) desc";

		return queryListLimit(query, 10);
	}

	public List<Book> getHighestRatedBooksForm() {
		String query = "select c.book "
				+ "from Comment as c "
				+ "group by c.book "
				+ "order by avg(c.grade) desc";

		return queryListLimit(query, 10);
	}

	public List<Book> getPopularBooks() {
		String query = "select t.book "
				+ "from Reservation as t "
				+ "group by t.book "
				+ "order by count(t) desc";

		return queryListLimit(query, 10);
	}

	public List<User> getBestMembersThisMonth() {
		String query = "select t.user "
				+ "from Taken as t "
				+ "where current_timestamp() - t.timestamp < 2629728000 "
				+ "group by t.user "
				+ "order by count(t) desc";

		return queryListLimit(query, 10);
	}

	public List<User> getBestMembersThisYear() {
		String query = "select t.user "
				+ "from Taken as t "
				+ "where current_timestamp() - t.timestamp < 31556952000 "
				+ "group by t.user "
				+ "order by count(t) desc";

		return queryListLimit(query, 10);
	}

	public boolean hasReadBook(User user, Book book) {
		Taken taken = (Taken)getObject("Taken", "user", user.getId(), "book", book.getId());
		if (taken == null) return false;

		return taken.getBack();
	}
}
