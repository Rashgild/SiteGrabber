package ru.site.grabber;

import ru.site.grabber.entity.Site;
import ru.site.grabber.service.SiteGrabService;
import ru.site.grabber.service.SiteGrabServiceImpl;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        SiteGrabService service = new SiteGrabServiceImpl();
        List<String> links = new ArrayList<>();
        links.add("http://www.amokb.ru/");
        links.add("http://www.astu.org/");
        List<Site> sites = service.createRootSite(links);

        for(Site site: sites){
            System.out.println("url ="+ site.getSiteUrl());
        }

        List<Site> childs = service.getUrl(sites, 1);
        for (int i = 2; i < 5; i++) {
            childs = service.getUrl(childs, i);
        }

    }
}
