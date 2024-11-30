package com.BankingApp.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankingApp.Dto.AccountDto;
import com.BankingApp.Exception.ApiResponse;
import com.BankingApp.ServiceImpl.AccountServiceImpl;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	@Autowired
	private AccountServiceImpl accountServiceImpl;

	public AccountController(AccountServiceImpl accountServiceImpl) {
		this.accountServiceImpl = accountServiceImpl;
	}
	
	@PostMapping
	public ResponseEntity<AccountDto> saveAccount(@RequestBody AccountDto accountDto){
		AccountDto saveAccountDto = accountServiceImpl.createAccount(accountDto);
		return new ResponseEntity<AccountDto>(saveAccountDto,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		List<AccountDto> accountDtos = accountServiceImpl.getAllAccounts();
		return new ResponseEntity<List<AccountDto>>(accountDtos,HttpStatus.OK);
	}
	
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> depositeAmount(@PathVariable long id, @RequestBody Map<String, Double> request){
		Double amount =request.get("amount");
		AccountDto accountDto = accountServiceImpl.depositeAmount(id, amount);
		return new ResponseEntity<AccountDto>(accountDto,HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withdrawAmount(@PathVariable long id, @RequestBody Map<String, Double> request){
		Double amount = request.get("amount");
		AccountDto accountDto = accountServiceImpl.withdrawAmount(id, amount);
		return new ResponseEntity<AccountDto>(accountDto,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountBuId(@PathVariable long id){
		AccountDto accountDto = accountServiceImpl.getAccountById(id);
		return new ResponseEntity<AccountDto>(accountDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteAccount(@PathVariable long id){
		accountServiceImpl.deleteAccount(id);
		return new ResponseEntity<>(new ApiResponse("Account is deleted successfully",true),HttpStatus.OK);
	}
}
