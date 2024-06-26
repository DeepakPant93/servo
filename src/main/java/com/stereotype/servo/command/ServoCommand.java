package com.stereotype.servo.command;

import com.stereotype.servo.commons.CommandEnum;
import com.stereotype.servo.commons.ServoConstant;
import com.stereotype.servo.engine.CommandEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command
public class ServoCommand {

    @Autowired
    private CommandEngine commandEngine;

    @Command(command = ServoConstant.INSTALL, description = "Install an application")
    public Integer install(@Option(required = true, longNames = "app") String app) {
        return commandEngine.execute(app, CommandEnum.Command.INSTALL);
    }

    @Command(command = ServoConstant.UNINSTALL, description = "Uninstall the application")
    public Integer uninstall(@Option(required = true, longNames = "app") String app) {
        return commandEngine.execute(app, CommandEnum.Command.UNINSTALL);
    }
}
