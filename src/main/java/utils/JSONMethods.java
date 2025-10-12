package utils;

import com.google.gson.Gson;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONMethods {

    public static String getJSONLocation() {
        File jarFile;
        try {
            jarFile = new File(JSONMethods.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String jarDir = jarFile.getParentFile().getAbsolutePath();
        return jarDir + File.separator + "expenses.json";
    }

    public static void saveState(ExpenseTracker expenseTracker) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(expenseTracker);
        String filePath = getJSONLocation();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonString);
            System.out.println("File content replaced successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static ExpenseTracker loadState() {
        Gson gson = new Gson();
        String filePath = getJSONLocation();
        Path path = Paths.get(filePath);
        try {
            // Si el archivo NO existe o está vacío → devolver nuevo ExpenseTracker(0)
            if (!Files.exists(path) || Files.size(path) == 0) {
                System.out.println("File not found or empty. Creating new ExpenseTracker...");
                return new ExpenseTracker(0);
            }
            try (Reader reader = Files.newBufferedReader(path)) {
                ExpenseTracker expenseTracker = gson.fromJson(reader, ExpenseTracker.class);
                if (expenseTracker == null) {
                    System.out.println("JSON file was empty or invalid. Creating new ExpenseTracker...");
                    return new ExpenseTracker(0);
                }
                System.out.println("Object loaded successfully:");
                return expenseTracker;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading file. Creating new ExpenseTracker...");
            return new ExpenseTracker(0);
        }
    }
}
