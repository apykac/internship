package internship.services.userService;

import internship.dao.userDAO.UserDatabaseDAO;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

@Command(scope = "user", name = "dao", description = "Switch to DB DAO.")
public class DAOSwitchCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "dao", description = "The new dao to be set. Could be either 'db' or 'hm'.", required = true, multiValued = false)
    String dao;

    public void setUser(UserServiceImpl user) {
        this.user = user;
    }

    private UserServiceImpl user;
    @Override
    protected Object doExecute() throws Exception {
        if (dao.equals("db")) {
            System.out.println("Switching to DB.");
            user.setDao(new UserDatabaseDAO());
        }
        else{
            System.out.println("Wrong dao. Could be either 'db' or 'hm'.");
        }
        return null;
    }
}
