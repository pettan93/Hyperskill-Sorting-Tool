package sorting.tools;

import java.util.ArrayList;
import java.util.Comparator;

public class WordSortingTool extends SortingTool<String> {

    public WordSortingTool() {
        super(Comparator.naturalOrder());
    }

    public WordSortingTool(ArrayList<String> elements) {
        super(elements, Comparator.naturalOrder());
    }

    public String getResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total words: ").append(elements.size()).append(".").append("\n");
        sb.append(getSortResult());
        return sb.toString();
    }
}
