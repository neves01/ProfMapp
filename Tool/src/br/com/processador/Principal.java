package br.com.processador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Principal {

	static List<App> apps_map = new ArrayList<App>();

	public static String feedAccessibilityList(String filePath) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));

			String line;
			while ((line = br.readLine()) != null)
				if (!line.contains("-"))
					sb.append(line + ";");

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static List<String> feedList(String filePath) {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));

			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

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
		Attributes_Accessibility attributes_acc = new Attributes_Accessibility();

		Redator redator = new Redator();
		StringBuilder sbRelatorioFinal = new StringBuilder();
		StringBuilder sbRelatorioDesafios = new StringBuilder();
		StringBuilder sbCSV = new StringBuilder();
		StringBuilder log = new StringBuilder();

		/*
		 * VARIAVEIS
		 * ---------------------------------------------------------------------
		 */ // filter-f-droid/SAST_Apps

		String repositorio = "/home/henrique/Experiment/filter-f-droid/SAST_Apps";
		String listaDeAttAcc = feedAccessibilityList(
				"/home/henrique/eclipse-workspace/ProfMapp/Tool/attribute_SRC.list");
		List<String> listaDeAttAccXML = feedList("/home/henrique/eclipse-workspace/ProfMapp/Tool/attribute_XML.list");
		List<String> listaDePrincipios = feedList("/home/henrique/eclipse-workspace/ProfMapp/Tool/attribute_wcag.list");

		/*
		 * MAPEAR PROJETOS
		 * ---------------------------------------------------------------------
		 */

		sbCSV.append(redator.buildHeader(listaDeAttAccXML, listaDeAttAcc));

		for (Projeto p : projeto.listarProjetos(repositorio)) {
			log = new StringBuilder();

			App a = new App();
			a.setPacka_name(p.getNome());
			// System.out.println("Entrando no projeto: " + p.getCaminho());

			diretorios = new Diretorios();
			diretorios = diretorios.mapearDirs(p);

			arquivos = new Arquivos();
			arquivos = arquivos.mapearArqs(diretorios);

			desafios = new Desafios();
			desafios = desafios.mapearDesafios(arquivos);

			sensores = new Sensores();
			sensores = sensores.mapearSensores(arquivos, apps_map, a);

			arquivos.searchForAndroidManifest(a, new File(p.getCaminho()), 0);
			// System.out.println("MANIFEST: " + a.getManifest_path());

			attributes_acc = new Attributes_Accessibility();
			attributes_acc.mapearAccessibilityAttributes(arquivos, apps_map, a, listaDeAttAcc, listaDePrincipios);

			frameworks = new Frameworks();
			frameworks = frameworks.mapearFrameworks(arquivos);

			configuracao = new Configuracao();
			configuracao = configuracao.mapearConfiguracao(arquivos);

			recursos = new Recursos();
			recursos = recursos.mapearOtimizacao(diretorios);

			metricas = new Metricas();
			metricas = metricas.mapearMetricas(arquivos);

			layouts = new Layouts();

			layouts = layouts.mapearLayout(arquivos, apps_map, a, listaDeAttAccXML, listaDePrincipios);
			apps_map.add(a);

			sbCSV.append(redator.escreverCSV(arquivos, desafios, sensores, frameworks, configuracao, recursos, metricas,
					layouts, a, listaDeAttAccXML, listaDeAttAcc));
			sbRelatorioFinal.append(redator.escreverRelatorio(arquivos, desafios, sensores, frameworks, configuracao,
					recursos, metricas, layouts));
			sbRelatorioDesafios.append(redator.escreverDesafios(desafios));
			log.append(redator.escreverLog(arquivos, desafios, sensores, frameworks, configuracao, recursos, metricas,
					layouts));
			// redator.escrita(log, diretorios.getProjeto().getNome() + "_log.txt");
		}

		// TODO: DESCOMENTAR
		redator.escrita(sbCSV, "Relatorio.csv");
		//redator.escrita(sbRelatorioFinal, "Relatorio_Final.txt");
		// redator.escrita(sbRelatorioDesafios, "RelatorioDesafios.csv");

		redator.accessibilityReport(apps_map, listaDeAttAccXML, listaDeAttAcc);
	}
}