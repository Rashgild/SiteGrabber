package ru.site.grabber.dao;

import ru.site.grabber.entity.Site;


public interface SiteGrabDao {

    void save(Site site);

    Boolean isHave(String link);
}
