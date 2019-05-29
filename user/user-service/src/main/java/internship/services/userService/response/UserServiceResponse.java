package internship.services.userService.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UserServiceResponse")
public class UserServiceResponse {
    private String userValidator;
    private String userDAO;
    private String DBConnector;

    public String getUserValidator() {
        return userValidator;
    }

    public void setUserValidator(String userValidator) {
        this.userValidator = userValidator;
    }

    public String getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(String userDAO) {
        this.userDAO = userDAO;
    }

    public String getDBConnector() {
        return DBConnector;
    }

    public void setDBConnector(String DBConnector) {
        this.DBConnector = DBConnector;
    }
}
