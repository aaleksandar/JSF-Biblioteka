package helpers;

import db.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * NOTE: session opens in constructor and closes in methods
 * for LazyInitializationException error prevention
 *
 * @author Aleksandar Abu-Samra
 */
public class GenericHelper {

	public Session session = null;
	public SessionFactory sessionFactory = null;

	public GenericHelper() {
		sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
	}


	/**
	 * Gets list with custom query
	 *
	 * @param table
	 * @return
	 */
	public List queryList(String query) {
		session.close();
		session = sessionFactory.openSession();

		List list = null;
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			Query q = session.createQuery(query);
			list = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return list;
	}

	/**
	 * Gets list with custom query, with LIMIT
	 *
	 * @param table
	 * @return
	 */
	public List queryListLimit(String query, int limit) {
		session.close();
		session = sessionFactory.openSession();

		List list = null;
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			Query q = session.createQuery(query);
			q.setMaxResults(limit);
			list = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return list;
	}

	/**
	 * Gets object with custom query
	 *
	 * @param table
	 * @return
	 */
	public Object queryObject(String query) {
		session.close();
		session = sessionFactory.openSession();
		Object obj = null;

		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			Query q = session.createQuery(query);
			obj = q.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return obj;
	}

	/**
	 * Gets list of all objects
	 *
	 * @param table
	 * @return
	 */
	public List getList(String table) {
		return queryList("from " + table);
	}


	/**
	 * Gets list, 2 parameters
	 *
	 * @param table
	 * @return
	 */
	public List getList(String table, String param1, int value1, String param2, int value2) {
		return queryList("from " + table + " as t where t." + param1 + " = '" + value1 + "' and t." + param2 + " = '" + value2 + "'");
	}

	/**
	 * Saves object to DB
	 *
	 * @param obj
	 * @return
	 */
	public boolean save(Object obj) {
		session.close();
		session = sessionFactory.openSession();
		boolean result = true;

		try {
			// insert into database
			org.hibernate.Transaction tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {

		}

		return result;
	}

	/**
	 * Updates object in DB
	 *
	 * @param obj
	 * @return
	 */
	public boolean update(Object obj) {
		session.close();
		session = sessionFactory.openSession();
		boolean result = true;

		try {
			// insert into database
			org.hibernate.Transaction tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {

		}

		return result;
	}

	/**
	 * Deletes object from DB
	 *
	 * @param obj
	 * @return
	 */
	public boolean delete(Object obj) {
		session.close();
		session = sessionFactory.openSession();
		boolean result = true;

		try {
			// insert into database
			org.hibernate.Transaction tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {

		}

		return result;
	}


	/**
	 * Gets object, 1 parameter
	 *
	 * @param table
	 * @return
	 */
	public Object getObject(String table, String param, String value) {
		return queryObject("from " + table + " as t where t." + param + " = '" + value + "'");
	}

	public Object getObject(String table, String param, int value) {
		return getObject(table, param, Integer.toString(value));
	}

	/**
	 * Gets object, 2 parameters
	 *
	 * @param table
	 * @return
	 */
	public Object getObject(String table, String param1, String value1, String param2, String value2) {
		return queryObject("from " + table + " as t where t." + param1 + " = '" + value1 + "' and t." + param2 + " = '" + value2 + "'");
	}

	public Object getObject(String table, String param1, int value1, String param2, int value2) {
		return getObject(table, param1, Integer.toString(value1), param2, Integer.toString(value2));
	}

	/**
	 * Gets list of objects
	 *
	 * @param table
	 * @return
	 */
	public List getList(String table, String param, String value) {
		return queryList("from " + table + " as t where t." + param + " = '" + value + "'");
	}

	public List getList(String table, String param, int value) {
		return getList(table, param, Integer.toString(value));
	}


}
