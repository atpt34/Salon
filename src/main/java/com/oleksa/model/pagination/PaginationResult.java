package com.oleksa.model.pagination;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class represents current page of items,
 * total pages and number of items.
 * 
 * @author atpt34
 *
 * @param <T> type
 */
public final class PaginationResult<T> {

    private final List<T> items;
    private final int total;
    private final int page;
    private final int totalPages;

    public PaginationResult(List<T> items, int page, int itemsOnPage, int total) {
        this.items = items;
        this.total = total;
        this.page = page;
        this.totalPages = total / itemsOnPage + ((total % itemsOnPage == 0) ? 0 : 1);
    }

    public List<T> getSortedItems(Comparator<? super T> comparator) {
        return items.stream().sorted(comparator).collect(Collectors.toList());
    }

    public <U> Map<U, List<T>> getGroupedItems(Function<T, U> classifier) {
        return items.stream().collect(Collectors.groupingBy(classifier));
    }

    public List<T> getItems() {
        return items;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public String toString() {
        return "PaginationResult [items=" + items + ", total=" + total + ", page=" + page + ", totalPages=" + totalPages
                + "]";
    }

}
