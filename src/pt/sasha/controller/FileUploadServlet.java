package pt.sasha.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = 
            Logger.getLogger(FileUploadServlet.class.getCanonicalName());
	
	public static String FILE_TEMP_PATH;

    /**
     * Default constructor. 
     */
    public FileUploadServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		
		String ngServerPath = "http://127.0.0.1:3000";
		LOGGER.info("Dev environment only - being served on Angular Lite-Server listening by default on "+ngServerPath);
		
		//TODO: Remove this line before deploying to the app server. 
		/** Intended for development environment only. The angular app will be integrated in this project, 
		 * so we will have to remove this before deploying to the app server.
		 */
		response.sendRedirect(ngServerPath);
		//uncomment and use this 2 lines instead:
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/");
//		dispatcher.forward(request, response);
		
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	        												throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

	    // Create path components to save the file
	    final String path = request.getParameter("destination");
	    final Part filePart = request.getPart("file");
	    final String fileName = getFileName(filePart);

	    OutputStream out = null;
	    InputStream filecontent = null;
	    
	    try {
	        out = new FileOutputStream(new File(path + File.separator
	                + fileName));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        LOGGER.log(Level.INFO, "File{0}  being uploaded to {1}", new Object[]{fileName, path});
	        
	        
	        
	    } catch (FileNotFoundException fne) {

	        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
	                new Object[]{fne.getMessage()});
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	    }
	    
	    FILE_TEMP_PATH = path+File.separator+fileName;
	}

	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}

}
