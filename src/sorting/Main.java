package sorting;

import sorting.model.SortingDataType;
import sorting.model.SortingType;
import sorting.tools.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.exit;

public class Main {

    public static SortingType defaultSortingType = SortingType.NATURAL;
    public static SortingDataType defaultSortingDataType = SortingDataType.LINE;

    public static void main(final String[] args) throws IOException {

        var argsMap = parseCmdArgs(args);
        checkIllegalArgs(argsMap);

        var sortingType = getSortingType(argsMap);
        var scanner = getScanner(argsMap);
        var sortingDataType = SortingDataType.byArgName(argsMap.get("-dataType")).orElse(defaultSortingDataType);
        var sortingTool = getByDataType(sortingDataType);
        var elements = processInput(sortingDataType, scanner);
        scanner.close();

        sortingTool.setElements(elements);
        sortingTool.sort(sortingType);

        PrintWriter out = argsMap.containsKey("-outputFile") ?
                new PrintWriter(new FileOutputStream(new File(argsMap.get("-outputFile")), true)) :
                new PrintWriter(System.out, true);

        out.println(sortingTool.getResults());
        // not closing output because JetBrains tests can't see system output if its closed after usage
        // out.close();
    }

    private static SortingTool getByDataType(SortingDataType sortingDataType) {
        switch (sortingDataType) {
            case LONG:
                return new LongSortingTool();
            case WORD:
                return new WordSortingTool();
            case INTEGER:
                return new IntegerSortingTool();
            default:
                return new LineSortingTool();
        }
    }

    private static Scanner getScanner(HashMap<String, String> argsMap) throws IOException {
        if (argsMap.containsKey("-inputFile")) {
            // contents of original input file copied to temporary file because of JetBrains test problem
            // test #13 can't pass - input file can't be deleted (although scanner is closed)
            var originalFile = new File("" + argsMap.get("-inputFile").trim());
            var tempFile = File.createTempFile("tmp", "dat");

            Files.copy(originalFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            originalFile.delete();
            tempFile.deleteOnExit();
            return new Scanner(tempFile);
        } else {
            return new Scanner(System.in);
        }
    }

    private static ArrayList<?> processInput(SortingDataType sortingDataType, Scanner scanner) {
        switch (sortingDataType) {
            case LONG: {
                ArrayList<Long> elements = new ArrayList<>();
                while (scanner.hasNext()) {
                    String token = null;
                    try {
                        token = scanner.next();
                        long number = Long.parseLong(token);
                        elements.add(number);
                    } catch (NumberFormatException e) {
                        System.out.println("\"" + token + "\" isn't a long. It's skipped.");
                    }
                }
                return elements;
            }
            case WORD: {
                ArrayList<String> elements = new ArrayList<>();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    elements.addAll(Arrays.asList(line.split("\\s+")));
                }
                return elements;
            }
            case INTEGER: {
                ArrayList<Integer> elements = new ArrayList<>();
                while (scanner.hasNextLong()) {
                    int number = scanner.nextInt();
                    elements.add(number);
                }
                return elements;
            }
            default: {
                ArrayList<String> elements = new ArrayList<>();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    elements.add(line);
                }
                return elements;
            }
        }
    }

    private static SortingType getSortingType(HashMap<String, String> argsMap) {
        SortingType sortingType = defaultSortingType;
        if (argsMap.containsKey("-sortingType")) {
            if (argsMap.get("-sortingType") == null) {
                System.out.println("No sorting type defined!");
                exit(0);
            }
            sortingType = argsMap.get("-sortingType")
                    .equalsIgnoreCase("byCount") ?
                    SortingType.BY_COUNT :
                    SortingType.NATURAL;
        }
        return sortingType;
    }

    private static void checkIllegalArgs(HashMap<String, String> argsMap) {
        Set<String> argsWhiteList = Stream.of("-sortingType", "-dataType", "-inputFile", "-outputFile")
                .collect(Collectors.toCollection(HashSet::new));
        argsMap.keySet().stream()
                .filter(k -> !argsWhiteList.contains(k))
                .filter(k -> k.startsWith("-"))
                .forEach(k -> System.out.println("\"" + k + "\" isn't a valid parameter. It's skipped."));
    }

    public static HashMap<String, String> parseCmdArgs(String[] args) {
        return IntStream.range(0, args.length)
                .collect(HashMap::new,
                        (m, i) -> m.put(args[i], i + 1 == args.length ? null : args[i + 1]),
                        Map::putAll);
    }
}



