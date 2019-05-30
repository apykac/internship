package internship.models.addressModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Addresses {
    private List<Address> addresses;

    public List<Address> getAddresses() {
        return addresses;
    }

    @XmlElement(name = "Address")
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
