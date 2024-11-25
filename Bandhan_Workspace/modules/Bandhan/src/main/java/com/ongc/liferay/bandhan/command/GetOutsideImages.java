package com.ongc.liferay.bandhan.command;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.log.LoggerFactory;

@Component(
	    immediate = true,
	    property = {
	        "osgi.http.whiteboard.context.path=/bandhanImageServlet",
	        "osgi.http.whiteboard.servlet.pattern=/blade/bandhanServlet/*"
	    },
	    service = Servlet.class
	)

public class GetOutsideImages extends HttpServlet{
	
	private static final Log LOGGER = LogFactoryUtil.getLog(GetOutsideImages.class);
	@Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
		
		String cpfnumber = request.getParameter("cpfno");
		ResourceBundle szLable = ResourceBundle.getBundle("/content/bandhan");
		
		LOGGER.info(szLable.getString("profileFilePath").toString().trim()+cpfnumber+".jpg");
		response.setContentType("image/jpeg");
		File file = new File(szLable.getString("profileFilePath").toString().trim()+cpfnumber+".jpg");
		if(file.exists()){}else {file = new File(szLable.getString("profileFilePath").toString().trim()+"default.jpg");}
		BufferedImage image = ImageIO.read(file);
		ImageIO.write(image, "jpg", response.getOutputStream());
		
		
        
    }
	
	

}
