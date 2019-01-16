package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CustomerVO;

public class CustomerDAO {
	// 신규대여자등록
	public CustomerVO getCustomerRegiste(CustomerVO cvo) throws Exception {

		String sql = "insert into customer "
				+ "(cus_type, cus_comname, cus_num, cus_ceoname, cus_name, cus_addr, cus_phone, cus_email, cus_genre, cus_title, cus_price, cus_starttime, cus_endtime, no) "
				+ "values " + "(?,?,?,?,?,?,?,?,?,?,?,?,?,customer_seq.nextval)";

		Connection con = null;
		PreparedStatement pstmt = null;
		CustomerVO customerVo = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cvo.getCusType());
			pstmt.setString(2, cvo.getCusComname());
			pstmt.setString(3, cvo.getCusNum());
			pstmt.setString(4, cvo.getCusCeoname());
			pstmt.setString(5, cvo.getCusName());
			pstmt.setString(6, cvo.getCusAddr());
			pstmt.setString(7, cvo.getCusPhone());
			pstmt.setString(8, cvo.getCusEmail());
			pstmt.setString(9, cvo.getCusGenre());
			pstmt.setString(10, cvo.getCusTitle());
			pstmt.setInt(11, cvo.getCusPrice());
			pstmt.setString(12, cvo.getCusStartTime());
			pstmt.setString(13, cvo.getCusEndTime());
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보입력");
				alert.setHeaderText("대여자 등록 완료!");
				alert.setContentText("대여자 등록 성공!");
				alert.showAndWait();

				customerVo = new CustomerVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("대여자 등록 실패!");
				alert.setContentText("대여자 등록 실패!");
				alert.showAndWait();
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}

		return customerVo;
	}

	// 단체명 입력받아 정보 조회
	public CustomerVO getCustomerCheck(String comName) throws Exception {
		String dml = "select * from customer where cus_comname = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO retval = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, comName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				retval = new CustomerVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getInt(14));
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return retval;
	}

	// 선택한 대관 단체 수정
	public CustomerVO getCustomerUpdate(CustomerVO cvo, int no) throws Exception {

		String dml = "update customer set "
				+ " cus_name=?,cus_addr=?,cus_phone=?,cus_email=?,cus_genre=?,cus_title=?,cus_price=?,cus_starttime=?,cus_endtime=? where no =?";

		Connection con = null;
		PreparedStatement pstmt = null;
		CustomerVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);

			pstmt.setString(1, cvo.getCusName());
			pstmt.setString(2, cvo.getCusAddr());
			pstmt.setString(3, cvo.getCusPhone());
			pstmt.setString(4, cvo.getCusEmail());
			pstmt.setString(5, cvo.getCusGenre());
			pstmt.setString(6, cvo.getCusTitle());
			pstmt.setInt(7, cvo.getCusPrice());
			pstmt.setString(8, cvo.getCusStartTime());
			pstmt.setString(9, cvo.getCusEndTime());
			pstmt.setInt(10, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보입력");
				alert.setHeaderText("대여자 수정 완료!");
				alert.setContentText("대여자 수정 성공!");
				alert.showAndWait();

				retval = new CustomerVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("대여자 수정 실패!");
				alert.setContentText("대여자 수정 실패!");
				alert.showAndWait();
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}

		return retval;

	}

	// 정보 삭제
	public void getCusDelete(int no) throws Exception {
		String dml = "delete from customer where no =?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보입력");
				alert.setHeaderText("고객정보 삭제 완료!");
				alert.setContentText("고객정보 삭제 성공!");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("추가 삭제 실패!");
				alert.setContentText("추가 삭제 실패!");
				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}

	}

	public ArrayList<CustomerVO> getCustomerTotal() {
		ArrayList<CustomerVO> list = new ArrayList<CustomerVO>();
		String tml = "select * from customer order by no desc";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emVo = new CustomerVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getInt(11), rs.getString(12), rs.getString(13), rs.getInt(14));
				list.add(emVo);
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}

	public ArrayList<CustomerVO> getCustomerTotal2() {
		ArrayList<CustomerVO> list = new ArrayList<CustomerVO>();
		String tml = "select cus_type, cus_comname, cus_num, cus_ceoname, cus_name, cus_addr, cus_phone, cus_email, no from customer order by no desc";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emVo = new CustomerVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9));
				list.add(emVo);
			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}
	
	// 데이터베이스에서 대여자 테이블 컬럼의 개수
	public ArrayList<String> getCusColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from customer";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 객체변수선언 resultset의 내부정보
		ResultSetMetaData rsmd = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getColumnName(i));

			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return columnName;
	}
	
	// 대여자번호 가져오기
	public int getCusNo() {

		String tml = "select max(no) from customer";
		int val=0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {

				val = rs.getInt(1);

			}

		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return val;
	}
	
}
