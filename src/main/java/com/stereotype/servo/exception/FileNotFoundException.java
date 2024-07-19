/*
###############################################################################
# Copyright (c) 2024-present, Deepak Pant. - All Rights Reserved              #
# Unauthorized copying of this file, via any medium is strictly prohibited    #
# Proprietary and confidential                                                #
# Written by Deepak Pant, July 2024                                           #
###############################################################################
*/

package com.stereotype.servo.exception;

public class FileNotFoundException extends ServoException {

    private static final String FILE_NOT_FOUND_EXCEPTION_CODE = "SE-1001";

    public FileNotFoundException() {
        super(FILE_NOT_FOUND_EXCEPTION_CODE);
    }

    public FileNotFoundException(String message) {
        super(FILE_NOT_FOUND_EXCEPTION_CODE, message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(FILE_NOT_FOUND_EXCEPTION_CODE, message, cause);
    }
}
