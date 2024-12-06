package day_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {

        String filePath = "day_3/resource_day_3.txt";
        ArrayList<String> listOfRegex = readAndFillData(filePath);

        int result = sum(listOfRegex);
        System.out.println("The result is: " + result);
    }

    public static ArrayList<String> readAndFillData(String filePath) {
        // define a Path object to contains filePath
        Path path = Paths.get(filePath);

        ArrayList<String> listOfRegex = new ArrayList<>();
        String regex = "mul\\((\\d+,(\\d+)\\))";

        // create Pattern project
        Pattern pattern = Pattern.compile(regex);

        try {
            BufferedReader reader = Files.newBufferedReader(path);
            String line;

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));

                    listOfRegex.add("mul(" + x + "," + y + ")");
                }
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return listOfRegex;
    }

    public static int sum(ArrayList<String> listRegex) {
        String regexForm = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regexForm);
        int sum = 0;

        for (String input : listRegex) {
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));

                int product = x * y;
                sum += product;
            }
        }
        return sum;
    }
}



