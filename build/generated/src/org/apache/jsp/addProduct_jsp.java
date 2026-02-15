package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class addProduct_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/footer.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write(" \r\n");
      out.write("<!DOCTYPE html> \r\n");
      out.write("<html> \r\n");
      out.write("<head> \r\n");
      out.write("<title>Add Product</title> \r\n");
      out.write("<meta charset=\"utf-8\"> \r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> \r\n");
      out.write("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\"> \r\n");
      out.write(" \r\n");
      out.write("<link rel=\"stylesheet\" href=\"mycss.css\"> \r\n");
      out.write("</head> \r\n");
      out.write("<body style=\"background-color: #E6F9E6;\"> \r\n");
      out.write(" ");
 
        String userName=(String)session.getAttribute("userName");
                String password=(String)session.getAttribute("password");
                String userType=(String)session.getAttribute("userType");
                if(userType==null || !userType.equalsIgnoreCase("admin")){
                    response.sendRedirect("login.jsp?message=Access denied ! Please login as admin");
                }else if(userName==null||password==null){
                    response.sendRedirect("login.jsp?message=Session expired ! Please login again");
                }

 
      out.write(" \r\n");
      out.write(" \r\n");
      out.write(" ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "header.jsp", out, false);
      out.write(" \r\n");
      out.write(" \r\n");
      out.write(" ");
 
        String message= (String)request.getAttribute("message");
        
 
      out.write(" \r\n");
      out.write(" <div class=\"container\"> \r\n");
      out.write("  <div class=\"row justify-content-center\"> \r\n");
      out.write("<form action=\"./AddProductServlet\" method=\"post\" enctype=\"multipart/form-data\" \r\n");
      out.write("     class=\"col-md-6 col-md-offset-3 myform\"> \r\n");
      out.write("    <div class=\"text-center\"> \r\n");
      out.write("     <h2 class=\"text-primary\">Product Addition Form</h2> \r\n");
      out.write("     ");
 
        if(message!=null){ 
     
      out.write(" \r\n");
      out.write("     <p style=\"color: blue;\"> \r\n");
      out.write("      ");
      out.print(message);
      out.write(" \r\n");
      out.write("     </p> \r\n");
      out.write("     ");
 
     } 
     
      out.write(" \r\n");
      out.write("    </div> \r\n");
      out.write("    <div></div> \r\n");
      out.write("    <div class=\"row mt-3\"> \r\n");
      out.write("     <div class=\"col-md-6 form-group\"> \r\n");
      out.write("      <label for=\"last_name\">Product Name</label><input type=\"text\" \r\n");
      out.write("       placeholder=\"Enter Product Name\" name=\"name\" class=\"form-control\" \r\n");
      out.write("       id=\"last_name\" required> \r\n");
      out.write("     </div> \r\n");
      out.write("     <div class=\"col-md-6 form-group\"> \r\n");
      out.write("      <label for=\"producttype\">Product Type</label><select name=\"type\" \r\n");
      out.write("       id=\"producttype\" class=\"form-control\" required> \r\n");
      out.write("       <option value=\"mobile\">MOBILE</option> \r\n");
      out.write("       <option value=\"tv\">TV</option> \r\n");
      out.write("       <option value=\"camera\">CAMERA</option> \r\n");
      out.write("       <option value=\"laptop\">LAPTOP</option> \r\n");
      out.write("       <option value=\"tablet\">TABLET</option> \r\n");
      out.write("       <option value=\"speaker\">SPEAKER</option> \r\n");
      out.write("       <option value=\"other\">Some Other Appliances</option> \r\n");
      out.write("      </select> \r\n");
      out.write("     </div> \r\n");
      out.write("    </div> \r\n");
      out.write("    <div class=\"form-group mt-3\"> \r\n");
      out.write("     <label for=\"last_name\">Product Description</label> \r\n");
      out.write("     <textarea name=\"info\" class=\"form-control\" id=\"last_name\" required></textarea> \r\n");
      out.write("    </div> \r\n");
      out.write("    <div class=\"row mt-3\"> \r\n");
      out.write("     <div class=\"col-md-6 form-group\"> \r\n");
      out.write("      <label for=\"last_name\">Unit Price</label><input type=\"number\" \r\n");
      out.write("       placeholder=\"Enter Unit Price\" name=\"price\" class=\"form-control\" \r\n");
      out.write("       id=\"last_name\" required> \r\n");
      out.write("     </div> \r\n");
      out.write("     <div class=\"col-md-6 form-group\"> \r\n");
      out.write("      <label for=\"last_name\">Stock Quantity</label><input type=\"number\" \r\n");
      out.write("       placeholder=\"Enter Stock Quantity\" name=\"quantity\" \r\n");
      out.write("       class=\"form-control\" id=\"last_name\" required> \r\n");
      out.write("     </div> \r\n");
      out.write("    </div> \r\n");
      out.write("    <div class=\"mt-3\"> \r\n");
      out.write("     <div class=\"col-md-12 form-group\"> \r\n");
      out.write("      <label for=\"last_name\">Product Image</label><input type=\"file\" \r\n");
      out.write("       placeholder=\"Select Image\" name=\"image\" class=\"form-control\" \r\n");
      out.write("       id=\"last_name\" required> \r\n");
      out.write("     </div> \r\n");
      out.write("    </div> \r\n");
      out.write("    <div class=\"row mt-3\"> \r\n");
      out.write("     <div class=\"col-md-6 text-center\" style=\"margin-bottom: 2px;\"> \r\n");
      out.write("      <button type=\"reset\" class=\"btn btn-danger\">Reset</button> \r\n");
      out.write("     </div> \r\n");
      out.write("     <div class=\"col-md-6 text-center\"> \r\n");
      out.write("      <button type=\"submit\" class=\"btn btn-primary\">Add Product</button> \r\n");
      out.write("     </div> \r\n");
      out.write("    </div> \r\n");
      out.write("   </form> \r\n");
      out.write("  </div> \r\n");
      out.write(" </div> \r\n");
      out.write(" \r\n");
      out.write(" ");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("  <head>\n");
      out.write("    <meta charset=\"utf-8\" />\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n");
      out.write("    <title>Bootstrap demo</title>\n");
      out.write("    <link\n");
      out.write("      href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\"\n");
      out.write("      rel=\"stylesheet\"\n");
      out.write("      integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\"\n");
      out.write("      crossorigin=\"anonymous\"\n");
      out.write("    />\n");
      out.write("    <link\n");
      out.write("      rel=\"stylesheet\"\n");
      out.write("      href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css\"\n");
      out.write("      integrity=\"sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==\"\n");
      out.write("      crossorigin=\"anonymous\"\n");
      out.write("      referrerpolicy=\"no-referrer\"\n");
      out.write("    />\n");
      out.write("  </head>\n");
      out.write("  <body>\n");
      out.write("    <div class=\"container-fluid bg-primary text-white p-3\">\n");
      out.write("      <div class=\"container\">\n");
      out.write("        <h2 class=\"text-center\">Contact</h2>\n");
      out.write("        <p class=\"text-center\">We love our fans!</p>\n");
      out.write("        <div class=\"row\">\n");
      out.write("          <div class=\"col-md-4\">\n");
      out.write("            <p><i class=\"fa-solid fa-phone\"></i> Phone : +91 1234567890</p>\n");
      out.write("            <p>\n");
      out.write("              <i class=\"fa-solid fa-envelope\"></i> Email : scalive4u@gmail.com\n");
      out.write("            </p>\n");
      out.write("            <p><i class=\"fa-solid fa-signs-post\"></i> Postal Code : 462020</p>\n");
      out.write("          </div>\n");
      out.write("\n");
      out.write("          <div class=\"col-md-8\">\n");
      out.write("            <form action=\"#\">\n");
      out.write("              <div class=\"row\">\n");
      out.write("                <div class=\"col-sm-6 form-group mt-1\">\n");
      out.write("                  <input\n");
      out.write("                    type=\"text\"\n");
      out.write("                    class=\"form-control\"\n");
      out.write("                    placeholder=\"name\"\n");
      out.write("                    required\n");
      out.write("                  />\n");
      out.write("                </div>\n");
      out.write("                <div class=\"col-sm-6 form-group mt-1\">\n");
      out.write("                  <input\n");
      out.write("                    type=\"email\"\n");
      out.write("                    class=\"form-control\"\n");
      out.write("                    placeholder=\"email\"\n");
      out.write("                    required\n");
      out.write("                  />\n");
      out.write("                </div>\n");
      out.write("              </div>\n");
      out.write("\n");
      out.write("              <textarea\n");
      out.write("                  name=\"\"\n");
      out.write("                  id=\"\"\n");
      out.write("                  rows=\"5\"\n");
      out.write("                  class=\"form-control mt-1\"\n");
      out.write("                  placeholder=\"Enter your message..\"\n");
      out.write("                ></textarea>\n");
      out.write("\n");
      out.write("                <div class=\"row\">\n");
      out.write("                    <div class=\"col-md-12 form-group mt-1\">\n");
      out.write("                        <button type=\"button\" class=\"btn btn-warning\">send</button>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </form>\n");
      out.write("          </div>\n");
      out.write("        </div>\n");
      out.write("      </div>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <script\n");
      out.write("      src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\"\n");
      out.write("      integrity=\"sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz\"\n");
      out.write("      crossorigin=\"anonymous\"\n");
      out.write("    ></script>\n");
      out.write("  </body>\n");
      out.write("</html>\n");
      out.write(" \r\n");
      out.write("</body> \r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
