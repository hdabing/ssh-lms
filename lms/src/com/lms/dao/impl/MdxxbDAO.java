package com.lms.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.lms.domain.Mdxxb;

/**
 * A data access object (DAO) providing persistence and search support for Mdxxb
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.lms.domain.Mdxxb
 * @author MyEclipse Persistence Tools
 */
public class MdxxbDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(MdxxbDAO.class);
	// property constants
	public static final String MDMC = "mdmc";
	public static final String MDJL = "mdjl";
	public static final String LXDH = "lxdh";
	public static final String DZ = "dz";

	protected void initDao() {
		// do nothing
	}

	public void save(Mdxxb transientInstance) {
		log.debug("saving Mdxxb instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Mdxxb persistentInstance) {
		log.debug("deleting Mdxxb instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Mdxxb findById(java.lang.Integer id) {
		log.debug("getting Mdxxb instance with id: " + id);
		try {
			Mdxxb instance = (Mdxxb) getHibernateTemplate().get(
					"com.lms.domain.Mdxxb", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Mdxxb instance) {
		log.debug("finding Mdxxb instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Mdxxb instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Mdxxb as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMdmc(Object mdmc) {
		return findByProperty(MDMC, mdmc);
	}

	public List findByMdjl(Object mdjl) {
		return findByProperty(MDJL, mdjl);
	}

	public List findByLxdh(Object lxdh) {
		return findByProperty(LXDH, lxdh);
	}

	public List findByDz(Object dz) {
		return findByProperty(DZ, dz);
	}

	public List findAll() {
		log.debug("finding all Mdxxb instances");
		try {
			String queryString = "from Mdxxb";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	/** 
	* �������detached״̬�Ķ�������Ը��Ƶ��־û������У������ظó־û����� 
	* �����session��û�й����ĳ־û����󣬼���һ��������������δ���棬����һ����������Ϊ�־ö��󷵻أ����������Ȼ����detached״̬�� 
	*/ 
	public Mdxxb merge(Mdxxb detachedInstance) {
		log.debug("merging Mdxxb instance");
		try {
			Mdxxb result = (Mdxxb) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	/** 
	* ������Ķ���־û������档 
	* �������δ���棨Transient״̬��������save�������档��������ѱ��棨Detached״̬��������update������������Session���¹����� 
	*/ 
	public void attachDirty(Mdxxb instance) {
		log.debug("attaching dirty Mdxxb instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	/** 
	* ������Ķ���״̬����ΪTransient״̬ 
	*/  
	public void attachClean(Mdxxb instance) {
		log.debug("attaching clean Mdxxb instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MdxxbDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MdxxbDAO) ctx.getBean("MdxxbDAO");
	}
}