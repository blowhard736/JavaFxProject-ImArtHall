package model;

public class RentalVO {
	private int rentalNo;
	private int hiNo;
	private int cusNo;
	private String cusComName;
	private String Date;
	private String rentalType;
	private String rentalGenre;
	private String rentalMorning;
	private String rentalNoon;
	private String rentalEvening;
	private int rentalCount;
	private int rentalEquipPrice;
	private double rentalRentalPrice;
	private double rentalContractP;
	private String rentalDefault;
	private int rentalExtraTime;
	private double rentalTotalPrice;
	private String regitDate;
	private String editDate;
	
	public RentalVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public RentalVO(int hiNo, int cusNo, String cusComName, String date, String rentalType, String rentalGenre,
			String rentalMorning, String rentalNoon, String rentalEvening, int rentalCount, int rentalEquipPrice,
			double rentalRentalPrice, double rentalContractP, String rentalDefault, int rentalExtraTime,
			double rentalTotalPrice, String regitDate, String editDate) {
		super();
		this.hiNo = hiNo;
		this.cusNo = cusNo;
		this.cusComName = cusComName;
		Date = date;
		this.rentalType = rentalType;
		this.rentalGenre = rentalGenre;
		this.rentalMorning = rentalMorning;
		this.rentalNoon = rentalNoon;
		this.rentalEvening = rentalEvening;
		this.rentalCount = rentalCount;
		this.rentalEquipPrice = rentalEquipPrice;
		this.rentalRentalPrice = rentalRentalPrice;
		this.rentalContractP = rentalContractP;
		this.rentalDefault = rentalDefault;
		this.rentalExtraTime = rentalExtraTime;
		this.rentalTotalPrice = rentalTotalPrice;
		this.regitDate = regitDate;
		this.editDate = editDate;
	}



	public RentalVO(int rentalNo, int hiNo, int cusNo, String cusComName, String date, String rentalType,
			String rentalGenre, String rentalMorning, String rentalNoon, String rentalEvening, int rentalCount,
			int rentalEquipPrice, double rentalRentalPrice, double rentalContractP, String rentalDefault,
			int rentalExtraTime, double rentalTotalPrice, String regitDate, String editDate) {
		super();
		this.rentalNo = rentalNo;
		this.hiNo = hiNo;
		this.cusNo = cusNo;
		this.cusComName = cusComName;
		Date = date;
		this.rentalType = rentalType;
		this.rentalGenre = rentalGenre;
		this.rentalMorning = rentalMorning;
		this.rentalNoon = rentalNoon;
		this.rentalEvening = rentalEvening;
		this.rentalCount = rentalCount;
		this.rentalEquipPrice = rentalEquipPrice;
		this.rentalRentalPrice = rentalRentalPrice;
		this.rentalContractP = rentalContractP;
		this.rentalDefault = rentalDefault;
		this.rentalExtraTime = rentalExtraTime;
		this.rentalTotalPrice = rentalTotalPrice;
		this.regitDate = regitDate;
		this.editDate = editDate;
	}



	public int getRentalNo() {
		return rentalNo;
	}

	public void setRentalNo(int rentalNo) {
		this.rentalNo = rentalNo;
	}

	public int getHiNo() {
		return hiNo;
	}

	public void setHiNo(int hiNo) {
		this.hiNo = hiNo;
	}
	
	public int getCusNo() {
		return cusNo;
	}



	public void setCusNo(int cusNo) {
		this.cusNo = cusNo;
	}



	public String getCusComName() {
		return cusComName;
	}

	public void setCusComName(String cusComName) {
		this.cusComName = cusComName;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getRentalType() {
		return rentalType;
	}

	public void setRentalType(String rentalType) {
		this.rentalType = rentalType;
	}

	public String getRentalGenre() {
		return rentalGenre;
	}

	public void setRentalGenre(String rentalGenre) {
		this.rentalGenre = rentalGenre;
	}

	public String getRentalMorning() {
		return rentalMorning;
	}

	public void setRentalMorning(String rentalMorning) {
		this.rentalMorning = rentalMorning;
	}

	public String getRentalNoon() {
		return rentalNoon;
	}

	public void setRentalNoon(String rentalNoon) {
		this.rentalNoon = rentalNoon;
	}

	public String getRentalEvening() {
		return rentalEvening;
	}

	public void setRentalEvening(String rentalEvening) {
		this.rentalEvening = rentalEvening;
	}

	public int getRentalCount() {
		return rentalCount;
	}

	public void setRentalCount(int rentalCount) {
		this.rentalCount = rentalCount;
	}

	public int getRentalEquipPrice() {
		return rentalEquipPrice;
	}

	public void setRentalEquipPrice(int rentalEquipPrice) {
		this.rentalEquipPrice = rentalEquipPrice;
	}

	public double getRentalRentalPrice() {
		return rentalRentalPrice;
	}

	public void setRentalRentalPrice(double rentalRentalPrice) {
		this.rentalRentalPrice = rentalRentalPrice;
	}

	public double getRentalContractP() {
		return rentalContractP;
	}

	public void setRentalContractP(double rentalContractP) {
		this.rentalContractP = rentalContractP;
	}

	public String getRentalDefault() {
		return rentalDefault;
	}

	public void setRentalDefault(String rentalDefault) {
		this.rentalDefault = rentalDefault;
	}

	public int getRentalExtraTime() {
		return rentalExtraTime;
	}

	public void setRentalExtraTime(int rentalExtraTime) {
		this.rentalExtraTime = rentalExtraTime;
	}

	public double getRentalTotalPrice() {
		return rentalTotalPrice;
	}

	public void setRentalTotalPrice(double rentalTotalPrice) {
		this.rentalTotalPrice = rentalTotalPrice;
	}

	public String getRegitDate() {
		return regitDate;
	}

	public void setRegitDate(String regitDate) {
		this.regitDate = regitDate;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	
}
