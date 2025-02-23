//View.html [ welcome file ]
//
//hyperlink
//	view persons
//		on click of the above link, control should go to
//			ViewPersonServ servlet 
//				which will retrieve all the records from person table and display them on the browser.

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class ViewPersonServ
 */
public class ViewPersonServ extends HttpServlet {
	private Connection con;
	private static final long serialVersionUID = 1L;
    
	public void init(ServletConfig config) throws ServletException {
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/java";
			con=DriverManager.getConnection(url,"root","Sohan@1034");
		}
		catch(Exception ee)
		{
			System.out.println(ee);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter pw=response.getWriter();
		try {
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from person");
			while(rs.next())
			{
				String name=rs.getString("name");
				int age=rs.getInt("age");
				pw.println(name+"   "+age);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	 try {
		 response.setContentType("text/html");
		 PrintWriter pw=response.getWriter();
		 String name=request.getParameter("name");
		 int age=Integer.parseInt(request.getParameter("age"));
		 PreparedStatement pst=con.prepareStatement("insert into person values(?,?)");
		 pst.setString(1, name);
		 pst.setInt(2, age);
		 int k=pst.executeUpdate();
			if(k>0)
			{
				pw.println("Record has been added");
			}
			else
			{
				pw.println("cannot add");
			}
		 
	 }
	 catch(Exception e)
	 {
		 System.out.println(e);
	 }
	}

}
