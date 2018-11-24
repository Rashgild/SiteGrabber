package ru.site.grabber.dao;

import ru.site.grabber.entity.Site;

import java.util.List;


public interface SiteGrabDao {

    void save(Site site);

    void saveList(List<Site> sites);

    Boolean isHave(String link);

    List<Site> getByIteration(Integer iteration);

    void update(Site site);
}
