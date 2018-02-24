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

import java.util.Locale;

/**
 * Lerc header information
 */
public class LercHeaderInfo {
    private int mNumRows;
    private int mNumCols;
    private double mZmin;
    private double mZmax;

    @Lerc.DataType
    private int mDataType;

    public LercHeaderInfo(int numRows, int numCols, double zMin, double zMax, @Lerc.DataType int dt) {
        mNumCols = numCols;
        mNumRows = numRows;
        mZmin = zMin;
        mZmax = zMax;
        mDataType = dt;
    }

    public int getNumCols() {
        return mNumCols;
    }

    public int getNumRows() {
        return mNumRows;
    }

    public double getZmin() {
        return mZmin;
    }

    public double getZmax() {
        return mZmax;
    }

    @Lerc.DataType
    public int getmDataType() {
        return mDataType;
    }

    @Override
    public String toString() {
        return String.format(Locale.CHINA,
                "Lerc nRows is %d, nCols is %d, zMin is %f, zMax is %f, dt is %d",
                mNumRows, mNumCols, mZmin, mZmax, mDataType);
    }
}
