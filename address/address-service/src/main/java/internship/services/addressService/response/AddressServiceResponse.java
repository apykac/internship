package internship.services.addressService.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddressServiceResponse")
public class AddressServiceResponse {
    private String addressValidator;
    private String addressDAO;
    private String userDAO;
    private String DBConnector;

    public String getAddressValidator() {
        return addressValidator;
    }

    public void setAddressValidator(String addressValidator) {
        this.addressValidator = addressValidator;
    }

    public String getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(String addressDAO) {
        this.addressDAO = addressDAO;
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
