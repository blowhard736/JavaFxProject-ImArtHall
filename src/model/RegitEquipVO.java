package model;

public class RegitEquipVO {
	private int eqNo;
	private String eqType;
	private String eqName;
	private int eqNum;
	private int eqPrice;
	private int eqRentNum;
	
	public RegitEquipVO() {
		super();
		
	}

	public RegitEquipVO(int eqNo, String eqType, String eqName, int eqNum, int eqPrice, int eqRentNum) {
		super();
		this.eqNo = eqNo;
		this.eqType = eqType;
		this.eqName = eqName;
		this.eqNum = eqNum;
		this.eqPrice = eqPrice;
		this.eqRentNum = eqRentNum;
	}

	public RegitEquipVO(String eqType, String eqName, int eqNum, int eqPrice, int eqRentNum) {
		super();
		this.eqType = eqType;
		this.eqName = eqName;
		this.eqNum = eqNum;
		this.eqPrice = eqPrice;
		this.eqRentNum = eqRentNum;
	}

	
	
	public RegitEquipVO(int eqNo, String eqType, String eqName, int eqNum, int eqPrice) {
		super();
		this.eqNo = eqNo;
		this.eqType = eqType;
		this.eqName = eqName;
		this.eqNum = eqNum;
		this.eqPrice = eqPrice;
	}

	public int getEqNo() {
		return eqNo;
	}

	public void setEqNo(int eqNo) {
		this.eqNo = eqNo;
	}

	public String getEqType() {
		return eqType;
	}

	public void setEqType(String eqType) {
		this.eqType = eqType;
	}

	public String getEqName() {
		return eqName;
	}

	public void setEqName(String eqName) {
		this.eqName = eqName;
	}

	public int getEqNum() {
		return eqNum;
	}

	public void setEqNum(int eqNum) {
		this.eqNum = eqNum;
	}

	public int getEqPrice() {
		return eqPrice;
	}

	public void setEqPrice(int eqPrice) {
		this.eqPrice = eqPrice;
	}

	public int getEqRentNum() {
		return eqRentNum;
	}

	public void setEqRentNum(int eqRentNum) {
		this.eqRentNum = eqRentNum;
	}

	
}
