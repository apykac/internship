package internship.services.addressService.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Error")
public class ErrorResponse {

    public static final String NO_ADDRESS_WITH_SUCH_ID = "No address with specified id found.";

    private String message = "Unknown error occurred.";

    public ErrorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }
}
