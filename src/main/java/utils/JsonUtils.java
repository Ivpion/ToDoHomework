package utils;

import com.google.gson.Gson;

import java.io.*;

public class JsonUtils {
    private static final Gson GSON = new Gson();

    public static String jsonToString(InputStream requestBody) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(requestBody))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T jsonStreamToObj(InputStream requestBody, Class<T> cl) {
        return GSON.fromJson(new InputStreamReader(requestBody), cl);
    }

    public static <T> T toObject(String jsonBody, Class<T> recordClass) {
        return GSON.fromJson(jsonBody, recordClass);
    }

    public static String toJson(Object saved) {
        return GSON.toJson(saved);
    }

    public static String readJsonFromFile(String filePath) {

        // todo find some library that has already implemented such logic
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath)))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean writeJsonToFile(String path, String json) {


        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) {

            writer.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
