package beans;

import db.Book;
import db.User;
import helpers.BookHelper;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@RequestScoped
public class SearchBean implements Serializable {

	private BookHelper bookHelper;

	/**
	 * Creates a new instance of SearchBean
	 */
	public SearchBean() {

		// set helper
		bookHelper = new BookHelper();
	}

	public List<Book> getBooks() {
		return bookHelper.getBooks();
	}


	public List<Book> getNewestBooks() {
		return bookHelper.getNewestBooks();
	}

	public List<Book> getPopularBooks() {
		return bookHelper.getPopularBooks();
	}

	public List<Book> getMostReadBooks() {
		return bookHelper.getPopularBooks();
	}

	public List<Book> getHighestRatedBooksForm() {
		return bookHelper.getHighestRatedBooksForm();
	}

	public List<User> getBestMembersThisMonth() {
		return bookHelper.getBestMembersThisMonth();
	}

	public List<User> getBestMembersThisYear() {
		return bookHelper.getBestMembersThisYear();
	}
}
