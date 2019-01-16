package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.RegitEquipVO;

public class RegitEquipDAO {
	// 신규설비등록
	public RegitEquipVO getEquipRegiste(RegitEquipVO evo) throws Exception {

		String sql = "insert into equipment " + "(eq_no, eq_type, eq_name, eq_num, eq_price, eq_rentnum) " + "values "
				+ "(equipment_seq.nextval,?,?,?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		RegitEquipVO regitEquipVo = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, evo.getEqType());
			pstmt.setString(2, evo.getEqName());
			pstmt.setInt(3, evo.getEqNum());
			pstmt.setInt(4, evo.getEqPrice());
			pstmt.setInt(5, evo.getEqRentNum());
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보입력");
				alert.setHeaderText("설비 등록 완료!");
				alert.setContentText("설비 등록 성공!");
				alert.showAndWait();

				regitEquipVo = new RegitEquipVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("설비 등록 실패!");
				alert.setContentText("설비 등록 실패!");
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

		return regitEquipVo;
	}

	// 선택한 설비 수정
	public RegitEquipVO getEquipUpdate(RegitEquipVO evo, int no) throws Exception {

		String dml = "update equipment set " + " eq_num=?,eq_price=? where eq_no =?";

		Connection con = null;
		PreparedStatement pstmt = null;
		RegitEquipVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);

			pstmt.setInt(1, evo.getEqNum());
			pstmt.setInt(2, evo.getEqPrice());
			pstmt.setInt(3, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보입력");
				alert.setHeaderText("설비 수정 완료!");
				alert.setContentText("설비 수정 성공!");
				alert.showAndWait();

				retval = new RegitEquipVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("설비 수정 실패!");
				alert.setContentText("설비 수정 실패!");
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

	public ArrayList<RegitEquipVO> getEquipTotal() {
		ArrayList<RegitEquipVO> list = new ArrayList<RegitEquipVO>();
		String tml = "select * from equipment order by eq_no desc";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RegitEquipVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emVo = new RegitEquipVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getInt(6));
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

	// 데이터베이스에서 설비 테이블 컬럼의 개수
	public ArrayList<String> getEquipColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from equipment";
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

	// 정보 삭제
	public void getEquipDelete(int no) throws Exception {
		String dml = "delete from equipment where eq_no =?";
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
				alert.setHeaderText("설비 삭제 완료!");
				alert.setContentText("설비 삭제 성공!");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("설비 삭제 실패!");
				alert.setContentText("설비 삭제 실패!");
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

	// 품명 입력받아 정보 가져오기
	public ObservableList<String> getEquipName() {

		ObservableList<String> list = FXCollections.observableArrayList();
		String tml = "select eq_name from equipment order by eq_no";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				emVo = rs.getString(1);
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

	// 품명 입력받아 설비번호 가져오기
	public int getEquipNum(String name) {

		String tml = "select eq_no from equipment where eq_name = ?";
		int val = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setString(1, name);
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

	// 품명 입력받아 대여비 가져오기
	public int getEquipPrice(String name) {

		String tml = "select eq_price from equipment where eq_name = ?";
		int val = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setString(1, name);
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

	// 품명 입력받아 수량 가져오기
	public int getEquipAmount(String name) {

		String tml = "select eq_num from equipment where eq_name = ?";
		int val = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setString(1, name);
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

	// 품명 입력받아 대여수량 가져오기
	public int getEquipRentAmount(String name) {

		String tml = "select eq_rentnum from equipment where eq_name = ?";
		int val = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setString(1, name);
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

	// 대여수량 수정
	public RegitEquipVO getRentNumUpdate(int rentnum, String name) throws Exception {

		String dml = "update equipment set " + " eq_rentnum=? where eq_name =?";

		Connection con = null;
		PreparedStatement pstmt = null;
		RegitEquipVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);

			pstmt.setInt(1, rentnum);
			pstmt.setString(2, name);

			int i = pstmt.executeUpdate();

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
	

}
