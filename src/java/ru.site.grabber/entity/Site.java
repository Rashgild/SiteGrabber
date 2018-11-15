package ru.site.grabber.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;


@Entity
@Table(name = "site", schema = "public" ,  indexes = { @Index(name="siteUrlIndex", columnList = "siteUrl")})
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "siteUrl", length = 1000)
    private String siteUrl;

    @Basic
    @Column(name="siteHtml", columnDefinition="TEXT")
    private String siteHtml;

    @OneToOne
    private Site parent;

    @Basic
    @Column(name = "iteration")
    private Integer iteration;

    @Basic
    @Column(name = "complete")
    private Boolean complete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSiteHtml() {
        return siteHtml;
    }

    public void setSiteHtml(String siteHtml) {
        this.siteHtml = siteHtml;
    }

    public Site getParent() {
        return parent;
    }

    public void setParent(Site parent) {
        this.parent = parent;
    }

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    public Boolean isComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
