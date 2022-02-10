package gestioneAcquisti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





@WebServlet("/PagamentoControl")
public class PagamentoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    // doGet
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        
        		
   
       getServletContext().getRequestDispatcher("/pagamento.jsp").forward(request, response);
       
    }

    //doPost
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 

        doGet(request, response);
    }

 

}