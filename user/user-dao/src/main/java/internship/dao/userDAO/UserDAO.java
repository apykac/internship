package internship.dao.userDAO;

import internship.models.userModel.User;

public interface UserDAO {
    /**
     * Ищет в БД данные о пользователе по пасспортным данным
     *
     * @param passport пасспортные данные пользователя
     * @return User если существует в БД пользователь с данным id, иначе null
     */
    User findUserByPassport(Long passport);

    /**
     * Меняет данные о пользователе по  указаному id
     * @param passport паспортные данные
     * @param user данные пользователя
     * @return User если существует в БД с данным id, иначе null
     */
    User updateUser(Long passport, User user);

    /**
     * Сохраняет пользователя в БД
     * @param user данные о пользователе
     * @return User если удалось сохранить пользователя в БД или null, если неудалось подключиться к базе данных возвращает null
     * */
    User createUser(User user);

    /**
     * Удаляет пользователя из базы данных по паспортным данным
     * @param passport паспортные данные пользователя
     */
    void removeUser(Long passport);

    /**
     * Проверяет удалссь ли подключиться к бандлу раздающей connection к базе данных
     * @return true если удалось подключиться, иначе false
     */
    boolean isConnectorUp();
}
