package somepackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a project with various details such as title, summary, description,
 * keywords, type, collaborators, link, learned skills, accomplishments, and future enhancements.
 */
public class Project {
    private String id;
    private String title;
    private String summary;
    private String description;
    private List<String> keywords;
    private String type;
    private List<String> collaborators;
    private String link;
    private String learned;
    private String accomplishments;
    private List<String> enhancements;
    private String downloadLink; // New field

    /**
     * Constructor to initialize a Project object with the provided details.
     *
     * @param id             The unique identifier of the project.
     * @param title          The title of the project.
     * @param summary        A brief summary of the project.
     * @param description    A detailed description of the project.
     * @param keywords       A list of keywords associated with the project.
     * @param type           The type or category of the project.
     * @param collaborators  A list of collaborators involved in the project.
     * @param link           A link to the project's repository or website.
     * @param learned        The skills or knowledge gained from the project.
     * @param accomplishments The accomplishments achieved in the project.
     * @param enhancements   A list of future enhancements planned for the project.
     */
    public Project(String id, String title, String summary, String description, List<String> keywords, String type, List<String> collaborators, String link, String learned, String accomplishments, List<String> enhancements, String downloadLink) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.keywords = keywords;
        this.type = type;
        this.collaborators = collaborators;
        this.link = link;
        this.learned = learned;
        this.accomplishments = accomplishments;
        this.enhancements = enhancements;
        this.downloadLink = downloadLink;
    }

    // Add getter and setter for downloadLink
    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<String> collaborators) {
        this.collaborators = collaborators;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLearned() {
        return learned;
    }

    public void setLearned(String learned) {
        this.learned = learned;
    }

    public String getAccomplishments() {
        return accomplishments;
    }

    public void setAccomplishments(String accomplishments) {
        this.accomplishments = accomplishments;
    }

    public List<String> getEnhancements() {
        return enhancements;
    }

    public void setEnhancements(List<String> enhancements) {
        this.enhancements = enhancements;
    }
}