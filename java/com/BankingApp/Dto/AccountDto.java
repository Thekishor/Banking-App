package com.BankingApp.Dto;

import lombok.Data;

@Data
public class AccountDto {

	private long id;
	private String accountHolderName;
	private double balance;
}
