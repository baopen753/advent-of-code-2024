import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution1 {
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

        // sorting both object arrays
        leftNums.sort(Integer::compareTo);
        rightNums.sort(Integer::compareTo);

        // fill sorted leftNum into datas
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i)[0] = leftNums.get(i);
        }

        // fill sorted rightNum into datas
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i)[1] = rightNums.get(i);
        }


        // calculate sum of subtraction
        datas.forEach(data -> {
            data[2] = data[1] - data[0];
        });

        AtomicInteger sum = new AtomicInteger();
        datas.forEach(data -> {
            sum.addAndGet(Math.abs(data[2]));
        });


        datas.forEach(data -> {
            System.out.println(data[0] + " " + data[1] + " " + data[2]);
        });

        System.out.println("This is the answer: " + sum.get());
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
}
