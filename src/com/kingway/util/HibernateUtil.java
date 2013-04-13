package com.kingway.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * 
 * Hibernate Util Class
 * 
 * @author ZERO
 * @version 2010-05-09 1.0
 * @version 2010-07-24 1.1 增加方法
 * @version 2010-07-26 1.2 修改初始化静态方法
 * @version 2010-09-03 1.3 增加批量处理方法
 * @version 2010-10-03 1.4 增加batchGet()方法
 * 
 * @openbugs 1.found on hibernate-3.2.6.ga Transaction1 was not be committed,
 *           however, when transaction2 was committed, transaction1 would be
 *           committed too.
 */

public final class HibernateUtil {

	private static int batchSize = 20; // 20,same as the JDBC batch size

	private HibernateUtil() {
	}

	private static class SessionFactoryBuilder {
		/* Configuration相当于JDBC的Class.forName(); */
		static Configuration cfg = new Configuration();
		/* 读取配置文件，默认为hibernate.cfg.xml，如果不是该文件需使用cfg.configure("fileName") */
		/*
		 * Use the mappings and properties specified in an application resource
		 * named hibernate.cfg.xml.
		 */
		static {
			cfg.configure();
		}
		/* SessionFactory相当于JDBC的DriverManager */
		static SessionFactory sessionFatory = cfg.buildSessionFactory();

		private SessionFactoryBuilder() {
		}

		private static SessionFactory getSessionFactory() {
			return sessionFatory;
		}

	}

	/**
	 * 获取SessionFactory对象
	 * 
	 * @return
	 */
	public static SessionFactory getSessionFatory() {
		return SessionFactoryBuilder.getSessionFactory();
	}

	/**
	 * 获取Session对象
	 * 
	 * @return
	 */
	public static Session getSession() {
		/* Session相当于JDBC的Connection;Hibernate中connection.setAutoCommit(false); */
		return getSessionFatory().openSession();
	}

	/**
	 * 插入一条记录
	 * 
	 * @param entity
	 */
	public static void save(Object entity) {
		Session s = null;
		Transaction tx = null;
		try {
			s = getSession();
			tx = s.beginTransaction(); // if the transaction has not been
			// committed, it will rollback
			s.save(entity);
			tx.commit();
		} catch (HibernateException e) { // catch part can be omitted
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 根据数据是否存在决定更新或插入
	 * 
	 * @param entity
	 */
	public static void saveOrUpdate(Object entity) {
		Session s = null;
		Transaction tx = null;
		try {
			s = getSession();
			tx = s.beginTransaction();
			s.saveOrUpdate(entity);
			tx.commit();
		} catch (HibernateException e) { // catch part can be omitted
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 删除一条记录
	 * 
	 * @param entity
	 */
	public static void delete(Object entity) {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			s.delete(entity);
			tx.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 获取一条记录，以PrimaryKey为条件
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object get(Class clazz, Serializable id) {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			Object obj = s.get(clazz, id);
			tx.commit();
			return obj;
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 返回一条记录，条件为非PrimaryKey的关键字 存在该记录返回该对象，否则返回null
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getRecord(String hql) {
		Session s = null;
		Transaction tx = null;
		Object obj = null;
		List resultList = null;
		try {
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			Query q = s.createQuery(hql);
			q.setMaxResults(1);
			tx.commit();
			resultList = q.list();
			if (resultList.size() == 0)
				return null;
			else {
				obj = resultList.get(0);
				return obj;
			}
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 更新一条记录 默认更新entity记录的所有字段
	 * 
	 * @param entity
	 */
	public static void update(Object entity) {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			s.update(entity);
			tx.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 更新一条记录 使用Hql，只更新对应的更新部分
	 * 
	 * @param hql
	 */
	public static void updateRecord(String hql) {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			Query q = s.createQuery(hql);
			q.executeUpdate();
			tx.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 获取结果集:如果结果为空则返回空List,即List的size为0
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List list(String hql) {
		Session s = null;
		Transaction tx = null;
		List resultList = null;
		try {
			s = getSession();
			tx = s.beginTransaction();
			Query q = s.createQuery(hql);
			resultList = q.list();
			tx.commit();
			return resultList;
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 获取结果集:如果结果为空则返回空List,即List的size为0
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List list(String hql, int begin, int max) {
		Session s = null;
		Transaction tx = null;
		List resultList = null;
		try {
			s = getSession();
			tx = s.beginTransaction();
			Query q = s.createQuery(hql);
			q.setFirstResult(begin);
			if (max > 0) {
				q.setMaxResults(max);
			}
			resultList = q.list();
			tx.commit();
			return resultList;
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 查询是否存在该记录，存在返回TRUE，否则返回FALSE
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isRecordExist(String hql) {
		Session s = null;
		Transaction tx = null;
		List resultList = null;
		try {
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			Query q = s.createQuery(hql);
			resultList = q.list();
			tx.commit();
			if (resultList.size() == 0)
				return false;
			else {
				return true;
			}
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 批量插入
	 * 
	 * @param list
	 */
	public static void batchInsert(List<?> list) {
		if (list == null || list.size() == 0)
			return;
		Session s = null;
		Transaction tx = null;
		int size = list.size();
		try {
			s = getSession();
			tx = s.beginTransaction();
			for (int i = 0; i < size; i++) {
				s.save(list.get(i));
				if (i % batchSize == 0 && i != 0) {
					/* flush a batch of delete and release memory */
					s.flush();
					s.clear();
				}
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 批量更新
	 * 
	 * @param list
	 */
	public static void batchUpdate(List<?> list) {
		if (list == null || list.size() == 0)
			return;
		Session s = null;
		Transaction tx = null;
		int size = list.size();
		try {
			s = getSession();
			tx = s.beginTransaction();
			for (int i = 0; i < size; i++) {
				s.update(list.get(i));
				if (i % batchSize == 0 && i != 0) {
					/* flush a batch of delete and release memory */
					s.flush();
					s.clear();
				}
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param list
	 */
	public static void batchDelete(List<?> list) {
		if (list == null || list.size() == 0)
			return;
		Session s = null;
		Transaction tx = null;
		int size = list.size();
		try {
			s = getSession();
			tx = s.beginTransaction();
			for (int i = 0; i < size; i++) {
				s.delete(list.get(i));
				if (i % batchSize == 0 && i != 0) {
					/* flush a batch of delete and release memory */
					s.flush();
					s.clear();
				}
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * 批量获取数据，以PrimaryKey为条件
	 * 
	 * @param list
	 *            存放PrimaryKey的List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List batchGet(Class clazz, List<?> list) {
		if (list == null || list.size() == 0)
			return null;
		Session s = null;
		Transaction tx = null;
		int size = list.size();
		List resultList = new ArrayList();
		try {
			s = getSession();
			tx = s.beginTransaction();
			for (int i = 0; i < size; i++) {
				resultList.add(s.get(clazz, (Serializable) list.get(i)));
				if (i % batchSize == 0 && i != 0) {
					/* flush a batch of delete and release memory */
					s.flush();
					s.clear();
				}
			}
			tx.commit();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			return resultList;
		} finally {
			if (s != null)
				s.close();
		}
	}

	public static boolean callProcedure(String procs) {

		String procedures[] = procs.split(",");
		for (int i = 0; i < procedures.length; i++) {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			// System.out.print("---begin---");
			Connection con = session.connection();
			String procedure = procedures[i];
			try {
				CallableStatement cstmt = con.prepareCall(procedure);
				cstmt.execute();

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				tx.commit();
				return false;
			}
			tx.commit();
			System.out.println(procedures[i] + " 执行完毕！");
		}
		return true;
	}

	public static boolean deleteUnuseContentbuffer(int days) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		// System.out.print("---begin---");
		Connection con = session.connection();

		String procedure = "{call deleteUnuseContentbuffer(?)}";
		try {
			CallableStatement cstmt = con.prepareCall(procedure);
			cstmt.setInt(1, days);
			cstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			tx.commit();
			return false;
		}
		tx.commit();
		System.out.println(" deleteUnuseContentbuffer 执行完毕！");

		return true;
	}
    /**
     * 
     * @param sql
     * @return
     */
	public static String getQueryResultBySQL(String sql) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		// System.out.print("---begin---");
		Connection con = session.connection();
		StringBuilder table = new StringBuilder();
		
		try {
			PreparedStatement cstmt = con.prepareStatement(sql);
			boolean r=cstmt.execute();
			if(r==false){
				tx.commit();
				return "成功更新或删除了"+cstmt.getUpdateCount()+"行";
			}
			table.append("<table>");
			table.append("<tr>");
			ResultSet rs = cstmt.getResultSet();
			ResultSetMetaData rsd = rs.getMetaData();
			for (int i = 1; i <= rsd.getColumnCount(); i++) {
				table.append("<th>");
				table.append(rsd.getColumnName(i));
				table.append("</th>");
			}
			table.append("</tr>");
			while (rs.next()) {
				table.append("<tr>");
				for (int i = 1; i <= rsd.getColumnCount(); i++) {
					table.append("<td>");
					table.append(rs.getObject(i));
					table.append("</td>");
				}
				table.append("</tr>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			tx.commit();
			return e.toString();
		}
		tx.commit();
		table.append("</table>");
		String result = null;
		try {
			result = new String(table.toString().getBytes(), "GB2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return table.toString();
		}
		return result;
	}
} // end of class

/*
 * important functions in Hibernate: session.save(); session.flush();
 * session.clear(); transaction.commit();
 */

