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
import model.RegitHallVO;

public class RegitHallDAO {
	// �űԱ�����
	public RegitHallVO getHallRegiste(RegitHallVO evo) throws Exception {

		String sql = "insert into hallinfo "
				+ "(hi_no, hi_name, hi_area, hi_num, hi_genre1, hi_genre2, hi_genre3, hi_genre4) " + "values "
				+ "(hallinfo_seq.nextval,?,?,?,?,?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		RegitHallVO regitHall = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, evo.getHiName());
			pstmt.setInt(2, evo.getHiArea());
			pstmt.setInt(3, evo.getHiNum());
			pstmt.setInt(4, evo.getGenreOne());
			pstmt.setInt(5, evo.getGenreTwo());
			pstmt.setInt(6, evo.getGenreThree());
			pstmt.setInt(7, evo.getGenreFour());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�����Է�");
				alert.setHeaderText("���� ��� �Ϸ�!");
				alert.setContentText("���� ��� ����!");
				alert.showAndWait();

				regitHall = new RegitHallVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�����Է�");
				alert.setHeaderText("���� ��� ����!");
				alert.setContentText("���� ��� ����!");
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

		return regitHall;
	}

	// ������ ���� ����
	public RegitHallVO getHallUpdate(RegitHallVO evo, int no) throws Exception {

		String dml = "update hallinfo set "
				+ " hi_area=?,hi_num=?,hi_genre1=?,hi_genre2=?,hi_genre3=?,hi_genre4=? where hi_no =?";

		Connection con = null;
		PreparedStatement pstmt = null;
		RegitHallVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);

			pstmt.setInt(1, evo.getHiArea());
			pstmt.setInt(2, evo.getHiNum());
			pstmt.setInt(3, evo.getGenreOne());
			pstmt.setInt(4, evo.getGenreTwo());
			pstmt.setInt(5, evo.getGenreThree());
			pstmt.setInt(6, evo.getGenreFour());
			pstmt.setInt(7, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�����Է�");
				alert.setHeaderText("���� ���� �Ϸ�!");
				alert.setContentText("���� ���� ����!");
				alert.showAndWait();

				retval = new RegitHallVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�����Է�");
				alert.setHeaderText("���� ���� ����!");
				alert.setContentText("���� ���� ����!");
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

	public ArrayList<RegitHallVO> getHallTotal() {
		ArrayList<RegitHallVO> list = new ArrayList<RegitHallVO>();
		String tml = "select * from hallinfo order by hi_no desc";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RegitHallVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emVo = new RegitHallVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						rs.getInt(6), rs.getInt(7), rs.getInt(8));
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

	// �����ͺ��̽����� ���� ���̺� �÷��� ����
	public ArrayList<String> getHallColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from hallinfo";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ��ü�������� resultset�� ��������
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

	// ���� ����
	public void getHallDelete(int no) throws Exception {
		String dml = "delete from hallinfo where hi_no =?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�����Է�");
				alert.setHeaderText("���� ���� �Ϸ�!");
				alert.setContentText("���� ���� ����!");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�����Է�");
				alert.setHeaderText("���� ���� ����!");
				alert.setContentText("���� ���� ����!");
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

	// ����� ���� ��������
	public ObservableList<String> getHallName() {

		ObservableList<String> list = FXCollections.observableArrayList();
		String tml = "select hi_name from hallinfo order by hi_no";

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

	// ����� �Է¹޾� �����ȣ ��������
	public int getHallNum(String name) {

		String tml = "select hi_no from hallinfo where hi_name = ?";
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

	// �����̸��� �帣�� �Է¹޾� ��� �ݾ� ��������
	public int getHallPrice(String name, String genre) {

		String tml = "select "+ genre +" from hallinfo where hi_name = ?";
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
	
	// �����ȣ �Է¹޾� ����� ��������
		public String getHallName(int no) {

			String tml = "select hi_name from hallinfo where hi_no = ?";
			String val = "";
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(tml);
				pstmt.setInt(1, no);
				rs = pstmt.executeQuery();

				while (rs.next()) {

					val = rs.getString(1);

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
