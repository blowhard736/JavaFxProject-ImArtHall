package model;

public class AddEquipVO {
	private int addNo;
	private int rentNo;
	private int equipNo;
	private String equipName;
	private int addNum;
	private int addPrice;
	private String addRentDate;
	private String addReturnDate;
	private String addState;
	
	public AddEquipVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddEquipVO(int addNo, int rentNo, int equipNo, String equipName, int addNum, int addPrice,
			String addRentDate, String returnDate, String addState) {
		super();
		this.addNo = addNo;
		this.rentNo = rentNo;
		this.equipNo = equipNo;
		this.equipName = equipName;
		this.addNum = addNum;
		this.addPrice = addPrice;
		this.addRentDate = addRentDate;
		this.addReturnDate = returnDate;
		this.addState = addState;
	}

	public AddEquipVO(int rentNo, int equipNo, String equipName, int addNum, int addPrice, String addRentDate,
			String addReturnDate, String addState) {
		super();
		this.rentNo = rentNo;
		this.equipNo = equipNo;
		this.equipName = equipName;
		this.addNum = addNum;
		this.addPrice = addPrice;
		this.addRentDate = addRentDate;
		this.addReturnDate = addReturnDate;
		this.addState = addState;
	}

	
	public AddEquipVO(int rentNo, int equipNo, String equipName, int addNum, int addPrice, String addRentDate,
			String addState) {
		super();
		this.rentNo = rentNo;
		this.equipNo = equipNo;
		this.equipName = equipName;
		this.addNum = addNum;
		this.addPrice = addPrice;
		this.addRentDate = addRentDate;
		this.addState = addState;
	}

	public int getAddNo() {
		return addNo;
	}

	public void setAddNo(int addNo) {
		this.addNo = addNo;
	}

	public int getRentNo() {
		return rentNo;
	}

	public void setRentNo(int rentNo) {
		this.rentNo = rentNo;
	}

	public int getEquipNo() {
		return equipNo;
	}

	public void setEquipNo(int equipNo) {
		this.equipNo = equipNo;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public int getAddNum() {
		return addNum;
	}

	public void setAddNum(int addNum) {
		this.addNum = addNum;
	}

	public int getAddPrice() {
		return addPrice;
	}

	public void setAddPrice(int addPrice) {
		this.addPrice = addPrice;
	}

	public String getAddRentDate() {
		return addRentDate;
	}

	public void setAddRentDate(String addRentDate) {
		this.addRentDate = addRentDate;
	}

	public String getAddReturnDate() {
		return addReturnDate;
	}

	public void setAddReturnDate(String addReturnDate) {
		this.addReturnDate = addReturnDate;
	}

	public String getAddState() {
		return addState;
	}

	public void setAddState(String addState) {
		this.addState = addState;
	}

	
}
