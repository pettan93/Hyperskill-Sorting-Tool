package sorting.tools;

import java.util.ArrayList;

public class IntegerSortingTool extends SortingTool<Integer> {

    public IntegerSortingTool() {
        super(Integer::compare);
    }

    public IntegerSortingTool(ArrayList<Integer> elements) {
        super(elements, Integer::compare);
    }

    public String getResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total numbers: ").append(elements.size()).append(".").append("\n");
        sb.append(getSortResult());
        return sb.toString();
    }

}


