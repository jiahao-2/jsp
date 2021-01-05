package newsServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import util.Pagination;

import dao.ManagerDAO;
import dao.NewsDAO;
import dao.NoticeDAO;
import dao.SortDAO;

import beans.Manager;
import beans.News;
import beans.Notice;
import beans.Sort;

public class managerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		RequestDispatcher rd =null;
		PrintWriter out = response.getWriter();

		if("login".equals(action)){
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			Manager m =new Manager();
			m.setName(account);
			m.setAccount(account);
			m.setPassword(password);
			m = ManagerDAO.CheckLogin(m);
			//System.out.println(m.getName());
			if(m != null){
				HttpSession session=request.getSession(true);
				session.setAttribute("account",m.getAccount());
				m.setNumber(m.getNumber()+1);
				session.setAttribute("manager", m);
				if(ManagerDAO.ManagerUpdate(m)){
					session.setAttribute("manager", m);
				}
				/*ArrayList<News> newsList = NewsDAO.getAllNews();
				session.setAttribute("nList", newsList);
				sortList = SortDAO.getAllSort();
				session.setAttribute("sortList", sortList);	*/
			}
				out.print("<script>parent.window.location.href='../index.jsp'</script>");
			
	
		}else if("add".equals(action)){
			
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			String name = request.getParameter("name");	
			Manager m = new Manager();
			m.setAccount(account);
			m.setName(name);
			m.setPassword(password);
		    if (ManagerDAO.ManagerAdd(m)){
		    	request.setAttribute("message", "success！！");
		    }else{
		    	request.setAttribute("message", "Fail！！");
		    }
		    disPatcher(request, response);  
		}else if("getManager".equals(action)){
			int id = Integer.parseInt(request.getParameter("Id"));
			Manager m = ManagerDAO.getManagerById(id);
			request.setAttribute("manager", m);
			rd = request.getRequestDispatcher("/bManager/updateManager.jsp");
			rd.forward(request, response);
		}else if("update".equals(action)){
			int id = Integer.parseInt(request.getParameter("Id"));
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			String name = request.getParameter("name");	
			Manager m = new Manager();
			m.setManagerID(id);
			m.setAccount(account);
			m.setName(name);
			m.setPassword(password);
			if (ManagerDAO.ManagerUpdate(m)){
		    	request.setAttribute("message", "success！！");
		    }else{
		    	request.setAttribute("message", "Fail！！");
		    }
		    disPatcher(request, response);  
		}else if("delete".equals(action)){
			int id = Integer.parseInt(request.getParameter("Id"));
			if(ManagerDAO.ManagerDetele(id)){
				request.setAttribute("message", "success！！");
		    }else{
		    	request.setAttribute("message", "Fail！！");
		    }
			 disPatcher(request, response);
		
	}else if("getAll".equals(action)){
		disPatcher(request,response);
	}else if("loginOut".equals(action)){
		request.getSession().removeAttribute("manager");
		out.print("<script>parent.window.location.href='../index.jsp'</script>");
	}		
		
	}
	private void disPatcher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		pageSplit(request);
		    rd = request.getRequestDispatcher("/bManager/selectManager.jsp");	
		    rd.forward(request, response);
	}
	private void pageSplit(HttpServletRequest request) {
		List<Manager> managerList;
		managerList = ManagerDAO.getAllManager();
		Integer pageNumber;
		String page = request.getParameter("page");
		if (null == page ||"".equals(page)) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.parseInt(page);
		}

		Pagination<Manager> managerPage = new Pagination<Manager>();
		managerList = managerPage.splitPage(managerList, "manager", pageNumber);
		request.setAttribute("page", pageNumber);
		request.setAttribute("totalPage", managerPage.getTotalPage());		
		

		request.setAttribute("managerList", managerList);
	}

	

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
