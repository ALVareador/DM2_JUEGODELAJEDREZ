package org.uvigo.dm2_juego_del_ajedrez;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class BitmapUploader {
    /**Convierte una skin en los assets EXAMPLE: context, imageffffff#000000.png*/
    public static Bitmap bitmapFromAssets(Context context, String imagePath)
    {
        InputStream stream = null;
        try
        {
            stream = context.getAssets().open(imagePath);
            return BitmapFactory.decodeStream(stream);
        }
        catch (Exception ignored) {}
        finally
        {
            try
            {
                if(stream != null)
                {
                    stream.close();
                }
            } catch (Exception ignored) {}
        }
        return null;
    }
}
