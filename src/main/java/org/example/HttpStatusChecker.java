package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpStatusChecker {
    private OkHttpClient client = new OkHttpClient();

    public String getStatusImage(int code) throws Exception {
        String url = "https://http.cat/" + code + ".jpg";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return url;
            } else {
                throw new Exception("Image not found for status code: " + code);
            }
        } catch (Exception e) {
            throw new Exception("Error while making the request: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        HttpStatusChecker checker = new HttpStatusChecker();

        try {
            String imageUrl1 = checker.getStatusImage(200);
            System.out.println("Image URL for status 200: " + imageUrl1);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        try {
            String imageUrl2 = checker.getStatusImage(10000);
            System.err.println("Error");
        } catch (Exception e) {
            System.out.println("Image not found for status code.");
        }
    }
}