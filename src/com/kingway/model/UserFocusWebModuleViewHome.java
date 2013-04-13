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
 * Home object for domain model class UserFocusWebModuleView.
 * @see com.kingway.model.UserFocusWebModuleView
 * @author Hibernate Tools
 */
public class UserFocusWebModuleViewHome {

	private static final Log log = LogFactory
			.getLog(UserFocusWebModuleViewHome.class);

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

	public void persist(UserFocusWebModuleView transientInstance) {
		log.debug("persisting UserFocusWebModuleView instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UserFocusWebModuleView instance) {
		log.debug("attaching dirty UserFocusWebModuleView instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserFocusWebModuleView instance) {
		log.debug("attaching clean UserFocusWebModuleView instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UserFocusWebModuleView persistentInstance) {
		log.debug("deleting UserFocusWebModuleView instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserFocusWebModuleView merge(UserFocusWebModuleView detachedInstance) {
		log.debug("merging UserFocusWebModuleView instance");
		try {
			UserFocusWebModuleView result = (UserFocusWebModuleView) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserFocusWebModuleView findById(
			com.kingway.model.UserFocusWebModuleViewId id) {
		log.debug("getting UserFocusWebModuleView instance with id: " + id);
		try {
			UserFocusWebModuleView instance = (UserFocusWebModuleView) sessionFactory
					.getCurrentSession().get(
							"com.kingway.model.UserFocusWebModuleView", id);
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

	public List findByExample(UserFocusWebModuleView instance) {
		log.debug("finding UserFocusWebModuleView instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria(
					"com.kingway.model.UserFocusWebModuleView").add(
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
