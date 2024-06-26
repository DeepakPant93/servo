package com.stereotype.servo.engine;

import com.stereotype.servo.commons.CommandEnum.Command;
import com.stereotype.servo.commons.ConfigFileUtil;
import com.stereotype.servo.commons.ConfigFileUtil.ServoConfig;
import com.stereotype.servo.commons.FileUtil;
import com.stereotype.servo.exception.FileNotFoundException;
import com.stereotype.servo.exception.ProcessInterruptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CommandEngine {

    @Autowired
    private ConfigFileUtil configFileUtil;

    @Autowired
    private FileUtil fileUtil;

    public Integer execute(String app, Command command) {
        ServoConfig config = configFileUtil.servoConfig();
        String tempFilepath = fileUtil.download(app, config.getPackageManager(), command.name());

        return execute(config, tempFilepath);
    }

    public Integer execute(ServoConfig config, String filepath) {
        Integer exitCode = null;
        try {
            String hosts = String.join(",", config.listHosts()) + ",";
            String ansibleCommand = config.getAnsiblePath() + "/" + "ansible-playbook";

            List<String> command = List.of(ansibleCommand, "-i", hosts, "-u", config.getUser(), filepath);
            System.out.println("Running command :: " + String.join(" ", command));

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // Start the process
            Process process = processBuilder.start();
            // Wait for the process to complete and get the exit value
            exitCode = process.waitFor();

            // Get the input stream to read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            if (exitCode == 0) {
                System.out.println("Playbook executed successfully!. Exit code: " + exitCode);
            } else {
                System.out.println("Error in executing playbook. Exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            throw new ProcessInterruptedException("Unable to complete the process.", e);
        } catch (IOException e) {
            throw new FileNotFoundException("Unable to process the file", e);
        }
        return exitCode;
    }
}
