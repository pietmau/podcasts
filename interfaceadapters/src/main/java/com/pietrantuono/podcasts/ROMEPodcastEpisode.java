package com.pietrantuono.podcasts;


import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.feed.synd.SyndPerson;

import org.jdom2.Element;

import java.util.Date;
import java.util.List;

public class ROMEPodcastEpisode implements PodcastEpisode {
    private final SyndEntry entry;

    public ROMEPodcastEpisode(SyndEntry entry) {
        this.entry = entry;
    }

    @Override
    public List<String> getSupportedFeedTypes() {
        return null;
    }

    @Override
    public WireFeed createWireFeed() {
        return null;
    }

    @Override
    public WireFeed createWireFeed(String feedType) {
        return null;
    }

    @Override
    public WireFeed originalWireFeed() {
        return null;
    }

    @Override
    public boolean isPreservingWireFeed() {
        return false;
    }

    @Override
    public String getFeedType() {
        return null;
    }

    @Override
    public void setFeedType(String feedType) {

    }

    @Override
    public String getEncoding() {
        return null;
    }

    @Override
    public void setEncoding(String encoding) {

    }

    @Override
    public String getUri() {
        return null;
    }

    @Override
    public void setUri(String uri) {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public SyndContent getTitleEx() {
        return null;
    }

    @Override
    public void setTitleEx(SyndContent title) {

    }

    @Override
    public String getLink() {
        return null;
    }

    @Override
    public void setLink(String link) {

    }

    @Override
    public List<SyndLink> getLinks() {
        return null;
    }

    @Override
    public void setLinks(List<SyndLink> links) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public SyndContent getDescriptionEx() {
        return null;
    }

    @Override
    public void setDescriptionEx(SyndContent description) {

    }

    @Override
    public Date getPublishedDate() {
        return null;
    }

    @Override
    public void setPublishedDate(Date publishedDate) {

    }

    @Override
    public List<SyndPerson> getAuthors() {
        return null;
    }

    @Override
    public void setAuthors(List<SyndPerson> authors) {

    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public void setAuthor(String author) {

    }

    @Override
    public List<SyndPerson> getContributors() {
        return null;
    }

    @Override
    public void setContributors(List<SyndPerson> contributors) {

    }

    @Override
    public String getCopyright() {
        return null;
    }

    @Override
    public void setCopyright(String copyright) {

    }

    @Override
    public SyndImage getImage() {
        return null;
    }

    @Override
    public void setImage(SyndImage image) {

    }

    @Override
    public List<SyndCategory> getCategories() {
        return null;
    }

    @Override
    public void setCategories(List<SyndCategory> categories) {

    }

    @Override
    public List<SyndEntry> getEntries() {
        return null;
    }

    @Override
    public void setEntries(List<SyndEntry> entries) {

    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public void setLanguage(String language) {

    }

    @Override
    public Module getModule(String uri) {
        return null;
    }

    @Override
    public List<Module> getModules() {
        return null;
    }

    @Override
    public void setModules(List<Module> modules) {

    }

    @Override
    public List<Element> getForeignMarkup() {
        return null;
    }

    @Override
    public void setForeignMarkup(List<Element> foreignMarkup) {

    }

    @Override
    public String getDocs() {
        return null;
    }

    @Override
    public void setDocs(String docs) {

    }

    @Override
    public String getGenerator() {
        return null;
    }

    @Override
    public void setGenerator(String generator) {

    }

    @Override
    public String getManagingEditor() {
        return null;
    }

    @Override
    public void setManagingEditor(String managingEditor) {

    }

    @Override
    public String getWebMaster() {
        return null;
    }

    @Override
    public void setWebMaster(String webMaster) {

    }

    @Override
    public String getStyleSheet() {
        return null;
    }

    @Override
    public void setStyleSheet(String styleSheet) {

    }
}
