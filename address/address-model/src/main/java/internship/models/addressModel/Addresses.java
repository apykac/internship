package internship.models.addressModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Addresses")
public class Addresses {

    private List<Address> addressList;

    public List<Address> getAddressList() {
        return addressList;
    }

    @XmlElement(name = "Address")
    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
