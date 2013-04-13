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
 * Home object for domain model class CheckUpdateUserModuleView.
 * @see com.kingway.model.CheckUpdateUserModuleView
 * @author Hibernate Tools
 */
public class CheckUpdateUserModuleViewHome {

	private static final Log log = LogFactory
			.getLog(CheckUpdateUserModuleViewHome.class);

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

	public void persist(CheckUpdateUserModuleView transientInstance) {
		log.debug("persisting CheckUpdateUserModuleView instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(CheckUpdateUserModuleView instance) {
		log.debug("attaching dirty CheckUpdateUserModuleView instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CheckUpdateUserModuleView instance) {
		log.debug("attaching clean CheckUpdateUserModuleView instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(CheckUpdateUserModuleView persistentInstance) {
		log.debug("deleting CheckUpdateUserModuleView instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CheckUpdateUserModuleView merge(
			CheckUpdateUserModuleView detachedInstance) {
		log.debug("merging CheckUpdateUserModuleView instance");
		try {
			CheckUpdateUserModuleView result = (CheckUpdateUserModuleView) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public CheckUpdateUserModuleView findById(
			com.kingway.model.CheckUpdateUserModuleViewId id) {
		log.debug("getting CheckUpdateUserModuleView instance with id: " + id);
		try {
			CheckUpdateUserModuleView instance = (CheckUpdateUserModuleView) sessionFactory
					.getCurrentSession().get(
							"com.kingway.model.CheckUpdateUserModuleView", id);
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

	public List findByExample(CheckUpdateUserModuleView instance) {
		log.debug("finding CheckUpdateUserModuleView instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria(
					"com.kingway.model.CheckUpdateUserModuleView").add(
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
