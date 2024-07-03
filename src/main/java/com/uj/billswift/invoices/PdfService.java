package com.uj.billswift.invoices;

import java.io.FileOutputStream;
import java.text.DecimalFormat;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class PdfService {
    public static String generatePdf(Invoice invoice) throws IOException, java.io.IOException {
        float totalPrice = invoice.getPrice() + invoice.getShipping();
        float icms = Math.round(totalPrice * 0.205);

        DecimalFormat df = new DecimalFormat("0.00");

        String pdfPath = invoice.getPdfPath();

        try (FileOutputStream fos = new FileOutputStream(pdfPath)) {
            PdfWriter pdfWriter = new PdfWriter(fos);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDocument);
        
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            document.setFont(font);

            document.add(new Paragraph("DANFE").setFontSize(20));
            document.add(new Paragraph("Documento Auxiliar da Nota Fiscal Eletrônica"));
            document.add(new Paragraph("Número: " + invoice.getInvoiceNumber()));
            document.add(new Paragraph("Série: " + invoice.getSeries()));
            document.add(new Paragraph("Propósito: " + invoice.getPurpose()));
            document.add(new Paragraph("Tipo de documento: " + invoice.getDocumentType()));
            document.add(new Paragraph("Natureza da Transação: " + invoice.getTransactionNature()));
            document.add(new Paragraph("Data de Emissão: " + invoice.getIssueDate()));
            document.add(new Paragraph("Data de Saída/Entrada: " + invoice.getEntryExitDate()));
            document.add(new Paragraph("Emitente").setFontSize(15));
            document.add(new Paragraph("Nome/Razão Social: " + invoice.getEmitter().getName()));
            document.add(new Paragraph("Inscrição Estadual: " + invoice.getEmitter().getStateRegistration()));
            document.add(new Paragraph("CNPJ: " + invoice.getEmitter().getCnpj()));
            document.add(new Paragraph("Destinatário/Remetente").setFontSize(15));
            document.add(new Paragraph("Nome/Razão Social: " + invoice.getRecipient().getName()));
            document.add(new Paragraph("CNPJ/CPF: " + invoice.getRecipient().getCpf()));
            document.add(new Paragraph("Endereço: " + invoice.getRecipient().getAddress()));
            document.add(new Paragraph("Bairro/Distrito: " + invoice.getRecipient().getNeighborhood()));
            document.add(new Paragraph("CEP: " + invoice.getRecipient().getZipCode()));
            document.add(new Paragraph("Município: " + invoice.getRecipient().getCity()));
            document.add(new Paragraph("Fone/Fax: " + invoice.getRecipient().getPhone()));
            document.add(new Paragraph("UF: " + invoice.getRecipient().getState()));
            document.add(new Paragraph("Cálculo do Imposto").setFontSize(15));
            document.add(new Paragraph("Base de cálculo do ICMS: " + totalPrice));
            document.add(new Paragraph("Valor do ICMS: " + df.format(icms)));
            document.add(new Paragraph("Base de cálculo do ICMS substituição: 0,00"));
            document.add(new Paragraph("Valor do ICMS substituição: 0,00"));
            document.add(new Paragraph("Valor total dos produtos: " + invoice.getPrice()));
            document.add(new Paragraph("Valor do frete: " + invoice.getShipping()));
            document.add(new Paragraph("Valor do seguro: 0,00"));
            document.add(new Paragraph("Desconto: " + invoice.getDiscount()));
            document.add(new Paragraph("Outras despesas acessórias: 0,00"));
            document.add(new Paragraph("Valor do IPI: 0,00"));
            document.add(new Paragraph("Valor total da nota: " + totalPrice));
            document.add(new Paragraph("Dados do Produto/Serviço").setFontSize(15));
            document.add(new Paragraph("Código do produto: " + invoice.getProductCode()));
            document.add(new Paragraph("Descrição dos produtos/serviços: " + invoice.getProductDescription()));
            document.add(new Paragraph("NCM/SH: "));
            document.add(new Paragraph("CST: "));
            document.add(new Paragraph("CFOP: "));
            document.add(new Paragraph("QTD.: " + invoice.getAmount()));
            document.add(new Paragraph("Valor unitário: " + invoice.getPrice()));
            document.add(new Paragraph("Valor total: " + totalPrice));

            document.close();
        }

        return pdfPath;
    }
}
