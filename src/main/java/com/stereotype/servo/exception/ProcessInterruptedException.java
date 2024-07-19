/*
###############################################################################
# Copyright (c) 2024-present, Deepak Pant. - All Rights Reserved              #
# Unauthorized copying of this file, via any medium is strictly prohibited    #
# Proprietary and confidential                                                #
# Written by Deepak Pant, July 2024                                           #
###############################################################################
*/

package com.stereotype.servo.exception;

public class ProcessInterruptedException extends ServoException {

    private static final String PROCESS_INTERRUPTED_EXCEPTION_CODE = "SE-1003";

    public ProcessInterruptedException() {
        super(PROCESS_INTERRUPTED_EXCEPTION_CODE);
    }

    public ProcessInterruptedException(String message) {
        super(PROCESS_INTERRUPTED_EXCEPTION_CODE, message);
    }

    public ProcessInterruptedException(String message, Throwable cause) {
        super(PROCESS_INTERRUPTED_EXCEPTION_CODE, message, cause);
    }
}
