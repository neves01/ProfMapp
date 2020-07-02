package br.com.processador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Principal {

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

		List<App> apps_map = new ArrayList<App>();

		/*
		 * VARIAVEIS
		 * ---------------------------------------------------------------------
		 */
		// String repositorio = "/home/bernardo/Repositorio/";
		// String repositorio = "/home/henrique/Experiment/filter-f-droid";
		String repositorio = "/home/henrique/Experiment/apps";
		// String repositorio = ".";

		/*
		 * MAPEAR PROJETOS
		 * ---------------------------------------------------------------------
		 */
		for (Projeto p : projeto.listarProjetos(repositorio)) {
			log = new StringBuilder();
			System.out.println("Entrando no projeto: " + p.getCaminho());

			diretorios = new Diretorios();
			diretorios = diretorios.mapearDirs(p);

			arquivos = new Arquivos();
			arquivos = arquivos.mapearArqs(diretorios);

			desafios = new Desafios();
			desafios = desafios.mapearDesafios(arquivos);

			map = new HashMap<String, Integer>();
			sensores = new Sensores();
			sensores = sensores.mapearSensores(map, arquivos);

			frameworks = new Frameworks();
			frameworks = frameworks.mapearFrameworks(map, arquivos);

			configuracao = new Configuracao();
			configuracao = configuracao.mapearConfiguracao(arquivos);

			recursos = new Recursos();
			recursos = recursos.mapearOtimizacao(diretorios);

			metricas = new Metricas();
			metricas = metricas.mapearMetricas(arquivos);

			layouts = new Layouts();

			App a = new App();
			a.setPacka_name(p.getNome());
			layouts = layouts.mapearLayout(map, arquivos, apps_map, a);
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

		for (Entry<String, HashMap<String, Integer>> entry : mapFilteredByProject.entrySet()) {
			System.out.println("App name: " + entry.getKey());
			for (Entry<String, Integer> i : entry.getValue().entrySet()) {
				System.out.println("\t" + i.getKey() + ":" + i.getValue().toString());

			}
		}

		// TODO: DESCOMENTAR
		// redator.escrita(sbCSV, "Relatorio.csv");
		// redator.escrita(sbRelatorioFinal, "Relatorio_Final.txt");
		// redator.escrita(sbRelatorioDesafios, "RelatorioDesafios.csv");
		System.out.println("TAMM " + apps_map.size());
		int editText = 0;
		int imageView = 0;
		int imageButton = 0;
		int button = 0;
		for (App a : apps_map) {
			System.out.println(a.getPacka_name());
			for (Widget w : a.getElements()) {
				System.out.println("\tID: " + w.getId() + " TAG: " + w.getTag() + " ACCESS: " + w.toStringList());
				if (w.getTag().contains("EditText"))
					editText++;
				if (w.getTag().contains("ImageView"))
					imageView++;
				if (w.getTag().contains("ImageButton"))
					imageButton++;
				if (w.getTag().contains("Button"))
					button++;
			}
		}
		
		System.out.println("HOW MANY EDITTEXT? " + editText);
		System.out.println("HOW MANY IMAGEVIEW? " + imageView);
		System.out.println("HOW MANY IMAGEBUTTON? " + imageButton);
		System.out.println("HOW MANY BUTTON? " + button);

	}
}