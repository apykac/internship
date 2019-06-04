package internship.dao.addressDAO;

import internship.models.addressModel.Address;
import internship.models.addressModel.Addresses;

public interface AddressDAO {

    /**
     * Метод ищет в БД адрес по id
     *
     * @param id адреса
     * @return Address если существует в БД адрес с данным id, иначе null
     */
    Address findAddressById(Long id);

    Addresses findAddressesByUserPassport(Long passport);

    /**
     * Меняет данные о адресе у указаного id
     * @param id адреса
     * @param address данные о адресе
     * @return Address если существует в БД с данным id, иначе null
     */
    Address updateAddress(Long id, Address address);

    /**
     * Сохраняет адрес в базу данных
     * @param address адреса
     * @return Address  с новым id или null, если неудалось подключиться к базе данных возвращает null
     */
    Address createAddress(Address address);

    /**
     * Удаляет адрес из базы данных по id
     * @param id адреса
     */
    void removeAddress(Long id);

    /**
     * Проверяет удалось ли подключиться к бандлу раздающей connection к базе данных
     * @return true если удалось подключиться, иначе false
     */
    boolean isConnectorUp();
}
