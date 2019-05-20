package internship.services.userService;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

@Command(scope = "user", name = "dao-db", description = "Switch to DB DAO.")
public class DAOSwitchCommand extends OsgiCommandSupport {
    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Switching to DB.");
        return null;
    }
}
