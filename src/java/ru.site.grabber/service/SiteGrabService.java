package ru.site.grabber.service;

import ru.site.grabber.entity.Site;

import java.util.List;

/**
 * Created by Rashgild on 14.11.2018.
 */
public interface SiteGrabService {

    List<Site> createRootSite(List<String> urls);

    List<Site> getUrl(List<Site> sites, int iteration);
}
