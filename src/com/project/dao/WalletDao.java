package com.project.dao;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.jpa.criteria.expression.SizeOfCollectionExpression;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.bean.Charges;
import com.project.bean.ClientDetails;
import com.project.bean.CreateCard;
import com.project.bean.Creds;
import com.project.bean.TransactionSpring;
import com.project.bean.WalletSpring;


@Repository
@Transactional
public class WalletDao implements WalletDaoInter {
	@PersistenceContext
	EntityManager em;
	CreateCard cc;
	Creds c;
	String s="";
	WalletSpring w;
	Charges ch;
	double money;
	TransactionSpring ts,ts1;
	public WalletSpring validateUser(String uid) {
		System.out.println(uid);
		w=em.find(WalletSpring.class, uid);
		System.out.println("After the DAO" + w);
		return w;
	}
	@Override
	public Creds validateUserCreds(String uName) {
		System.out.println(uName);
		TypedQuery<Creds> q=em.createQuery("from Creds w where w.userName=:n", Creds.class);
		q.setParameter("n", uName);
		
		try {
			c=q.getSingleResult();
			if(c!=null)
			{
				return c;
			}
		}
		catch(Exception e)
		{
			System.out.println("exception caught");
			c=null;
		}
		return c;
	}
	public Charges validateCharge(String type)
	{
		TypedQuery<Charges> q=em.createQuery("from Charges w where w.transtype=:n", Charges.class);
		q.setParameter("n",type);
		try {
			ch = q.getSingleResult();
			if(ch!=null)
			{
				return ch;
			}
		}
		catch(Exception e)
		{
			ch = null;
		}
		return ch;
	}

	public String createUser(WalletSpring w) {
		System.out.println("In the DAO");
		TypedQuery<String> q=em.createQuery("select w.user_name from WalletSpring w", String.class);
		List<String> usernames=q.getResultList();
		int a=usernames.size();
		long account=w.getAccount_number();
		account=account+a;
		w.setAccount_number(account);
		em.persist(w);
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date=new Date();
		ts=new TransactionSpring(w.getUser_name(), "Wallet Created", 0, 0, "No Trasactions Done", df.format(date));
		em.persist(ts);
		return w.getUser_name();
	}
	public String createCredsUser(Creds c) {
		String u_name = c.getUserName();
		em.persist(c);
		return "User Created With User ID "+u_name;
	}

	public String withdrawAmount(String uid,double amount) {
		w=em.find(WalletSpring.class, uid);
		money=w.getAmount();
		money=money-amount;
		w.setAmount(money);
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date=new Date();
		ts=new TransactionSpring(uid, "Withdrawl", amount, 0, "Balance After Withdrawl is"+money, df.format(date));
		em.persist(ts);
		return "Withdraw Successfull...!!!!";
	}

	public String updateAmount(String uid,double amount) {
		w=em.find(WalletSpring.class, uid);
		money=w.getAmount();
		money=money+amount;
		w.setAmount(money);
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date=new Date();
		ts=new TransactionSpring(uid, "Deposit", amount, 0, "Balance After deposit is"+money, df.format(date));
		em.persist(ts);
		return "Deposited Succesfully";
	}

	public String fundTransfer(String uid, String ruid,double amount) {
		w=em.find(WalletSpring.class, uid);
		money=w.getAmount();
		money=money-amount;
		w.setAmount(money);
		WalletSpring w1=em.find(WalletSpring.class, ruid);
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date=new Date();
		ts=new TransactionSpring(uid, "Fund Transafer to "+w1.getAccount_number(), amount, 0,"Balance is "+w.getAmount() , df.format(date));
		//ts1=em.find(TransactionSpring.class, uid);
		em.persist(ts);
		double m=w1.getAmount();
		m=m+amount;
		w1.setAmount(m);
		ts=new TransactionSpring(ruid, "Fund Transafer from "+w.getAccount_number(), 0, amount,"Balance is "+w1.getAmount() , df.format(date));
		em.persist(ts);
		String str=df.format(date);
		return str;
	}

	public String changePassword(String uid, String newpass) {
		TypedQuery<Creds> q=em.createQuery("from Creds w where w.userName=:n", Creds.class);
		q.setParameter("n", uid);
		try {
			c=q.getSingleResult();
			if(c!=null)
			{
				c.setPass(newpass);
			}
		}
		catch(Exception e)
		{
			System.out.println("No record found in database");
		}
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date=new Date();
		ts=new TransactionSpring(uid, "Password Changed", 0, 0, "No Trasactions Done", df.format(date));
		em.persist(ts);
		return "Password Changed Successfully...!!!";
	}

	public String validateAccountNumber(long Account) {
		TypedQuery<String> q=em.createQuery("select w.user_name from WalletSpring w where w.Account_number=:n", String.class);
		q.setParameter("n", Account);
		String Acc=q.getSingleResult();
		return Acc;
	}
	public String showBalance(String uid)
	{
		w=em.find(WalletSpring.class, uid);
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date=new Date();
		ts=new TransactionSpring(uid, "Show Balance", 0, 0, "No Trasactions Done", df.format(date));
		em.persist(ts);
		return ""+w.getAmount();
	}
	public List<TransactionSpring> transactions(String uid)
	{
		TypedQuery<TransactionSpring> q=em.createQuery("from TransactionSpring b where b.user_id=:n order by b.trans_id", TransactionSpring.class);
		q.setParameter("n", uid);
		List<TransactionSpring> transaction=q.getResultList();
		return transaction;
	}
	@Override
	public List<Creds> usersCreds() {
		TypedQuery<Creds> q=em.createQuery("from Creds c", Creds.class);
		List<Creds> li = q.getResultList();
		return li;
	}
	@Override
	public String cardCreation(CreateCard c)
	{
		String te="";
		boolean a=true;
		while(a==true)
		{
		int temp=c.getCardnumber();
		cc=em.find(CreateCard.class, temp);
		if(cc!=null)
		{
			Random rand = new Random();
			int cardnum = 100000+rand.nextInt(100000);
			c.setCardnumber(cardnum);
		}
		else {
			em.persist(c);
			a=false;
			te="Card Added Successfully";
		}
		}
		return te;
	}
	@Override
	public CreateCard validateCard(String uid) {
		TypedQuery<CreateCard> q=em.createQuery("from CreateCard us where us.cust_id=:n", CreateCard.class);
		q.setParameter("n", uid);
		CreateCard b;
		try {
			b=q.getSingleResult();
			if(b!=null)
			{
				return b;
			}
		}
		catch(Exception e)
		{
			b=null;
		}
		return b;
	}
	@Override
	public String deatailsMail(String uid) {
		String from="pushpakkireeti17@gmail.com";
		String host="localhost";
		final String SSL_FACTORY="javax.net.ssl.SSLSocketFactory";
		Properties properties=System.getProperties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port","465");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.debug", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		final String username="pushpakkireeti17@gmail.com";
		final String password="dtxjewwdoabqtqxp";
		try {
			Session session=Session.getDefaultInstance(properties,new Authenticator() {
				protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
				return new javax.mail.PasswordAuthentication(username, password);	
				}
			});
		
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			
			
			/*TypedQuery<String> q1=em.createQuery("select w.email from WalletSpring w where w.cust_id=:n", String.class);
			q1.setParameter("n", uid);*/
			WalletSpring b=em.find(WalletSpring.class, uid);
			String to=b.getEmail();
					
			TypedQuery<Integer> q1=em.createQuery("select c.cardnumber from CreateCard c where c.cust_id=:n", Integer.class);
			q1.setParameter("n", uid);		
			int Cuid=q1.getSingleResult();
			CreateCard c=em.find(CreateCard.class, Cuid);		
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to, false));;
			message.setSubject("Details Of Card");
			/*String oneTimePassword=String.valueOf((long)((Math.random()*((199999-100000)+1))+100000));
			message.setText("OTP:"+"  "+oneTimePassword);*/
			message.setText("Card Number :"+c.getCardnumber()+"Card Name :"+c.getCardname()+"Card CVV :"+c.getCvv()+"Card Exp Month :"+c.getExpmonth()+"Card Exp Year :"+c.getExpyear());
			Transport.send(message);
			System.out.println("message Sent successfully....");
		    return "Email";
		}
		catch(MessagingException mex)
		{
			mex.printStackTrace();
			
			return "Email";
		}
	}
	@Override
	public String editCredUser(Creds c) {
		Query q=em.createQuery("update Creds us set us.custName=:n1, us.phone=:n2, us.pass=:n3 where us.userName=:n4");
		q.setParameter("n1", c.getCustName());
		q.setParameter("n2", c.getPhone());
		q.setParameter("n3", c.getPass());
		q.setParameter("n4", c.getUserName());
		q.executeUpdate();
		return "User Details has been updated";
	}
	@Override
	public String deleteCredUser(String c) {
		System.out.println("Inside wallet Dao cred d : "+c);
		Query q=em.createQuery("delete from Creds s where s.userName=:n1");
		q.setParameter("n1", c);
		q.executeUpdate();
		return "Admin User has been deleted";
	}
	@Override
	public String deleteUser(String c) {
		String id=c;
		/*WalletDao d=new WalletDao();
		String res=d.deleteCredUser(id);*/
		Query q1=em.createQuery("delete from Creds s where s.userName=:n1");
		q1.setParameter("n1", c);
		q1.executeUpdate();
		Query q=em.createQuery("delete from WalletSpring us where us.user_name=:n1");
		q.setParameter("n1", id);
		q.executeUpdate();
		return "User has been deleted";
	}
	@Override
	public List<WalletSpring> usersAdmin(String c) {
		TypedQuery<WalletSpring> q=em.createQuery("from WalletSpring c", WalletSpring.class);
		List<WalletSpring> li = q.getResultList();
		System.out.println("Inside DAO useradmin : "+ li);
		if(li==null)
		{
			TypedQuery<WalletSpring> record=em.createQuery("from WalletSpring c", WalletSpring.class);
			List<WalletSpring> li1= new ArrayList<WalletSpring>();
			WalletSpring e= q.getSingleResult();
			li1.add(e);
			System.out.println("Inside if : "+ li1);
			return li1;
		}
		else return li;
		
	}
	@Override
	public String editUser(WalletSpring w) {
		Query q=em.createQuery("update WalletSpring us set us.name=:n1, us.mobile_number=:n2,us.amount=:n3,us.email=:n4, us.user_name=:n5, us.Account_number=:n6 where us.user_name=:n5");
		q.setParameter("n1", w.getName());
		q.setParameter("n2", w.getMobile_number());
		q.setParameter("n3", w.getAmount());
		q.setParameter("n4", w.getEmail());
		q.setParameter("n5", w.getUser_name());
		q.setParameter("n6", w.getAccount_number());
		q.executeUpdate();
		System.out.println("Updated user");
		return "User Details has been updated";
	}
	@Override
	public List<CreateCard> cards(String c) {
		TypedQuery<CreateCard> q=em.createQuery("from CreateCard c", CreateCard.class);
		List<CreateCard> li = q.getResultList();
		return li;
	}
	@Override
	public List<CreateCard> userCards(String c) {
		TypedQuery<CreateCard> q=em.createQuery("from CreateCard us where us.cust_id=:n", CreateCard.class);
		q.setParameter("n", c);
		List<CreateCard> li = q.getResultList();
		return li;
	}
	@Override
	public String deleteCard(String c) {
		System.out.println("Dao :"+c);
		Query q1=em.createQuery("delete from CreateCard s where s.cust_id=:n1");
		q1.setParameter("n1", c);
		q1.executeUpdate();
		return "Deleted";
	}
	@Override
	public List<Charges> showCharges(String type) {
		List<Charges> charge = null;
		TypedQuery<Charges> q=em.createQuery("from Charges w", Charges.class);
		try {
			charge=q.getResultList();
			if(charge!=null)
			{
				return charge;
			}
		}
		catch(Exception e)
		{
			System.out.println("exception caught");
			charge=null;
		}
		return charge ;
	}
	@Override
	public String saveCharge(Charges cha) {
		System.out.println("In Dao");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println(cha.getLastdate());
		String date=sdf.format(new Date());
		System.out.println(date);
		cha.setLastdate(date);
		em.persist(cha);
		return "Charge Created";
	}
	@Override
	public String editCharge(Charges ch) {
		Query q=em.createQuery("update Charges us set us.freq=:n1, us.transcharge=:n2");
		q.setParameter("n1", ch.getFreq());
		q.setParameter("n2", ch.getTranscharge());
		q.executeUpdate();
		System.out.println("Updated Charge");
		return "Charges has been updated";
	}
	@Override
	public String deleteCharge(String id) {
		System.out.println("In Dao" +id);
		Query q1=em.createQuery("delete from Charges s where s.transtype=:n1");
		q1.setParameter("n1", id);
		q1.executeUpdate();
		return "Charges Deleted";
	}
	@Override
	public String exeCharge(String id) {
		TypedQuery<Double> q1=em.createQuery("select c.transcharge from Charges c where c.transtype=:n", Double.class);
		q1.setParameter("n", id);
		double charge=q1.getSingleResult();
		Query q2=em.createQuery("select c.user_name from WalletSpring c");
		List<String> a=q2.getResultList();
		try {
			Query q4=em.createQuery("select c.lastdate from Charges c where c.transtype=:n1");
			q4.setParameter("n1", id);
			String ld=(String) q4.getSingleResult();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date sd = new Date();
			String strD=sdf.format(sd);
			Date firstDate = sdf.parse(ld);
			Date secondDate=sdf.parse(strD);
			long diffInMillies = Math.abs(firstDate.getTime() - secondDate.getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    System.out.println(diff);
		    System.out.println(firstDate);
		    System.out.println(secondDate);
		    int wait= (int) (30-diff);
		for(int i=0; i<a.size(); i++)
		{
			
			    if(diff<30)
			    {
			    	return "Last Charge ran on "+ld+" Please wait for "+ wait+ "days";
			    }
			    else {
			    	Query q3=em.createQuery("select c.amount from WalletSpring c where c.user_name=:n1");
					q3.setParameter("n1", a.get(i));
					double amount=(double) q3.getSingleResult();
					double finalAmount = amount - charge;
					Query q=em.createQuery("update WalletSpring us set us.amount=:n1 where us.user_name=:n2");
					q.setParameter("n1", finalAmount);
					q.setParameter("n2", a.get(i));
					q.executeUpdate();
					Query q5=em.createQuery("update Charges us set us.lastdate=:n1 where us.transtype=:n2");
					q5.setParameter("n1", strD);
					q5.setParameter("n2", id);
					q5.executeUpdate();
			    }
			}
		}catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "updated charge";
	}
	@Override
	public String addClient(ClientDetails cd) {
		System.out.println(cd.getIp());
		System.out.println(cd.getName());
		System.out.println(cd.getAccess());
		em.persist(cd);
		return "Client Added";
	}

}
