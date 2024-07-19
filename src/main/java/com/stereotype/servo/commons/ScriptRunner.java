/*
###############################################################################
# Copyright (c) 2024-present, Deepak Pant. - All Rights Reserved              #
# Unauthorized copying of this file, via any medium is strictly prohibited    #
# Proprietary and confidential                                                #
# Written by Deepak Pant, July 2024                                           #
###############################################################################
*/

package com.stereotype.servo.commons;

import com.stereotype.servo.exception.FileNotFoundException;
import com.stereotype.servo.exception.ProcessInterruptedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
@Slf4j
public class ScriptRunner {

    private ResourceLoader resourceLoader;

    /**
     * Updates the authentication keys by running a script.
     *
     * @param args optional arguments to pass to the script
     * @throws IOException if an error occurs while running the script
     */
    public void updateAuthKeys(String... args) {
        String script = ServoConstant.SCRIPT_FOLDER + "/" + ServoConstant.SCRIPT_UPDATE_AUTH_FILE;
        run(script, args);
    }

    /**
     * Runs a script with the given script name and arguments. The script is loaded from the classpath.
     *
     * @param script the name of the script to run
     * @param args   optional arguments to pass to the script
     * @throws FileNotFoundException if the script file cannot be found
     */
    public void run(String script, String... args) {

        Resource resource = resourceLoader.getResource("classpath:" + script);
        try {
            String filename = resource.getFile().getAbsolutePath();

            // Create commands array
            List<String> command = Stream.concat(
                    Stream.of(filename),
                    Arrays.stream(args)
            ).toList();

            // Run the script
            run(command.toArray(new String[0]));

        } catch (IOException e) {
            throw new FileNotFoundException("File not found: " + script);
        }
    }


    private void run(String... commands) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commands);

            // Redirect error stream to standard output (optional)
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            // Wait for the process to complete and get the exit value
            int exitValue = process.waitFor();
            log.info("Script executed with exit code: {}", exitValue);

        } catch (IOException | InterruptedException e) {
            throw new ProcessInterruptedException("Unable to complete the process.", e);
        }
    }
}
