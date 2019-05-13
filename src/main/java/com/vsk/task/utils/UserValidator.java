package com.vsk.task.utils;

import com.vsk.task.model.dto.UserDTO;
import com.vsk.task.response.BadUserResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserValidator {
    private static final int MAX_SIZE_NAME = 50;
    private static final int MIN_SIZE_NAME = 2;
    private static final int CURRENT_YEAR = 2019;
    private final static String DATE_FORMAT = "dd-MM-yyyy";
    private UserDTO user;

    public UserValidator(UserDTO user) {
        this.user = user;
    }

    private BadUserResponse badUserResponse = new BadUserResponse();

    private boolean isNameValid(String name) {
        if (name == null) {
            badUserResponse.setNsp("В полях name, surname, patronymic присутствует пустое значение");
            return false;
        }
        String tempName = name.trim();
        if (tempName.length() < MIN_SIZE_NAME || tempName.length() > MAX_SIZE_NAME) {
            badUserResponse.setNsp(String.format("Слишком маленькая или большая длина name, surname, patronymic (Допустимый размер от %d до %d)", MIN_SIZE_NAME, MAX_SIZE_NAME));
            return false;
        }
        for (int i = 0; i < tempName.length(); i++) {
            if (!Character.isLetter(tempName.charAt(i))) {
                badUserResponse.setNsp("В полях name, surname, patronymic встречаются символы отличные от букв");
                return false;
            }
        }
        return true;
    }

    private boolean isDateBirthdayValid(String birthday) {
        if (birthday == null) {
            badUserResponse.setBirthday("Введено пустое значение для даты рождения");
            return false;
        }
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(birthday);
        } catch (ParseException e) {
            badUserResponse.setBirthday("Введен неправильный формат даты(формат должен быть dd-MM-yyyy) или не существующая дата");
            return false;
        }
        String[] a = birthday.split("-");
        int yearOfBirth = Integer.parseInt(a[2]);
        if (yearOfBirth <= 1900 || yearOfBirth > CURRENT_YEAR) {
            badUserResponse.setBirthday("Введенная некорректная дата");
            return false;
        }
        return true;
    }

    private boolean isPassportNumberValid(String passport) {
        if (passport == null) {
            badUserResponse.setPassportNumber("Пустое значение паспортных данных");
            return false;
        }
        if (passport.length() != 10) {
            badUserResponse.setPassportNumber("Номер пасспорта должен содержать ровно 10 цифр");
            return false;
        }
        for (int i = 0; i < passport.length(); i++) {
            if (!Character.isDigit(passport.charAt(i))) {
                badUserResponse.setPassportNumber("В номере пасспорта присутствует символы отличные от цифр");
                return false;
            }
        }
        return true;
    }

    public boolean isValid() {
        return (isNameValid(user.getName()) &
                isNameValid(user.getSurname()) &
                isNameValid(user.getPatronymic()) &
                isDateBirthdayValid(user.getBirthday()) &
                isPassportNumberValid(user.getPassportNumber()));
    }

    public BadUserResponse getErrorMessage() {
        return badUserResponse;
    }
}
