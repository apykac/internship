package internship.services.addressSort;

import internship.models.addressModel.Address;

import java.util.List;

public interface IAddressSort {

    // TODO: Написать по какому принципу сортируются адреса.
    /**
     * Отсортировать список адресов.
     * @param address Адреса для сортировки
     * @return Возвращает отсортированный список адресов
     */
    List<Address> sort(List<Address> address);
}
