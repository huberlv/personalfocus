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
 * Home object for domain model class ModuleNeedToBeUpdateView.
 * @see com.kingway.model.ModuleNeedToBeUpdateView
 * @author Hibernate Tools
 */
public class ModuleNeedToBeUpdateViewHome {

	private static final Log log = LogFactory
			.getLog(ModuleNeedToBeUpdateViewHome.class);

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

	public void persist(ModuleNeedToBeUpdateView transientInstance) {
		log.debug("persisting ModuleNeedToBeUpdateView instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ModuleNeedToBeUpdateView instance) {
		log.debug("attaching dirty ModuleNeedToBeUpdateView instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ModuleNeedToBeUpdateView instance) {
		log.debug("attaching clean ModuleNeedToBeUpdateView instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ModuleNeedToBeUpdateView persistentInstance) {
		log.debug("deleting ModuleNeedToBeUpdateView instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ModuleNeedToBeUpdateView merge(
			ModuleNeedToBeUpdateView detachedInstance) {
		log.debug("merging ModuleNeedToBeUpdateView instance");
		try {
			ModuleNeedToBeUpdateView result = (ModuleNeedToBeUpdateView) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ModuleNeedToBeUpdateView findById(
			com.kingway.model.ModuleNeedToBeUpdateViewId id) {
		log.debug("getting ModuleNeedToBeUpdateView instance with id: " + id);
		try {
			ModuleNeedToBeUpdateView instance = (ModuleNeedToBeUpdateView) sessionFactory
					.getCurrentSession().get(
							"com.kingway.model.ModuleNeedToBeUpdateView", id);
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

	public List findByExample(ModuleNeedToBeUpdateView instance) {
		log.debug("finding ModuleNeedToBeUpdateView instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria(
					"com.kingway.model.ModuleNeedToBeUpdateView").add(
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
