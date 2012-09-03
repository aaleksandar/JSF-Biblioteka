/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Book;
import db.User;
import helpers.BookHelper;
import helpers.SystemHelper;
import helpers.UserHelper;
import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Aleksandar Abu-Samra
*/
@ManagedBean
@RequestScoped
public class LibrarianBean {

	private Book newBook;
	private UploadedFile coverBlob;
	private UploadedFile pdfBlob;
	private BookHelper bookHelper;
	private UserHelper userHelper;
	private SystemHelper systemHelper;

	private List<Book> books;

	/**
	 * Creates a new instance of LibrarianBean
	 */
	public LibrarianBean() {
		bookHelper = new BookHelper();
		userHelper = new UserHelper();
		systemHelper = new SystemHelper();

		newBook = new Book();
		newBook.setEdition(1);
		newBook.setYear(2012);
		newBook.setTakeAway(Boolean.TRUE);
		newBook.setNumOfCopies(1);
	}

	public String addBook() {
		// parametri su uglavnom automatski prosledjeni, BLOBovi moraju rucno

		if (newBook.getNumOfCopies() > 0) newBook.setPaperVersion(Boolean.TRUE);

		// save BLOBs
		// kopiraj cover
		if (coverBlob != null) {
			try {
				SystemBean.copyFile(SystemBean.COVERS_DIR, coverBlob.getFileName(), coverBlob.getInputstream());
				newBook.setPicture(coverBlob.getFileName());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// kopiraj pdf
		if (pdfBlob != null) {
			try {
				SystemBean.copyFile(SystemBean.PDFS_DIR, pdfBlob.getFileName(), pdfBlob.getInputstream());
				newBook.setPdf(pdfBlob.getFileName());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		bookHelper.addBook(newBook);

		return "librarian.xhtml?faces-redirect=true";
	}



	/**
	 * @return the newBook
	 */
	public Book getNewBook() {
		return newBook;
	}

	/**
	 * @param newBook the newBook to set
	 */
	public void setNewBook(Book newBook) {
		this.newBook = newBook;
	}

	/**
	 * @return the coverBlob
	 */
	public UploadedFile getCoverBlob() {
		return coverBlob;
	}

	/**
	 * @param coverBlob the coverBlob to set
	 */
	public void setCoverBlob(UploadedFile coverBlob) {
		this.coverBlob = coverBlob;
	}

	/**
	 * @return the pdfBlob
	 */
	public UploadedFile getPdfBlob() {
		return pdfBlob;
	}

	/**
	 * @param pdfBlob the pdfBlob to set
	 */
	public void setPdfBlob(UploadedFile pdfBlob) {
		this.pdfBlob = pdfBlob;
	}

	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		if (books == null) {
			books = bookHelper.getBooks();
		}
		return books;
	}

	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public boolean isSystemActivated() {
		return systemHelper.isSystemActivated();
	}

	public String newYearActivation() {
		// aktiviraj sistem
		systemHelper.activateSystem();

		// resetuj clanarine
		List<User> members = userHelper.getMembers();
		for (User member : members) {
			member.setActive(Boolean.FALSE);

			// ako ima YearlyAward aktiviraj ga
			if (userHelper.hasYearlyAward(member)) member.setActive(Boolean.TRUE);

			userHelper.updateUser(member);
		}

		return "librarian.xhtml?faces-redirect=true";
	}
}
