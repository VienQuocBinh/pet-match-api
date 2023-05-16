package petmatch.configuration.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class InvalidArgumentException extends RuntimeException{
    public InvalidArgumentException(Class<?> clazz, Object... searchParamsMap) {
        super(InvalidArgumentException.generateMessage(clazz.getSimpleName(), toMap(searchParamsMap)));
    }

    private static String generateMessage(String keyword, Map<String, Object> searchParams) {
        return keyword +
                " was not found for parameters " +
                searchParams;
    }

    private static Map<String, Object> toMap(Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(String.valueOf(entries[i]), String.valueOf(entries[i + 1])),
                        Map::putAll);
    }
}
