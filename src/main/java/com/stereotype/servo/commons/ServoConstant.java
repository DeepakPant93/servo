/*
###############################################################################
# Copyright (c) 2024-present, Deepak Pant. - All Rights Reserved              #
# Unauthorized copying of this file, via any medium is strictly prohibited    #
# Proprietary and confidential                                                #
# Written by Deepak Pant, July 2024                                           #
###############################################################################
*/

package com.stereotype.servo.commons;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ServoConstant {


    // Servo Commands
    public static final String INSTALL = "install";
    public static final String UNINSTALL = "uninstall";
    public static final String SETUP = "setup";

    // Script
    public static final String SCRIPT_FOLDER = "script";
    public static final String SCRIPT_UPDATE_AUTH_FILE = "update_authorized_keys.sh";

}
