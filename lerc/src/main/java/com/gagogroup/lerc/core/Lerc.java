/*
 * Copyright 2018 The Gago Group Mobile Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gagogroup.lerc.core;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Lerc Decoder
 *
 */
public class Lerc {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @IntDef({DT_CHAR, DT_BYTE, DT_SHORT, DT_USHOTR, DT_INT, DT_UINT, DT_FLOAT, DT_DOUBLE, DT_UNDEFINED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataType {}

    public static final int DT_CHAR = 0;
    public static final int DT_BYTE = 1;
    public static final int DT_SHORT = 2;
    public static final int DT_USHOTR = 3;
    public static final int DT_INT = 4;
    public static final int DT_UINT = 5;
    public static final int DT_FLOAT = 6;
    public static final int DT_DOUBLE = 7;
    public static final int DT_UNDEFINED = 8;


    /**
     * Gets Lerc blob info
     * @param rawData Lerc file stream as bytes
     * @return Lerc blob info
     */
    public static LercHeaderInfo getHeaderInfo(byte[] rawData) {
        return nativeGetHeaderInfo(rawData);
    }

    public static byte[] decode(byte[] rawData) {
        return nativeDecode(rawData);
    }

    private static native LercHeaderInfo nativeGetHeaderInfo(byte[] rawData);

    private static native byte[] nativeDecode(byte[] rawData);
}
