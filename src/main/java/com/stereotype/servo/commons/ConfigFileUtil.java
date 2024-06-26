package com.stereotype.servo.commons;

import com.stereotype.servo.exception.ConfigNotFoundException;
import com.stereotype.servo.exception.FileNotFoundException;
import com.stereotype.servo.exception.InvalidPackageManagerException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class ConfigFileUtil {

    // Config file default path
    private final static String PATH = "/etc/servo";
    private final static String FILENAME = "config.ini";
    private final static String FILEPATH = PATH + "/" + FILENAME;


    // Section Keys
    private final static String ANSIBLE_SECTION = "ansible";
    private final static String GENERAL_SECTION = "general";
    private final static String HOST_SECTION = "hosts";


    // Config keys
    private final static String ANSIBLE_PATH_KEY = "ansible_path";
    private final static String ANSIBLE_PLAYBOOK_REPO_PATH_KEY = "playbook_repo_path";
    private final static String PACKAGE_MANAGER_KEY = "package_manager";
    private final static String USER_KEY = "user";


    // Config Default keys
    private final static String DEFAULT_ANSIBLE_PATH = "/usr/bin/ansible";
    private final static String DEFAULT_PACKAGE_MANAGER = "apt";
    private static final String DEFAULT_ANSIBLE_PLAYBOOK_REPO_PATH = "https://raw.githubusercontent.com/DeepakPant93/servo-ansible-playbooks/main/scripts";

    private final INIConfiguration config;

    public ConfigFileUtil() {
        try {
            Configurations configs = new Configurations();
            this.config = configs.ini(new File(FILEPATH));
        } catch (ConfigurationException e) {
            throw new FileNotFoundException("Exception occurred while reading the file " + FILEPATH, e);
        }
    }

    public ServoConfig servoConfig() {
        ConfigFileUtil configFileUtil = new ConfigFileUtil();

        Map<String, String> ansibleSection = configFileUtil.section(ANSIBLE_SECTION);
        Map<String, String> generalSection = configFileUtil.section(GENERAL_SECTION);
        Map<String, String> hostSection = configFileUtil.section(HOST_SECTION);

        String ansiblePath = ansibleSection.getOrDefault(ANSIBLE_PATH_KEY, DEFAULT_ANSIBLE_PATH);
        String ansiblePlaybookRepoPath = ansibleSection.getOrDefault(ANSIBLE_PLAYBOOK_REPO_PATH_KEY, DEFAULT_ANSIBLE_PLAYBOOK_REPO_PATH);

        String packageManager = generalSection.getOrDefault(PACKAGE_MANAGER_KEY, DEFAULT_PACKAGE_MANAGER);
        String user = generalSection.get(USER_KEY);

        // Check user
        if (user == null || user.isBlank()) {
            throw new ConfigNotFoundException("User is blank in the configuration file.");
        }

        // Check host
        if (hostSection.isEmpty()) {
            throw new ConfigNotFoundException("Host is not found in the configuration file.");
        }

        // Check package manager
        boolean validPackageManager = Arrays.stream(CommandEnum.PackageManager.values()).anyMatch(pm -> pm.name().equalsIgnoreCase(packageManager));
        if (!validPackageManager) {
            throw new InvalidPackageManagerException(packageManager + " is not a valid package manager.");
        }

        ServoConfig servoConfig = new ServoConfig();

        servoConfig.setAnsiblePath(ansiblePath);
        servoConfig.setAnsiblePlaybookRepoPath(ansiblePlaybookRepoPath);
        servoConfig.setPackageManager(packageManager);
        servoConfig.setUser(user);
        servoConfig.setHosts(hostSection);

        return servoConfig;

    }

    private Map<String, String> section(String section) {
        SubnodeConfiguration subConfig = this.config.getSection(section);

        Map<String, String> map = new HashMap<>();
        Iterator<String> keys = subConfig.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, subConfig.getString(key));
        }
        return map;
    }

    @Data
    @NoArgsConstructor
    public class ServoConfig {
        private String ansiblePath;
        private String ansiblePlaybookRepoPath;
        private String packageManager;
        private String user;
        private Map<String, String> hosts;

        public List<String> listHosts() {
            return hosts.values().stream().toList();
        }
    }
}
