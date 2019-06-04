package internship.services.userService.response;


import internship.models.userModel.Users;
import internship.validators.userValidator.models.ValidationResult;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FilterResponse")
public class FilterResponse {
    private ValidationResult validationResult;
    private Users sorted;

    public FilterResponse() {
    }

    public FilterResponse(ValidationResult validationResult, Users sorted) {
        this.validationResult = validationResult;
        this.sorted = sorted;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    public Users getSorted() {
        return sorted;
    }

    public void setSorted(Users sorted) {
        this.sorted = sorted;
    }
}
