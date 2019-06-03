package internship.validators.userValidator.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Error")
public class ValidationError {
    private String cause;
    private String message;

    public ValidationError() {
    }

    /**
     * @param cause   Место возникновения ошибки
     * @param message Сообщение об ошибке
     */
    public ValidationError(String cause, String message) {
        this.cause = cause;
        this.message = message;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
