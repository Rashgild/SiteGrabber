package ru.site.grabber.service;


import ru.site.grabber.dao.SiteGrabDao;
import ru.site.grabber.dao.SiteGrabDaoImpl;
import ru.site.grabber.entity.Site;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SiteGrabServiceImpl implements SiteGrabService {

    @Override
    public List<Site> createRootSite(List<String> urls) {
        SiteGrabDao dao = new SiteGrabDaoImpl();

        List<Site> rootSites = new ArrayList<>();
        try {
            for (String url : urls) {
                Site site = new Site();
                Document doc = Jsoup.connect(url).get();
                site.setSiteHtml(doc.toString());
                site.setSiteUrl(url);
                site.setIteration(0);
                site.setComplete(true);
                dao.save(site);
                rootSites.add(site);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootSites;
    }

    @Override
    public List<Site> getUrl(List<Site> sites, int iteration) {

        SiteGrabDao dao = new SiteGrabDaoImpl();
        List<Site> childSites = new ArrayList<>();

        for (Site site : sites) {
            try {
                Document doc = Jsoup.connect(site.getSiteUrl()).get();

                Element body = doc.body();
                Elements urls = null;
                if (body != null) {
                    urls = body.getElementsByTag("a");
                }

                for (Element el : urls) {
                    if (el.attr("href").contains("http") || el.attr("href").contains("https")) {

                        String url = el.attr("href");
                        if (!dao.isHave(url)) {
                            System.out.println(url);
                            Site childSite = new Site();
                            childSite.setParent(site);
                            childSite.setSiteUrl(url);
                            Document doc2 = Jsoup.connect(childSite.getSiteUrl()).get();
                            childSite.setSiteHtml(doc2.toString());
                            childSite.setIteration(iteration);
                            dao.save(childSite);
                            childSites.add(childSite);
                        }
                    }
                }
            } catch (HttpStatusException e){
                System.out.println("Ссылка вернула статус 404");
            } catch (UnsupportedMimeTypeException e){
                System.out.println("Похоже, тут нет html");
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return childSites;
    }
}
