package ru.site.grabber.dao;

import ru.site.grabber.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AbstractDao {

    private HibernateSessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void persist(Object entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.save(entity);
        trans.commit();
    }

    public void delete(Object entity) {
        Session session = sessionFactory.getCurrentSession();
        Transaction trans = session.beginTransaction();
        session.delete(entity);
        trans.commit();
    }
}
