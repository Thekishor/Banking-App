package com.BankingApp.Service;

import java.util.List;

import com.BankingApp.Dto.AccountDto;

public interface AccountService {
	public AccountDto createAccount(AccountDto accountDto);
	public List<AccountDto> getAllAccounts();
	public AccountDto getAccountById(long id);
	public void deleteAccount(long id);
	public AccountDto depositeAmount(long id, double amount);
	public AccountDto withdrawAmount(long id, double amount);
}
