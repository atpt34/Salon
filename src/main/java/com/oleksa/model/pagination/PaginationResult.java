package com.oleksa.model.pagination;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaginationResult<T> {

	private List<T> items;
	private int total;
	private int page;
	private int totalPages;
	
	
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

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "PaginationResult [items=" + items + ", total=" + total + ", page=" + page + ", totalPages=" + totalPages
				+ "]";
	}


	
}
