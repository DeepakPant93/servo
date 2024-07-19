/*
###############################################################################
# Copyright (c) 2024-present, Deepak Pant. - All Rights Reserved              #
# Unauthorized copying of this file, via any medium is strictly prohibited    #
# Proprietary and confidential                                                #
# Written by Deepak Pant, July 2024                                           #
###############################################################################
*/

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
    private static final String PATH = "/etc/servo";
    private static final String FILENAME = "config.ini";
    private static final String FILEPATH = PATH + "/" + FILENAME;


    // Section Keys
    private static final String ANSIBLE_SECTION = "ansible";
    private static final String GENERAL_SECTION = "general";
    private static final String HOST_SECTION = "hosts";


    // Config keys
    private static final String ANSIBLE_PATH_KEY = "ansible_path";
    private static final String ANSIBLE_PLAYBOOK_REPO_PATH_KEY = "playbook_repo_path";
    private static final String PACKAGE_MANAGER_KEY = "package_manager";
    private static final String USER_KEY = "user";
    private static final String PUBLIC_KEY = "public_key";
    private static final String PRIVATE_KEY = "private_key";


    // Config Default keys
    private static final String DEFAULT_ANSIBLE_PATH = "/usr/bin/ansible";
    private static final String DEFAULT_PACKAGE_MANAGER = "apt";
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

    /**
     * Generates a ServoConfig object based on the configuration settings retrieved from the configuration file.
     *
     * @return the generated ServoConfig object
     */
    public ServoConfig servoConfig() {
        ConfigFileUtil configFileUtil = new ConfigFileUtil();

        Map<String, String> ansibleSection = configFileUtil.section(ANSIBLE_SECTION);
        Map<String, String> generalSection = configFileUtil.section(GENERAL_SECTION);
        Map<String, String> hostSection = configFileUtil.section(HOST_SECTION);

        String ansiblePath = ansibleSection.getOrDefault(ANSIBLE_PATH_KEY, DEFAULT_ANSIBLE_PATH);
        String ansiblePlaybookRepoPath = ansibleSection.getOrDefault(ANSIBLE_PLAYBOOK_REPO_PATH_KEY, DEFAULT_ANSIBLE_PLAYBOOK_REPO_PATH);

        String packageManager = generalSection.getOrDefault(PACKAGE_MANAGER_KEY, DEFAULT_PACKAGE_MANAGER);
        String user = generalSection.get(USER_KEY);
        String publicKey = generalSection.get(PUBLIC_KEY);
        String privateKey = generalSection.get(PRIVATE_KEY);

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
        servoConfig.setPublicKey(publicKey);
        servoConfig.setPrivateKey(privateKey);
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
        private String publicKey;
        private String privateKey;

        public List<String> listHosts() {
            return hosts.values().stream().toList();
        }
    }
}
