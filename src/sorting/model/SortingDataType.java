package sorting.model;

import java.util.Optional;

public enum SortingDataType {
    LONG,
    WORD,
    INTEGER,
    LINE;

    static public Optional<SortingDataType> byArgName(String argName) {
        return argName == null ? Optional.empty() :
                Optional.of(SortingDataType.valueOf(argName.trim().toUpperCase()));
    }
}
