# Servo

Servo is a command line tool that leverages Ansible to install or uninstall applications on remote servers. This tool supports a variety of applications including Apache HTTP Server, Nginx, Python, Java, Maven, PostgreSQL, MongoDB, Kafka and more.

## Features

- **Flexible Installation and Uninstallation:** Easily install or uninstall applications using simple commands.
- **Customizable Configurations:** Define hosts, package managers, and user credentials directly in commands or via a configuration file.
- **Command-line Options:** Override configuration settings with command-line options for greater control.

## Command Usage

### Install Command
```
install <application> -i '<hosts>' -p 'apt/yum' -u '<user>'
```

### Uninstall Command
```
uninstall <application> -i '<hosts>' -p 'apt/yum' -u '<user>'
```

### Command Options

- `-i`: (Optional) Comma-separated list of host/hosts. If not provided, hosts will be picked from the `servo.config` file.
- `-p`: (Optional) Package manager for the remote server (`apt` or `yum`). Defaults to `apt` if not specified in the command or `servo.config`.
- `-u`: (Optional) Username for the remote server. If not provided, it must be defined in the `servo.config` file.

### Variable Preference
Variables will be picked in the following order:
1. Command line
2. `servo.config` file
3. Default values (if available)

If a required variable is missing in both the command and the `servo.config` file, an error will occur.

## Prerequisites

- **Ansible**
- **Java 21**

Ensure the Ansible installation path is defined in the `servo.config` file, otherwise, the default Ansible path will be used.

## Configuration File

### Path
`/etc/servo/servo.config`

### Sample Configuration (`servo.config`)
```ini
[general]
ansible_path = /usr/bin/ansible

[defaults]
package_manager = apt
user = default_user

[hosts]
host1 = 192.168.1.1
host2 = 192.168.1.2
```

## Ansible Scripts

Servo uses a central repository for all Ansible scripts. Ensure that your scripts are properly maintained and accessible from this repository.

## Supported Applications

The following applications can be managed using Servo:
- Apache HTTP Server
- Nginx
- Python
- Java
- Maven
- PostgreSQL
- MongoDB

## Contact

For issues, questions, or contributions, don't hesitate to get in touch with deepak.93p@gmail.com

---

Happy DevOps-ing with Servo!
