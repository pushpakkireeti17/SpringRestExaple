package com.project.service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bean.Charges;
import com.project.bean.ClientDetails;
import com.project.bean.CreateCard;
import com.project.bean.Creds;
import com.project.bean.TransactionSpring;
import com.project.bean.WalletSpring;
import com.project.dao.WalletDao;
import com.project.dao.WalletDaoInter;

@Service
public class WalletService implements WalletServiceInter {
	@Autowired
	WalletDaoInter wd;
	CreateCard create;
	public WalletSpring validateUser(String uid) {
		String id = uid.substring(0,5);
		if(uid.length()<3)
		{
			return null;
		}
		else if(id.equals("admin"))
		{
			return null;
		}
		else
		{
			return wd.validateUser(uid);
		}
	}
	public Creds validateUserCreds(String uid) {
		return wd.validateUserCreds(uid);
	}
	public String validateAccountNumber(long Account) {
		if(Account==0)
		{
			return "Zero";
		}
		else if(Account<1100010000||Account>1100099999)
		{
			return "Account Number Less";
		}
		else
		{
			return wd.validateAccountNumber(Account);
		}
	}

	public String createUser(WalletSpring w) {
		if(w.getName()==null||w.getName().length()<3)
		{
			return "Invalid Name";
		}
		else if(w.getName().matches("[a-zA-Z]+")!=true)
		{
			return"Invalid Name. Name should only contain Alphabets";
		}
		else if(w.getMobile_number()<6000000000l||w.getMobile_number()>9999999999l)
		{
			return "Invalid Mobile Number";
		}
		else if(w.getAmount()<1000)
		{
			return "Please Deposit more than 1000 to craete account";
		}
		else if(w.getUser_name().length()<5)
		{
			return "User Name Should be atleast of 5 Charachters ";
		}
		/*else if(w.getPassword().length()<8)
		{
			return "Password Should be atleast of 8 Charachters ";
		}*/
		else
		{
			return wd.createUser(w);
		}
	}
	public String createCredsUser(Creds c) {
		return wd.createCredsUser(c);
	}

	public String withdrawAmount(String uid,double amount) {
		if(uid!= null)
		{
			return wd.withdrawAmount(uid,amount);
		}
		else
		{
			return "UID Not received";
		}
	}

	public String updateAmount(String uid,double amount) {
		if(uid!= null)
		{
			return wd.updateAmount(uid,amount);
		}
		else
		{
			return "UID Not received";
		}
	}

	public String fundTransfer(String uid,String ruid,double amount) {
		if(uid==null)
		{
			return "UID Not received";
		}
		else if(amount<0)
		{
			return "Amount Should be More than 0";
		}
		else
		{
			return wd.fundTransfer(uid, ruid, amount);
		}
	}

	public String changePassword(String uid, String newpass) {
		if(uid==null)
		{
			return "uid not recieved";
		}
		else 
		{
			return wd.changePassword(uid, newpass);//write Server Side Validations in HTML page
		}
	}
		public String showBalance(String uid)
		{
			if(uid==null)
			{
				return "UID not Received";
			}
			else
			{
				return wd.showBalance(uid);
			}
		}
		public List<TransactionSpring> transactions(String uid)
		{
			return wd.transactions(uid);
		}
		@Override
		public String createCard(String uid, String custname) {
			Random rand = new Random(); 
			Calendar now = Calendar.getInstance();
	        int cardnumber1 =0;
	        cardnumber1=(int)((Math.random()*900000)+100000);
	        System.out.println(cardnumber1);
	        int cvv = 100+rand.nextInt(1000);
	        int year=(10+now.get(Calendar.YEAR));
	        // month start from 0 to 11
	        int month=(now.get(Calendar.MONTH) + 1);
	        create=new CreateCard(cardnumber1, custname, month, year, cvv, uid);
	        return wd.cardCreation(create);
		}
		@Override
		public CreateCard validateCard(String uid) {
			return wd.validateCard(uid);
			
		}
		@Override
		public String deatailsMail(String uid) {
			return wd.deatailsMail(uid);
		}
		@Override
		public List<Creds> usersCreds(String c) {
			Creds cc = new Creds();
			cc= wd.validateUserCreds(c);
			
			if(cc!= null)
			{
			return wd.usersCreds();}
			else return null;
		}
		@Override
		public Creds validateAdminUser(String uid) {
			
			String id = uid.substring(0,5);
			if(id.equals("admin"))
			{
				return wd.validateUserCreds(uid);
			}
			else
			{
				return null;
			}
		}

		@Override
		public String createAdmin(Creds c) {
			String check = c.getUserName();
			if(!(check.substring(0,5).equals("admin")))
			{
				return "Admin User Name should start  with 'Admin'";
			}
			else if(check.length()<5)
			{
				return "Length of user name should be atleast 5 chars and start with 'Admin'";
			}
			/*else if(c.getPass().length()<8)
			{
				return "Password should be minimum of 8 chars";
			}*/
			else if(c.getCustName()==null||c.getCustName().length()<3)
			{
				return "Invalid Name";
			}
			else if(c.getCustName().matches("[a-zA-Z]+")!=true)
			{
				return"Invalid Name. Name should only contain Alphabets";
			}
			else if(c.getPhone()<6000000000l||c.getPhone()>9999999999l)
			{
				return "Invalid Mobile Number";
			}
			else return wd.createCredsUser(c);
			
			
		}
		@Override
		public String editCredUser(Creds c)
		{
			return wd.editCredUser(c);
		}
		@Override
		public String deleteCredUser(String c) {
			// TODO Auto-generated method stub
			String id = c.substring(0,5);
			if(!(id.equals("admin")))
			{
				return "User can be deleted from User Tab in Home Page";
			}
			else
			{
				return wd.deleteCredUser(c);
			}
		}
		@Override
		public String deleteUser(String c) {
			// TODO Auto-generated method stub
			return wd.deleteUser(c);
		}
		@Override
		public List<WalletSpring> usersAdmin(String c) {
			Creds cc = new Creds();
			cc= wd.validateUserCreds(c);
			
			if(cc!= null)
			{
			return wd.usersAdmin(c);}
			else 
				{
				
				return null;}
		}
		@Override
		public String editUser(WalletSpring w) {
			
			return wd.editUser(w);
		}
		@Override
		public List<CreateCard> cards(String c) {
			Creds cc = new Creds();
			cc= wd.validateUserCreds(c);
			
			if(cc!= null)
			{
			return wd.cards(c);}
			else return null;
		}
		@Override
		public List<CreateCard> userCards(String c) {
			// TODO Auto-generated method stub
			return wd.userCards(c);
		}
		@Override
		public String deleteCard(String c) {
			// TODO Auto-generated method stub
			return wd.deleteCard(c);
		}
		@Override
		public String reApplyCard(String c) {
			String uid =c;
			return wd.deleteCard(uid);
			
		}
		@Override
		public List<Charges> showCharges(String type) {
			return wd.showCharges(type);
		}
		@Override
		public String saveCharge(Charges charge) {
			if(charge.getTranscharge()<0)
			{
				return "Charge should not be less than 0";
			}
			
			Charges ch=wd.validateCharge(charge.getTranstype());
			if(ch==null)
			{
				return wd.saveCharge(charge);
			}
			else 
			{
				return "Charge already exists";
			}
		}
		@Override
		public Charges validateCharge(String type) {
			
			return wd.validateCharge(type);
		}
		@Override
		public String editCharge(Charges ch) {
			return wd.editCharge(ch);
		}
		@Override
		public String deleteCharge(String id) {
			return wd.deleteCharge(id);
		}
		@Override
		public String exeCharge(String id) {
			return wd.exeCharge(id);
		}
		@Override
		public String addClient(ClientDetails cd) {
			
			return wd.addClient(cd);
		}
		
}
