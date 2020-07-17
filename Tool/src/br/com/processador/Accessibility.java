package br.com.processador;

public class Accessibility {
	private String attribute;
	private String principle;

	public Accessibility(String att) {
		this.attribute = att;
	}

	public Accessibility(String att, String principle) {
		this.attribute = att;
		this.principle = principle;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getPrinciple() {
		return principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

}
