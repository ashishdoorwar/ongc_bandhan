package com.ongc.liferay.bandhan.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.ongc.liferay.bandhan.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;



public class PdfGeneration {
	final static Log log = LogFactoryUtil.getLog(PdfGeneration.class.getName());
	
	
	public static void genrateEmployeeDeclarationFormPdf(User user,EmployeeDeclarationModel model,String filePath){
		log.info("genrateEmployeeDeclarationFormPdf :start");
		 Document document = new Document();
		 try {
			 log.info("name:"+user.getUserName()+",cpf:"+user.getCpfNo());
			 	
				Font smallNormalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9f, Font.NORMAL, BaseColor.BLACK);				
				Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.RED);

	            PdfWriter.getInstance(document, new FileOutputStream(new File(filePath)));

	            //open
	            document.open();
	            Paragraph emptyPra = new Paragraph();
	            Paragraph bodyTitle = new Paragraph("Declaration for availing ONGC Post Retirement Medical Benefit", headerFont);
				bodyTitle.add("\n\n");
				bodyTitle.setAlignment(Element.ALIGN_CENTER);
				document.add(emptyPra);
				document.add(emptyPra);
				document.add(bodyTitle);

				
				StringBuilder content1=new StringBuilder();
				SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
				
				
				content1.append("I "+user.getUserName()+" Identification No."+ user.getCpfNo());
				content1.append(" retired/voluntary retired as user on "+ sf.format(user.getDateOfSep()) +" residing at "+user.getLocation());
				content1.append(" hereby declare that I have not taken-up any full time regular employment outside ONGC after retirement/separation.");
				
				
				Paragraph content = new Paragraph(content1.toString(), smallNormalFont);
				content.add("\n");
				document.add(emptyPra);
				document.add(emptyPra);
				document.add(content);
				
				StringBuilder content2=new StringBuilder();
				content2.append("I do further solemnly declare that when I will take-up full time regular employment outside ONGC, " +
						"I shall immediately inform ONGC about the employment and will not avail medical facility during the duration of my " +
						"full time regular employment.\n");
				
				content2.append("\n I solemnly affirm that the above declaration is true to the best of my knowledge and belief. " +
						"I understand that in the event of the declaration being found to be incorrect at a later date or failure " +
						"to inform ONGC within a month of taking up employment outside ONGC, " +
						"I shall be liable to be debarred from availing medical facility under ONGC Post Retirement Medical Scheme.");
				
				Paragraph p3 = new Paragraph(content2.toString(), smallNormalFont);
				//p3.setIndentationLeft(20f);
				p3.setSpacingAfter(5f);
				p3.setSpacingBefore(20f);
				document.add(p3);
				String currentDt=sf.format(new Date());
				
				Paragraph p4 = new Paragraph("Date :"+currentDt, smallNormalFont);
				p4.setSpacingAfter(5f);
				document.add(p4);
				
				Paragraph p5 = new Paragraph("Current Residence Address :"+model.getCurrentPlace() , smallNormalFont);
				p5.setSpacingAfter(20f);
				
				document.add(p5);

	            //close
	            document.close();

	            System.out.println("Done");
	            log.info("Done");

	        } catch (Exception e) {
	            log.info("error::"+e.getMessage());
	        }

		
	}
	
	
	
	public static void main(String args[]){
		User user=new User();
		user.setCpfNo("test");
		user.setDateOfSep(new Date());
		user.setLocation("Bhind");
	
		
		
		//genrateEmployeeDeclarationFormPdf(user,FILE_NAME);
	}

}
