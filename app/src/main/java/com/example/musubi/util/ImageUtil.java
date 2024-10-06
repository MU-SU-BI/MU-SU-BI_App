package com.example.musubi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {
    public static Uri byteStringToUri(Context context, String byteString, String fileName) {
        byte[] byteArray = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            byteArray = Base64.getDecoder().decode(byteString);
        }

        File file = new File(context.getFilesDir(), fileName);

        try {
            // byte 배열을 Bitmap으로 디코딩
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            if (bitmap == null) {
                throw new IOException("Failed to decode byte array to Bitmap");
            }

            FileOutputStream fos = new FileOutputStream(file);
            // Bitmap을 PNG 형식으로 파일에 저장
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            // FileProvider를 사용하여 Uri 생성
            return FileProvider.getUriForFile(
                    context,
                    context.getApplicationContext().getPackageName() + ".fileprovider",
                    file
            );
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
