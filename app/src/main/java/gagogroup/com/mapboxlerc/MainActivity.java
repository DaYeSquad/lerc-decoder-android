package gagogroup.com.mapboxlerc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.gagogroup.lerc.core.Lerc;
import com.gagogroup.lerc.core.LercHeaderInfo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check read permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("app", "read permission denied");
        }

        // read lerc from SDCard
        File file = new File("/sdcard/california_400_400_1_float.lerc2");
        int size = (int) file.length();
        byte[] lercBytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(lercBytes, 0, lercBytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get lerc blob info
        LercHeaderInfo blobInfo = Lerc.getHeaderInfo(lercBytes);
        Log.e("app", blobInfo.toString());

        // decode lerc
        byte[] decodedBytes = Lerc.decode(lercBytes);

        FloatBuffer fb = ByteBuffer.wrap(decodedBytes).asFloatBuffer();
        float[] dst = new float[fb.capacity()];
        fb.get(dst);

        int[] pixels = new int[400 * 400];

        for (int i = 0; i < 400 * 400; i++) {
            pixels[i] = (int)Math.round((dst[i] + 82.98) / (4080.61 + 83) * 256);
        }

        Bitmap bmp = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        bmp.setPixels(pixels, 0, 400, 0, 0, 400, 400);

        // show bitmap on image view
        ImageView lercView = findViewById(R.id.lercImage);
        lercView.setImageBitmap(bmp);
    }
}
