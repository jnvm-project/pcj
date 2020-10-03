/* Copyright (C) 2017  Intel Corporation
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 only, as published by the Free Software Foundation.
 * This file has been designated as subject to the "Classpath"
 * exception as provided in the LICENSE file that accompanied
 * this code.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License version 2 for more details (a copy
 * is included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU General Public License
 * version 2 along with this program; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 */

package lib.xpersistent;

import lib.util.persistent.TransactionCore;
import lib.util.persistent.TransactionException;
import lib.util.persistent.PersistenceException;
import lib.util.persistent.spi.PersistentMemoryProvider;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XTransaction implements TransactionCore {

    private static void loadNativeLibraryFromJar(String lib) {
        String libPathName = System.mapLibraryName(lib);
        InputStream ipStream = XTransaction.class.getClassLoader().getResourceAsStream(libPathName);
        try {
            File tmpFile = File.createTempFile("lib", "so");
            tmpFile.deleteOnExit();

            byte[] buff = new byte[1024];
            int streamLength;
            OutputStream opStream = new FileOutputStream(tmpFile);
            while ((streamLength = ipStream.read(buff)) != -1) {
                opStream.write(buff, 0, streamLength);
            }
            opStream.close();
            ipStream.close();
            System.load(tmpFile.getAbsolutePath());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    static {
        loadNativeLibraryFromJar("Persistent");
        lib.util.persistent.spi.PersistentMemoryProvider.getDefaultProvider().getHeap().open();
    }

    XTransaction() {}

    public void start() {
        nativeStartTransaction();
    }

    public void commit() {
        nativeCommitTransaction();
        nativeEndTransaction();
    }

    public void abort(TransactionException e) {
        if (!(e.getCause() instanceof PersistenceException))
            nativeAbortTransaction();
        nativeEndTransaction();
    }

    private native void nativeStartTransaction();
    private native void nativeCommitTransaction();
    private native void nativeEndTransaction();
    private native void nativeAbortTransaction();
}
