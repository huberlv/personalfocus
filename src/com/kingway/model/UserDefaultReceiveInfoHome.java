package com.kingway.model;

// Generated 2011-1-23 9:52:11 by Hibernate Tools 3.3.0.GA

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class UserDefaultReceiveInfo.
 * @see com.kingway.model.UserDefaultReceiveInfo
 * @author Hibernate Tools
 */
public class UserDefaultReceiveInfoHome {

	private static final Log log = LogFactory
			.getLog(UserDefaultReceiveInfoHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(UserDefaultReceiveInfo transientInstance) {
		log.debug("persisting UserDefaultReceiveInfo instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UserDefaultReceiveInfo instance) {
		log.debug("attaching dirty UserDefaultReceiveInfo instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserDefaultReceiveInfo instance) {
		log.debug("attaching clean UserDefaultReceiveInfo instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UserDefaultReceiveInfo persistentInstance) {
		log.debug("deleting UserDefaultReceiveInfo instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserDefaultReceiveInfo merge(UserDefaultReceiveInfo detachedInstance) {
		log.debug("merging UserDefaultReceiveInfo instance");
		try {
			UserDefaultReceiveInfo result = (UserDefaultReceiveInfo) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserDefaultReceiveInfo findById(java.lang.Long id) {
		log.debug("getting UserDefaultReceiveInfo instance with id: " + id);
		try {
			UserDefaultReceiveInfo instance = (UserDefaultReceiveInfo) sessionFactory
					.getCurrentSession().get(
							"com.kingway.model.UserDefaultReceiveInfo", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserDefaultReceiveInfo instance) {
		log.debug("finding UserDefaultReceiveInfo instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria(
					"com.kingway.model.UserDefaultReceiveInfo").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
