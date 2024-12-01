package somepackage;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Managed bean for handling keyword-related operations in a JSF application.
 * This bean allows adding and removing keywords for a selected project.
 */
@ManagedBean(name = "keywordBean")
@SessionScoped
public class KeywordBean {
    private String newKeyword; // Stores the new keyword to be added

    // Getter for newKeyword
    public String getNewKeyword() {
        return newKeyword;
    }

    // Setter for newKeyword
    public void setNewKeyword(String newKeyword) {
        this.newKeyword = newKeyword;
    }

    /**
     * Adds a new keyword to the selected project's keyword list.
     * Displays an error message if the keyword is empty or a duplicate.
     */
    public void addKeyword() {
        FacesContext context = FacesContext.getCurrentInstance();
        ProjectBean projectBean = context.getApplication().evaluateExpressionGet(context, "#{projectBean}", ProjectBean.class);

        // Check if the new keyword is empty
        if (newKeyword == null || newKeyword.trim().isEmpty()) {
            FacesMessage message = new FacesMessage("Keyword cannot be empty.");
            context.addMessage("editForm:new-keyword", message);
            return;
        }

        List<String> keywords = projectBean.getSelectedProject().getKeywords();

        // Check if the new keyword is a duplicate
        if (keywords.contains(newKeyword)) {
            FacesMessage message = new FacesMessage("Duplicate keyword cannot be added.");
            context.addMessage("editForm:new-keyword", message);
            return;
        }

        // Add the new keyword to the list
        keywords.add(newKeyword);
        newKeyword = ""; // Clear the new keyword input
    }

    /**
     * Removes a keyword from the selected project's keyword list.
     * Displays an error message if attempting to remove the last remaining keyword.
     *
     * @param keyword the keyword to be removed
     */
    public void removeKeyword(String keyword) {
        FacesContext context = FacesContext.getCurrentInstance();
        ProjectBean projectBean = context.getApplication().evaluateExpressionGet(context, "#{projectBean}", ProjectBean.class);

        List<String> keywords = projectBean.getSelectedProject().getKeywords();

        // Check if there is only one keyword remaining
        if (keywords.size() == 1) {
            FacesMessage message = new FacesMessage("At least one keyword is required.");
            context.addMessage("editForm:keywords", message);
            return;
        }

        // Remove the keyword from the list
        keywords.remove(keyword);
    }
}