package sorting.tools;

import java.util.ArrayList;

public class LongSortingTool extends SortingTool<Long> {

    public LongSortingTool() {
        super(Long::compare);
    }

    public LongSortingTool(ArrayList<Long> elements) {
        super(elements, Long::compare);
    }

    public String getResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total numbers: ").append(elements.size()).append(".").append("\n");
        sb.append(getSortResult());
        return sb.toString();
    }
}
