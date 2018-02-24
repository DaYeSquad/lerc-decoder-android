#OVERVIEW

A project demonstrates how to display lerc file as bitmap on Android


# USAGE

## Read lerc file information

```Java
LercHeaderInfo blobInfo = Lerc.getHeaderInfo(lercBytes);
Log.e("tag", blobInfo.toString()); // this will show the result
```
