/* Copyright (C) 2016  Intel Corporation
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

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class lib_persistent_Aggregation */

#ifndef _Included_lib_persistent_Aggregation
#define _Included_lib_persistent_Aggregation
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     lib_persistent_Aggregation
 * Method:    nativeGetField
 * Signature: (JJ)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_lib_persistent_Aggregation_nativeGetField
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     lib_persistent_Aggregation
 * Method:    nativeSetField
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_lib_persistent_Aggregation_nativeSetField
  (JNIEnv *, jclass, jlong, jlong, jlong);

/*
 * Class:     lib_persistent_Aggregation
 * Method:    nativeAggregate
 * Signature: (Ljava/lang/String;J)J
 */
JNIEXPORT jlong JNICALL Java_lib_persistent_Aggregation_nativeAggregate
  (JNIEnv *, jclass, jstring, jlong);

/*
 * Class:     lib_persistent_Aggregation
 * Method:    nativeGetFieldCount
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_lib_persistent_Aggregation_nativeGetFieldCount
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif