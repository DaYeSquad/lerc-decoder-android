#include "./common.h"

#include "./vendor/LercLib/Lerc.h"
#include "./vendor/LercLib/Lerc_c_api.h"

int DataTypeSize(LercNS::Lerc::DataType dt) {
  if (dt == LercNS::Lerc::DataType::DT_Char || dt == LercNS::Lerc::DataType::DT_Byte) {
    return 1;
  } else if (dt == LercNS::Lerc::DataType::DT_Float ||
      dt == LercNS::Lerc::DataType::DT_Int ||
      dt == LercNS::Lerc::DataType::DT_UInt) {
    return 8;
  } else if (dt == LercNS::Lerc::DataType::DT_Short || dt == LercNS::Lerc::DataType::DT_UShort) {
    return 4;
  } else if (dt == LercNS::Lerc::DataType::DT_Double) {
    return 16;
  }

  return 0;
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_gagogroup_lerc_core_Lerc_nativeGetHeaderInfo(JNIEnv *env, jobject instance,
                                                            jbyteArray lercDataJbytes) {
  int len = env->GetArrayLength(lercDataJbytes);
  unsigned char *lercData = new unsigned char[len];
  env->GetByteArrayRegion(lercDataJbytes, 0, len, reinterpret_cast<jbyte *>(lercData));

  LercNS::Lerc::LercInfo headerInfo;
  LercNS::Lerc::GetLercInfo(lercData, static_cast<unsigned int>(len), headerInfo);

  jclass headerInfoJCls = env->FindClass("com/gagogroup/lerc/core/LercHeaderInfo");
  jmethodID constructor = env->GetMethodID(headerInfoJCls, "<init>", "(IIDDI)V");

  jobject jHeaderInfoObj = env->NewObject(headerInfoJCls, constructor, headerInfo.nRows,
                                          headerInfo.nCols, headerInfo.zMin, headerInfo.zMax,
                                          headerInfo.dt);

  delete[] lercData;

  return jHeaderInfoObj;
}

extern "C"
JNIEXPORT jbyteArray JNICALL
Java_com_gagogroup_lerc_core_Lerc_nativeDecode(JNIEnv *env, jclass type, jbyteArray lercDataJbytes) {
  // get c bytes
  int len = env->GetArrayLength(lercDataJbytes);
  unsigned char *lercData = new unsigned char[len];
  env->GetByteArrayRegion(lercDataJbytes, 0, len, reinterpret_cast<jbyte *>(lercData));

  // read header
  LercNS::Lerc::LercInfo headerInfo;
  LercNS::Lerc::GetLercInfo(lercData, static_cast<unsigned int>(len), headerInfo);

  int decodedSize = DataTypeSize(headerInfo.dt) * headerInfo.nCols * headerInfo.nRows
      * headerInfo.nDim * headerInfo.nBands;

  unsigned char *decodedBuf = new unsigned char[decodedSize];

  lerc_decode(lercData, static_cast<unsigned int>(len), 0, headerInfo.nDim, headerInfo.nCols,
              headerInfo.nRows, headerInfo.nBands, headerInfo.dt, decodedBuf);

  jbyteArray result = env->NewByteArray(decodedSize);
  env->SetByteArrayRegion(result, 0, decodedSize, reinterpret_cast<jbyte *>(decodedBuf));

  delete[] lercData;
  delete[] decodedBuf;

  return result;
}