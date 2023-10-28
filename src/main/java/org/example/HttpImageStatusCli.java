package org.example;

import java.util.Scanner;

public class HttpImageStatusCli {
    private HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();
    private Scanner scanner = new Scanner(System.in);

    public void askStatus() {
        String input;
        System.out.println("Enter an HTTP status code or type 'close' to exit:");

        while (true) {
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("close") || input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int statusCode = Integer.parseInt(input);
                try {
                    downloader.downloadStatusImage(statusCode);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number or type 'close' to exit.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        HttpImageStatusCli cli = new HttpImageStatusCli();
        cli.askStatus();
    }
}
