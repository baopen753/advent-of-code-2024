import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class Solution {
    public static void main(String[] args) {
        String filePath = "/day_2/resource_day_2.txt";

        ArrayList<ArrayList<Integer>> datas = readAndFillData(filePath);
        Set<Integer> distanceRule = getDistanceRule();
        AtomicInteger count = new AtomicInteger(0);

        datas.forEach(data -> {
            if (isSafe(data, distanceRule)) {
                count.getAndIncrement();
            }
        });

        System.out.println("The number of safe report: " + count.get());
    }

    public static ArrayList<ArrayList<Integer>> readAndFillData(String filePath) {
        ArrayList<ArrayList<Integer>> datas = new ArrayList<>();

        try {
            Path path = Paths.get(filePath);
            BufferedReader reader = Files.newBufferedReader(path);

            String line;

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] tokens = line.trim().split("\\s");

                ArrayList<Integer> data = fillDataFromLine(tokens);
                datas.add(data);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return datas;
    }

    public static ArrayList<Integer> fillDataFromLine(String[] tokens) {
        ArrayList<Integer> data = new ArrayList<>();
        for (String token : tokens) {
            data.add(Integer.parseInt(token));
        }
        return data;
    }

    public static Set<Integer> getDistanceRule() {
        Set<Integer> distanceRule = new HashSet<>();
        distanceRule.add(1);
        distanceRule.add(2);
        distanceRule.add(3);
        return distanceRule;
    }

    public static boolean isSafe(ArrayList<Integer> data, Set<Integer> distanceRule) {

        // make sure row of data has 2 elements or above
        if (data.size() < 2) return false;

        // make sure f'(i) != 0
        int keyTwin = data.get(0) - data.get(1);
        if (keyTwin == 0) return false;

        for (int i = 0; i < data.size() - 1; i++) {

            // make sure f'(i) > 0
            if (keyTwin * (data.get(i) - data.get(i + 1)) <= 0) return false;

            // make sure the differ between numbers adhere to distanceRule
            int actualDistance = Math.abs(data.get(i) - data.get(i + 1));
            if (!distanceRule.contains(actualDistance)) return false;
        }
        return true;
    }
}
