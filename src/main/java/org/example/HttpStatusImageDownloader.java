package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpStatusImageDownloader {
    private OkHttpClient client = new OkHttpClient();

    public void downloadStatusImage(int code) throws Exception {
        HttpStatusChecker checker = new HttpStatusChecker();
        String imageUrl = checker.getStatusImage(code);

        if (imageUrl != null) {
            Request request = new Request.Builder()
                    .url(imageUrl)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    saveImage(response.body(), code + ".jpg");
                } else {
                    throw new Exception("Failed to download image for status code: " + code);
                }
            } catch (Exception e) {
                throw new Exception("Error while making the request: " + e.getMessage());
            }
        } else {
            throw new Exception("Image not found for status code:" + code);
        }
    }

    private void saveImage(ResponseBody body, String fileName) throws IOException {
        try (InputStream in = body.byteStream();
             BufferedInputStream bis = new BufferedInputStream(in);
             FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] data = new byte[1024];
            int count;
            while ((count = bis.read(data)) != -1) {
                fos.write(data, 0, count);
            }
            System.out.println("Image downloaded and saved as: " + fileName);
        }
    }

    public static void main(String[] args) {
        HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();

        try {
            downloader.downloadStatusImage(200);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        try {
            downloader.downloadStatusImage(404);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
