package br.com.nex.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class IreporUtil {

	public IreporUtil() {
	}

	private static final String PATH = "/WEB-INF/reports/";

	public static boolean printReport(String format, Map params,
			String jasperName, List data, ServletContext servletContext)
			throws JRException, IOException {

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) externalContext
					.getResponse();
			ServletOutputStream responseStream = null;

			/*
			 * Importante: Se data � null, definir no relat�rio a seguinte
			 * propriedade: [When no data: All Sections, no detail.] ou Colocar
			 * um valor default em data, para sempre ter valores em detail.
			 */
			if (data == null) {
				data = new ArrayList<Integer>();
				data.add(1);
			}

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(data);

			InputStream fis = externalContext.getResourceAsStream(PATH
					+ jasperName);
			JasperReport relatorio = (JasperReport) JRLoader.loadObject(fis);

			if (format.equals("P")) {

				JasperPrint jasperPrint = JasperFillManager.fillReport(
						relatorio, params, ds);
				responseStream = response.getOutputStream();

				JRExporter exp = new JRPdfExporter();
				exp.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exp.setParameter(JRExporterParameter.OUTPUT_STREAM,
						responseStream);
				exp.exportReport();

			} else if (format.equals("X")) {
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						relatorio, params, ds);
				responseStream = response.getOutputStream();

				JRExporter exp = new JRXlsExporter();
				exp.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exp.setParameter(JRExporterParameter.OUTPUT_STREAM,
						responseStream);
				exp.exportReport();

			} else if (format.equals("D")) {

				JasperPrint jasperPrint = JasperFillManager.fillReport(
						relatorio, params, ds);
				JRRtfExporter docExporter = new JRRtfExporter();
				ByteArrayOutputStream docReport = new ByteArrayOutputStream();
				docExporter.setParameter(
						JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
				docExporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				docExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
						docReport);
				docExporter.exportReport();
				docReport.close();

			}

			if (responseStream != null) {
				responseStream.flush();
				responseStream.close();
			}

			context.renderResponse();

			context.responseComplete();

			return true;

		} catch (JRException ex) {
			throw new JRException(ex.getMessage());

		} catch (IOException ioex) {
			throw new IOException(ioex.getMessage());
		}
	}

	public static byte[] getPdfReportAsByteArray(String format, Map params,
			String jasperName, List data, ServletContext servletContext)
			throws JRException {

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();

			if (data == null) {
				data = new ArrayList<Integer>();
				data.add(1);
			}

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(data);
			InputStream relatorio = externalContext.getResourceAsStream(PATH
					+ jasperName);

			ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
			JasperPrint jasperPrint = JasperFillManager.fillReport(relatorio,
					params, ds);

			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);

			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					jasperPrintList);

			exporter.setParameter(
					JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
					Boolean.TRUE);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					responseStream);
			exporter.exportReport();

			return responseStream.toByteArray();

		} catch (JRException ex) {
			throw new JRException(ex.getMessage());

		}
	}

	public static byte[] mergeFilesInPages(List<byte[]> pdfFilesAsByteArray,
			Rectangle pageSize, float marginLeft, float marginRight,
			float marginTop, float marginBottom) throws DocumentException,
			IOException {

		Document document = new Document();
		document.setPageSize(pageSize);
		document.setMargins(marginLeft, marginRight, marginTop, marginBottom);
		ByteArrayOutputStream byteOS = new ByteArrayOutputStream();

		PdfWriter writer = PdfWriter.getInstance(document, byteOS);

		document.open();

		PdfContentByte cb = writer.getDirectContent();
		float positionAnterior = 0;

		// Para cada arquivo da lista, cria-se um PdfReader, respons�vel por
		// ler o arquivo PDF e recuperar informa��es dele.
		for (byte[] pdfFile : pdfFilesAsByteArray) {

			PdfReader reader = new PdfReader(pdfFile);

			// Faz o processo de mesclagem por p�gina do arquivo, come�ando
			// pela de n�mero 1.
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {

				float documentHeight = cb.getPdfDocument().getPageSize()
						.getHeight();

				// Importa a p�gina do PDF de origem
				PdfImportedPage page = writer.getImportedPage(reader, i);
				if (positionAnterior == 0) {
					positionAnterior = documentHeight - page.getHeight();
				}
				float pagePosition = positionAnterior;

				/*
				 * Se a altura restante no documento de destino form menor que a
				 * altura do documento, cria-se uma nova p�gina no documento
				 * de destino.
				 */
				if ((positionAnterior) < 22) {
					document.newPage();
					document.setPageSize(pageSize);
					document.setMargins(marginLeft, marginRight, marginTop,
							marginBottom);
					pagePosition = 0;
					positionAnterior = documentHeight - page.getHeight();
					// Adiciona a p�gina ao PDF destino
					cb.addTemplate(page, 0, positionAnterior);
				} else {
					// Adiciona a p�gina ao PDF destino
					cb.addTemplate(page, 0, pagePosition);
				}
				positionAnterior -= page.getHeight();
			}
		}

		byteOS.flush();
		document.close();
		byte[] arquivoEmBytes = byteOS.toByteArray();
		byteOS.close();
		return arquivoEmBytes;
	}

	public static byte[] mergeFilesInPages(List<byte[]> pdfFilesAsByteArray)
			throws DocumentException, IOException {
		return mergeFilesInPages(pdfFilesAsByteArray, PageSize.A4, 0, 0, 0, 0);
	}
	
	public static void reports(Map<String, Object> params,
			List<String> relatorios, ServletContext servletContext)
			throws JRException, IOException, DocumentException {

		// ServletContext servletContext = this.getContextoServlet();

		List<byte[]> filesInBytes = new ArrayList<byte[]>();



		for (String relatorio : relatorios) {
			filesInBytes.add(IreporUtil.getPdfReportAsByteArray("P", params, relatorio
					+ ".jasper", null, servletContext));
		}

		byte[] files = null;
		files = IreporUtil.mergeFilesInPages(filesInBytes);

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext
				.getResponse();

		response.setContentType(null);
		response.setContentLength(files.length);

		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
		Date date = new Date();
		String filename = "ficha_" + dateFormat.format(date) + ".pdf";

		response.addHeader("Content-Disposition", "filename=" + "\"" + filename
				+ "\"");

		ServletOutputStream responseStream = response.getOutputStream();
		responseStream.write(files, 0, files.length);

		if (responseStream != null) {
			responseStream.flush();
			responseStream.close();
		}

		context.renderResponse();
		context.responseComplete();

	}

}
