package internship.validators.addressValidator.response;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class BadAddressListResponce {
    private List<BadAddressResponse> badAddressResponses=new ArrayList<>();

    public List<BadAddressResponse> getBadAddressResponses() {
        return badAddressResponses;
    }

    @XmlElement(name = "BadAddressResponse")
    public void setBadAddressResponses(List<BadAddressResponse> badAddressResponses) {
        this.badAddressResponses = badAddressResponses;
    }

    public void addBadAddressResponse(BadAddressResponse badAddressResponse){
        badAddressResponses.add(badAddressResponse);
    }
}
