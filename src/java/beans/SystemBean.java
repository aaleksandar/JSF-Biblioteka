package beans;

import db.Const;
import helpers.SystemHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

/**
 *
 * @author Aleksandar Abu-Samra
 */
@ManagedBean
@ApplicationScoped
public class SystemBean {
	public static final long DAY_IN_MILLISECONDS = 86400000;
	public static final long MONTH_IN_MILLISECONDS = 2629728000L;

	// application constants
	public static final String RESOURCES_DIR = "/home/tmrc/dev/NetBeansProjects/JSF_Biblioteka/web/resources/";
	public static final String COVERS_DIR = "/home/tmrc/dev/NetBeansProjects/JSF_Biblioteka/web/resources/covers/";
	public static final String PDFS_DIR = "/home/tmrc/dev/NetBeansProjects/JSF_Biblioteka/web/resources/pdfs/";

	private static final SystemHelper systemHelper = new SystemHelper();
	private static final Const consts = systemHelper.getConsts();

	public static final int numBooksAllowedToTake = consts.getM();
	public static final int numBooksAllowedToReserve = consts.getN();
	public static final int numDaysAllowedToHold = consts.getS();
	public static final int penaltyPerDay = consts.getP();

	/**
	 * Writes a BLOB file to directory
	 * @param directory
	 * @param fileName
	 * @param in
	 */
	public static void copyFile(String directory, String fileName, InputStream in) {
		try {
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(directory + fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("New file created!");
			} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void giveYearlyAwards() {
		systemHelper.giveYearlyAwards();
	}

	public static boolean yearlyAwardsAllowed() {
		int year = Calendar.getInstance().get(Calendar.YEAR);

		return systemHelper.yearlyAwardsAllowed(year);
	}

	public static void monthlyAwards() {
		if (systemHelper.monthlyAwardsAllowed()) {
			systemHelper.giveMonthlyAwards();
		}
	}

	/**
	 * Creates a new instance of SystemBean
	 */
	public SystemBean() {
	}
}
