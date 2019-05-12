package com.vsk.task.utils;

import com.vsk.task.dao.UserDao;
import com.vsk.task.model.Address;
import com.vsk.task.model.BadAddressResponse;

public class AddressValidator {
    private Address address;
    UserDao userDao = new UserDao();
    BadAddressResponse badAddressResponse;

    public AddressValidator(Address address) {
        this.address = address;
    }

    private boolean isExitUserId(long userId) {
        if (userDao.getUser(userId) != null) {
            return true;
        }
        badAddressResponse.setUserId("Ошибка, несуществующий Id пользователя");
        return false;
    }

    private boolean isCountryValid(String country) {
        if (country == null) {
            badAddressResponse.setCountry("Ошибка, поля country, city, street не должны быть пустые");
            return false;
        }
        String tempCountry = country.trim();
        if (tempCountry.length() < 3) {
            badAddressResponse.setCountry("Ошибка, поля country, city, street должны быть больше 3 символов");
            return false;
        }
        for (int i = 0; i < tempCountry.length(); i++) {
            if (Character.isDigit(tempCountry.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        badAddressResponse = new BadAddressResponse();
        if (isExitUserId(address.getUserId()) & isCountryValid(address.getCountry())) {
            return true;
        }
        return false;
    }

    public BadAddressResponse getMessageError() {
        return badAddressResponse;
    }
}
