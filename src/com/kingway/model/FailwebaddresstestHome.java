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
 * Home object for domain model class Failwebaddresstest.
 * @see com.kingway.model.Failwebaddresstest
 * @author Hibernate Tools
 */
public class FailwebaddresstestHome {

	private static final Log log = LogFactory
			.getLog(FailwebaddresstestHome.class);

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

	public void persist(Failwebaddresstest transientInstance) {
		log.debug("persisting Failwebaddresstest instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Failwebaddresstest instance) {
		log.debug("attaching dirty Failwebaddresstest instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Failwebaddresstest instance) {
		log.debug("attaching clean Failwebaddresstest instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Failwebaddresstest persistentInstance) {
		log.debug("deleting Failwebaddresstest instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Failwebaddresstest merge(Failwebaddresstest detachedInstance) {
		log.debug("merging Failwebaddresstest instance");
		try {
			Failwebaddresstest result = (Failwebaddresstest) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Failwebaddresstest findById(com.kingway.model.FailwebaddresstestId id) {
		log.debug("getting Failwebaddresstest instance with id: " + id);
		try {
			Failwebaddresstest instance = (Failwebaddresstest) sessionFactory
					.getCurrentSession().get(
							"com.kingway.model.Failwebaddresstest", id);
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

	public List findByExample(Failwebaddresstest instance) {
		log.debug("finding Failwebaddresstest instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria(
					"com.kingway.model.Failwebaddresstest").add(
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
