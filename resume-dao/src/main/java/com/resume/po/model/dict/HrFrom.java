package com.resume.po.model.dict;

public class HrFrom {
	
    private Long fromId;

    private String name;

    private String webUrl;

    private String searchUrl;

    private String matchPage;

    private String matchLink;

    private Long addTime;

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl == null ? null : webUrl.trim();
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl == null ? null : searchUrl.trim();
    }

    public String getMatchPage() {
        return matchPage;
    }

    public void setMatchPage(String matchPage) {
        this.matchPage = matchPage == null ? null : matchPage.trim();
    }

    public String getMatchLink() {
        return matchLink;
    }

    public void setMatchLink(String matchLink) {
        this.matchLink = matchLink == null ? null : matchLink.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}