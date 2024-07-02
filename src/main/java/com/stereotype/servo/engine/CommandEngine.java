package com.stereotype.servo.engine;

import com.stereotype.servo.commons.CommandEnum.Command;
import com.stereotype.servo.commons.ConfigFileUtil;
import com.stereotype.servo.commons.ConfigFileUtil.ServoConfig;
import com.stereotype.servo.commons.FileUtil;
import com.stereotype.servo.exception.CommandNotFoundException;
import com.stereotype.servo.exception.FileNotFoundException;
import com.stereotype.servo.exception.ProcessInterruptedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class CommandEngine {

    private ConfigFileUtil configFileUtil;
    private FileUtil fileUtil;

    /**
     * Executes a command for a given application.
     *
     * @param app     the application to execute the command on
     * @param command the command to be executed
     * @return the exit code of the command execution
     */
    public Integer execute(String app, Command command) {
        ServoConfig config = configFileUtil.servoConfig();
        String tempFilepath = fileUtil.download(app, config.getPackageManager(), config.getAnsiblePlaybookRepoPath(), command.name());

        Integer exitCode;
        if (tempFilepath != null && !tempFilepath.isBlank()) {
            exitCode = execute(config, tempFilepath);

            // Cleanup - Deletes the temp file
            fileUtil.delete(tempFilepath);
        } else {
            throw new CommandNotFoundException("Provided command does not exists.");
        }
        return exitCode;
    }

    private Integer execute(ServoConfig config, String filepath) {
        Integer exitCode;
        try {
            String hosts = String.join(",", config.listHosts()) + ",";
            String ansibleCommand = config.getAnsiblePath() + "/" + "ansible-playbook";

            List<String> command = List.of(ansibleCommand, "-i", hosts, "-u", config.getUser(), filepath);
            log.info("Command to be executed {} ", String.join(" ", command));

            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // Start the process
            Process process = processBuilder.start();
            // Wait for the process to complete and get the exit value
            exitCode = process.waitFor();

            // Get the input stream to read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            if (exitCode == 0) {
                log.info("Playbook executed successfully!. Exit code: {}", exitCode);
            } else {
                log.error("Error in executing playbook. Exit code: {}", exitCode);
            }
        } catch (InterruptedException e) {
            throw new ProcessInterruptedException("Unable to complete the process.", e);
        } catch (IOException e) {
            throw new FileNotFoundException("Unable to process the file", e);
        }
        return exitCode;
    }
}
