package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import beans.Notice;
/**
 * ���������
 * @project Notice
 * @author admin
 *
 */

public class NoticeDAO {
/**
 * ��ӹ�����Ϣ
 * @param notice ��Ҫ��ӵĹ���
 * @return �Ƿ���ӳɹ�
 */
	public static boolean NoticeAdd(Notice notice) {
		Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
		String sqlStr = "insert into NoticeInfo (NoticeTitle,NoticeContent,CreateTime) values(?,?,?)";
		PreparedStatement ps = null;//���ݿ�Ԥ����
		try {
			 ps = con.prepareStatement(sqlStr);
			ps.setString(1, notice.getNoticeTitle());
			ps.setString(2, notice.getNoticeContent());
			ps.setString(3, notice.getCreateTime());
			if(ps.executeUpdate()>0){
				return true;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbConnectDAO.freeConnection(con, ps, null); //�ͷ�����
		}
		return false;
	}
	
/**
 * ���¹�����Ϣ
 * @param notice ��Ҫ���µĹ���
 * @return �Ƿ���³ɹ�
 */
	public static boolean NoticeUpdate(Notice notice) {
	
			Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
			String sqlStr = "update NoticeInfo set NoticeTitle=?,NoticeContent=? where NoticeId=?";
			PreparedStatement ps = null;
			try {
			   ps = con.prepareStatement(sqlStr);//���ݿ�Ԥ����
				ps.setString(1, notice.getNoticeTitle());
				ps.setString(2, notice.getNoticeContent());
				ps.setInt(3, notice.getNoticeID());
				if(ps.executeUpdate()>0){
					return true;
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DbConnectDAO.freeConnection(con, ps, null);//�ͷ�����
			}
			return false;	
		}
	/**
	 * ɾ��������Ϣ
	 * @param id ����ɾ�������id
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public static  boolean NoticeDelete(int id) {
			Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
			String sqlStr = "delete from NoticeInfo where NoticeID =" + id;
			PreparedStatement ps = null;
			try {
					 ps = con.prepareStatement(sqlStr);//���ݿ�Ԥ����
					if(ps.executeUpdate()>0){
						return true;
					}
					con.close();
					ps.close();
				} catch (SQLException e) {
				
					e.printStackTrace();
				}finally{
					DbConnectDAO.freeConnection(con, ps, null);//�ͷ�����
				}
			return false;
			
		}
/**
 * ���ݱ�Ż�ȡ������Ϣ	
 * @param noticeId  �����ȡ��Ϣ�����id
 * @return ���ݱ�Ų�ѯ�Ĺ������Ϣ
 */
		public static Notice getNoticeByID(int noticeId){
			Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
			String sqlStr = "select NoticeID,NoticeTitle,NoticeContent,CreateTime from NoticeInfo where NoticeID ="+noticeId;
			Statement stm = null;
			ResultSet rs = null;
			try {
				 stm = con.createStatement();
				 rs = stm.executeQuery(sqlStr);
				if(rs.next()){
					Notice notice = returnNotice(rs);
					return notice;
				}
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DbConnectDAO.freeConnection(con, stm, rs);//�ͷ�����
			}
			return null;
			
		}
/**
 * ������Ϣ���
 * @param rs ���ݵĽ��
 * @return �����Ϣ��Ĺ���
 * @throws SQLException
 */
		public static Notice returnNotice(ResultSet rs) throws SQLException {
			Notice notice = new Notice();
			notice.setNoticeID(rs.getInt("NoticeID"));
			notice.setNoticeTitle(rs.getString("NoticeTitle"));
			notice.setNoticeContent(rs.getString("NoticeContent"));
			notice.setCreateTime(rs.getString("CreateTime"));
			return notice;
		}
	

/**
 * ��ȡ����������Ϣ
 * @return ���й������Ϣ
 */
		public static ArrayList<Notice>  getAllNotice(){
			Connection con = DbConnectDAO.getConnection();//��ȡ���ݿ����Ӷ���
			String sqlStr = "select NoticeID,NoticeTitle,NoticeContent,CreateTime from NoticeInfo order by CreateTime desc";
			Statement stm = null;
			ResultSet rs = null;
			try {
				 stm = con.createStatement();
				 rs = stm.executeQuery(sqlStr);
				ArrayList<Notice> noticeList = new ArrayList<Notice>();
				while(rs.next()){
					Notice notice = returnNotice(rs);
					noticeList.add(notice);
				}
				return noticeList;
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DbConnectDAO.freeConnection(con, stm, rs);//�ͷ�����
			}
			
			return null;
		}
	
	public static void main(String[] args) throws SQLException {
		/*Notice n = new Notice();
		n.setNoticeTitle("�͵�����ɵý�����");
		n.setNoticeContent("���Ӱ���ֵĿ�¼��");
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time =timeFormat.format(new java.util.Date());
		n.setCreateTime(time);
		if(NoticeDAO.NoticeAdd(n)){
			System.out.println("������Ϣ��ӳɹ���");
		}else{
			System.out.println("������Ϣ���ʧ�ܣ�");
			}*/
	
		
//		n.setNoticeTitle("�ӿڼ��ɿ�����");
//		if(NoticeDAO.NoticeUpdate(n)){
//			System.out.println("������Ϣ���³ɹ���");
//		}else{
//			System.out.println("������Ϣ����ʧ�ܣ�");
//		}
		
//		if(NoticeDAO.NoticeDelete(2)){
//			System.out.println("������Ϣɾ���ɹ���");
//		}else{
//			System.out.println("������Ϣɾ��ʧ�ܣ�");
//		}
		
//		Notice n= NoticeDAO.getNoticeByID(1);
//		System.out.println(n.getNoticeID()+"\t"+n.getNoticeTitle()+"\t"+n.getNoticeContent()+"\t"+n.getCreateTime());
		ArrayList<Notice> nl = NoticeDAO.getAllNotice();
		for (Notice notice : nl) {
			System.out.println(notice.getNoticeID()+"\t"+notice.getNoticeTitle()+"\t"+notice.getNoticeContent()+"\t"+notice.getCreateTime());
			
		}
	}

	}

