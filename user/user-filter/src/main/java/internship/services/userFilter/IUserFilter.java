package internship.services.userFilter;

import internship.models.userModel.User;

import java.util.List;

public interface IUserFilter {
    /**
     * Фильтрует список пользователей по доходу(income)
     * @param user список пользователей
     * @return список пользователей, успещно прошедшие фильтрацию
     */
    List<User> filter(List<User> user);
}
