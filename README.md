# OVERVIEW

A project demonstrates how to display lerc file as bitmap on Android


# USAGE

## Read lerc file information

```Java
LercHeaderInfo blobInfo = Lerc.getHeaderInfo(lercBytes);
Log.e("tag", blobInfo.toString()); // this will show the result
```

## Decode lerc file

```Java
byte[] decodedBytes = Lerc.decode(lercBytes);
```


# EXAMPLE DATA

The example data used in app is found at `https://github.com/Esri/lerc/tree/master/testData/california_400_400_1_float.lerc2`
