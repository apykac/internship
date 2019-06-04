package internship.services.addressService.response;

import internship.models.addressModel.Addresses;
import internship.validators.addressValidator.models.ValidationResult;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SortResponse")
public class SortResponse {
    private ValidationResult validationResult;
    private Addresses sorted;

    public SortResponse() {
    }

    public SortResponse(ValidationResult validationResult, Addresses sorted) {
        this.validationResult = validationResult;
        this.sorted = sorted;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult validationResult) {
        this.validationResult = validationResult;
    }

    public Addresses getSorted() {
        return sorted;
    }

    public void setSorted(Addresses sorted) {
        this.sorted = sorted;
    }
}
