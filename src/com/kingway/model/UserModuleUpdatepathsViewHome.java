package com.kingway.model;

// Generated 2010-12-9 22:12:03 by Hibernate Tools 3.3.0.GA

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class UserModuleUpdatepathsView.
 * @see com.kingway.model.UserModuleUpdatepathsView
 * @author Hibernate Tools
 */
public class UserModuleUpdatepathsViewHome {

	private static final Log log = LogFactory
			.getLog(UserModuleUpdatepathsViewHome.class);

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

	public void persist(UserModuleUpdatepathsView transientInstance) {
		log.debug("persisting UserModuleUpdatepathsView instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UserModuleUpdatepathsView instance) {
		log.debug("attaching dirty UserModuleUpdatepathsView instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserModuleUpdatepathsView instance) {
		log.debug("attaching clean UserModuleUpdatepathsView instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UserModuleUpdatepathsView persistentInstance) {
		log.debug("deleting UserModuleUpdatepathsView instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserModuleUpdatepathsView merge(
			UserModuleUpdatepathsView detachedInstance) {
		log.debug("merging UserModuleUpdatepathsView instance");
		try {
			UserModuleUpdatepathsView result = (UserModuleUpdatepathsView) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserModuleUpdatepathsView findById(
			com.kingway.model.UserModuleUpdatepathsViewId id) {
		log.debug("getting UserModuleUpdatepathsView instance with id: " + id);
		try {
			UserModuleUpdatepathsView instance = (UserModuleUpdatepathsView) sessionFactory
					.getCurrentSession().get(
							"com.kingway.model.UserModuleUpdatepathsView", id);
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

	public List findByExample(UserModuleUpdatepathsView instance) {
		log.debug("finding UserModuleUpdatepathsView instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria(
					"com.kingway.model.UserModuleUpdatepathsView").add(
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
