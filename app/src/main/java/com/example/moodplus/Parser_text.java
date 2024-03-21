package com.example.moodplus;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Parser_text {
    public static String getRandomLineFromRaw(Context context, int resourceId) {
        List<String> lines = readRawFile(context, resourceId);
        if (!lines.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(lines.size());
            return lines.get(randomIndex);
        } else {
            return null;
        }
    }

    private static List<String> readRawFile(Context context, int resourceId) {
        List<String> lines = new ArrayList<>();
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lines;
    }
}
