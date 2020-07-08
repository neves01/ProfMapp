package br.com.processador;

import java.util.ArrayList;
import java.util.List;

public class Widget {
	private String id;
	private String tag;
	private String source;
	private List<Accessibility> list_accessibility;

	public Widget() {
		list_accessibility = new ArrayList<Accessibility>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Accessibility> getAccessibility() {
		return this.list_accessibility;
	}

	public String toStringList() {
		StringBuilder sb = new StringBuilder();
		for (Accessibility a : this.list_accessibility) {
			sb.append(a.getAttribute());
		}

		return sb.toString();
	}

	public boolean attributeIsHere(String attribute) {
		for (Accessibility a : this.list_accessibility) {
			if (a.getAttribute().toLowerCase().contains(attribute.toLowerCase()))
				return true;
		}
		return false;
	}

	public void addAccessibility(String att) {
		this.list_accessibility.add(new Accessibility(att));
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
