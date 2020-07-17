package br.com.processador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.GregorianCalendar;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Redator {

	/*
	 * ESCREVE ARQUIVO TEXTO
	 * -------------------------------------------------------------
	 */
	public void escrita(StringBuilder texto, String nomeDocumento) {

		FileWriter arq;
		try {

			// arq = new FileWriter("/home/bernardo/Repositorio/" + nomeDocumento);
			arq = new FileWriter("/home/henrique/Experiment/" + nomeDocumento);
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.println(texto);
			arq.close();

		} catch (IOException ex) {

			Logger.getLogger(Redator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static String leitura() {
		String texto = "";
		try {
			FileReader arq = new FileReader("/home/davi/texto.txt");
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				texto += " " + linha;
				linha = lerArq.readLine();
			}
			arq.close();
			return texto;

		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
			return "erro";
		}

	}

	/*
	 * LÊ ARQUIVO TEXTO
	 * -------------------------------------------------------------
	 */
	public double contaLinhasProducao(String caminho, double linhasProducao) {
		boolean flag = true;
		try {
			// System.out.println(caminho);
			FileReader arq = new FileReader(caminho);
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();

			while (linha != null) {
				if (!linha.trim().equals("") && !linha.trim().startsWith("//")) {
					if (!linha.trim().startsWith("/*") && flag) {
						linhasProducao++;
					} else if (linha.trim().contains("*/"))
						flag = true;
					else
						flag = false;
				}

				linha = lerArq.readLine();
			}
			arq.close();
			// System.out.println(linhasProducao);
			return linhasProducao;

		} catch (IOException e) {

			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
			return 0.0;
		}

	}

	/*
	 * CAPTURA DATA INICIO
	 * -------------------------------------------------------------
	 */
	public String dataInicio(String nomeProjeto) {
		GregorianCalendar calendar = new GregorianCalendar();
		String texto = "";

		texto += "\n==========================================================";
		texto += "\n Projeto " + nomeProjeto + " iniciado em: " + calendar.getTime();
		texto += "\n==========================================================";

		return texto;
	}

	/*
	 * CAPTURA DATA FIM
	 * -------------------------------------------------------------
	 */
	public String dataFim() {
		GregorianCalendar calendar = new GregorianCalendar();
		String texto = "";

		texto += "\n\n=====================================================";
		texto += "\n Relatório finalizado em: " + calendar.getTime();
		texto += "\n=====================================================";

		return texto;
	}

	/*
	 * ESCREVE RELATÓRIO FINAL - INFO GERAL
	 * -------------------------------------------------------------
	 */
	public StringBuilder escreverRelatorio(Arquivos a, Desafios d, Sensores s, Frameworks f, Configuracao c, Recursos r,
			Metricas m, Layouts l) {
		StringBuilder sb = new StringBuilder();

		sb.append(dataInicio(a.getDiretorio().getProjeto().getNome()));
		sb.append("\n** FRAMEWORKS");
		sb.append(f.getFrameworks());

		return sb;
	}

	/*
	 * ESCREVE LOG POR PROJETO - INFO DETALHADO
	 * -------------------------------------------------------------
	 */
	public StringBuilder escreverLog(Arquivos a, Desafios d, Sensores s, Frameworks f, Configuracao c, Recursos r,
			Metricas m, Layouts l) {
		Utilitarios uti = new Utilitarios();
		StringBuilder sb = new StringBuilder();

		sb.append(dataInicio(a.getDiretorio().getProjeto().getNome()));

		sb.append("\n** DIRETÓRIOS DE PRODUÇÃO: \n" + uti.quebraLista(a.getDiretorio().getDirsSRC()));
		sb.append("\n** DIRETÓRIOS DE RECURSOS: \n" + uti.quebraLista(a.getDiretorio().getDirsRes()));
		sb.append("\n** DIRETÓRIOS DE INTERNACIONALIZAÇÃO: \n" + uti.quebraLista(a.getDiretorio().getDirsValues()));
		sb.append("\n** DIRETÓRIOS DE LAYOUT: \n" + uti.quebraLista(a.getDiretorio().getDirsLayout()));
		sb.append("\n** DIRETÓRIOS DE IMAGENS: \n" + uti.quebraLista(a.getDiretorio().getDirsDrawable()));
		sb.append("\n** DIRETÓRIOS DE TESTE: \n" + uti.quebraLista(a.getDiretorio().getDirsTeste()));
		sb.append("\n** CLASSES JAVA DE PRODUÇÃO: \n" + uti.quebraLista(a.getArqsJavaSRC()));
		sb.append("\n** CLASSES JAVA DE TESTE: \n" + uti.quebraLista(a.getArqsJavaTeste()));
		sb.append("\n** ARQUIVO DE CONFIGURAÇÃO: \n" + uti.quebraLista(a.getArqsManifesto()));
		sb.append("\n** ARQUIVOS XML: \n" + uti.quebraLista(a.getArqsXML()));
		sb.append("\n** ARQUIVOS DE INTERNACIONALIZAÇÃO: \n" + uti.quebraLista(a.getArqsValues()));
		sb.append("\n** ARQUIVOS DE LAYOUT: \n" + uti.quebraLista(a.getArqsLayout()));
		sb.append("\n** ARQUIVOS DE IMAGENS: \n" + uti.quebraLista(a.getArqsDrawable()));

		sb.append("\n** DESAFIOS PRODUCAO");
		sb.append(d.getDesafiosSRC());

		sb.append("\n** DESAFIOS TESTE");
		sb.append(d.getDesafiosTeste());

		sb.append("\n** SENSORES PRODUCAO");
		sb.append(s.getSensoresSRC());

		sb.append("\n** SENSORES TESTE");
		sb.append(s.getSensoresTeste());

		sb.append("\n** IMPORTS TESTE");
		sb.append(f.getImportsTeste());

		sb.append("\n** PACOTES SRC");
		sb.append(f.getPacotesSRC());

		sb.append("\n** FRAMEWORKS");
		sb.append(f.getFrameworks());
		return sb;
	}

	public StringBuilder buildHeader(List<String> listaDeAttAccXML, String listaDeAttAcc) {
		StringBuilder sb = new StringBuilder();
		// PROJETO
		sb.append("APP;");

		// PRODUCAO E TESTE
		sb.append("DIR;");
		sb.append("DIR_TEST;");
		sb.append("ARQ_SRC;");
		sb.append("TEST_SRC;");
		sb.append("LINES_SRC;");
		sb.append("TestLINES_SRC;");
		sb.append("METH_SRC;");
		sb.append("testMETH_SRC;");
		sb.append("PCKG_SRC;");
		sb.append("TEST_PCKG;");

		// IDES
		sb.append("AS_Studio;");
		sb.append("Eclipse;");

		// OUTRAS LINGUAGENS
		sb.append("XML;");
		sb.append("JS;");
		sb.append("HTML;");
		sb.append("CSS;");
		sb.append("H;");
		sb.append("C;");
		sb.append("CPP;");
		sb.append("RB;");
		sb.append("PY;");
		sb.append("SCALA;");

		// OUTROS RECURSOS
		sb.append("DIR_VALUES;");
		sb.append("DIR_LAYOUT;");
		sb.append("DIR_DRAWABLE;");
		sb.append("ARQ_DRAWABLE;");
		sb.append("ARQ_VALUES;");
		sb.append("ARQ_LAYOUT;");

		// RESIZE SCREEN
		sb.append("INTSMALL;");
		sb.append("INTNORMAL;");
		sb.append("INTLARGE;");
		sb.append("INTXLARGE;");
		sb.append("INTLDPI;");
		sb.append("INTMDPI;");
		sb.append("INTHDPI;");
		sb.append("INTXDPI;");
		sb.append("INTXXDPI;");
		sb.append("INTXXXDPI;");
		sb.append("INTNODPI;");

		// SUPPORT SCREEN
		sb.append("isBlResizeable;");
		sb.append("isBlSmallScreens;");
		sb.append("isBlNormalScreens;");
		sb.append("isBlLargeScreens;");
		sb.append("isBlXlargeScreens;");
		sb.append("isBlAnyDensity;");
		sb.append("getIntRequiresSmallestWidthDp;");
		sb.append("getIntCompatibleWidthLimitDp;");
		sb.append("getIntLargestWidthLimitDp;");

		// COMPATIBLE SCREEN
		sb.append("getScreenSize;");
		sb.append("getScreenDensity;");

		// USES-CONFIGURATION
		sb.append("isReqFiveWayNav;");
		sb.append("isReqHardKeyboard;");
		sb.append("getReqKeyboardType;");
		sb.append("getReqNavigation;");
		sb.append("getReqTouchScreen;");

		// VERSION
		sb.append("getMinSdkVersion;");
		sb.append("getTargetSdkVersion;");
		sb.append("getMaxSdkVersion;");

		// ORIENTATION
		sb.append("getIntPortrait;");
		sb.append("getIntLandscape;");

		// PERMISSIONS
		sb.append("getNetworkState;");
		sb.append("getNetworkState;");
		sb.append("getBluetooth;");
		sb.append("getBluetoothAdmin;");
		sb.append("getInternet;");
		sb.append("getChangeNetworkState;");
		sb.append("getChangeWifiState;");

		// SENSORS SRC
		sb.append("getSrcAccelerometer;");
		sb.append("getSrcAmbient_temperature;");
		sb.append("getSrcGravity;");
		sb.append("getSrcGyroscope;");
		sb.append("getSrcLight;");
		sb.append("getSrcLinear_acceleration;");
		sb.append("getSrcMagnetic_field;");
		sb.append("getSrcOrientation;");
		sb.append("getSrcPressure;");
		sb.append("getSrcProximity;");
		sb.append("getSrcRelative_humidity;");
		sb.append("getSrcRotation_vector;");
		sb.append("getSrcTemperature;");
		sb.append("getSrcGpsLocation;");

		// SENSORS TESTE
		sb.append("getTesteAccelerometer;");
		sb.append("getTesteAmbient_temperature;");
		sb.append("getTesteGravity;");
		sb.append("getTesteGyroscope;");
		sb.append("getTesteLight;");
		sb.append("getTesteLinear_acceleration;");
		sb.append("getTesteMagnetic_field;");
		sb.append("getTesteOrientation;");
		sb.append("getTestePressure;");
		sb.append("getTesteProximity;");
		sb.append("getTesteRelative_humidity;");
		sb.append("getTesteRotation_vector;");
		sb.append("getTesteTemperature;");
		sb.append("getTesteGpsLocation;");

		// SENSORS XML
		sb.append("XMLgetStWifi;");
		sb.append("XMLgetStBluetooth;");
		sb.append("XMLgetStInternet;");
		sb.append("XMLgetStNetwork;");
		sb.append("XMLgetStLocation;");
		sb.append("XMLgetStAccess_gps;");
		sb.append("XMLgetStRecord_audio;");

		// DESAFIOS SRC
		sb.append("getSrcConnected;");
		sb.append("getSrcGUI;");
		sb.append("getSrcResources;");
		sb.append("getSrcSensors;");
		sb.append("getSrcMultiple;");

		// DESAFIOS TESTE
		sb.append("getTesteConnected;");
		sb.append("getTesteGUI;");
		sb.append("getTesteResources;");
		sb.append("getTesteSensors;");
		sb.append("getTesteMultiple;");

		// FRAMEWORKS
		sb.append("getStandroid;");
		sb.append("getStjunit;");
		sb.append("getStrobolectric;");
		sb.append("getSthamcrest;");
		sb.append("getStfest;");
		sb.append("getStrobotium;");
		sb.append("getStselenium;");
		sb.append("getStespresso;");
		sb.append("getStuiautomator;");
		sb.append("getSteasymock;");
		sb.append("getStmock;");
		sb.append("getStmockito;");
		sb.append("getStmonkeyrunner;");
		sb.append("getStappium;");
		sb.append("getSttestng;");
		sb.append("getStjmeter;");
		sb.append("getStselendroid;");

		// LAYOUT
		sb.append("getStFrameLayout;");
		sb.append("getStLinearLayout;");
		sb.append("getStTableLayout;");
		sb.append("getStGridLayout;");
		sb.append("getStRelativeLayout;");

		// WIDGETS
		sb.append("getStTextView;");
		sb.append("getStButton;");
		sb.append("getStRadioButton;");
		sb.append("getStCheckBox;");
		sb.append("getStSwitch;");
		sb.append("getStToggleButton;");
		sb.append("getStImageView;");
		sb.append("getStProgressBar;");
		sb.append("getStSeekBar;");
		sb.append("getStRatingBar;");
		sb.append("getStSpinner;");
		sb.append("getStWebView;");
		sb.append("getStText;");

		// TEXT FIELDS
		sb.append("getStEditText;");
		sb.append("getStPersonName;");
		sb.append("getStPassword;");
		sb.append("getStPhone;");
		sb.append("getStMultilineText;");
		sb.append("getStNumber;");

		sb.append("getStRadioGroup;");
		sb.append("getStListView;");
		sb.append("getStGridView;");

		// EVENTOS GUI
		sb.append("getOnswipetouchlistener;");
		sb.append("getOndoubletaplistener;");
		sb.append("getOnscalegesturelistener;");
		sb.append("getShakelistener;");
		sb.append("getOndraglistener;");
		sb.append("getOnscrolllistener;");

		// ATRIBUTOS DE ACESSIBILIDADE
		for (String s_xml : listaDeAttAccXML)
			if (!s_xml.equals("-"))
				sb.append(s_xml + ";");

		for (String s_src : listaDeAttAcc.split(";"))
			if (!s_src.equals("-"))
				sb.append(s_src + ";");

		// QUANTIDADE TOTAL DE FEATURES DE ACESSIBILIDADE
		sb.append("TOTAL_ACC_ATTRIBUTES;");

		// QUANTIDADE DE TAG ACTIVITY
		sb.append("ACTIVITIES;");

		// SCORE
		sb.append("SCORE_ACC_NORMALIZED;");

		sb.append("XML_ATT;");
		sb.append("SRC_ATT;");

		sb.append("ATT_Perceivable;");
		sb.append("ATT_Operable;");
		sb.append("ATT_Understandable;");
		sb.append("ATT_Robust;");

		// LAST COLUMN
		sb.append("\n");

		return sb;

	}

	/*
	 * ESCREVE CSV POR PROJETO - INFO TABULAR
	 * -------------------------------------------------------------
	 */
	public StringBuilder escreverCSV(Arquivos a, Desafios d, Sensores s, Frameworks f, Configuracao c, Recursos r,
			Metricas m, Layouts l, App app, List<String> listaDeAttAccXML, String listaDeAttAcc) {
		Utilitarios uti = new Utilitarios();
		StringBuilder sb = new StringBuilder();

		// PROJETO
		sb.append(a.getDiretorio().getProjeto().getNome().toLowerCase() + ";");

		// PRODUCAO E TESTE
		sb.append(a.getDiretorio().getDirsSRC().size() + ";" + a.getDiretorio().getDirsTeste().size() + ";");
		sb.append(a.getArqsJavaSRC().size() + ";" + a.getArqsJavaTeste().size() + ";");
		sb.append(m.getLinhasSRC() + ";" + m.getLinhasTeste() + ";");
		sb.append(d.getContadorDeMetodosSRC() + ";" + d.getContadorDeMetodosTeste() + ";");
		sb.append(uti.contaSB(f.getPacotesSRC()) + ";" + uti.contaSB(f.getPacotesTeste()) + ";");

		// IDES
		sb.append(a.getDiretorio().getArqsAStudio().size() + ";" + a.getDiretorio().getArqsEclipse().size() + ";");

		// OUTRAS LINGUAGENS
		sb.append(a.getArqsXML().size() + ";" + a.getArqsJS().size() + ";" + a.getArqsHTML().size() + ";"
				+ a.getArqsCSS().size() + ";" + a.getArqsH().size() + ";" + a.getArqsC().size() + ";"
				+ a.getArqsCPP().size() + ";" + a.getArqsRb().size() + ";" + a.getArqsPy().size() + ";"
				+ a.getArqsScala().size() + ";");

		// RECURSOS (VALUES (-1) IGNORA PASTA PRINCIPAL) - NÃO DEU CERTO - REVER
		sb.append(a.getDiretorio().getDirsValues().size() + ";" + a.getDiretorio().getDirsLayout().size() + ";"
				+ a.getDiretorio().getDirsDrawable().size() + ";");
		sb.append(a.getArqsValues().size() + ";" + a.getArqsLayout().size() + ";" + a.getArqsDrawable().size() + ";");

		// RESIZE SCREEN
		sb.append(r.getIntSmall() + ";" + r.getIntNormal() + ";" + r.getIntLarge() + ";" + r.getIntXlarge() + ";"
				+ r.getIntLdpi() + ";" + r.getIntMdpi() + ";" + r.getIntHdpi() + ";" + r.getIntXhdpi() + ";"
				+ r.getIntXxhdpi() + ";" + r.getIntXxxhdpi() + ";" + r.getIntNodpi() + ";");

		// USES-FEATURE
		// sb.append(c.getName() + ";" + uti.converteBoleano(c.isRequired()) + ";" +
		// c.getGlEsVersion() + ";");

		// SUPPORT SCREEN
		sb.append(uti.converteBoleano(c.isBlResizeable()) + ";" + uti.converteBoleano(c.isBlSmallScreens()) + ";"
				+ uti.converteBoleano(c.isBlNormalScreens()) + ";");
		sb.append(c.isBlLargeScreens() + ";" + c.isBlXlargeScreens() + ";" + uti.converteBoleano(c.isBlAnyDensity())
				+ ";" + c.getIntRequiresSmallestWidthDp() + ";" + c.getIntCompatibleWidthLimitDp() + ";"
				+ c.getIntLargestWidthLimitDp() + ";");

		// COMPATIBLE SCREEN
		sb.append(c.getScreenSize() + ";" + c.getScreenDensity() + ";");

		// USES-CONFIGURATION
		sb.append(uti.converteBoleano(c.isReqFiveWayNav()) + ";" + uti.converteBoleano(c.isReqHardKeyboard()) + ";"
				+ c.getReqKeyboardType() + ";" + c.getReqNavigation() + ";" + c.getReqTouchScreen() + ";");

		// VERSION
		sb.append(c.getMinSdkVersion() + ";" + c.getTargetSdkVersion() + ";" + c.getMaxSdkVersion() + ";");

		// ORIENTATION
		sb.append(c.getIntPortrait() + ";" + c.getIntLandscape() + ";");

		// PERMISSIONS
		sb.append(c.getNetworkState() + ";" + c.getNetworkState() + ";" + c.getBluetooth() + ";" + c.getBluetoothAdmin()
				+ ";" + c.getInternet() + ";" + c.getChangeNetworkState() + ";" + c.getChangeWifiState() + ";");

		// SENSORS SRC
		sb.append(s.getSrcAccelerometer() + ";" + s.getSrcAmbient_temperature() + ";" + s.getSrcGravity() + ";"
				+ s.getSrcGyroscope() + ";" + s.getSrcLight() + ";" + s.getSrcLinear_acceleration() + ";"
				+ s.getSrcMagnetic_field() + ";" + s.getSrcOrientation() + ";" + s.getSrcPressure() + ";"
				+ s.getSrcProximity() + ";" + s.getSrcRelative_humidity() + ";" + s.getSrcRotation_vector() + ";"
				+ s.getSrcTemperature() + ";" + s.getSrcGpsLocation() + ";");

		// SENSORS TESTE
		sb.append(s.getTesteAccelerometer() + ";" + s.getTesteAmbient_temperature() + ";" + s.getTesteGravity() + ";"
				+ s.getTesteGyroscope() + ";" + s.getTesteLight() + ";" + s.getTesteLinear_acceleration() + ";"
				+ s.getTesteMagnetic_field() + ";" + s.getTesteOrientation() + ";" + s.getTestePressure() + ";"
				+ s.getTesteProximity() + ";" + s.getTesteRelative_humidity() + ";" + s.getTesteRotation_vector() + ";"
				+ s.getTesteTemperature() + ";" + s.getTesteGpsLocation() + ";");

		// SENSORS XML
		sb.append(c.getStWifi() + ";" + c.getStBluetooth() + ";" + c.getStInternet() + ";" + c.getStNetwork() + ";"
				+ c.getStLocation() + ";" + c.getStAccess_gps() + ";" + c.getStRecord_audio() + ";");

		// DESAFIOS SRC
		sb.append(d.getSrcConnected() + ";" + d.getSrcGUI() + ";" + d.getSrcResources() + ";" + d.getSrcSensors() + ";"
				+ d.getSrcMultiple() + ";");

		// DESAFIOS TESTE
		sb.append(d.getTesteConnected() + ";" + d.getTesteGUI() + ";" + d.getTesteResources() + ";"
				+ d.getTesteSensors() + ";" + d.getTesteMultiple() + ";");

		// FRAMEWORKS
		sb.append(f.getStandroid() + ";" + f.getStjunit() + ";" + f.getStrobolectric() + ";" + f.getSthamcrest() + ";"
				+ f.getStfest() + ";");
		sb.append(f.getStrobotium() + ";" + f.getStselenium() + ";" + f.getStespresso() + ";" + f.getStuiautomator()
				+ ";");
		sb.append(f.getSteasymock() + ";" + f.getStmock() + ";" + f.getStmockito() + ";" + f.getStmonkeyrunner() + ";"
				+ f.getStappium() + ";");
		sb.append(f.getSttestng() + ";" + f.getStjmeter() + ";" + f.getStselendroid() + ";");
		// + f.getStspongycastle() + ";" + f.getStjersey() + ";" + f.getStroboguice());

		// LAYOUT
		sb.append(l.getStFrameLayout() + ";" + l.getStLinearLayout() + ";" + l.getStTableLayout() + ";"
				+ l.getStGridLayout() + ";" + l.getStRelativeLayout() + ";");

		// WIDGETS
		sb.append(l.getStTextView() + ";" + l.getStButton() + ";" + l.getStRadioButton() + ";" + l.getStCheckBox() + ";"
				+ l.getStSwitch() + ";");
		sb.append(l.getStToggleButton() + ";" + l.getStImageView() + ";" + l.getStProgressBar() + ";" + l.getStSeekBar()
				+ ";" + l.getStRatingBar() + ";" + l.getStSpinner() + ";" + l.getStWebView() + ";" + l.getStText()
				+ ";");

		// TEXT FIELD
		sb.append(l.getStEditText() + ";" + l.getStPersonName() + ";" + l.getStPassword() + ";" + l.getStPhone() + ";"
				+ l.getStMultilineText() + ";" + l.getStNumber() + ";");

		// CONTAINERS
		sb.append(l.getStRadioGroup() + ";" + l.getStListView() + ";" + l.getStGridView() + ";");

		// EVENTOS GUI
		sb.append(d.getOnswipetouchlistener() + ";" + d.getOndoubletaplistener() + ";" + d.getOnscalegesturelistener()
				+ ";" + d.getShakelistener() + ";" + d.getOndraglistener() + ";" + d.getOnscrolllistener() + ";");

		// ACCESSIBILITY XML ATTRIBUTES
		for (String s_xml : listaDeAttAccXML)
			if (!s_xml.equals("-"))
				sb.append(count(app, "XML", s_xml) + ";");

		// ACCESSIBILITY JAVA ATTRIBUTES
		for (String s_src : listaDeAttAcc.split(";"))
			if (!s_src.equals("-"))
				sb.append(count(app, "SRC", s_src) + ";");

		int qtde_acc_total = 0, total_XML = 0, total_SRC = 0;
		total_XML = countSource(app, "XML");
		total_SRC = countSource(app, "SRC");
		qtde_acc_total = total_XML + total_SRC;

		// QUANTIDADE TOTAL DE FEATURES DE ACESSIBILIDADE
		sb.append(qtde_acc_total + ";");

		int qtde_act = 0;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(app.getManifest_path());
			NodeList nodes = doc.getDocumentElement().getElementsByTagName("activity");
			qtde_act = nodes.getLength();

			// QUANTIDADE DE TAG ACTIVITY
			sb.append(qtde_act + ";");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// SCORE
		float score_normalized = -1;
		if (qtde_act > 0)
			score_normalized = (float) qtde_acc_total / qtde_act;

		sb.append(score_normalized + ";");

		sb.append(total_XML + ";");
		sb.append(total_SRC + ";");

		sb.append(countPrinciple(app, "Perceivable") + ";");
		sb.append(countPrinciple(app, "Operable") + ";");
		sb.append(countPrinciple(app, "Understandable") + ";");
		sb.append(countPrinciple(app, "Robust") + ";");

		// LAST COLUMN
		sb.append("\n");

		return sb;
	}

	public StringBuilder escreverDesafios(Desafios d) {
		StringBuilder sb = new StringBuilder();
		sb.append(d.getDesafiosTeste());

		return sb;
	}

	public int countSource(App app, String source) {
		int counter = 0;
		for (Widget w : app.getElements()) {
			if (w.getSource().equals(source) && !w.getAccessibility().isEmpty()) {
				for (Accessibility a : w.getAccessibility())
					if (!a.getAttribute().equals("NONE"))
						counter++;
			}
		}
		return counter;
	}

	public int countPrinciple(App app, String principle) {
		int counter = 0;
		for (Widget w : app.getElements()) {
			if (w != null && !w.getAccessibility().isEmpty()) {
				for (Accessibility a : w.getAccessibility())
					if (a.getPrinciple().equals(principle) && !a.getAttribute().equals("NONE"))
						counter++;
			}
		}

		return counter;
	}

	public int count(App a, String source, String attribute) {
		int counter = 0;
		for (Widget w : a.getElements()) {
			if (anyAccessibilityAttribute(a) && w.attributeIsHere(attribute) && w.getSource().equals(source))
				counter++;
		}

		return counter;
	}

	public boolean anyAccessibilityAttribute(App a) {
		for (Widget w : a.getElements())
			if (w.getAccessibility().size() > 0 && !w.getAccessibility().get(0).getAttribute().isEmpty())
				return true;
		return false;
	}

	public void customAppend(StringBuilder sb, String left, App a, String src, String attribute) {
		int count = count(a, src, attribute);
		if (count > 0)
			sb.append(left + ": " + count(a, src, attribute) + "\n");
	}

	public void accessibilityReport(List<App> apps_map, List<String> listaDeAttAccXML, String listaDeAttAcc) {
		StringBuilder report = new StringBuilder();
		System.out.println(listaDeAttAcc);
		for (App a : apps_map) {
			report.append("------------------------------------------\n");
			report.append("App: " + a.getPacka_name() + "\n");
			for (String s : listaDeAttAccXML)
				customAppend(report, "\tXML_" + s, a, "XML", s);

			for (String s_src : listaDeAttAcc.split(";"))
				customAppend(report, "\tSRC_" + s_src, a, "SRC", s_src);

		}
		escrita(report, "accessibilityReport.log");
		// System.out.println("Apps without accessibilities attributes: " +
		// appWithoutAccessibilities);
	}
}
