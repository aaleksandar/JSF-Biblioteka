package beans;

import db.Book;
import helpers.BookHelper;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ViewScoped
@ManagedBean
public class EditBookBean {
	private int id;

	private Book book;
	private BookHelper bookHelper;

	private UploadedFile coverBlob;
	private UploadedFile pdfBlob;

	/**
	 * Creates a new instance of EditBookBean
	 */
	public EditBookBean() {
		bookHelper = new BookHelper();
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		if (book == null) {
			setBook(bookHelper.getBookByID(getId()));
		}
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}


	/**
	 * Updates book in database
	 * @return
	 */
	public String updateBook() {
		if (book.getNumOfCopies() > 0) book.setPaperVersion(Boolean.TRUE);
		else book.setPaperVersion(Boolean.FALSE);

				// save BLOBs
		// kopiraj cover
		if (coverBlob != null) {
			try {
				SystemBean.copyFile(SystemBean.COVERS_DIR, coverBlob.getFileName(), coverBlob.getInputstream());
				book.setPicture(coverBlob.getFileName());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// kopiraj pdf
		if (pdfBlob != null) {
			try {
				SystemBean.copyFile(SystemBean.PDFS_DIR, pdfBlob.getFileName(), pdfBlob.getInputstream());
				book.setPdf(pdfBlob.getFileName());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		bookHelper.updateBook(book);

		return "/book.xhtml?faces-redirect=true&id=" + getId();
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
}
