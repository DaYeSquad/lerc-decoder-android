#include "./common.h"

#include "./vendor/LercLib/Lerc.h"
#include "./vendor/LercLib/Lerc2.h"

extern "C"
JNIEXPORT jobject JNICALL
Java_com_gagogroup_lerc_core_Lerc_nativeGetHeaderInfo(JNIEnv *env, jobject instance,
                                                            jbyteArray lercDataJbytes) {
  int len = env->GetArrayLength(lercDataJbytes);
  unsigned char *lercData = new unsigned char[len];
  env->GetByteArrayRegion(lercDataJbytes, 0, len, reinterpret_cast<jbyte *>(lercData));

  LercNS::Lerc2::HeaderInfo headerInfo;

  LercNS::Lerc2::GetHeaderInfo(lercData, static_cast<size_t>(len), headerInfo);

  jclass headerInfoJCls = env->FindClass("com/gagogroup/lerc/core/LercHeaderInfo");
  jmethodID constructor = env->GetMethodID(headerInfoJCls, "<init>", "(IIDDI)V");

  jobject jHeaderInfoObj = env->NewObject(headerInfoJCls, constructor, headerInfo.nRows,
                                          headerInfo.nCols, headerInfo.zMin, headerInfo.zMax,
                                          headerInfo.dt);

  delete[] lercData;

  return jHeaderInfoObj;
}