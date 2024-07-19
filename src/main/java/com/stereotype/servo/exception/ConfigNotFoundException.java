/*
###############################################################################
# Copyright (c) 2024-present, Deepak Pant. - All Rights Reserved              #
# Unauthorized copying of this file, via any medium is strictly prohibited    #
# Proprietary and confidential                                                #
# Written by Deepak Pant, July 2024                                           #
###############################################################################
*/

package com.stereotype.servo.exception;

public class ConfigNotFoundException extends ServoException {

    private static final String CONFIG_NOT_FOUND_EXCEPTION_CODE = "SE-1002";

    public ConfigNotFoundException() {
        super(CONFIG_NOT_FOUND_EXCEPTION_CODE);
    }

    public ConfigNotFoundException(String message) {
        super(CONFIG_NOT_FOUND_EXCEPTION_CODE, message);
    }

    public ConfigNotFoundException(String message, Throwable cause) {
        super(CONFIG_NOT_FOUND_EXCEPTION_CODE, message, cause);
    }
}
