package com.kingway.model;

// Generated 2011-3-12 23:18:02 by Hibernate Tools 3.3.0.GA

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class ModuleHistoyNumView.
 * @see com.kingway.model.ModuleHistoyNumView
 * @author Hibernate Tools
 */
public class ModuleHistoyNumViewHome {

	private static final Log log = LogFactory
			.getLog(ModuleHistoyNumViewHome.class);

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

	public void persist(ModuleHistoyNumView transientInstance) {
		log.debug("persisting ModuleHistoyNumView instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ModuleHistoyNumView instance) {
		log.debug("attaching dirty ModuleHistoyNumView instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ModuleHistoyNumView instance) {
		log.debug("attaching clean ModuleHistoyNumView instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ModuleHistoyNumView persistentInstance) {
		log.debug("deleting ModuleHistoyNumView instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ModuleHistoyNumView merge(ModuleHistoyNumView detachedInstance) {
		log.debug("merging ModuleHistoyNumView instance");
		try {
			ModuleHistoyNumView result = (ModuleHistoyNumView) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ModuleHistoyNumView findById(
			com.kingway.model.ModuleHistoyNumViewId id) {
		log.debug("getting ModuleHistoyNumView instance with id: " + id);
		try {
			ModuleHistoyNumView instance = (ModuleHistoyNumView) sessionFactory
					.getCurrentSession().get(
							"com.kingway.model.ModuleHistoyNumView", id);
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

	public List findByExample(ModuleHistoyNumView instance) {
		log.debug("finding ModuleHistoyNumView instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria(
					"com.kingway.model.ModuleHistoyNumView").add(
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
