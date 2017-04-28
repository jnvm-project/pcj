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

package lib.util.persistent;

import lib.util.persistent.spi.*;
import lib.xpersistent.XRoot;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.ref.WeakReference;

@SuppressWarnings("unchecked")
public class ObjectDirectory {
    static PersistentHashMap<PersistentString, PersistentObject> map;
    static PhantomQueue<Persistent<?>> pq;

    static {
        pq = new PhantomQueue<Persistent<?>>((Pointer pointer, String name) -> {
            // System.out.println("enqueued: Ref(\"" + name + "\", " + pointer.addr() + ")");
            if (pointer instanceof ObjectPointer) {
                synchronized(ObjectDirectory.class) {
                    Transaction.run(() -> {
                        PersistentObject.decRefCountAtAddress(pointer.addr());
                        deregisterObject(pointer.addr());
                    });
                }
            }
            else if (pointer instanceof ValuePointer) {
                PersistentMemoryProvider.getDefaultProvider().getHeap().freeRegion(pointer.region());
            }
        });
    }

    public static <T extends PersistentObject> T get(String s, Class<T> cls) {
        return (T)map.get(new PersistentString(s + cls.getName()));
    }

    public static <T extends PersistentObject> void put(String s, T obj) {
        map.put(new PersistentString(s + obj.getClass().getName()), obj);
    }

    public static <T extends PersistentObject> T remove(String s, Class<T> cls) {
        return (T)map.remove(new PersistentString(s + cls.getName()));
    }

    public static void initialize() {
        map = PersistentMemoryProvider.getDefaultProvider().getHeap().getRoot().getObjectDirectory();
    }

    static synchronized <T extends Persistent<T>> void registerObject(T obj) {
        if (obj instanceof PersistentValue) return;
        Transaction.run(() -> {
            pq.registerObject(obj, obj.getPointer());
            ((XRoot)(PersistentMemoryProvider.getDefaultProvider().getHeap().getRoot())).registerObject(obj.getPointer().region().addr());
        });
    }

    static synchronized void deregisterObject(long addr) {
        ((XRoot)(PersistentMemoryProvider.getDefaultProvider().getHeap().getRoot())).deregisterObject(addr);
    }
}
