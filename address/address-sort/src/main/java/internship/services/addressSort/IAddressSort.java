package internship.services.addressSort;

import internship.models.addressModel.Address;

import java.util.List;

public interface IAddressSort {


    /**
     * Сортирует список адресов, в алфавитном порядке по регионам.
     *
     * @param address Адреса для сортировки
     * @return Возвращает отсортированный список адресов
     */
    List<Address> sort(List<Address> address);
}
