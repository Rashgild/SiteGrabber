package ru.site.grabber.service;

import ru.site.grabber.entity.Site;

import java.util.List;

public interface SiteGrabService {

    List<Site> createRootSite(List<String> urls);

    List<Site> getUrl(List<Site> sites, int iteration,int thNum);

    List<Site> getUrlMultithread(Site site, int iteration,int thNum);
}
