package com.oleksa.model.entity;

import java.util.Objects;

public class Comment extends AbstractEntity<Integer> {

	private String text;
	private int stars;
	
	public Comment(Integer id, String text, int stars) {
		super(id);
		this.text = text;
		this.stars = stars;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, text, stars);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(text, other.text)
				&& Objects.equals(stars, other.stars);
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", text=" + text + ", stars=" + stars + "]";
	}
	
}
