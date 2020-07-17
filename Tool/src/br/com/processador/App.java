package br.com.processador;

import java.util.ArrayList;
import java.util.List;

public class App {
	private String packa_name;
	private String manifest_path;
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

	public String getManifest_path() {
		return manifest_path;
	}

	public void setManifest_path(String manifest_path) {
		this.manifest_path = manifest_path;
	}

}
