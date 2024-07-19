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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This class provides utility methods for downloading file content.
 * It contains methods to download and delete files.
 */
@Component
@Slf4j
public class FileUtil {

    private static final String BASE_DEFAULT_URL_FORMAT = "/%s/%s/%s.yml";
    private static final String TEMP_FILENAME = "script.yml";
    private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");

    /**
     * Downloads a file specified by the app, package manager, ansible playbook repo path, and command.
     *
     * @param app                     the application name
     * @param packageManager          the package manager name
     * @param ansiblePlaybookRepoPath the ansible playbook repository path
     * @param command                 the command to download the file
     * @return the filepath of the downloaded file
     */
    public String download(String app, String packageManager, String ansiblePlaybookRepoPath, String command) {
        String tempFilepath = null;
        String url = populateURL(app, packageManager, ansiblePlaybookRepoPath, command);

        try {
            URL fileUrl = new URI(url).toURL();

            Path targetPath = Path.of(TEMP_DIRECTORY, TEMP_FILENAME);
            Files.copy(fileUrl.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            tempFilepath = targetPath.toString();
        } catch (URISyntaxException | IOException e) {
            log.info("Unable to download the file. Error message :: {}", e.getMessage());
        }
        return tempFilepath;
    }

    /**
     * Deletes the file specified by the filepath if it exists.
     *
     * @param filepath the path of the file to be deleted
     */
    public void delete(String filepath) {
        Path path = Paths.get(filepath);

        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File not found at " + filepath);
        }
    }

    private String populateURL(String app, String packageManager, String ansiblePlaybookRepoPath, String command) {
        return String.format(ansiblePlaybookRepoPath.concat(BASE_DEFAULT_URL_FORMAT), app.toLowerCase(), packageManager, command.toLowerCase());
    }
}
