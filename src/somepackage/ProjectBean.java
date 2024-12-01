package somepackage;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ProjectBean is a managed bean that handles the business logic for managing projects.
 * It is session scoped, meaning it will retain its state across multiple HTTP requests
 * in the same user session.
 */
@ManagedBean(name = "projectBean")
@SessionScoped
public class ProjectBean {

    // List to store project details
    private List<Project> projects;

    // Selected project for viewing or editing
    private Project selectedProject;

    /**
     * Initializes the bean and loads projects from the database.
     * This method is called after the bean's construction.
     */
    @PostConstruct
    public void init() {
        projects = new ArrayList<>();
        loadProjectsFromDatabase();
    }

    /**
     * Returns the list of projects.
     *
     * @return List of projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Returns the currently selected project.
     *
     * @return The selected project
     */
    public Project getSelectedProject() {
        return selectedProject;
    }

    /**
     * Sets the selected project.
     *
     * @param selectedProject The project to select
     */
    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    /**
     * Loads projects from the database into the projects list.
     * This method connects to the database, executes a query to retrieve projects,
     * and populates the projects list with the results.
     */
    private void loadProjectsFromDatabase() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Explicitly load Derby driver class
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby:/usr/local/DerbyDB;create=true");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM projects");

            // Iterate over the result set and populate the projects list
            while (rs.next()) {
                Project project = new Project(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("summary"),
                        rs.getString("description"),
                        Arrays.asList(rs.getString("keywords").split(",")),
                        rs.getString("type"),
                        Arrays.asList(rs.getString("collaborators").split(",")),
                        rs.getString("link"),
                        rs.getString("learned"),
                        rs.getString("accomplishments"),
                        Arrays.asList(rs.getString("enhancements").split(",")),
                        rs.getString("downloadLink")
                );
                projects.add(project);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Ensure resources are closed properly
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads the details of a specific project based on its ID.
     *
     * @param projectId The ID of the project to load
     */
    public void loadProjectDetails(String projectId) {
        for (Project project : projects) {
            if (project.getId().equals(projectId)) {
                this.selectedProject = project;
                break;
            }
        }
    }

    /**
     * Resets the selected project to null.
     * This is useful for hiding project details.
     */
    public void resetSelectedProject() {
        this.selectedProject = null;
    }

    /**
     * Loads a project based on the projectId parameter from the request.
     *
     * @return Navigation outcome string for JSF navigation
     */
    public String loadProject() {
        String projectId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("projectId");
        if (projectId != null) {
            loadProjectDetails(projectId);
            return "project"; // Return navigation result
        }
        return null;
    }

    /**
     * Saves the selected project's details to the database.
     *
     * @return Navigation outcome string for JSF navigation
     */
    public String saveProject() {
        FacesContext context = FacesContext.getCurrentInstance();

        // Validate that all required fields are filled
        if (selectedProject.getTitle() == null || selectedProject.getTitle().isEmpty() ||
                selectedProject.getSummary() == null || selectedProject.getSummary().isEmpty() ||
                selectedProject.getDescription() == null || selectedProject.getDescription().isEmpty()) {
            FacesMessage message = new FacesMessage("All fields are required.");
            context.addMessage(null, message);
            return null;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:derby:/usr/local/DerbyDB;create=true");
             PreparedStatement pstmt = conn.prepareStatement("UPDATE projects SET title=?, summary=?, description=?, keywords=?, type=?, collaborators=?, link=?, learned=?, accomplishments=?, enhancements=?, downloadLink=? WHERE id=?")) {

            // Set parameters for the prepared statement
            pstmt.setString(1, selectedProject.getTitle());
            pstmt.setString(2, selectedProject.getSummary());
            pstmt.setString(3, selectedProject.getDescription());
            pstmt.setString(4, String.join(",", selectedProject.getKeywords()));
            pstmt.setString(5, selectedProject.getType());
            pstmt.setString(6, String.join(",", selectedProject.getCollaborators()));
            pstmt.setString(7, selectedProject.getLink());
            pstmt.setString(8, selectedProject.getLearned());
            pstmt.setString(9, selectedProject.getAccomplishments());
            pstmt.setString(10, String.join(",", selectedProject.getEnhancements()));
            pstmt.setString(11, selectedProject.getDownloadLink());
            pstmt.setString(12, selectedProject.getId());

            pstmt.executeUpdate(); // Execute update query
        } catch (SQLException e) {
            e.printStackTrace();
            FacesMessage message = new FacesMessage("Failed to save project data.");
            context.addMessage(null, message);
            return null;
        }

        FacesMessage message = new FacesMessage("Project data saved.");
        context.addMessage(null, message);
        return "project?faces-redirect=true&projectId=" + selectedProject.getId();
    }
}