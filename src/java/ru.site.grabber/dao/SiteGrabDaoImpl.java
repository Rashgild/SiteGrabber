package ru.site.grabber.dao;

import ru.site.grabber.entity.Site;
import org.hibernate.Session;

public class SiteGrabDaoImpl extends AbstractDao implements SiteGrabDao {

    @Override
    public void save(Site site) {
        persist(site);
    }

    @Override
    public Boolean isHave(String link) {

        Session session = getSession();
        session.beginTransaction();
         if(session
                 .createQuery("from Site where siteUrl=:siteUrl")
                 .setParameter("siteUrl",link).list().size() > 0){
             session.getTransaction().commit();
             return true;
         }
        return false;
    }
}
