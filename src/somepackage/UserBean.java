package somepackage;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.sql.*;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean {
    private User currentUser;
    private String username;
    private String password;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage("Username and password are required"));
            return null;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:derby:/Users/yichengxu/DerbyDB;create=true")) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        currentUser = new User();
                        currentUser.setId(rs.getString("id"));
                        currentUser.setUsername(rs.getString("username"));
                        currentUser.setPassword(rs.getString("password"));
                        currentUser.setFullname(rs.getString("fullname"));
                        currentUser.setEmail(rs.getString("email"));
                        return "portfolio?faces-redirect=true";
                    } else {
                        context.addMessage(null, new FacesMessage("Invalid username or password"));
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage("Login failed"));
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
}