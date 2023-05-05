package com.coderscampus.assignment13.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;
	
	@GetMapping("users/{userId}/accounts/{accountId}")
	public String getAccounts (ModelMap model, @PathVariable Long accountId) {
		Account accounts = accountService.findAccountById(accountId);
		User user = accounts.getUsers().get(0);
		
		model.put("accounts", accounts);
		model.put("user", user);
		
		return "accounts";
	}
	
	@PostMapping("users/{userId}/accounts")
	public String postOneAccount (@PathVariable Long userId) {
		accountService.save(userId);
		
		return "redirect:/users/" + userId;
	}
	
	@PostMapping("users/{userId}/accounts/{accountId}") 
	public String updateAccountName (@PathVariable Long userId, @PathVariable Long accountId, Account account) {
		account.setAccountName(account.getAccountName());
		accountService.save(account);
		
		userService.saveUser(userService.findById(userId));
		
		System.out.println(account.getAccountName());
		return "redirect:/users/" + userId + "/accounts/" + accountId;
	}
}
