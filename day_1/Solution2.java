import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class Solution2 {

    public static void main(String[] args) {
        String pathFile = "/resource_day_1.txt";
        ArrayList<int[]> datas = readAndFillData(pathFile);

        ArrayList<Integer> leftNums = new ArrayList<>();
        ArrayList<Integer> rightNums = new ArrayList<>();

        // convert primitive arrays to object arrays --> in order to use to sort
        datas.forEach(data -> {
            leftNums.add(data[0]);
            rightNums.add(data[1]);
        });


        HashMap<Integer, Integer> mapLeft = fillToHashMap(datas, 0);
        HashMap<Integer, Integer> mapRight = fillToHashMap(datas, 1);

        int result = getResult(mapLeft, mapRight);
        System.out.println("The result is: " + result);

    }

    public static ArrayList<int[]> readAndFillData(String filePath) {

        ArrayList<int[]> datas = new ArrayList<>();

        try {
            Path path = Paths.get(filePath);
            BufferedReader reader = Files.newBufferedReader(path);

            String line;

            while ((line = reader.readLine()) != null && !line.isEmpty()) {    // don't count the trailing newline
                String[] tokens = line.split("\\s+");    // split by whitespace, even with multiple whitespaces

                // each tokens is a line within the text file
                int[] data = fillDataFromLine(tokens);
                datas.add(data);
            }

        } catch (IOException ioException) {
            ioException.getMessage();
        }
        return datas;
    }

    public static int[] fillDataFromLine(String[] tokens) {
        int[] dataLine = new int[tokens.length + 1];
        for (int i = 0; i < tokens.length; i++) {
            dataLine[i] = Integer.parseInt(tokens[i]);
        }
        return dataLine;
    }

    public static HashMap<Integer, Integer> fillToHashMap(ArrayList<int[]> datas, int index) {
        HashMap<Integer, Integer> maps = new HashMap<>();
        datas.forEach(data -> {
            maps.put(data[index], maps.getOrDefault(data[index], 0) + 1);
        });
        return maps;
    }

    public static Integer getResult(HashMap<Integer, Integer> leftNums, HashMap<Integer, Integer> rightNums) {
        int sum = 0;
        for (HashMap.Entry<Integer, Integer> entry : leftNums.entrySet()) {
            if (rightNums.containsKey(entry.getKey()))
                sum += entry.getValue() * entry.getKey() * rightNums.get(entry.getKey());
        }
        return sum;
    }

}

