package ru.site.grabber.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import ru.site.grabber.entity.Site;

import java.util.List;

public class SiteGrabDaoImpl extends AbstractDao implements SiteGrabDao {

    @Override
    public void save(Site site) {
        persist(site);
    }

    @Override
    public void saveList(List<Site> sites) {
        for (Site site : sites) {
            persist(site);
        }
    }

    @Override
    public Boolean isHave(String link) {

        Session session = getSession();
        session.beginTransaction();
        if (session
                .createQuery("from Site where siteUrl=:siteUrl")
                .setParameter("siteUrl", link).list().size() > 0) {
            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    @Override
    public List<Site> getByIteration(Integer iteration) {
        Session session = getSession();
        session.beginTransaction();

        Query query = session.createQuery("from Site where iteration=:iteration and (complete = false or complete is null)")
                .setParameter("iteration", iteration);
        session.getTransaction().commit();
        return query.list();
    }

    @Override
    public void update(Site site) {
        Session session = getSession();
        session.beginTransaction();
        session.update(site);
        session.getTransaction().commit();
    }

}
