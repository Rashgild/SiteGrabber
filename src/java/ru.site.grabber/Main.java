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

        for (Site site : sites) {
            System.out.println("url =" + site.getSiteUrl());
            Threader thread = new Threader(site);    //Создание потока
            thread.start();
        }
    }

    static class Threader extends Thread {
        SiteGrabService service = new SiteGrabServiceImpl();
        Site mySite;

        Threader(Site site) {
            mySite = site;
        }

        @Override
        public void run()    //Этот метод будет выполнен в побочном потоке
        {
            List<Site> childs = service.getUrlMultithread(mySite, 1);
            for (int i = 2; i < 5; i++) {
                childs = service.getUrl(childs, i);
            }
        }
    }
}
