package internship.validators.addressValidator.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "ValidationResult")
public class ValidationResult {
    private boolean isValid = true;

    @XmlElementWrapper(name = "errors")
    @XmlElement(name = "error")
    private List<String> errors = new LinkedList<>();

    public void addError(String error){
        isValid = false;
        errors.add(error);
    }

    public List<String> getErrors(){return errors;}

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}

