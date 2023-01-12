package com.project.dao;

import java.util.List;

import com.project.bean.Charges;
import com.project.bean.ClientDetails;
import com.project.bean.CreateCard;
import com.project.bean.Creds;
import com.project.bean.TransactionSpring;
import com.project.bean.WalletSpring;

public interface WalletDaoInter {
	public WalletSpring validateUser(String uid);
	public Creds validateUserCreds(String uid);
	public String createUser(WalletSpring w);
	public String createCredsUser(Creds c);
	public String withdrawAmount(String uid,double amount);
	public String updateAmount(String uid,double amount);
	public String fundTransfer(String uid,String ruid,double amount);
	public String changePassword(String uid,String newpass);
	public String validateAccountNumber(long Account);
	public List<TransactionSpring> transactions(String uid);
	public String showBalance(String uid);
	public String cardCreation(CreateCard c);
	public CreateCard validateCard(String uid);
	public String deatailsMail(String uid);
	public List<Creds> usersCreds();
	public String editCredUser(Creds c);
	public String deleteCredUser(String c);
	public String deleteUser(String c);
	public List<WalletSpring> usersAdmin(String c);
	public String editUser(WalletSpring w);
	public List<CreateCard> cards(String c);
	public List<CreateCard> userCards(String c);
	public String deleteCard(String c);
	public List<Charges> showCharges(String type);
	public String saveCharge(Charges charge);
	public Charges validateCharge(String type);
	public String editCharge(Charges ch);
	public String deleteCharge(String id);
	public String exeCharge(String id);
	public String addClient(ClientDetails cd);
}
