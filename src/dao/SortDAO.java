package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import beans.Sort;

public class SortDAO {
	//������ŷ���
	public static boolean SortAdd(Sort sort) {
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
		PreparedStatement ps = null;
		String sqlStr = "insert into SortInfo (SortName,CreateTime) values(?,?)";
		try {
			ps = con.prepareStatement(sqlStr);
			ps.setString(1, sort.getSortName());
			ps.setString(2, sort.getCreateTime());
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
	
	//�������ŷ���
	public static boolean SortUpdate(Sort sort) {
	
			Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
			String sqlStr = "update SortInfo set SortName=? where SortId=?";
			PreparedStatement ps = null;
			try {
				 ps = con.prepareStatement(sqlStr);
				ps.setString(1, sort.getSortName());
				ps.setInt(2, sort.getSortId());
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
	
	//ɾ�����ŷ���
	public static  boolean SortDelete(int id) {
			Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
			String sqlStr = "delete from SortInfo where SortID =" + id;
			PreparedStatement ps = null;
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
	//���ݱ�Ż�ȡ���ŷ�����Ϣ		
		public static Sort getSortByID(int sortId){
			Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
			try {
				Statement stm = con.createStatement();
				String sqlStr = "select SortID,SortName,CreateTime from SortInfo where SortID ="+sortId;
				ResultSet rs = stm.executeQuery(sqlStr);
				if(rs.next()){
					Sort sort = returnSort(rs);
					return sort;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}

		public static Sort returnSort(ResultSet rs) throws SQLException {
			Sort sort = new Sort();
			sort.setSortId(rs.getInt("SortID"));
			sort.setSortName(rs.getString("SortName"));
			sort.setCreateTime(rs.getString("CreateTime"));
			return sort;
		}
	

		//��ȡ����������Ϣ
		public static ArrayList<Sort>  getAllSort(){
			Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
			String sqlStr = "select SortID,SortName,CreateTime from SortInfo ";
			Statement stm = null;
			ResultSet rs = null;
			try {
				stm = con.createStatement();
				 rs = stm.executeQuery(sqlStr);
				ArrayList<Sort> sortList = new ArrayList<Sort>();
				while(rs.next()){
					Sort sort = returnSort(rs);
					sortList.add(sort);
				}
				return sortList;
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DbConnectDAO.freeConnection(con, stm, rs);
			}
			
			return null;
		}
	
	public static void main(String[] args) throws SQLException {
/*		Sort s = new Sort();
		s.setSortID(4);
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time =timeFormat.format(new java.util.Date());
		s.setCreateTime(time);*/
		
	/*	if(SortDAO.SortAdd(s)){
			System.out.println("���ŷ�����ӳɹ���");
		}else{
			System.out.println("���ŷ������ʧ�ܣ�");
	}*/
	/*	
		s.setSortName("�ӿڼ��ɿ�����");
		if(SortDAO.SortUpdate(s)){
			System.out.println("���ŷ�����³ɹ���");
		}else{
			System.out.println("���ŷ������ʧ�ܣ�");
		}*/
		
	/*	if(SortDAO.SortDelete(3)){
			System.out.println("���ŷ���ɾ���ɹ���");
		}else{
			System.out.println("���ŷ���ɾ��ʧ�ܣ�");
		}*/
		
		Sort s = SortDAO.getSortByID(1);
		System.out.println(s.getSortId()+"\t"+s.getSortName()+"\t"+s.getCreateTime());
		ArrayList<Sort> sl = SortDAO.getAllSort();
		for (Sort sort : sl) {
			System.out.println(sort.getSortId()+"\t"+sort.getSortName()+"\t"+sort.getCreateTime());
			
		}
	}

	}

