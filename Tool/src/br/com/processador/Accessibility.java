package br.com.processador;

public class Accessibility {
	private String attribute;

	public Accessibility(String att) {
		this.attribute = att;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

}
