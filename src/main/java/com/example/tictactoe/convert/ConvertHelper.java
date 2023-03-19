package com.example.tictactoe.convert;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConvertHelper {
    public static <E, D> List<D> toList(Collection<E> collection, Function<E, D> convertFunction) {
        if (collection == null) {
            return null;
        }
        return toList(collection.stream(), convertFunction);
    }

    public static <E, D> List<D> toList(Stream<E> stream, Function<E, D> convertFunction) {
        return stream
                .map(convertFunction)
                .collect(Collectors.toList());
    }
}
