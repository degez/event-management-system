package com.yucel.model;

public enum DiscountCodes {

	GAMMA(10, "13032018", "13032018"), BECK(15, "31032018", "31032018"), 
		CUNNINGHAM(5, "26052018","26052018"), AGILE(20, "11022018", "13022018");

	private int discount;
	private String startDate;
	private String endDate;

	private DiscountCodes(int discount, String startDate, String endDate) {
		this.discount = discount;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getDiscount() {
		return discount;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

}
