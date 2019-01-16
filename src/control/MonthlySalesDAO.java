package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RentalVO;

public class MonthlySalesDAO {
	RentalVO rvo = new RentalVO();

	// 1ø˘∏≈√‚
	public ObservableList<Double> getJanSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='01' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 2ø˘∏≈√‚
	public ObservableList<Double> getFebSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='02' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 3ø˘∏≈√‚
	public ObservableList<Double> getMarSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='03' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 4ø˘∏≈√‚
	public ObservableList<Double> getAprSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='04' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 5ø˘∏≈√‚
	public ObservableList<Double> getMaySales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='05' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 6ø˘∏≈√‚
	public ObservableList<Double> getJunSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='06' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 7ø˘∏≈√‚
	public ObservableList<Double> getJulSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='07' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 8ø˘∏≈√‚
	public ObservableList<Double> getAugSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='08' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 9ø˘∏≈√‚
	public ObservableList<Double> getSepSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='09' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 10ø˘∏≈√‚
	public ObservableList<Double> getOctSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='10' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 11ø˘∏≈√‚
	public ObservableList<Double> getNovSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='11' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// 12ø˘∏≈√‚
	public ObservableList<Double> getDecSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental "
				+ " group by rollup(to_char(r_date,'mm'),r_default,to_char(r_date,'yyyy')) "
				+ " having to_char(r_date,'mm')='12' and r_default !='πÃ≥≥' and to_char(r_date,'yyyy')='" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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

	// ø¨∏≈√‚
	public ObservableList<Double> getTotalSales(String date) {
		ObservableList<Double> list = FXCollections.observableArrayList();
		String tml = "select sum(r_totalprice) from rental " + " group by rollup(to_char(r_date,'yyyy'),r_default) "
				+ " having  r_default != 'πÃ≥≥' and to_char(r_date,'yyyy') = '" + date + "'";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double emVo = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(tml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = rs.getDouble(1);
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
