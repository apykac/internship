package internship.models.addressModel;

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
    private List<ValidationError> errors = new LinkedList<>();

    public void addError(ValidationError error){
        isValid = false;
        errors.add(error);
    }

    public List<ValidationError> getErrors(){return errors;}

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}

