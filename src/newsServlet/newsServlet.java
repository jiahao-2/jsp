package newsServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Pagination;

import dao.NewsDAO;
import dao.SortDAO;

import beans.News;
import beans.Sort;

public class newsServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");

		String forward = "";
		String message = "";
		String sortName = "";
		List<News> newsList = null;
		RequestDispatcher rd = null;
		ArrayList<Sort> sortList = null;
		if ("getNewsBySortId".equals(action)) {
			int sortId = Integer.parseInt(request.getParameter("sortId"));
			sortName = SortDAO.getSortByID(sortId).getSortName();
			getNewsBySortId(request, sortId);
			request.setAttribute("sortId", sortId);
			request.setAttribute("sortName", sortName);
			forward = "/bNews/selectNews.jsp";
			// System.out.println(request.getAttribute("sortId"));

		} else if ("add".equals(action)) {
			int sortId = Integer.parseInt(request.getParameter("sortId"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int sId = Integer.parseInt(request.getParameter("sId"));
			News news = new News();
			news.setNewsTitle(title);
			news.setSortId(sId);
			news.setNewsContent(content);
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			String time = timeFormat.format(new java.util.Date());
			news.setCreateTime(time);
			if (NewsDAO.NewsAdd(news)) {
				message = "success！";

			} else {
				message = "Fail！";
			}
			request.setAttribute("message", message);
			if (sortId == 0) {
		
				pageSplit(request);
	           
	             forward ="../background.jsp";
				

			} else {
				sortName = SortDAO.getSortByID(sortId).getSortName();
				request.setAttribute("sortName", sortName);
				getNewsBySortId(request, sortId);
				request.setAttribute("sortId", sortId);
				forward = "/bNews/selectNews.jsp";
			}

		} else if ("getDetail".equals(action)) {

			int newsId = Integer.parseInt(request.getParameter("id"));
			News news = null;
			news = NewsDAO.getNewsByID(newsId);
			int sortId = Integer.parseInt(request.getParameter("sortId"));
			if (sortId != 0) {
				sortName = SortDAO.getSortByID(sortId).getSortName();

			} else {
				sortName = SortDAO.getSortByID(news.getSortId()).getSortName();
			}
			request.setAttribute("sortName", sortName);
			request.setAttribute("news", news);
			request.setAttribute("sortId", sortId);

			forward = "/bNews/selectNewsContent.jsp";

		} else if ("getNews".equals(action)) {

			int newsId = Integer.parseInt(request.getParameter("id"));
			News news = null;
			news = NewsDAO.getNewsByID(newsId);
			sortList = SortDAO.getAllSort();

			int sortId = Integer.parseInt(request.getParameter("sortId"));
			if (sortId == 0) {
				sortName = SortDAO.getSortByID(news.getSortId()).getSortName();
			} else {
				sortName = SortDAO.getSortByID(sortId).getSortName();

			}
			request.setAttribute("sortList", sortList);
			request.setAttribute("news", news);
			request.setAttribute("sortId", sortId);
			request.setAttribute("sortName", sortName);
			forward = "/bNews/updateNews.jsp";

		} else if ("getSort".equals(action)) {
			int sortId = Integer.parseInt(request.getParameter("sortId"));
			if (sortId != 0) {
				sortName = SortDAO.getSortByID(sortId).getSortName();
				request.setAttribute("sortName", sortName);
			}

			sortList = SortDAO.getAllSort();
			request.setAttribute("sortList", sortList);
			request.setAttribute("sortId", sortId);
			forward = "/bNews/insertNews.jsp";

		} else if ("update".equals(action)) {
			int sortId = Integer.parseInt(request.getParameter("sortId"));

			int newsId = Integer.parseInt(request.getParameter("newsId"));
			int sId = Integer.parseInt(request.getParameter("sId"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			News news = new News();
			news.setNewsID(newsId);
			news.setNewsTitle(title);
			news.setSortId(sId);
			news.setNewsContent(content);
			if (NewsDAO.NewsUpdate(news)) {
				message = "success！";
			} else {
				message = "Fail！";

			}
			request.setAttribute("message", message);
			if (sortId == 0) {
				pageSplit(request);
				
				forward ="../background.jsp";
				
			} else {
				sortName = SortDAO.getSortByID(sortId).getSortName();
				getNewsBySortId(request, sortId);
				request.setAttribute("sortId", sortId);
				request.setAttribute("sortName", sortName);
				forward = "/bNews/selectNews.jsp";
			}

		} else if ("delete".equals(action)) {
			int sortId = Integer.parseInt(request.getParameter("sortId"));

			int newsId = Integer.parseInt(request.getParameter("id"));
			if (NewsDAO.deleteNews(newsId)) {

				message = "success！";

			} else {
				message = "Fail！";
			}
			request.setAttribute("message", message);
			if (sortId == 0) {

				pageSplit(request);

				forward ="../background.jsp";
				
			} else {
				sortName = SortDAO.getSortByID(sortId).getSortName();
				getNewsBySortId(request, sortId);
				request.setAttribute("sortId", sortId);
				request.setAttribute("sortName", sortName);
				forward = "/bNews/selectNews.jsp";

			}

		} else if ("getAllSort".equals(action)) {
			int sortId = Integer.parseInt(request.getParameter("sortId"));
			sortList = SortDAO.getAllSort();
			request.setAttribute("sortList", sortList);
			request.setAttribute("sortId", sortId);
			forward = "/bNews/insertNews.jsp";

		}else if("getAllNews".equals(action)){
			
			pageSplit(request);
	
			
			forward = "../background.jsp";
			
			
		}
		if (null != forward && !"".equals(forward)) {

			rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);

		}

	}


	private void pageSplit(HttpServletRequest request) {
		List<News> newsList;
		Integer pageNumber;
		String page = request.getParameter("page");
		if (null == page ||"".equals(page)) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.parseInt(page);
		}
		newsList = NewsDAO.getAllNews();
		System.out.println(newsList.size());
		Pagination<News> newsPage = new Pagination<News>();
		newsList = newsPage.splitPage(newsList, "news", pageNumber);
		request.setAttribute("page", pageNumber);
		request.setAttribute("totalPage", newsPage.getTotalPage());
		request.setAttribute("nList", newsList);
	}


	private void getNewsBySortId(HttpServletRequest request, int sortId) {
		List<News> newsList;
		Integer pageNumber;
		String page = request.getParameter("page");
		if (null == page || "".equals(page)) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.parseInt(page);
		}
		newsList = NewsDAO.getNewsBySortID(sortId);
		Pagination<News> newsPage = new Pagination<News>();
		newsList = newsPage.splitPage(newsList, "news", pageNumber);
		request.setAttribute("page", pageNumber);
		request.setAttribute("totalPage", newsPage.getTotalPage());
		request.setAttribute("newsList", newsList);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
