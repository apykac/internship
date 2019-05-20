package internship.services.userService;

import internship.dao.userDAO.UserDatabaseDAO;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

@Command(scope = "user", name = "dao-db", description = "Switch to DB DAO.")
public class DAOSwitchCommand extends OsgiCommandSupport {
    public void setUser(UserServiceImpl user) {
        this.user = user;
    }

    private UserServiceImpl user;
    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Switching to DB.");
        user.setDao(new UserDatabaseDAO());
        return null;
    }
}
