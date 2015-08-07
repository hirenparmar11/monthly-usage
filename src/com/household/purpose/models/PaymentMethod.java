package com.household.purpose.models;

public class PaymentMethod {

	private PaymentType type;
	private String bankName;
	private String accountNumber;
	private String readableName;
	
	public static enum PaymentType {CREDIT_CARD, DEBIT_CARD, CASH}

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getReadableName() {
		return readableName;
	}

	public void setReadableName(String readableName) {
		this.readableName = readableName;
	}
	
	
}
