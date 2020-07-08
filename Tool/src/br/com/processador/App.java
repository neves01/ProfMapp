package br.com.processador;

import java.util.ArrayList;
import java.util.List;

public class App {
	private String packa_name;
	private List<Widget> elements;

	App() {
		this.elements = new ArrayList<Widget>();
	}

	public String getPacka_name() {
		return packa_name;
	}

	public void setPacka_name(String packa_name) {
		this.packa_name = packa_name;
	}

	public List<Widget> getElements() {
		return elements;
	}

	public void setElements(List<Widget> elements) {
		this.elements = elements;
	}

	public boolean checkWidget(String id) {
		for (Widget w : this.elements) {
			if (w.getId().equalsIgnoreCase(id))
				return true;
		}
		return false;
	}

}
