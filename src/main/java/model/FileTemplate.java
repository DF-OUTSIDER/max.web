package model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class FileTemplate {
    private long id;
    private String templateName;
    private String templateHistory;
    private String templateHtml;
    private String templateXml;
    private Timestamp lastModifyTime;
    private User user;

    @Id
    @Column(name = "Id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TemplateName", nullable = true, length = 150)
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Basic
    @Column(name = "TemplateHistory", nullable = true, length = -1)
    public String getTemplateHistory() {
        return templateHistory;
    }

    public void setTemplateHistory(String templateHistory) {
        this.templateHistory = templateHistory;
    }

    @Basic
    @Column(name = "TemplateHtml", nullable = true, length = -1)
    public String getTemplateHtml() {
        return templateHtml;
    }

    public void setTemplateHtml(String templateHtml) {
        this.templateHtml = templateHtml;
    }

    @Basic
    @Column(name = "TemplateXml", nullable = true, length = -1)
    public String getTemplateXml() {
        return templateXml;
    }

    public void setTemplateXml(String templateXml) {
        this.templateXml = templateXml;
    }

    @Basic
    @Column(name = "LastModifyTime", nullable = false)
    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @ManyToOne
    @JoinColumn(name = "Creator")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileTemplate that = (FileTemplate) o;

        if (id != that.id) return false;
        if (templateName != null ? !templateName.equals(that.templateName) : that.templateName != null) return false;
        if (templateHistory != null ? !templateHistory.equals(that.templateHistory) : that.templateHistory != null)
            return false;
        if (templateHtml != null ? !templateHtml.equals(that.templateHtml) : that.templateHtml != null) return false;
        if (templateXml != null ? !templateXml.equals(that.templateXml) : that.templateXml != null) return false;
        if (lastModifyTime != null ? !lastModifyTime.equals(that.lastModifyTime) : that.lastModifyTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (templateName != null ? templateName.hashCode() : 0);
        result = 31 * result + (templateHistory != null ? templateHistory.hashCode() : 0);
        result = 31 * result + (templateHtml != null ? templateHtml.hashCode() : 0);
        result = 31 * result + (templateXml != null ? templateXml.hashCode() : 0);
        result = 31 * result + (lastModifyTime != null ? lastModifyTime.hashCode() : 0);
        return result;
    }
}
