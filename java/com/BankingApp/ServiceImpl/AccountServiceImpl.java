package com.BankingApp.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankingApp.Dto.AccountDto;
import com.BankingApp.Entities.Account;
import com.BankingApp.Exception.ResourceNotFoundException;
import com.BankingApp.Repository.AccountRepository;
import com.BankingApp.Service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
		this.accountRepository = accountRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = DtoToAccount(accountDto);
		Account saveAccount = accountRepository.save(account);
		return AccountToDto(saveAccount);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		List<AccountDto> accountDtos = accounts.stream().map((account) -> AccountToDto(account))
				.collect(Collectors.toList());
		return accountDtos;
	}

	@Override
	public AccountDto getAccountById(long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
		return AccountToDto(account);
	}

	@Override
	public void deleteAccount(long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
		accountRepository.delete(account);
	}

	@Override
	public AccountDto depositeAmount(long id, double amount) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
		double total = account.getBalance() + amount;
		account.setBalance(total);
		Account saveAccount = accountRepository.save(account);
		return AccountToDto(saveAccount);
	}

	@Override
	public AccountDto withdrawAmount(long id, double amount) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "id", id));
		if (account.getBalance() < amount) {
			throw new RuntimeException("Insufficient amount");
		}
		double total = account.getBalance() - amount;
		account.setBalance(total);
		Account saveAccount = accountRepository.save(account);
		return AccountToDto(saveAccount);
	}

	public Account DtoToAccount(AccountDto accountDto) {
		return modelMapper.map(accountDto, Account.class);
	}

	public AccountDto AccountToDto(Account account) {
		return modelMapper.map(account, AccountDto.class);
	}

}
