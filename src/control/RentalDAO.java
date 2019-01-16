package control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.RentalVO;

public class RentalDAO {

	// 신규대관등록
	public RentalVO getRentalRegiste(RentalVO rvo) throws Exception {
		String day = rvo.getDate();
		String rDay = rvo.getRegitDate();
		String eDay = rvo.getEditDate();

		String sql = "insert into rental "
				+ "(r_no,hi_no,cus_no,cus_comname,r_date,r_type,r_genre,r_morning,r_noon,r_evening,r_count,r_equipprice,r_rentprice,r_contractp,r_default,r_extratime,r_totalprice,r_regitdate,r_editdate) "
				+ "values " + "(rental_seq.nextval,?,?,?,to_date('" + day
				+ "','YYYY-MM-DD'),?,?,?,?,?,?,?,?,?,?,?,?,to_date('" + rDay + "','YYYY-MM-DD'),to_date('" + eDay
				+ "','YYYY-MM-DD'))";

		Connection con = null;
		PreparedStatement pstmt = null;
		RentalVO rentalVo = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, rvo.getHiNo());
			pstmt.setInt(2, rvo.getCusNo());
			pstmt.setString(3, rvo.getCusComName());
			pstmt.setString(4, rvo.getRentalType());
			pstmt.setString(5, rvo.getRentalGenre());
			pstmt.setString(6, rvo.getRentalMorning());
			pstmt.setString(7, rvo.getRentalNoon());
			pstmt.setString(8, rvo.getRentalEvening());
			pstmt.setInt(9, rvo.getRentalCount());
			pstmt.setInt(10, rvo.getRentalEquipPrice());
			pstmt.setDouble(11, rvo.getRentalRentalPrice());
			pstmt.setDouble(12, rvo.getRentalContractP());
			pstmt.setString(13, rvo.getRentalDefault());
			pstmt.setInt(14, rvo.getRentalExtraTime());
			pstmt.setDouble(15, rvo.getRentalTotalPrice());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보입력");
				alert.setHeaderText("대관 등록 완료!");
				alert.setContentText("대관 등록 성공!");
				alert.showAndWait();

				rentalVo = new RentalVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("대관 등록 실패!");
				alert.setContentText("대관 등록 실패!");
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

		return rentalVo;
	}

	// 단체명 입력받아 정보 조회
	public RentalVO getRentalCheck(String comName) throws Exception {
		String dml = "select * from rental where cus_comname = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RentalVO retval = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, comName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				retval = new RentalVO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getDate(5).toString(), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getInt(11), rs.getInt(12), rs.getDouble(13), rs.getDouble(14),
						rs.getString(15), rs.getInt(16), rs.getDouble(17), rs.getDate(18).toString(), rs.getDate(19).toString());
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

	public ArrayList<RentalVO> getRentalTotal() {
		ArrayList<RentalVO> list = new ArrayList<RentalVO>();
		String tml = "select * from rental order by r_no desc";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RentalVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emVo = new RentalVO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5).toString(),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getInt(11), rs.getInt(12), rs.getDouble(13), rs.getDouble(14), rs.getString(15),
						rs.getInt(16), rs.getDouble(17), rs.getDate(18).toString(), rs.getDate(19).toString());
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
	public ArrayList<String> getRentColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from rental";
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

	// 선택한 대관 정보 수정 및 등록버튼
	public RentalVO getRentalUpdate(RentalVO rvo, int no) throws Exception {
		String day = rvo.getDate();
		String rDay = rvo.getRegitDate();
		String eDay = rvo.getEditDate();

		String dml = "update rental set " + " hi_no=?,cus_comname=?,r_date=to_date('" + day
				+ "','YYYY-MM-DD'),r_type=?,r_genre=?,r_morning=?,r_noon=?,r_evening=?,r_count=?,r_equipprice=?,r_rentprice=?,r_contractp=?,r_default=?,r_extratime=?,r_totalprice=?,r_regitdate=to_date('"
				+ rDay + "','YYYY-MM-DD'),r_editdate=to_date('" + eDay + "','YYYY-MM-DD') where r_no =?";

		Connection con = null;
		PreparedStatement pstmt = null;
		RentalVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);

			pstmt.setInt(1, rvo.getHiNo());
			pstmt.setString(2, rvo.getCusComName());
			pstmt.setString(3, rvo.getRentalType());
			pstmt.setString(4, rvo.getRentalGenre());
			pstmt.setString(5, rvo.getRentalMorning());
			pstmt.setString(6, rvo.getRentalNoon());
			pstmt.setString(7, rvo.getRentalEvening());
			pstmt.setInt(8, rvo.getRentalCount());
			pstmt.setInt(9, rvo.getRentalEquipPrice());
			pstmt.setDouble(10, rvo.getRentalRentalPrice());
			pstmt.setDouble(11, rvo.getRentalContractP());
			pstmt.setString(12, rvo.getRentalDefault());
			pstmt.setInt(13, rvo.getRentalExtraTime());
			pstmt.setDouble(14, rvo.getRentalTotalPrice());
			pstmt.setInt(15, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보입력");
				alert.setHeaderText("대관정보 등록 완료!");
				alert.setContentText("대관정보 등록 성공!");
				alert.showAndWait();

				retval = new RentalVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("대관정보 등록 실패!");
				alert.setContentText("대관정보 등록 실패!");
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

	// 선택한 대관 취소버튼
	public RentalVO getRentalCancel(int no) throws Exception {

		String dml = "update rental set " + "r_default='최종미납',r_totalprice=r_contractp where r_no =?";

		Connection con = null;
		PreparedStatement pstmt = null;
		RentalVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);

			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보입력");
				alert.setHeaderText("대관정보 등록 완료!");
				alert.setContentText("대관정보 등록 성공!");
				alert.showAndWait();

				retval = new RentalVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("대관정보 등록 실패!");
				alert.setContentText("대관정보 등록 실패!");
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

	// 선택한 대관 완납버튼
	public RentalVO getCalculated(int no) throws Exception {

		String dml = "update rental set " + "r_default='완납' where r_no =?";

		Connection con = null;
		PreparedStatement pstmt = null;
		RentalVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);

			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("정보입력");
				alert.setHeaderText("대관정보 등록 완료!");
				alert.setContentText("대관정보 등록 성공!");
				alert.showAndWait();

				retval = new RentalVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("정보입력");
				alert.setHeaderText("대관정보 등록 실패!");
				alert.setContentText("대관정보 등록 실패!");
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

	// 등록자 번호 입력받아 대관번호 가져오기
	public int getRentNumber(int no) {

		String tml = "select r_no from rental where cus_no = ?";
		int val = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setInt(1, no);
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
	
	// 대관 번호 입력받아 대관일 가져오기
		public String getRentDate(int no) {

			String tml = "select r_date from rental where r_no = ?";
			String val = "";
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Date date ;
			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(tml);
				pstmt.setInt(1, no);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					date = rs.getDate(1);
					val = date.toString();

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

	// 대여자번호 중복검사
	public boolean getCNo(int no) {

		String tml = "select cus_no from rental where cus_no = ?";
		boolean val = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				val = true;

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
	
	// 대여횟수 중복검사
		public boolean getRentCount(String m,String n,String eve,int no) {

			String tml = "select * from rental where r_morning =? and r_noon =? and r_evening=? and r_no=?";
			boolean val = false;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(tml);
				pstmt.setString(1, m);
				pstmt.setString(2, n);
				pstmt.setString(3, eve);
				pstmt.setInt(4, no);
				rs = pstmt.executeQuery();

				if (rs.next()) {

					val = true;

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

	// 대관날짜 및 대관 장소 중복검사 아침
	public int getDayLoCheckMorning(String localDate, int no) {
		String tml = "select r_morning,r_noon,r_evening,r_date from rental where "
				+ "(r_default = '미납' or r_default = '완납')and r_date=? and r_morning='o' and hi_no=?";
		int val = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setString(1, localDate);
			pstmt.setInt(2, no);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				val = 1;

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

	// 대관날짜 및 대관 장소 중복검사 점심
	public int getDayLoCheckNoon(String date, int no) {

		String tml = "select r_morning,r_noon,r_evening,r_date from rental where (r_default = '미납' or r_default = '완납')and r_date=? and r_noon='o' and hi_no=?";
		int val = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setString(1, date);
			pstmt.setInt(2, no);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				val = 1;

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

	// 대관날짜 및 대관 장소 중복검사 저녁
	public int getDayLoCheckEvening(String date, int no) {

		String tml = "select r_morning,r_noon,r_evening,r_date from rental where (r_default = '미납' or r_default = '완납')and r_date=? and r_evening='o' and hi_no=?";
		int val = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setString(1, date);
			pstmt.setInt(2, no);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				val = 1;

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

	// 마감임박 단체명 가져오기
	public ObservableList<String> getCusComName() {

		ObservableList<String> list = FXCollections.observableArrayList();
		String tml = "select cus_comname from rental where r_date-sysdate>7 and r_date-sysdate<8 and r_default='미납'";

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

	// 최종미납확정된 단체명 가져오기
	public ObservableList<String> getDefaultCusComName() {

		ObservableList<String> list = FXCollections.observableArrayList();
		String tml = "select cus_comname from rental where r_date-sysdate<7 and r_default='미납'";

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

	// 최종미납확정된 대관번호 가져오기
	public ObservableList<Integer> getDefaultRentNo() {

		ObservableList<Integer> list = FXCollections.observableArrayList();
		String tml = "select r_no from rental where r_date-sysdate<7 and r_default='미납'";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int emVo = 0;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				emVo = rs.getInt(1);
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

	// 신청된 대관날짜의 년도 가져오기
	public ObservableList<String> getRentDate() {

		ObservableList<String> list = FXCollections.observableArrayList();
		String tml = "select to_char(r_date,'yyyy') from rental group by to_char(r_date,'yyyy')";

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
}
