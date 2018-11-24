package ru.site.grabber.service;


import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.site.grabber.dao.SiteGrabDao;
import ru.site.grabber.dao.SiteGrabDaoImpl;
import ru.site.grabber.entity.Site;

import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
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
                dao.save(site);
                rootSites.add(site);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootSites;
    }

    @Override
    public List<Site> getUrl(List<Site> parents, int iteration, int thNum) {
        List<Site> childs = new ArrayList<>();
        for (Site parent : parents) {
            childs.addAll(getUrlMultithread(parent, iteration, thNum));
        }
        return childs;
    }

    @Override
    public List<Site> getUrlMultithread(Site parent, int iteration, int thNum) {
        List<Site> childSites = parseUrls(iteration, parent, thNum);
        return childSites;
    }

    private List<Site> parseUrls(int iteration, Site parent, int thNum) {
        SiteGrabDao dao = new SiteGrabDaoImpl();
        List<Site> childSites = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(parent.getSiteUrl()).get();
            Element body = doc.body();
            Elements urls = null;
            if (body != null) {
                urls = body.getElementsByTag("a");
            }
            for (Element el : urls) {
                if (el.attr("href").contains("http") || el.attr("href").contains("https")) {

                    String url = el.attr("href");
                    if (!dao.isHave(url)) {
                        System.out.println(thNum + ") " + url);
                        Site childSite = new Site();
                        childSite.setParent(parent);
                        childSite.setSiteUrl(url);
                        Document doc2 = Jsoup.connect(childSite.getSiteUrl()).get();
                        childSite.setSiteHtml(doc2.toString());
                        childSite.setIteration(iteration);
                        childSites.add(childSite);
                    }
                }
            }
        } catch (UnknownHostException | SocketTimeoutException | HttpStatusException | ConnectException e) {
            System.out.println(thNum + ") Ссылка вернула статус 404");
        } catch (UnsupportedMimeTypeException e) {
            System.out.println(thNum + ") Похоже, тут нет html");
        } catch (SSLHandshakeException e) {
            System.out.println(thNum + ") Проблемы с ssl");
        } catch (IllegalArgumentException e) {
            System.out.println(thNum + ") Плохой линк");
        } catch (Exception e) {
            e.printStackTrace();
        }

        parent.setComplete(true);
        dao.update(parent);
        dao.saveList(childSites);
        return childSites;
    }
}
