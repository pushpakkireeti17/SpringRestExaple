package com.project.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bean.ChangePass;
import com.project.bean.Charges;
import com.project.bean.ClientDetails;
import com.project.bean.CreateCard;
import com.project.bean.Creds;
import com.project.bean.TransactionSpring;
import com.project.bean.ValidationCardDetails;
import com.project.bean.WalletSpring;
import com.project.service.WalletServiceInter;

@RestController
public class WalletController {
	String str=null;
	@Autowired
	WalletServiceInter ws;
	WalletSpring w,w2;
	CreateCard c;
	Creds cc;
	
	@GetMapping(path="employeemain", produces={"application/xml","application/json"})
	public String homePage()
	{
		return "Apply Card Should Be Displayed";
	}
		
	@PostMapping(path="employee/login" ,consumes= {"application/xml","application/json"})
	public WalletSpring Login(@RequestBody Creds c, HttpServletRequest req,HttpSession ses)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		String Uid=c.getUserName();
		String pass=c.getPass();
		System.out.println(Uid);
		System.out.println(pass);
		w= ws.validateUser(Uid);
		if(w!=null)
		{
			if(c.getPass().equals(pass))
			{
				System.out.println(w);
				return w;
			}
			else
				return null;
		}
		else
		{
			return null;
		}
	}
	/*@PostMapping(path="employee/login/id={id}&pass={pass}", consumes="application/json")
	public WalletSpring Login(@PathVariable("id") String Uid,@PathVariable("pass") String pass,HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		String Uid=w.getUser_name();
		String pass=w.getPassword();
		System.out.println(Uid);
		System.out.println(pass);
		w= ws.validateUser(Uid);
		if(w!=null)
		{
			if(w.getPassword().equals(pass))
			{
				System.out.println(w);
				return w;
			}
			else
				return null;
		}
		else
		{
			return null;
		}
	}*/
	@PostMapping(path="employee/show", produces="application/json")
	public String showBalance(@RequestBody WalletSpring w, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		
		return ws.showBalance(w.getUser_name());	
	}
	/*@PostMapping(path="employee/show/{id}", produces="application/json")
	public String showBalance(@PathVariable("id") String uid, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		
		return ws.showBalance(uid);	
	}*/
	@PostMapping(path="employee/deposit", consumes= {"*/*"})
	public String deposit(@RequestBody WalletSpring w, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		System.out.println("Deposit Section is Working");
		return ws.updateAmount(w.getUser_name(), w.getAmount());
	}
	@PostMapping(path="employee/withdraw", produces="application/json")
	public String withdraw(@RequestBody WalletSpring w1, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		System.out.println("Withdraw Section is Working");
		System.out.println(w1.getAmount());
		w=ws.validateUser(w1.getUser_name());
		if(w!=null)
		{
			if(w.getAmount()>w1.getAmount())
			{
				System.out.println(w.getAmount());
				return ws.withdrawAmount(w1.getUser_name(), w1.getAmount());
			}
			else
			{
				return "Insufficient Funds In your Account";
			}
		}
		else
			return "";
			}
	@PostMapping(path="employee/ft")
	public String fundTransfer(@RequestBody WalletSpring w1, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		System.out.println("Fund Transfer Section is Working");
		System.out.println(w1.getUser_name());
		System.out.println(w1.getAccount_number());
		System.out.println(w1.getAmount());
		w=ws.validateUser(w1.getUser_name());
		if(w!=null)
		{
			long acc=w.getAccount_number();
			long acc1=w1.getAccount_number();
			System.out.println(acc);
			if(acc!=acc1)
			{
				if(w.getAmount()>w1.getAmount())
				{
					String ruid=ws.validateAccountNumber(acc1);
					System.out.println(acc1);
					System.out.println(w1.getUser_name());
					System.out.println(w1.getAmount());
					System.out.println(ruid);
					if(ruid!=null)
					{
						return str=ws.fundTransfer(w1.getUser_name(), ruid, w1.getAmount());
					}
					else
					{
						return "Account Number is Either Incorrect or Not Available";
					}
			
				}
				else
				{
					return "InSufficient Funds In Your Account";
				}
			}
			else
			{
				return "you cannot send Money To your Account";
			}
		}
		else
		{
			return "User Not Exist";
		}
	}
	
	@PostMapping(path="employee/changepass", consumes= {"*/*"})
	public String changePass(@RequestBody ChangePass w1, HttpServletRequest req,HttpSession ses)
	{
		String old=w1.getOldpass();
		String New=w1.getNewpass();
		String Uid=w1.getUsername();
		System.out.println("UiD is "+Uid);
		System.out.println(""+old);
		System.out.println(""+New);
		System.out.println("*******************"+req.getRemoteAddr());
		cc=ws.validateUserCreds(Uid);
		if(cc!=null)
		{
			if(cc.getPass().equals(old))
			{
				return ws.changePassword(Uid,New);
			}
			else
			{
				return "Old Password is Incorrect";
			}
		}
		else
			return "User Not Present";
	}
	
	@PostMapping(path="employee/trans", produces="application/json")
	public List<TransactionSpring> transactions(@RequestBody WalletSpring w, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		System.out.println("Transactions Section is Working");
		List<TransactionSpring> li=ws.transactions(w.getUser_name());
		return li;
		
	}
	@PostMapping(path="employee/password" ,consumes= {"application/xml","application/json"})
	public String createCreds(@RequestBody Creds cred)
	{
		 cc=ws.validateUserCreds(cred.getUserName());
		if(cc==null)
		{
			return ws.createCredsUser(cred);
		}
		else
		{
			return "User Already exists please contact admin";
		}
	}
	@PostMapping(path="employee/create", consumes= {"*/*"})
	public String saveEmployee(@RequestBody WalletSpring wallet, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		System.out.println("============="+wallet.getName()+"===============");
		w= ws.validateUser(wallet.getUser_name());
		if(w==null)
		{
		return ws.createUser(wallet);
		}
		else
		{
			return "User Name Exists Please Try Another User NAme";
		}
	}
	@PostMapping(path="employee/apply", consumes={"*/*"})
	public String applyCard(@RequestBody CreateCard c,HttpSession ses)
	{

		String userid=c.getCust_id();
		String name=c.getCardname();
		System.out.println(userid);
		System.out.println(name);
		c=ws.validateCard(userid);
		if(c==null)
		{
		str=ws.createCard(userid, name);
		ws.deatailsMail(userid);
		return str+"Check Mail For Details";
		}
		else
		{
			return "You have Already Applied For Card. Please Check Email";
		}
	
	}
	@PostMapping(path="admin/login" ,consumes= {"application/xml","application/json"})
	public Creds adminLogin(@RequestBody Creds c, HttpServletRequest req,HttpSession ses)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		String Uid=c.getUserName();
		String pass=c.getPass();
		System.out.println(Uid);
		System.out.println(pass);
		cc= ws.validateAdminUser(Uid);
		if(cc!=null)
		{
			if(cc.getPass().equals(pass))
			{
				System.out.println("Got the object");
				return cc;
			}
			else
				return null;
		}
		else
		{
			return null;
		}
	}
	
	@PostMapping(path="admin/usersCreds", produces="application/json")
	public List<Creds> userCreds(@RequestBody Creds c, HttpServletRequest req)
	{
		System.out.println(c.getUserName());
				List<Creds> creds=ws.usersCreds(c.getUserName());
		System.out.println("*******************"+req.getRemoteAddr());
		return creds;
		
	}
	@PostMapping(path="admin/addAdmin", consumes= {"*/*"})
	public String saveEmployee(@RequestBody Creds c, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		Creds cc= ws.validateAdminUser(c.getUserName());
		if(cc==null)
		{
		return ws.createAdmin(c);
		}
		else
		{
			return "User Name Exists Please Try Another User NAme";
		}
	}
	@PostMapping(path="admin/retrieveUser" ,consumes= {"*/*"})
	public Creds retrieveCredUser(@RequestBody Creds c, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		System.out.println(c.getUserName());
		cc= ws.validateUserCreds((c.getUserName()));
		System.out.println(cc.getUserName());
		if(cc!=null)
		{
			return cc;
		}
		else
		{
			return null;
		}
	}
	@PostMapping(path="admin/retrieveUserEdit" ,consumes= {"*/*"})
	public WalletSpring retrieveUserEdit(@RequestBody WalletSpring c, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		w= ws.validateUser((c.getUser_name()));
		if(w!=null)
		{
			return w;
		}
		else
		{
			return null;
		}
	}
	@PostMapping(path="admin/editCredUsers" ,consumes= {"*/*"})
	public String editCredUser(@RequestBody Creds c)
	{
			return ws.editCredUser(c);
	}
	@PostMapping(path="admin/editUsers" ,consumes= {"*/*"})
	public String editUser(@RequestBody WalletSpring c)
	{
			return ws.editUser(c);
	}
	@PostMapping(path="admin/deleteCredUsers" ,consumes= {"*/*"})
	public String deleteCredUser(@RequestBody String uid)
	{
			return ws.deleteCredUser(uid);
	}
	@PostMapping(path="admin/deleteUsers" ,consumes= {"*/*"})
	public String deleteUser(@RequestBody String uid)
	{
			return ws.deleteUser(uid);
	}
	@PostMapping(path="admin/users", produces="application/json")
	public List<WalletSpring> users(@RequestBody WalletSpring c, HttpServletRequest req)
	{
		System.out.println(c.getUser_name());
				List<WalletSpring> users=ws.usersAdmin(c.getUser_name());
				System.out.println(users);
		System.out.println("*******************"+req.getRemoteAddr());
		return users;
		
	}
	@PostMapping(path="admin/carddetails", produces="application/json")
	public List<CreateCard> cardDetails(@RequestBody WalletSpring c, HttpServletRequest req)
	{
		System.out.println(c.getUser_name());
				List<CreateCard> users=ws.cards((c.getUser_name()));
				System.out.println(users);
		System.out.println("*******************"+req.getRemoteAddr());
		return users;
		
	}
	@PostMapping(path="admin/usercard", produces="application/json")
	public List<CreateCard> usreCardDetails(@RequestBody WalletSpring c, HttpServletRequest req)
	{
		System.out.println(c.getUser_name());
				List<CreateCard> users=ws.userCards(c.getUser_name());
				System.out.println(users);
		System.out.println("*******************"+req.getRemoteAddr());
		return users;
		
	}
	
	@PostMapping(path="admin/editcard" ,consumes= {"*/*"})
	public String userCardEdit(@RequestBody CreateCard createCard, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		CreateCard card= createCard;
		String s=ws.reApplyCard(card.getCust_id());
		return ws.createCard(card.getCust_id(), card.getCardname());
	}
	//@PostMapping(path="admin/editcardsubmit" ,consumes= {"*/*"})
	/*public CreateCard cardEditSubmit(@RequestBody CreateCard createCard, HttpServletRequest req)
	{
		System.out.println("*******************"+req.getRemoteAddr());
		CreateCard card= ws.validateCard(createCard.getCust_id());
		if(card!=null)
		{
			return card;
		}
		else
		{
			return null;
		}
	}*/
	@PostMapping(path="admin/deletecard" ,consumes= {"*/*"})
	public String deleteCard(@RequestBody CreateCard card)
	{
		String uname=card.getCust_id();
			return ws.deleteCard(uname);
	}
	@PostMapping(path="admin/charges" ,produces="application/json")
	public List<Charges> showCharges(@RequestBody Creds c)
	{
			String uname = c.getUserName();
			List<Charges> charg=ws.showCharges(uname);
			System.out.println(charg);
			return charg;
	}
	@PostMapping(path="admin/editcharge" ,produces="application/json")
	public Charges editCharges(@RequestBody Charges c)
	{
			return ws.validateCharge(c.getTranstype());
	}
	@PostMapping(path="admin/editchargesubmit" ,consumes={"*/*"})
	public String submitCharges(@RequestBody Charges c)
	{
			return ws.editCharge(c);
	}
	@PostMapping(path="admin/addcharge" ,consumes= {"*/*"})
	public String saveCharges(@RequestBody Charges charge)
	{
			return ws.saveCharge(charge);
	}
	@PostMapping(path="admin/deletecharge" ,consumes= {"*/*"})
	public String deleteCharge(@RequestBody Charges charge)
	{
		String uname=charge.getTranstype();
		System.out.println("Wallet Controller"+uname);
			return ws.deleteCharge(uname);
	}
	@PostMapping(path="admin/execharges" ,produces="application/json")
	public String exeCharge(@RequestBody Creds cr)
	{
		String uname=cr.getUserName();
		System.out.println("Wallet Controller"+uname);
			return ws.exeCharge(uname);
	}
	@PostMapping(path="admin/addclient" ,consumes= {"*/*"})
	public String addClient(@RequestBody ClientDetails cr)
	{
		System.out.println(cr.getIp());
			return ws.addClient(cr);
	}
}
