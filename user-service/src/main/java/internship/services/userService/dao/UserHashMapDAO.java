package internship.services.userService.dao;

import internship.services.userService.model.User;
import internship.services.userService.model.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

public class UserHashMapDAO implements UserDAO {
    private static UserHashMapDAO dao;
    private Map<Long, User> users = new HashMap<>();
    private Long currentId = 1L;

    public static UserDAO getInstance() {
        if (dao == null) {
            dao = new UserHashMapDAO();
            UserDTO vanya = new UserDTO("Ivan", "Rybachenko", "Alexandrovich", "15.05.1999", "");
            dao.createUser(vanya);
            UserDTO pushkin = new UserDTO("Alexandr", "Pushkin", "Sergeevich", "26.05.1799", "");
            dao.createUser(pushkin);
        }
        return dao;
    }

    @Override
    public User findUserById(Long id) {
        return users.get(id);
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        User userFromDB = findUserById(id);
        userFromDB.setName(userDTO.getName());
        userFromDB.setSurname(userDTO.getSurname());
        userFromDB.setPatronymic(userDTO.getPatronymic());
        userFromDB.setBirthday(userDTO.getBirthday());
        userFromDB.setPassportNumber(userDTO.getPassportNumber());

        return userFromDB;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setId(currentId++);
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setBirthday(userDTO.getBirthday());
        user.setPassportNumber(userDTO.getPassportNumber());

        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void removeUser(Long id) {
        users.remove(id);
    }
}
