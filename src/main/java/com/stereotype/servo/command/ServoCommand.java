package com.stereotype.servo.command;

import com.stereotype.servo.commons.CommandEnum;
import com.stereotype.servo.commons.ServoConstant;
import com.stereotype.servo.engine.CommandEngine;
import lombok.AllArgsConstructor;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command
@AllArgsConstructor
public class ServoCommand {

    private CommandEngine commandEngine;

    /**
     * Install an application.
     *
     * @param app Install an application
     * @return description of return value
     */
    @Command(command = ServoConstant.INSTALL, description = "Install an application")
    public Integer install(@Option(required = true, longNames = "app", shortNames = 'a', description = "Install an application") String app) {
        return commandEngine.execute(app, CommandEnum.Command.INSTALL);
    }

    /**
     * Uninstall the application.
     *
     * @param app the name of the application to uninstall
     * @return the exit code of the uninstallation process
     */
    @Command(command = ServoConstant.UNINSTALL, description = "Uninstall the application")
    public Integer uninstall(@Option(required = true, longNames = "app", shortNames = 'a', description = "Uninstall an application") String app) {
        return commandEngine.execute(app, CommandEnum.Command.UNINSTALL);
    }
}
