package internship.services.userService;

import internship.dao.userDAO.UserDatabaseDAO;
import internship.dao.userDAO.UserHashMapDAO;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

@Command(scope = "user", name ="dao", description = "Switch to DB DAO.")
public class DAOSwitchCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "dao", description = "The new dao to be set. Could be either 'db' or 'hm'.", required = true, multiValued = false)
    String dao;

    public DAOSwitchCommand() {
        this.user = this.user;
    }

    public DAOSwitchCommand(UserServiceImpl user) {
        this.user = user;
    }

    public void setUser(UserServiceImpl user) {
        this.user = user;
    }

    private UserServiceImpl user;
    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Version 1");
        if (dao.equals("db")) {
            System.out.println("Switching to Postgres DataBase.");
            user.setDao(new UserDatabaseDAO());
        }
        else if (dao.equals("hm")){
            System.out.println("Switching to HashMap");
            user.setDao(UserHashMapDAO.getInstance());
        }
        else{
            System.out.println("Wrong dao. Could be either 'db' or 'hm'.");
        }
        return null;
    }
}
