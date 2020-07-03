package br.com.processador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Principal {

	static List<App> apps_map = new ArrayList<App>();

	public static void main(String[] args) throws Exception {

		/*
		 * OBJETOS ---------------------------------------------------------------------
		 */
		Projeto projeto = new Projeto();
		Diretorios diretorios = new Diretorios();
		Arquivos arquivos = new Arquivos();
		Desafios desafios = new Desafios();
		Sensores sensores = new Sensores();
		Recursos recursos = new Recursos();
		Frameworks frameworks = new Frameworks();
		Configuracao configuracao = new Configuracao();
		Metricas metricas = new Metricas();
		Layouts layouts = new Layouts();

		Redator redator = new Redator();
		StringBuilder sbRelatorioFinal = new StringBuilder();
		StringBuilder sbRelatorioDesafios = new StringBuilder();
		StringBuilder sbCSV = new StringBuilder();
		StringBuilder log = new StringBuilder();

		HashMap<String, Integer> map = null;
		HashMap<String, HashMap<String, Integer>> mapFilteredByProject = new HashMap<String, HashMap<String, Integer>>();

		/*
		 * VARIAVEIS
		 * ---------------------------------------------------------------------
		 */
		// String repositorio = "/home/bernardo/Repositorio/";
		String repositorio = "/home/henrique/Experiment/filter-f-droid";
		// String repositorio = "/home/henrique/Experiment/apps";
		// String repositorio = ".";

		/*
		 * MAPEAR PROJETOS
		 * ---------------------------------------------------------------------
		 */
		for (Projeto p : projeto.listarProjetos(repositorio)) {
			log = new StringBuilder();

			App a = new App();
			a.setPacka_name(p.getNome());
			System.out.println("Entrando no projeto: " + p.getCaminho());

			diretorios = new Diretorios();
			diretorios = diretorios.mapearDirs(p);

			arquivos = new Arquivos();
			arquivos = arquivos.mapearArqs(diretorios);

			desafios = new Desafios();
			desafios = desafios.mapearDesafios(arquivos);

			map = new HashMap<String, Integer>();
			sensores = new Sensores();
			sensores = sensores.mapearSensores(arquivos, apps_map, a);

			frameworks = new Frameworks();
			frameworks = frameworks.mapearFrameworks(map, arquivos);

			configuracao = new Configuracao();
			configuracao = configuracao.mapearConfiguracao(arquivos);

			recursos = new Recursos();
			recursos = recursos.mapearOtimizacao(diretorios);

			metricas = new Metricas();
			metricas = metricas.mapearMetricas(arquivos);

			layouts = new Layouts();

			layouts = layouts.mapearLayout(arquivos, apps_map, a);
			apps_map.add(a);

			mapFilteredByProject.put(p.getNome(), map);

			sbCSV.append(redator.escreverCSV(arquivos, desafios, sensores, frameworks, configuracao, recursos, metricas,
					layouts));
			sbRelatorioFinal.append(redator.escreverRelatorio(arquivos, desafios, sensores, frameworks, configuracao,
					recursos, metricas, layouts));
			sbRelatorioDesafios.append(redator.escreverDesafios(desafios));
			log.append(redator.escreverLog(arquivos, desafios, sensores, frameworks, configuracao, recursos, metricas,
					layouts));
			redator.escrita(log, diretorios.getProjeto().getNome() + "_log.txt");
		}

		// TODO: DESCOMENTAR
		// redator.escrita(sbCSV, "Relatorio.csv");
		// redator.escrita(sbRelatorioFinal, "Relatorio_Final.txt");
		// redator.escrita(sbRelatorioDesafios, "RelatorioDesafios.csv");

		int hint = 0, contentDescription = 0, labelFor = 0, minWidth = 0, minHeight = 0, inputType = 0,
				accessibilityLiveRegion = 0;

		int editText = 0, imageView = 0, imageButton = 0, button = 0, textView = 0;
		for (App a : apps_map) {
			System.out.println(a.getPacka_name());
			for (Widget w : a.getElements()) {
				for (Accessibility acc : w.getAccessibility()) {
					if (acc.getAttribute().contains("hint")) {
						hint++;
						if (acc.getAttribute().contains("contentDescription"))
							contentDescription++;
						if (acc.getAttribute().contains("labelFor"))
							labelFor++;
						if (acc.getAttribute().contains("minWidth"))
							minWidth++;
						if (acc.getAttribute().contains("minHeight"))
							minHeight++;
						if (acc.getAttribute().contains("inputType"))
							inputType++;
						if (acc.getAttribute().contains("accessibilityLiveRegion"))
							accessibilityLiveRegion++;
					}

					// System.out.println("\tID: " + w.getId() + " TAG: " + w.getTag() + " ACCESS: "
					// + w.toStringList());
					if (w.getTag().contains("EditText"))
						editText++;
					if (w.getTag().contains("ImageView"))
						imageView++;
					if (w.getTag().contains("ImageButton"))
						imageButton++;
					if (w.getTag().contains("Button"))
						button++;
					if (w.getTag().contains("TextView"))
						textView++;

				}
			}

			System.out.println("TOTAL APPS: " + apps_map.size());
			System.out.println("XML_EDITTEXT: " + editText);
			System.out.println("XML_IMAGEVIEW: " + imageView);
			System.out.println("XML_IMAGEBUTTON: " + imageButton);
			System.out.println("XML_BUTTON: " + button);
			System.out.println("XML_HINT: " + hint);
			System.out.println("XML_CONTENTDESCRIPTION: " + contentDescription);
			System.out.println("XML_LABELFOR: " + labelFor);
			System.out.println("XML_MINWIDTH: " + minWidth);
			System.out.println("XML_MINHEIGHT: " + minHeight);
			System.out.println("XML_INPUTTYPE: " + inputType);
			System.out.println("XML_ACCESSIBILITYLIVEREGION: " + accessibilityLiveRegion);

			redator.accessibilityReport(apps_map);
		}
	}
}