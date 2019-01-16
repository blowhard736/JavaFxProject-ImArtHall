package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.AddEquipVO;

public class AddEquipDAO {
	// �űԱ�����
	public AddEquipVO getAddRegiste(AddEquipVO avo) throws Exception {
		String rentDay = avo.getAddRentDate();

		String sql = "insert into addequip "
				+ "(add_no, r_no, eq_no, eq_name, add_num, add_price, add_rentdate, add_returndate, add_state) "
				+ "values " + "(addequip_seq.nextval,?,?,?,?,?,to_date('" + rentDay + "','YYYY-MM-DD'),?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		AddEquipVO addEquip = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, avo.getRentNo());
			pstmt.setInt(2, avo.getEquipNo());
			pstmt.setString(3, avo.getEquipName());
			pstmt.setInt(4, avo.getAddNum());
			pstmt.setInt(5, avo.getAddPrice());
			pstmt.setString(6, avo.getAddReturnDate());
			pstmt.setString(7, avo.getAddState());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�����Է�");
				alert.setHeaderText("���� �߰� �Ϸ�!");
				alert.setContentText("���� �߰� ����!");
				alert.showAndWait();

				addEquip = new AddEquipVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�����Է�");
				alert.setHeaderText("���� �߰� ����!");
				alert.setContentText("���� �߰� ����!");
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

		return addEquip;
	}

	// ������ ���� ����
	public AddEquipVO getAddUpdate(AddEquipVO avo, int no) throws Exception {
		String rentDay = avo.getAddRentDate();

		String dml = "update addequip set " + " r_no=?,eq_no=?,eq_name=?,add_num=?,add_price=?,add_rentdate=to_date('"
				+ rentDay + "','yyyy-mm-dd') , add_returndate= ?, add_state= ? where add_no = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		AddEquipVO retval = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(dml);

			pstmt.setInt(1, avo.getRentNo());
			pstmt.setInt(2, avo.getEquipNo());
			pstmt.setString(3, avo.getEquipName());
			pstmt.setInt(4, avo.getAddNum());
			pstmt.setInt(5, avo.getAddPrice());
			pstmt.setString(6, avo.getAddReturnDate());
			pstmt.setString(7, avo.getAddState());
			pstmt.setInt(8, no);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�����Է�");
				alert.setHeaderText("�߰� ���� �Ϸ�!");
				alert.setContentText("�߰� ���� ����!");
				alert.showAndWait();

				retval = new AddEquipVO();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�����Է�");
				alert.setHeaderText("�߰� ���� ����!");
				alert.setContentText("�߰� ���� ����!");
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

	public ArrayList<AddEquipVO> getAddTotal() {
		ArrayList<AddEquipVO> list = new ArrayList<AddEquipVO>();
		String tml = "select * from addequip order by add_no desc";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AddEquipVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				emVo = new AddEquipVO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
						rs.getInt(6), rs.getDate(7).toString(), rs.getString(8), rs.getString(9));
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
	public ArrayList<String> getAddColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from addequip";
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
	public void getAddDelete(int no) throws Exception {
		String dml = "delete from addequip where add_no =?";
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
				alert.setHeaderText("�߰� ���� �Ϸ�!");
				alert.setContentText("�߰� ���� ����!");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�����Է�");
				alert.setHeaderText("�߰� ���� ����!");
				alert.setContentText("�߰� ���� ����!");
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

	// �����ȣ �Է¹޾� �뿩�� ���� ��������
	public int getTotalEquipPrice(String no) {

		String tml = "select sum(add_price) from addequip where r_no = ?";
		int val = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			pstmt.setString(1, no);
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

	// ǰ�� �Է¹޾� ���� ��������
	public int getAddEquipNum(int no) {

		String tml = "select add_num from addequip where add_no = ?";
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

	// �߰������ȣ �Է¹޾� �뿩���� ��������
	public String getRentState(int no) {

		String tml = "select add_state from addequip where add_no = ?";
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
