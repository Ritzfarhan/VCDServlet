package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShoppingServlet
 */
@WebServlet("/ShoppingServlet")
public class ShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        Vector buylist = (Vector) session.getAttribute("cart");

        String action = request.getParameter("action");

        if (action.equals("VIEW"))
        {
				if (buylist != null && (buylist.size() > 0))
				{
			                out.println("<html>\n" +
						"<head>\n" +
							"<title>Shopping Cart Example - View Cart</title>\n" +
						"</head>\n" +
						"<body bgcolor=\"#33CCFF\">\n" +
						"<center>\n" +
							"<table border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#FFFFFF\">\n" +
			                    	"<tr>\n" +
			  				"	<td><b>VCD TITLE</b></td>\n" +
			  				"	<td><b>ACTOR</b></td>\n" +
				      		"       <td><b>PRICE</b></td>\n" +
			  				"	<td><b>QUANTITY</b></td>\n" +
			  				"	<td></td>\n" +
							"</tr>\n");
			
			                for (int index=0; index < buylist.size();index++)
			                {
			                	VCDBean anOrder = (VCDBean) buylist.elementAt(index);
			                	out.println("<tr>\n" +
			                                "	<td><b>" + anOrder.getTitle() + "</b></td>\n" +
			                                "	<td><b>" + anOrder.getActor() + "</b></td>\n" +
			                                "   <td><b>" + anOrder.getPrice() + "</b></td>\n" +
			                                "	<td><b>" + anOrder.getQuantity() + "</b></td>\n" +
			                                "	<td>\n" +
			                                "		<form name=\"deleteForm\" action=\"ShoppingServlet\" method=\"POST\">\n" +
			                                "		<input type=\"submit\" value=\"Delete\">\n" +
			                                "		<input type=\"hidden\" name=\"delindex\" value='" + index + "'>\n" +
			                                "		<input type=\"hidden\" name=\"action\" value=\"DELETE\">\n" +
			                                "		</form>\n" +
			                                "	</td>\n" +
			                                "</tr> \n");
			
			                }
			
			                out.println("</table>\n" +
						"<center><a href=\"index.html\">shopping</a>&nbsp;&nbsp;<a href=\"ShoppingServlet?action=CHECKOUT\">checkout</a>\n" +
						"&nbsp;&nbsp;<a href=\"ShoppingServlet?action=LOGOUT\">logout/remove</a></center>\n" +
			  				"</body>\n" +
			                    	"</html>\n");
				}
				else
			                out.println("<html>\n" +
						"<head>\n" +
						"<title>Cart Empty</title>\n" +
						"</head>\n" +
						"<body bgcolor=\"#33CCFF\">\n" +
						"<center><b><p>Shopping Cart Empty!</b></center>\n" +
						"<center><a href=\"index.html\">continue shopping?</a></center>\n" +
						"</body>\n" +
						"</html>\n");
        }
        else if (action.equals("CHECKOUT"))
        {
	            if (buylist != null && (buylist.size() > 0))
	            {
	                float total = 0;
	
	                out.println("<html>\n" +
	                            "<head>\n" +
	                            "<title>Shopping Checkout</title>\n" +
	                            "</head>\n" +
	                            "<body bgcolor=\"#33CCFF\">\n" +
	                            "<center><h1>VCD Shopping Checkout<h1></center>\n" +
	                            "<hr><p>\n" +
	                            "<center>\n" +
	                            "<table border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#FFFFFF\">\n" +
	                            "   <tr>\n" +    
	                            "       <td><b>VCD TITLE</b></td>\n" +
	                            "       <td><b>ACTOR</b></td>\n" +
	                            "	<td><b>PRICE</b></td>\n" +
	                            "	<td><b>QUANTITY</b></td>\n" +
	                            "   </tr>\n");
	
	                for (int index=0; index < buylist.size();index++)
	                {
	                    VCDBean anOrder = (VCDBean) buylist.elementAt(index);
	                    out.println("	<tr>\n" +
	                                "		<td><b>" + anOrder.getTitle() + "</b></td>\n" +
	                                "		<td><b>" + anOrder.getActor() + "</b></td>\n" +
	                                "   	<td><b>" + anOrder.getPrice() + "</b></td>\n" +
	                                "		<td><b>" + anOrder.getQuantity() + "</b></td>\n" +
	                                "   </tr>\n");
	                    total = total + anOrder.getPrice() * anOrder.getQuantity();
	                }
	
	                total += 0.005;
	                String amount = new Float(total).toString();
	                int n = amount.indexOf('.');
	                amount = amount.substring(0,n+3);
	                out.println("	<tr>\n" +
	                            "		<td>     </td>\n" +
	                            "		<td><b>TOTAL</b></td>\n" +
	                            "		<td><b>" + amount + "</b></td>\n" +
	                            "		<td>     </td>\n" +
	                            "	</tr>\n");
	
	                out.println("</table>\n" +
	                            "<p><a href=\"index.html\">Shop some more!</a>\n" +
	                            "</center>\n" +
	                            "</body>\n" +
	                            "</html>\n");
	
	                session.invalidate();
	            }
	            else
	                out.println("<html>\n" +
	                            "<head>\n" +
	                            "<title>Cart Empty</title>\n" +
	                            "</head>\n" +
	                            "<body bgcolor=\"#33CCFF\">\n" +
	                            "<center><b><p>Shopping Cart Empty!</b></center>\n" +
	                            "<center><a href=\"index.html\">continue shopping?</a></center>\n" +
	                            "</body>\n" +
	                            "</html>\n");
       }
        else if (action.equals("LOGOUT"))
        {
				session.invalidate();
				out.println("<html>\n" +
			                       "<head>\n" +
			                        "<title>Cart Empty / Shopping Cancel</title>\n" +
			                        "</head>\n" +
			                        "<body bgcolor=\"#33CCFF\">\n" +
			                        "<center><b><p>Shopping Cart Empty!</b></center>\n" +
			                        "<center><b><p>Please come again or click <a href=\"index.html\">here</a> to shop/refill your cart</b></center>\n" +
			                        "</body>\n" +
			                        "</html>\n");
	     }
	}
        
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");

                HttpSession session = request.getSession(true);
                String action = request.getParameter("action");

                Vector buylist = (Vector)session.getAttribute("cart");

                boolean match = false;
                if (action.equals("ADD"))
                {
	            	VCDBean vcd = getVCD(request);
	            	if (buylist == null)
	            	{
	                        //add first vcd to the cart
	                        buylist = new Vector();
	                        buylist.addElement(vcd);
	            	}
	            	else
	            	{
	                        for(int i=0; i<buylist.size(); i++)
	                        {
	                            VCDBean aVCD = (VCDBean)buylist.elementAt(i);
	                            
	                            if (aVCD.getTitle().equals(vcd.getTitle()))
	                            {
	                                aVCD.setQuantity(aVCD.getQuantity() + vcd.getQuantity());
	                                buylist.setElementAt(aVCD, i);
	                                match = true;
	                            }
	                        }
	                        
	                        if (!match)
	                            buylist.addElement(vcd);
	            	}
	
		    		session.setAttribute("cart", buylist);
		    		out.println("<html>\n" +
		                                "<head>\n" +
		                                "<title>Shoping Cart Add Result</title>\n" +
		                                "</head>\n" +
		                                "<body bgcolor=\"#33CCFF\">\n" +
		                                "<center><B>" + vcd.getQuantity() + " item added to your shopping cart</B></center><br>\n" +
		                                "<center><a href=\"ShoppingServlet?action=VIEW\">view</a>&nbsp;&nbsp;<a href=\"index.html\">shopping</a>&nbsp;&nbsp;<a href=\"ShoppingServlet?action=CHECKOUT\">checkout</a>\n" +
		                                "&nbsp;&nbsp;<a href=\"ShoppingServlet?action=LOGOUT\">logout/remove</a>\n" +
		                                "</center></body>\n" +
		                                "</html>\n");

                }
                else if (action.equals("DELETE"))
                {
		    		String del = request.getParameter("delindex");
		    		VCDBean vcd = (VCDBean)buylist.elementAt((new Integer(del)).intValue());
		    		String title = vcd.getTitle().trim();
		    		int d = (new Integer(del)).intValue();
	            	buylist.removeElementAt(d);
		
		    		out.println("<html>\n" +
		                                "<head>\n" +
		                                "<title>Cart Item Remove</title>\n" +
		                                "</head>\n" +
		                                "<body bgcolor=\"#33CCFF\">\n" +
		                                "<center><B>VCD title = [" + title + "] removed from your shopping cart</B></center><br>\n" +
		                                "<center><a href=\"ShoppingServlet?action=VIEW\">view</a>&nbsp;&nbsp;<a href=\"index.html\">shopping</a>&nbsp;&nbsp;<a href=\"ShoppingServlet?action=CHECKOUT\">checkout</a>\n" +
		                                "&nbsp;&nbsp;<a href=\"ShoppingServlet?action=LOGOUT\">logout/remove</a>\n" +
		                                "</center></body>\n" +
		                                "</html>\n");
                }
    }

	
	void sendPage(HttpServletRequest request, HttpServletResponse response, String fileName) throws ServletException, IOException
    {
                // Get the dispatcher; it gets the main page to the user
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(fileName);

                if (dispatcher == null)
                {
    		System.out.println("There was no dispatcher");
                    // No dispatcher means the html file could not be found.
                    response.sendError(response.SC_NO_CONTENT);
                }
                else
                    dispatcher.forward(request, response);
    }


    VCDBean getVCD(HttpServletRequest request)
    {
                String myVCD = request.getParameter("VCD");
                String qty = request.getParameter("qty");
                StringTokenizer t = new StringTokenizer(myVCD,"|");
                String title = t.nextToken();
                String actor = t.nextToken();

                String price = t.nextToken();
                //price = price.replace('$',' ').trim();
                price = price.replace('R',' ').trim();
                price = price.replace('M',' ').trim();

                VCDBean vcd = new VCDBean();
                vcd.setTitle(title);
                vcd.setActor(actor);
                vcd.setPrice((new Float(price)).floatValue());
                vcd.setQuantity((new Integer(qty)).intValue());
                return vcd;
     }
        
        
}

