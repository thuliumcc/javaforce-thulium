JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__J
  (JNIEnv *e, jclass c, jlong ptr)
{
  (*(void (*)())ptr)();
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JI
  (JNIEnv *e, jclass c, jlong ptr, jint i1)
{
  (*(void (*)(jint i1))ptr)(i1);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JII
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2)
{
  (*(void (*)(jint i1, jint i2))ptr)(i1,i2);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIF
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jfloat f2)
{
  (*(void (*)(jint i1, jfloat f2))ptr)(i1,f2);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JII_3F
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jfloatArray f3)
{
  float *f3ptr = (*e)->GetFloatArrayElements(e,f3,NULL);
  (*(void (*)(jint i1, jint i2, jfloat *f3))ptr)(i1,i2,f3ptr);
  (*e)->ReleaseFloatArrayElements(e, f3, f3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JI_3I
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jintArray i2)
{
  int *i2ptr = (*e)->GetIntArrayElements(e,i2,NULL);
  (*(void (*)(jint i1, jint *i2ptr))ptr)(i1,i2ptr);
  (*e)->ReleaseIntArrayElements(e, i2, i2ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIII
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jint i3)
{
  (*(void (*)(jint i1, jint i2, jint i3))ptr)(i1,i2,i3);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JII_3I
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jintArray i3)
{
  int *i3ptr = (*e)->GetIntArrayElements(e,i3,NULL);
  (*(void (*)(jint i1, jint i2, jint *i3ptr))ptr)(i1,i2,i3ptr);
  (*e)->ReleaseIntArrayElements(e, i3, i3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIII_3F
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jint i3, jfloatArray f4)
{
  jfloat *f4ptr = (*e)->GetFloatArrayElements(e,f4,NULL);
  (*(void (*)(jint i1, jint i2, jint i3, jfloat *f4ptr))ptr)(i1,i2,i3,f4ptr);
  (*e)->ReleaseFloatArrayElements(e, f4, f4ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIIII
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jint i3, jint i4)
{
  (*(void (*)(jint i1, jint i2, jint i3, jint i4))ptr)(i1,i2,i3,i4);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIIIJ
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jint i3, jlong l4)
{
  (*(void (*)(jint i1, jint i2, jint i3, jlong l4))ptr)(i1,i2,i3,l4);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIIJJ
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jlong l3, jlong l4)
{
  (*(void (*)(jint i1, jint i2, jlong l3, jlong l4))ptr)(i1,i2,l3,l4);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JII_3FI
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jfloatArray f3, jint i4)
{
  jfloat *f3ptr = (*e)->GetFloatArrayElements(e,f3,NULL);
  (*(void (*)(jint i1, jint i2, jfloat *f3ptr, jint i4))ptr)(i1,i2,f3ptr,i4);
  (*e)->ReleaseFloatArrayElements(e, f3, f3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIJ_3FI
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jlong l2, jfloatArray f3, jint i4)
{
  jfloat *f3ptr = (*e)->GetFloatArrayElements(e,f3,NULL);
  (*(void (*)(jint i1, jlong l2, jfloat *f3ptr, jint i4))ptr)(i1,l2,f3ptr,i4);
  (*e)->ReleaseFloatArrayElements(e, f3, f3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JII_3SI
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jshortArray s3, jint i4)
{
  jshort *s3ptr = (*e)->GetShortArrayElements(e,s3,NULL);
  (*(void (*)(jint i1, jint i2, jshort *s3ptr, jint i4))ptr)(i1,i2,s3ptr,i4);
  (*e)->ReleaseShortArrayElements(e, s3, s3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIJ_3SI
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jlong l2, jshortArray s3, jint i4)
{
  jshort *s3ptr = (*e)->GetShortArrayElements(e,s3,NULL);
  (*(void (*)(jint i1, jlong l2, jshort *s3ptr, jint i4))ptr)(i1,l2,s3ptr,i4);
  (*e)->ReleaseShortArrayElements(e, s3, s3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JII_3BI
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jbyteArray b3, jint i4)
{
  jbyte *b3ptr = (*e)->GetByteArrayElements(e,b3,NULL);
  (*(void (*)(jint i1, jint i2, jbyte *s3ptr, jint i4))ptr)(i1,i2,b3ptr,i4);
  (*e)->ReleaseByteArrayElements(e, b3, b3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIJ_3BI
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jlong l2, jbyteArray b3, jint i4)
{
  jbyte *b3ptr = (*e)->GetByteArrayElements(e,b3,NULL);
  (*(void (*)(jint i1, jlong l2, jbyte *s3ptr, jint i4))ptr)(i1,l2,b3ptr,i4);
  (*e)->ReleaseByteArrayElements(e, b3, b3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JII_3II
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jintArray i3, jint i4)
{
  int *i3ptr = (*e)->GetIntArrayElements(e,i3,NULL);
  (*(void (*)(jint i1, jint i2, jint *i3, jint i4))ptr)(i1,i2,i3ptr,i4);
  (*e)->ReleaseIntArrayElements(e, i3, i3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIJ_3II
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jlong l2, jintArray i3, jint i4)
{
  int *i3ptr = (*e)->GetIntArrayElements(e,i3,NULL);
  (*(void (*)(jint i1, jlong l2, jint *i3, jint i4))ptr)(i1,l2,i3ptr,i4);
  (*e)->ReleaseIntArrayElements(e, i3, i3ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIIIIII
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jint i3, jint i4, jint i5, jint i6)
{
  (*(void (*)(jint i1, jint i2, jint i3, jint i4, jint i5, jint i6))ptr)(i1,i2,i3,i4,i5,i6);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIIIIIJ
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jint i3, jint i4, jint i5, jlong l6)
{
  (*(void (*)(jint i1, jint i2, jint i3, jint i4, jint i5, jlong l6))ptr)(i1,i2,i3,i4,i5,l6);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIIIIII_3I
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jint i3, jint i4, jint i5, jint i6, jintArray i7)
{
  int *i7ptr = (*e)->GetIntArrayElements(e,i7,NULL);
  (*(void (*)(jint i1, jint i2, jint i3, jint i4, jint i5, jint i6, jint* i7))ptr)(i1,i2,i3,i4,i5,i6,i7ptr);
  (*e)->ReleaseIntArrayElements(e, i7, i7ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JIIIIIIII_3I
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jint i3, jint i4, jint i5, jint i6, jint i7, jint i8, jintArray i9)
{
  int *i9ptr = (*e)->GetIntArrayElements(e,i9,NULL);
  (*(void (*)(jint i1, jint i2, jint i3, jint i4, jint i5, jint i6, jint i7, jint i8, jint* i9))ptr)(i1,i2,i3,i4,i5,i6,i7,i8,i9ptr);
  (*e)->ReleaseIntArrayElements(e, i9, i9ptr, 0);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JFFFF
  (JNIEnv *e, jclass c, jlong ptr, jfloat f1, jfloat f2, jfloat f3, jfloat f4)
{
  (*(void (*)(jfloat f1, jfloat f2, jfloat f3, jfloat f4))ptr)(f1,f2,f3,f4);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JZ
  (JNIEnv *e, jclass c, jlong ptr, jboolean b1)
{
  (*(void (*)(jboolean b1))ptr)(b1);
}

JNIEXPORT void JNICALL Java_javaforce_jni_JFNative_invokeVoid__JZZZZ
  (JNIEnv *e, jclass c, jlong ptr, jboolean b1, jboolean b2, jboolean b3, jboolean b4)
{
  (*(void (*)(jboolean b1, jboolean b2, jboolean b3, jboolean b4))ptr)(b1,b2,b3,b4);
}

JNIEXPORT jint JNICALL Java_javaforce_jni_JFNative_invokeInt__J
  (JNIEnv *e, jclass c, jlong ptr)
{
  return (*(jint (*)())ptr)();
}

JNIEXPORT jint JNICALL Java_javaforce_jni_JFNative_invokeInt__JI
  (JNIEnv *e, jclass c, jlong ptr, jint i1)
{
  return (*(jint (*)(jint i1))ptr)(i1);
}

JNIEXPORT jint JNICALL Java_javaforce_jni_JFNative_invokeInt__JIII
  (JNIEnv *e, jclass c, jlong ptr, jint i1, jint i2, jint i3)
{
  return (*(jint (*)(jint i1, jint i2, jint i3))ptr)(i1,i2,i3);
}


JNIEXPORT jlong JNICALL Java_javaforce_jni_JFNative_invokeLong
  (JNIEnv *e, jclass c, jlong ptr, jint i1)
{
  return (*(jlong (*)(jint i1))ptr)(i1);
}
