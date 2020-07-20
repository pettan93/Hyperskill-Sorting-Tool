package sorting.tools;

import sorting.model.SortingType;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public abstract class SortingTool<T> {

    protected ArrayList<T> elements;

    protected Comparator<T> comparator;

    protected SortingType sortingType;

    public SortingTool(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public SortingTool(ArrayList<T> elements, Comparator<T> comparator) {
        this.elements = elements;
        this.comparator = comparator;
    }

    public void setElements(ArrayList<T> elements) {
        this.elements = elements;
    }

    public long getLength() {
        return elements.size();
    }

    public T getMax() {
        Optional<T> max = elements.stream().max(comparator);
        if (max.isEmpty()) {
            throw new RuntimeException();
        }
        return max.get();
    }

    public void sort(SortingType sortingType) {
        this.sortingType = sortingType;
        elements.sort(comparator);
    }

    public long occurrenceOf(T element) {
        return elements.stream().filter(e -> e.equals(element)).count();
    }

    public String getPercentageOccurrenceOf(long occurrence) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);
        return df.format((double) occurrence / getLength() * 100) + "%";
    }

    public abstract String getResults();

    public String getSortResult() {
        StringBuilder sb = new StringBuilder();
        if (SortingType.NATURAL.equals(sortingType)) {
            sb.append("Sorted data: ");
            elements.forEach(e -> sb.append(e).append(" "));
        }
        if (SortingType.BY_COUNT.equals(sortingType)) {
            sb.append(getSortByCountResultOutput());
        }
        return sb.toString();
    }

    private String getSortByCountResultOutput() {
        Map<T, Long> occurrenceMap = new HashMap<>();
        elements.stream().distinct().forEach(e -> {
            occurrenceMap.put(e, occurrenceOf(e));
        });

        // firstly compare by occurrence, then compare by given natural comparator
        Comparator<T> c = Comparator.comparing(occurrenceMap::get, Comparator.naturalOrder());
        c = c.thenComparing(comparator);

        return occurrenceMap.keySet()
                .stream()
                .distinct()
                .sorted(c)
                .map(e -> {
                    StringBuilder sb = new StringBuilder();
                    long occurrence = occurrenceOf(e);
                    sb.append(e).append(": ").append(occurrence).append(" times(s), ").append(getPercentageOccurrenceOf(occurrence));
                    return sb.toString();
                })
                .collect(Collectors.joining("\n"));
    }


}
