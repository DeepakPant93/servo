package com.stereotype.servo.commons;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * This class provides utility methods for reading and writing file content.
 * It contains methods to read and write files.
 */
@Component
public class FileUtil {

    private static final String BASE_DEFAULT_URL_FORMAT = "https://raw.githubusercontent.com/DeepakPant93/servo-ansible-playbooks/main/scripts/%s/%s/%s.yml";
    private static final String TEMP_FILENAME = "script.yml";
    private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");

    public String download(String app, String packageManager, String command) {
        String tempFilepath = null;
        String url = populateURL(app, packageManager, command);

        try {
            URL fileUrl = new URL(url);

            Path targetPath = Path.of(TEMP_DIRECTORY, TEMP_FILENAME);
            Files.copy(fileUrl.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            tempFilepath = targetPath.toString();
        } catch (IOException e) {
            System.out.println("Unable to process the file. Error message :: " + e.getMessage());
        }
        return tempFilepath;
    }

    private String populateURL(String app, String packageManager, String command) {
        return String.format(BASE_DEFAULT_URL_FORMAT, app, packageManager, command.toLowerCase());
    }
}
