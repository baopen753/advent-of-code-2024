package day_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
        String filePath = "day_4/resource_day_4.txt";

        ArrayList<String> grid = readAndFillData(filePath);
        String targetWord = "XMAS";

        ArrayList<List<Integer>> coordinateOfFirstMatchCharacter = getCoordinateOfTheFirstMatchedCharacter(grid, targetWord);
        HashMap<List<Integer>, List<List<Integer>>> siteMap = calculateValidatedDirectionOfEachMatchedFirstCharacter(coordinateOfFirstMatchCharacter, grid, targetWord);

        int countOccurrences = totalOccurrence(siteMap);
        System.out.println("The word " + targetWord + " appears " + countOccurrences + " times.");
    }

    public static ArrayList<String> readAndFillData(String filePath) {
        ArrayList<String> datas = new ArrayList<>();

        try {
            Path path = Paths.get(filePath);
            BufferedReader reader = Files.newBufferedReader(path);

            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                datas.add(line);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return datas;
    }

    public static ArrayList<List<Integer>> getCoordinateOfTheFirstMatchedCharacter(ArrayList<String> grid, String targetWord) {
        ArrayList<List<Integer>> result = new ArrayList<>();
        for (int row = 0; row < grid.size(); row++) {
            for (int col = 0; col < grid.get(row).length(); col++) {
                if (grid.get(row).charAt(col) == targetWord.charAt(0)) {
                    List<Integer> coordinates = List.of(row, col);
                    result.add(coordinates);
                }
            }
        }
        return result;
    }

    public static int totalOccurrence(HashMap<List<Integer>, List<List<Integer>>> siteMap) {
        int count = 0;
        for (Map.Entry<List<Integer>, List<List<Integer>>> entry : siteMap.entrySet()) {
            System.out.println(entry.getKey());

            entry.getValue().forEach(coordinate -> System.out.println(coordinate.get(0) + ", " + coordinate.get(1)));
            count += entry.getValue().size();
        }
        return count;
    }


    public static HashMap<List<Integer>, List<List<Integer>>> calculateValidatedDirectionOfEachMatchedFirstCharacter(ArrayList<List<Integer>> coordinates, ArrayList<String> grid, String targetWord) {
        HashMap<List<Integer>, List<List<Integer>>> siteMap = new HashMap<>();

        // get grid dimensions
        int rowSize = grid.size();
        int colSize = grid.get(0).length();

        // define 8 directions:
        List<int[]> directions = List.of(
                new int[]{-1, -1},   // top-left
                new int[]{-1, 0},    // top
                new int[]{-1, 1},    // top-right
                new int[]{0, 1},     // right
                new int[]{1, 1},     // bottom-right
                new int[]{1, 0},     // bottom
                new int[]{1, -1},    // bottom-left
                new int[]{0, -1}     // left
        );

        // Time Complexity: O(n*8*n) ~ O(n2)
        for (List<Integer> coordinate : coordinates) {
            List<List<Integer>> result = new ArrayList<>();
            for (int[] direction : directions) {
                List<Integer> coordinateOfLastMatchedCharacter = new ArrayList<>();

                int currentRow = coordinate.get(0);
                int currentCol = coordinate.get(1);

                boolean isWordFound = true; // Assume the word can be found in the current direction

                for (int k = 1; k < targetWord.length(); k++) {
                    currentRow += direction[0];
                    currentCol += direction[1];

                    if (currentRow < 0 || currentRow >= rowSize || currentCol < 0 || currentCol >= colSize) {
                        isWordFound = false;
                        break;
                    }

                    if (grid.get(currentRow).charAt(currentCol) != targetWord.charAt(k)) {
                        isWordFound = false;
                        break;
                    }
                }

                if (isWordFound) {
                    // store coordinate of last character
                    coordinateOfLastMatchedCharacter = List.of(currentRow, currentCol);

                    // store coordinate of last character in
                    result.add(coordinateOfLastMatchedCharacter);
                }
            }
            siteMap.put(List.of(coordinate.get(0), coordinate.get(1)), result);
        }
        return siteMap;
    }
}



