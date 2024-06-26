package com.stereotype.servo.commons;

import com.stereotype.servo.exception.ConfigNotFoundException;
import com.stereotype.servo.exception.FileNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ConfigFileUtil {

    // Config file default path
    private final static String path = "/etc/servo";
    private final static String filename = "config.ini";
    private final static String filepath = path + "/" + filename;

    // Section Keys
    private final static String GENERAL_SECTION = "general";
    private final static String DEFAULT_SECTION = "defaults";
    private final static String HOST_SECTION = "hosts";


    // Config keys
    private final static String ANSIBLE_PATH_KEY = "ansible_path";
    private final static String PACKAGE_MANAGER_KEY = "package_manager";
    private final static String USER_KEY = "user";


    // Config Default keys
    private final static String ANSIBLE_PATH_DEFAULT_KEY = "/usr/bin/ansible";
    private final static String PACKAGE_MANAGER_DEFAULT_KEY = "apt";


    private final INIConfiguration config;

    public ConfigFileUtil() {
        try {
            Configurations configs = new Configurations();
            this.config = configs.ini(new File(filepath));
        } catch (ConfigurationException e) {
            throw new FileNotFoundException("Exception occurred while reading the file " + filepath, e);
        }
    }

    public ServoConfig servoConfig() {
        ConfigFileUtil configFileUtil = new ConfigFileUtil();

        Map<String, String> generalSection = configFileUtil.section(GENERAL_SECTION);
        Map<String, String> defaultSection = configFileUtil.section(DEFAULT_SECTION);
        Map<String, String> hostSection = configFileUtil.section(HOST_SECTION);

        String ansiblePath = generalSection.getOrDefault(ANSIBLE_PATH_KEY, ANSIBLE_PATH_DEFAULT_KEY);
        String packageManager = defaultSection.getOrDefault(PACKAGE_MANAGER_KEY, PACKAGE_MANAGER_DEFAULT_KEY);
        String user = defaultSection.get(USER_KEY);

        if (user == null || user.isBlank()) {
            throw new ConfigNotFoundException("User is blank in the configuration file.");
        }

        if (hostSection.isEmpty()) {
            throw new ConfigNotFoundException("Host is not found in the configuration file.");
        }

        ServoConfig servoConfig = new ServoConfig();

        servoConfig.setAnsiblePath(ansiblePath);
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
        private String packageManager;
        private String user;
        private Map<String, String> hosts;

        public List<String> listHosts() {
            return hosts.values().stream().toList();
        }
    }
}
