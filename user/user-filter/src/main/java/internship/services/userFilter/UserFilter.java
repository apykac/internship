package internship.services.userFilter;

import internship.models.userModel.User;

import java.util.Comparator;
import java.util.List;

public class UserFilter implements IUserFilter {

    public List<User> filter(List<User> userList) {
        userList.removeIf(user -> user.getIncome() < 20000);
        userList.sort(Comparator.comparing(User::getIncome).reversed());
        return userList;
    }
}
