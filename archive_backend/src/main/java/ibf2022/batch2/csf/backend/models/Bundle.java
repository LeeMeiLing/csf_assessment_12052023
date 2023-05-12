package ibf2022.batch2.csf.backend.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

public class Bundle {
    
    private String bundleId;
    private Date date;
    private String title;
    private String name;
    private String comments;
    private List<String> urls = new ArrayList<>();
    
    public String getBundleId() {
        return bundleId;
    }
    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public List<String> getUrls() {
        return urls;
    }
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    @Override
    public String toString() {
        return "Bundle [bundleId=" + bundleId + ", date=" + date + ", title=" + title + ", name=" + name + ", comments="
                + comments + ", urls=" + urls + "]";
    }

    public Document toDocument(){

        Document d = new Document();
        d.put("bundleId", this.getBundleId());
        d.put("date", this.getDate());
        d.put("title", this.getTitle());
        d.put("name", this.getName());
        d.put("comments", this.getComments());
        d.put("urls", this.getUrls());
        
        return d;
    }
}
