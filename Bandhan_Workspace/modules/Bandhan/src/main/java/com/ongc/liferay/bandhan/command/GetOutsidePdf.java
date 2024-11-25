package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.ongc.liferay.bandhan.dao.TrustDao;
import com.ongc.liferay.bandhan.dao.Impl.TrustDaoImpl;
import com.ongc.liferay.bandhan.model.User;
import com.ongc.liferay.bandhan.service.UserService;
import com.ongc.liferay.bandhan.service.Impl.UserServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ResourceBundle;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

@Component(
	    immediate = true,
	    property = {
	        "osgi.http.whiteboard.context.path=/trustPdf",
	        "osgi.http.whiteboard.servlet.pattern=/blade/trustPdfservlet/*"
	    },
	    service = Servlet.class
	)

public class GetOutsidePdf extends HttpServlet{

	private static final Log LOGGER = LogFactoryUtil.getLog(GetOutsidePdf.class);

	ResourceBundle szLable = ResourceBundle.getBundle("/content/bandhan");
	@Override
	protected void doGet(
	        HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException {
		
		String trust = request.getParameter("trust");
		String doctype = request.getParameter("doc");
		String year = request.getParameter("year");
		String cpfNo = request.getParameter("cpfNo");
		
		StringBuffer fileName = new StringBuffer();
		TrustDao tdao = new TrustDaoImpl();
		String panNo = tdao.getPanNo(cpfNo)!=null?tdao.getPanNo(cpfNo):"";
		
		if (doctype.equalsIgnoreCase("tds_q1")) {
			doctype="tds";
			fileName.append(panNo + "_Q1.pdf");
		} else if (doctype.equalsIgnoreCase("tds_q2")) {
				doctype="tds";
				fileName.append(panNo + "_Q2.pdf");
		}else if (doctype.equalsIgnoreCase("tds_q3")) {
				doctype="tds";
				fileName.append(panNo + "_Q3.pdf");
		}else if (doctype.equalsIgnoreCase("tds_q4")) {
				doctype="tds";
				fileName.append(panNo + "_Q4.pdf");
		} else {
			fileName.append(year + "_" + cpfNo + "_" + trust + "_" + doctype + ".pdf");
			//fileName.append("2019_47259_ECPF_BALSTMT.pdf");
		}
		String folderName=szLable.getString("trustPdfFilePath").toString().trim()+ year + "/" + trust + "/" + doctype + "/";
		response.setContentType("application/pdf");		
		response.setHeader("Content-Disposition", "inline; filename="+fileName+";");
		OutputStream out=response.getOutputStream();
	
		try {
			//LOGGER.info("File name GetOutsidePdf bandhan ====="+folderName+fileName);
			File file=new File(folderName+fileName);
			FileInputStream in =null;
			if(file.exists()) {
		in= new FileInputStream(folderName+fileName);
			
		
		
		int content;
		while((content=in.read())!=-1) {
			out.write(content);
		}
			}else {
				LOGGER.info("File====not found===============");
			}
		
		}catch(IOException e) {
			LOGGER.error("Exception ===="+e);
		}
		
		
		out.close();
		
		
	}
	
	
	

}
