package sorting.tools;

import java.util.ArrayList;
import java.util.Comparator;

public class LineSortingTool extends SortingTool<String> {

    public LineSortingTool() {
        super(Comparator.naturalOrder());
    }

    public LineSortingTool(ArrayList<String> elements) {
        super(elements, Comparator.naturalOrder());
    }

    public String getResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total lines: ").append(elements.size()).append(".").append("\n");
        sb.append(getSortResult());
        return sb.toString();
    }
}
