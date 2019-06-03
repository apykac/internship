package internship.services.userFilter;

import internship.models.userModel.User;

import java.util.List;

public interface IUserFilter {
    List<User> filter(List<User> user);
}
