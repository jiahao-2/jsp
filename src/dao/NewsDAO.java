package dao;

import java.sql.*;
import java.util.ArrayList;

import beans.News;

public class NewsDAO {
//�������
	public static boolean NewsAdd(News news){
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
		//���NewsInfo����һ��News���͵�news
		String sqlStr = "insert into NewsInfo (SortId,NewsTitle,NewsContent,CreateTime) values(?,?,?,?)";
		PreparedStatement ps =null;
		try {
			 ps = con.prepareStatement(sqlStr);
			ps.setInt(1, news.getSortId());
			ps.setString(2, news.getNewsTitle());
			ps.setString(3, news.getNewsContent());		
			ps.setString(4, news.getCreateTime());
			if(ps.executeUpdate()>0){
				
				return true;
			}
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbConnectDAO.freeConnection(con, ps, null);
		}
		return false;
	}
	//��������
	public static boolean NewsUpdate(News news){
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
		//���±�NewsInfo��NewsIDΪnews.NewsID�ļ�¼
		String sqlStr = "update NewsInfo set SortID=?,NewsTitle=?,NewsContent=? where NewsID=?";
		PreparedStatement ps = null;
		try {
			 ps = con.prepareStatement(sqlStr);
			ps.setInt(1, news.getSortId());
			ps.setString(2, news.getNewsTitle());
			ps.setString(3, news.getNewsContent());
			ps.setInt(4, news.getNewsID());
			if(ps.executeUpdate()>0){
				return true;
				}
		
					} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbConnectDAO.freeConnection(con, ps, null);
		}
		return false;
		
		
	}
	//ɾ������
	public static boolean deleteNews(int newsId){
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
		//ɾ����NewsInfo��NewsID��newsId�ļ�¼
		String sqlStr = "delete from NewsInfo where NewsID =" + newsId;
		PreparedStatement ps= null;
		try {
				 ps = con.prepareStatement(sqlStr);
				if(ps.executeUpdate()>0){
					return true;
				}
		} catch (SQLException e) {
			
				e.printStackTrace();
			}finally{
				DbConnectDAO.freeConnection(con, ps, null);
			}
		return false;
		
	}
	
	
	//�������ű�Ż�ȡ������Ϣ
	public static News getNewsByID(int newsId){
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
	
		Statement stm;
		try {
			stm = con.createStatement();
			//���ұ�NewsInfo��NewsId��newsId������
			String sqlStr = "select NewsID,SortID,NewsTitle,NewsContent,CreateTime from NewsInfo where NewsId ="+newsId;
			ResultSet rs = stm.executeQuery(sqlStr);
			if(rs.next()){
				News news = returnNews(rs);
				return news;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;	
	}
	
	//��ȡ����������Ϣ
	public static ArrayList<News>  getAllNews(){
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
		//���ұ�NewsInfo�е����м�¼
		String sqlStr = "select NewsID,NewsInfo.SortID,NewsTitle,NewsContent,NewsInfo.CreateTime from NewsInfo,SortInfo  where NewsInfo.SortID=SortInfo.SortID  order by CreateTime desc";
		return returnNewsList(con, sqlStr);
	}
	
	//�������ű���鿴������Ϣ
	public static ArrayList<News>  getNewsByNewsTitle(String title){
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
		//���ұ�NewsInfo�б����к���title�ļ�¼
		String sqlStr = "select NewsID,SortID,NewsTitle,NewsContent,CreateTime from NewsInfo where NewsTitle like '%"+title+"%'  order by CreateTime desc";
		return returnNewsList(con, sqlStr);
	}
	public static ArrayList<News> returnNewsList(Connection con, String sqlStr) {
		Statement stm =null;
		ResultSet rs = null;
		try {
			 stm = con.createStatement();
			 rs = stm.executeQuery(sqlStr);
			
			ArrayList<News> newsList = new ArrayList<News>();
			while(rs.next()){
				News news = returnNews(rs);
				newsList.add(news);
			}
			return newsList;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally
		{
			DbConnectDAO.freeConnection(con, stm, rs);
		}
		
		return null;
	}
	
	public static News returnNews(ResultSet rs) throws SQLException {
		News news = new News();
		news.setNewsID(rs.getInt("NewsID"));
		news.setSortId(rs.getInt("SortID"));
		news.setNewsTitle(rs.getString("NewsTitle"));
		news.setNewsContent(rs.getString("NewsContent"));
		news.setCreateTime(rs.getString("CreateTime"));
		return news;
	}
	
	//���ݷ����Ų鿴������Ϣ
	public static ArrayList<News>  getNewsBySortID(int sortID){
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
		//�鿴NewsInfo�з���ΪsortID������
		String sqlStr = "select NewsID,SortID,NewsTitle,NewsContent,CreateTime from NewsInfo where SortID ="+sortID + " order by CreateTime desc";
		return returnNewsList(con, sqlStr);
	}
	
	//�������ű���ͷ����Ų鿴������Ϣ
	public static ArrayList<News>  getNewsByNewsTitleandSortID(String newsTitle,int sortID){
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
		//�鿴��NewsInfo�з���ΪsortID�µİ���newsTitle���������
		String sqlStr = "select NewsID,SortID,NewsTitle,NewsContent,CreateTime from NewsInfo where 1=1";
	    if(null!= newsTitle && !("".equals(newsTitle)) ){
	    	sqlStr += " and NewsTitle like '%"+newsTitle+"%'";
	    	
	    }if(sortID != 0){
	    	sqlStr += "  and SortID ="+sortID;
	    	
	    }
			
	    sqlStr += " order by CreateTime desc ";
		return returnNewsList(con, sqlStr);
	}
	public static void main(String[] args) throws SQLException {
		ArrayList<News> nl = NewsDAO.getAllNews();
		System.out.println(nl.size());
		/*for (News news : nl) {
			System.out.println(news.getNewsID()+"\t"+news.getSortId()+"\t"+news.getNewsTitle()+"\t"+news.getNewsContent()+"\t"+news.getCreateTime());
			
		}*/
		
		
		
	}



}
