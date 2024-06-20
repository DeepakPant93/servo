package com.stereotype.servo.command;

import com.stereotype.servo.commons.ServoConstant;
import org.springframework.shell.command.annotation.Command;

@Command
public class ServoCommand {

    @Command(command = ServoConstant.INSTALL, description = "Install an application")
    public void install() {
        System.out.println("Installation begins...");

    }

    @Command(command = ServoConstant.UNINSTALL, description = "Uninstall the application")
    public void uninstall() {
        System.out.println("Un-Installation completed...");

    }
}
