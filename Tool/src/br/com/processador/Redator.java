package br.com.processador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Redator {
	static String delimiter = ",";

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
		sb.append("APP" + delimiter);

		// PRODUCAO E TESTE
		sb.append("DIR" + delimiter);
		sb.append("DIR_TEST" + delimiter);
		sb.append("ARQ_SRC" + delimiter);
		sb.append("TEST_SRC" + delimiter);
		sb.append("LINES_SRC" + delimiter);
		sb.append("TestLINES_SRC" + delimiter);
		sb.append("METH_SRC" + delimiter);
		sb.append("testMETH_SRC" + delimiter);
		sb.append("PCKG_SRC" + delimiter);
		sb.append("TEST_PCKG" + delimiter);

		// IDES
		sb.append("AS_Studio" + delimiter);
		sb.append("Eclipse" + delimiter);

		// OUTRAS LINGUAGENS
		sb.append("XML" + delimiter);
		sb.append("JS" + delimiter);
		sb.append("HTML" + delimiter);
		sb.append("CSS" + delimiter);
		sb.append("H" + delimiter);
		sb.append("C" + delimiter);
		sb.append("CPP" + delimiter);
		sb.append("RB" + delimiter);
		sb.append("PY" + delimiter);
		sb.append("SCALA" + delimiter);

		// OUTROS RECURSOS
		sb.append("DIR_VALUES" + delimiter);
		sb.append("DIR_LAYOUT" + delimiter);
		sb.append("DIR_DRAWABLE" + delimiter);
		sb.append("ARQ_DRAWABLE" + delimiter);
		sb.append("ARQ_VALUES" + delimiter);
		sb.append("ARQ_LAYOUT" + delimiter);

		// RESIZE SCREEN
		sb.append("INTSMALL" + delimiter);
		sb.append("INTNORMAL" + delimiter);
		sb.append("INTLARGE" + delimiter);
		sb.append("INTXLARGE" + delimiter);
		sb.append("INTLDPI" + delimiter);
		sb.append("INTMDPI" + delimiter);
		sb.append("INTHDPI" + delimiter);
		sb.append("INTXDPI" + delimiter);
		sb.append("INTXXDPI" + delimiter);
		sb.append("INTXXXDPI" + delimiter);
		sb.append("INTNODPI" + delimiter);

		// SUPPORT SCREEN
		sb.append("isBlResizeable" + delimiter);
		sb.append("isBlSmallScreens" + delimiter);
		sb.append("isBlNormalScreens" + delimiter);
		sb.append("isBlLargeScreens" + delimiter);
		sb.append("isBlXlargeScreens" + delimiter);
		sb.append("isBlAnyDensity" + delimiter);
		sb.append("getIntRequiresSmallestWidthDp" + delimiter);
		sb.append("getIntCompatibleWidthLimitDp" + delimiter);
		sb.append("getIntLargestWidthLimitDp" + delimiter);

		// COMPATIBLE SCREEN
		sb.append("getScreenSize" + delimiter);
		sb.append("getScreenDensity" + delimiter);

		// USES-CONFIGURATION
		sb.append("isReqFiveWayNav" + delimiter);
		sb.append("isReqHardKeyboard" + delimiter);
		sb.append("getReqKeyboardType" + delimiter);
		sb.append("getReqNavigation" + delimiter);
		sb.append("getReqTouchScreen" + delimiter);

		// VERSION
		sb.append("getMinSdkVersion" + delimiter);
		sb.append("getTargetSdkVersion" + delimiter);
		sb.append("getMaxSdkVersion" + delimiter);

		// ORIENTATION
		sb.append("getIntPortrait" + delimiter);
		sb.append("getIntLandscape" + delimiter);

		// PERMISSIONS
		sb.append("getNetworkState" + delimiter);
		//sb.append("getNetworkState" + delimiter);
		sb.append("getBluetooth" + delimiter);
		sb.append("getBluetoothAdmin" + delimiter);
		sb.append("getInternet" + delimiter);
		sb.append("getChangeNetworkState" + delimiter);
		sb.append("getChangeWifiState" + delimiter);

		// SENSORS SRC
		sb.append("getSrcAccelerometer" + delimiter);
		sb.append("getSrcAmbient_temperature" + delimiter);
		sb.append("getSrcGravity" + delimiter);
		sb.append("getSrcGyroscope" + delimiter);
		sb.append("getSrcLight" + delimiter);
		sb.append("getSrcLinear_acceleration" + delimiter);
		sb.append("getSrcMagnetic_field" + delimiter);
		sb.append("getSrcOrientation" + delimiter);
		sb.append("getSrcPressure" + delimiter);
		sb.append("getSrcProximity" + delimiter);
		sb.append("getSrcRelative_humidity" + delimiter);
		sb.append("getSrcRotation_vector" + delimiter);
		sb.append("getSrcTemperature" + delimiter);
		sb.append("getSrcGpsLocation" + delimiter);

		// SENSORS TESTE
		sb.append("getTesteAccelerometer" + delimiter);
		sb.append("getTesteAmbient_temperature" + delimiter);
		sb.append("getTesteGravity" + delimiter);
		sb.append("getTesteGyroscope" + delimiter);
		sb.append("getTesteLight" + delimiter);
		sb.append("getTesteLinear_acceleration" + delimiter);
		sb.append("getTesteMagnetic_field" + delimiter);
		sb.append("getTesteOrientation" + delimiter);
		sb.append("getTestePressure" + delimiter);
		sb.append("getTesteProximity" + delimiter);
		sb.append("getTesteRelative_humidity" + delimiter);
		sb.append("getTesteRotation_vector" + delimiter);
		sb.append("getTesteTemperature" + delimiter);
		sb.append("getTesteGpsLocation" + delimiter);

		// SENSORS XML
		sb.append("XMLgetStWifi" + delimiter);
		sb.append("XMLgetStBluetooth" + delimiter);
		sb.append("XMLgetStInternet" + delimiter);
		sb.append("XMLgetStNetwork" + delimiter);
		sb.append("XMLgetStLocation" + delimiter);
		sb.append("XMLgetStAccess_gps" + delimiter);
		sb.append("XMLgetStRecord_audio" + delimiter);

		// DESAFIOS SRC
		sb.append("getSrcConnected" + delimiter);
		sb.append("getSrcGUI" + delimiter);
		sb.append("getSrcResources" + delimiter);
		sb.append("getSrcSensors" + delimiter);
		sb.append("getSrcMultiple" + delimiter);

		// DESAFIOS TESTE
		sb.append("getTesteConnected" + delimiter);
		sb.append("getTesteGUI" + delimiter);
		sb.append("getTesteResources" + delimiter);
		sb.append("getTesteSensors" + delimiter);
		sb.append("getTesteMultiple" + delimiter);

		// FRAMEWORKS
		sb.append("getStandroid" + delimiter);
		sb.append("getStjunit" + delimiter);
		sb.append("getStrobolectric" + delimiter);
		sb.append("getSthamcrest" + delimiter);
		sb.append("getStfest" + delimiter);
		sb.append("getStrobotium" + delimiter);
		sb.append("getStselenium" + delimiter);
		sb.append("getStespresso" + delimiter);
		sb.append("getStuiautomator" + delimiter);
		sb.append("getSteasymock" + delimiter);
		sb.append("getStmock" + delimiter);
		sb.append("getStmockito" + delimiter);
		sb.append("getStmonkeyrunner" + delimiter);
		sb.append("getStappium" + delimiter);
		sb.append("getSttestng" + delimiter);
		sb.append("getStjmeter" + delimiter);
		sb.append("getStselendroid" + delimiter);

		// LAYOUT
		sb.append("getStFrameLayout" + delimiter);
		sb.append("getStLinearLayout" + delimiter);
		sb.append("getStTableLayout" + delimiter);
		sb.append("getStGridLayout" + delimiter);
		sb.append("getStRelativeLayout" + delimiter);

		// WIDGETS
		sb.append("getStTextView" + delimiter);
		sb.append("getStButton" + delimiter);
		sb.append("getStRadioButton" + delimiter);
		sb.append("getStCheckBox" + delimiter);
		sb.append("getStSwitch" + delimiter);
		sb.append("getStToggleButton" + delimiter);
		sb.append("getStImageView" + delimiter);
		sb.append("getStProgressBar" + delimiter);
		sb.append("getStSeekBar" + delimiter);
		sb.append("getStRatingBar" + delimiter);
		sb.append("getStSpinner" + delimiter);
		sb.append("getStWebView" + delimiter);
		sb.append("getStText" + delimiter);

		// TEXT FIELDS
		sb.append("getStEditText" + delimiter);
		sb.append("getStPersonName" + delimiter);
		sb.append("getStPassword" + delimiter);
		sb.append("getStPhone" + delimiter);
		sb.append("getStMultilineText" + delimiter);
		sb.append("getStNumber" + delimiter);

		sb.append("getStRadioGroup" + delimiter);
		sb.append("getStListView" + delimiter);
		sb.append("getStGridView" + delimiter);

		// EVENTOS GUI
		sb.append("getOnswipetouchlistener" + delimiter);
		sb.append("getOndoubletaplistener" + delimiter);
		sb.append("getOnscalegesturelistener" + delimiter);
		sb.append("getShakelistener" + delimiter);
		sb.append("getOndraglistener" + delimiter);
		sb.append("getOnscrolllistener" + delimiter);

		// ATRIBUTOS DE ACESSIBILIDADE
		for (String s_xml : listaDeAttAccXML)
			if (!s_xml.equals("-"))
				sb.append("XML_" + s_xml + delimiter);

		for (String s_src : listaDeAttAcc.split(";"))
			if (!s_src.equals("-"))
				sb.append("SRC_" + s_src + delimiter);

		// QUANTIDADE TOTAL DE FEATURES DE ACESSIBILIDADE
		sb.append("TOTAL_ACC_ATTRIBUTES" + delimiter);

		// QUANTIDADE DE TAG ACTIVITY
		sb.append("ACTIVITIES" + delimiter);

		// SCORE
		sb.append("SCORE_ACC_NORMALIZED" + delimiter);

		sb.append("XML_ATT" + delimiter);
		sb.append("SRC_ATT" + delimiter);

		sb.append("ATT_Perceivable" + delimiter);
		sb.append("ATT_Operable" + delimiter);
		sb.append("ATT_Understandable" + delimiter);
		sb.append("ATT_Robust");

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
		sb.append(a.getDiretorio().getProjeto().getNome().toLowerCase() + delimiter);

		// PRODUCAO E TESTE
		sb.append(
				a.getDiretorio().getDirsSRC().size() + delimiter + a.getDiretorio().getDirsTeste().size() + delimiter);
		sb.append(a.getArqsJavaSRC().size() + delimiter + a.getArqsJavaTeste().size() + delimiter);
		sb.append(m.getLinhasSRC() + delimiter + m.getLinhasTeste() + delimiter);
		sb.append(d.getContadorDeMetodosSRC() + delimiter + d.getContadorDeMetodosTeste() + delimiter);
		sb.append(uti.contaSB(f.getPacotesSRC()) + delimiter + uti.contaSB(f.getPacotesTeste()) + delimiter);

		// IDES
		sb.append(a.getDiretorio().getArqsAStudio().size() + delimiter + a.getDiretorio().getArqsEclipse().size()
				+ delimiter);

		// OUTRAS LINGUAGENS
		sb.append(a.getArqsXML().size() + delimiter + a.getArqsJS().size() + delimiter + a.getArqsHTML().size()
				+ delimiter + a.getArqsCSS().size() + delimiter + a.getArqsH().size() + delimiter + a.getArqsC().size()
				+ delimiter + a.getArqsCPP().size() + delimiter + a.getArqsRb().size() + delimiter
				+ a.getArqsPy().size() + delimiter + a.getArqsScala().size() + delimiter);

		// RECURSOS (VALUES (-1) IGNORA PASTA PRINCIPAL) - NÃO DEU CERTO - REVER
		sb.append(a.getDiretorio().getDirsValues().size() + delimiter + a.getDiretorio().getDirsLayout().size()
				+ delimiter + a.getDiretorio().getDirsDrawable().size() + delimiter);

		// int aGetArqsLayoutSize = a.searchForLayoutFiles(a);
		sb.append(a.getArqsDrawable().size() + delimiter + a.getArqsValues().size() + delimiter
				+ a.getArqsLayout().size() + delimiter);

		// RESIZE SCREEN
		sb.append(r.getIntSmall() + delimiter + r.getIntNormal() + delimiter + r.getIntLarge() + delimiter
				+ r.getIntXlarge() + delimiter + r.getIntLdpi() + delimiter + r.getIntMdpi() + delimiter
				+ r.getIntHdpi() + delimiter + r.getIntXhdpi() + delimiter + r.getIntXxhdpi() + delimiter
				+ r.getIntXxxhdpi() + delimiter + r.getIntNodpi() + delimiter);

		// USES-FEATURE
		// sb.append(c.getName() + delimiter + uti.converteBoleano(c.isRequired()) +
		// delimiter +
		// c.getGlEsVersion() + delimiter);

		// SUPPORT SCREEN
		sb.append(uti.converteBoleano(c.isBlResizeable()) + delimiter + uti.converteBoleano(c.isBlSmallScreens())
				+ delimiter + uti.converteBoleano(c.isBlNormalScreens()) + delimiter);
		sb.append(c.isBlLargeScreens() + delimiter + c.isBlXlargeScreens() + delimiter
				+ uti.converteBoleano(c.isBlAnyDensity()) + delimiter + c.getIntRequiresSmallestWidthDp() + delimiter
				+ c.getIntCompatibleWidthLimitDp() + delimiter + c.getIntLargestWidthLimitDp() + delimiter);

		// COMPATIBLE SCREEN
		sb.append(c.getScreenSize() + delimiter + c.getScreenDensity() + delimiter);

		// USES-CONFIGURATION
		sb.append(uti.converteBoleano(c.isReqFiveWayNav()) + delimiter + uti.converteBoleano(c.isReqHardKeyboard())
				+ delimiter + c.getReqKeyboardType() + delimiter + c.getReqNavigation() + delimiter
				+ c.getReqTouchScreen() + delimiter);

		// VERSION
		sb.append(c.getMinSdkVersion() + delimiter + c.getTargetSdkVersion() + delimiter + c.getMaxSdkVersion()
				+ delimiter);

		// ORIENTATION
		sb.append(c.getIntPortrait() + delimiter + c.getIntLandscape() + delimiter);

		// PERMISSIONS
		sb.append(c.getNetworkState() + delimiter + c.getBluetooth() + delimiter
				+ c.getBluetoothAdmin() + delimiter + c.getInternet() + delimiter + c.getChangeNetworkState()
				+ delimiter + c.getChangeWifiState() + delimiter);

		// SENSORS SRC
		sb.append(s.getSrcAccelerometer() + delimiter + s.getSrcAmbient_temperature() + delimiter + s.getSrcGravity()
				+ delimiter + s.getSrcGyroscope() + delimiter + s.getSrcLight() + delimiter
				+ s.getSrcLinear_acceleration() + delimiter + s.getSrcMagnetic_field() + delimiter
				+ s.getSrcOrientation() + delimiter + s.getSrcPressure() + delimiter + s.getSrcProximity() + delimiter
				+ s.getSrcRelative_humidity() + delimiter + s.getSrcRotation_vector() + delimiter
				+ s.getSrcTemperature() + delimiter + s.getSrcGpsLocation() + delimiter);

		// SENSORS TESTE
		sb.append(s.getTesteAccelerometer() + delimiter + s.getTesteAmbient_temperature() + delimiter
				+ s.getTesteGravity() + delimiter + s.getTesteGyroscope() + delimiter + s.getTesteLight() + delimiter
				+ s.getTesteLinear_acceleration() + delimiter + s.getTesteMagnetic_field() + delimiter
				+ s.getTesteOrientation() + delimiter + s.getTestePressure() + delimiter + s.getTesteProximity()
				+ delimiter + s.getTesteRelative_humidity() + delimiter + s.getTesteRotation_vector() + delimiter
				+ s.getTesteTemperature() + delimiter + s.getTesteGpsLocation() + delimiter);

		// SENSORS XML
		sb.append(c.getStWifi() + delimiter + c.getStBluetooth() + delimiter + c.getStInternet() + delimiter
				+ c.getStNetwork() + delimiter + c.getStLocation() + delimiter + c.getStAccess_gps() + delimiter
				+ c.getStRecord_audio() + delimiter);

		// DESAFIOS SRC
		sb.append(d.getSrcConnected() + delimiter + d.getSrcGUI() + delimiter + d.getSrcResources() + delimiter
				+ d.getSrcSensors() + delimiter + d.getSrcMultiple() + delimiter);

		// DESAFIOS TESTE
		sb.append(d.getTesteConnected() + delimiter + d.getTesteGUI() + delimiter + d.getTesteResources() + delimiter
				+ d.getTesteSensors() + delimiter + d.getTesteMultiple() + delimiter);

		// FRAMEWORKS
		sb.append(f.getStandroid() + delimiter + f.getStjunit() + delimiter + f.getStrobolectric() + delimiter
				+ f.getSthamcrest() + delimiter + f.getStfest() + delimiter);
		sb.append(f.getStrobotium() + delimiter + f.getStselenium() + delimiter + f.getStespresso() + delimiter
				+ f.getStuiautomator() + delimiter);
		sb.append(f.getSteasymock() + delimiter + f.getStmock() + delimiter + f.getStmockito() + delimiter
				+ f.getStmonkeyrunner() + delimiter + f.getStappium() + delimiter);
		sb.append(f.getSttestng() + delimiter + f.getStjmeter() + delimiter + f.getStselendroid() + delimiter);
		// + f.getStspongycastle() + delimiter + f.getStjersey() + delimiter +
		// f.getStroboguice());

		// LAYOUT
		sb.append(l.getStFrameLayout() + delimiter + l.getStLinearLayout() + delimiter + l.getStTableLayout()
				+ delimiter + l.getStGridLayout() + delimiter + l.getStRelativeLayout() + delimiter);

		// WIDGETS
		sb.append(l.getStTextView() + delimiter + l.getStButton() + delimiter + l.getStRadioButton() + delimiter
				+ l.getStCheckBox() + delimiter + l.getStSwitch() + delimiter);
		sb.append(l.getStToggleButton() + delimiter + l.getStImageView() + delimiter + l.getStProgressBar() + delimiter
				+ l.getStSeekBar() + delimiter + l.getStRatingBar() + delimiter + l.getStSpinner() + delimiter
				+ l.getStWebView() + delimiter + l.getStText() + delimiter);

		// TEXT FIELD
		sb.append(l.getStEditText() + delimiter + l.getStPersonName() + delimiter + l.getStPassword() + delimiter
				+ l.getStPhone() + delimiter + l.getStMultilineText() + delimiter + l.getStNumber() + delimiter);

		// CONTAINERS
		sb.append(l.getStRadioGroup() + delimiter + l.getStListView() + delimiter + l.getStGridView() + delimiter);

		// EVENTOS GUI
		sb.append(d.getOnswipetouchlistener() + delimiter + d.getOndoubletaplistener() + delimiter
				+ d.getOnscalegesturelistener() + delimiter + d.getShakelistener() + delimiter + d.getOndraglistener()
				+ delimiter + d.getOnscrolllistener() + delimiter);

		// ACCESSIBILITY XML ATTRIBUTES
		for (String s_xml : listaDeAttAccXML)
			if (!s_xml.equals("-"))
				sb.append(count(app, "XML", s_xml) + delimiter);

		// ACCESSIBILITY JAVA ATTRIBUTES
		for (String s_src : listaDeAttAcc.split(";"))
			if (!s_src.equals("-")) {
				sb.append(count(app, "SRC", s_src) + delimiter);

			}
		int qtde_acc_total = 0, total_XML = 0, total_SRC = 0;
		total_XML = countSource(app, "XML");
		total_SRC = countSource(app, "SRC");
		qtde_acc_total = total_XML + total_SRC;

		// QUANTIDADE TOTAL DE FEATURES DE ACESSIBILIDADE
		sb.append(qtde_acc_total + delimiter);

		int qtde_act = 0;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(app.getManifest_path());
			NodeList nodes = doc.getDocumentElement().getElementsByTagName("activity");
			qtde_act = nodes.getLength();

			// QUANTIDADE DE TAG ACTIVITY
			sb.append(qtde_act + delimiter);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// SCORE
		float score_normalized = -1;
		if (qtde_act > 0)
			score_normalized = (float) qtde_acc_total / qtde_act;

		sb.append(score_normalized + delimiter);

		sb.append(total_XML + delimiter);
		sb.append(total_SRC + delimiter);

		sb.append(countPrinciple(app, "Perceivable") + delimiter);
		sb.append(countPrinciple(app, "Operable") + delimiter);
		sb.append(countPrinciple(app, "Understandable") + delimiter);
		sb.append(countPrinciple(app, "Robust"));

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

	public static boolean writeLines(String filePath, List<String> lines) {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));

			for (String newLine : lines) {
				bw.write(newLine);
				bw.newLine();
				bw.flush();
			}

			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
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

	public void accessibilityReport(List<App> apps_map, List<String> listaDeAttAccXML, String listaDeAttAcc) {
		StringBuilder report = new StringBuilder();
		List<String> listFile = new ArrayList<String>();
		List<String> listXML = feedList("/home/henrique/eclipse-workspace/ProfMapp/Tool/attribute_XML.list");
		List<String> listSRC = feedList("/home/henrique/eclipse-workspace/ProfMapp/Tool/attribute_SRC.list");
		System.out.println(listaDeAttAcc);
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		for (App a : apps_map) {
			report.append("------------------------------------------\n");
			report.append("App: " + a.getPacka_name() + "\n");

			for (String s : listaDeAttAccXML) {
				if (map.get(s) == null)
					map.put(s, (count(a, "XML", s) > 0) ? 1 : 0);
				else
					map.put(s, map.get(s) + ((count(a, "XML", s) > 0) ? 1 : 0));

				customAppend(report, "\tXML_" + s, a, "XML", s);
			}

			for (String s_src : listaDeAttAcc.split(";")) {
				if (map.get(s_src) == null)
					map.put(s_src, (count(a, "s_src", s_src) > 0) ? 1 : 0);
				else
					map.put(s_src, map.get(s_src) + ((count(a, "SRC", s_src) > 0) ? 1 : 0));

				customAppend(report, "\tSRC_" + s_src, a, "SRC", s_src);
			}

			int i = 0;
			int achouSRC = 0;
			for (i = 0; i < listXML.size(); i++) {
				int achouXML = (count(a, "XML", listXML.get(i)) > 0) ? 1 : 0;
				achouSRC = 0;
				if (achouXML == 0)
					achouSRC = (count(a, "SRC", listSRC.get(i)) > 0) ? 1 : 0;

				String key = i + "";
				if (map.get(key) == null)
					map.put(key, achouXML + achouSRC);
				else
					map.put(key, map.get(key) + achouXML + achouSRC);
				// listFile.add((achouXML + achouSRC) + "");
			}

		}

		// List<String> listFile = new ArrayList<String>();
		for (Entry<String, Integer> entry : map.entrySet()) {
			listFile.add(entry.getKey() + " : " + entry.getValue());
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}

		writeLines("/home/henrique/Experiment/totalCodedUsed.log", listFile);
		escrita(report, "accessibilityReport.log");
		// System.out.println("Apps without accessibilities attributes: " +
		// appWithoutAccessibilities);
	}
}
