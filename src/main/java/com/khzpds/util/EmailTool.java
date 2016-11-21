package com.khzpds.util;

//import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.khzpds.base.SystemConfig;

//import com.chinahrt.common.util.StringUtil;

public class EmailTool {
	private static final Log log = LogFactory.getLog(EmailTool.class);
	public static void main(String[] args) {
		try {
			// SimpleDateFormat sdf = new
			// SimpleDateFormat(StringUtil.DATE_FORMAT_DATETIME);
			// Date sendTime = sdf.parse("2012-04-17 13:40:00");
			boolean res= EmailTool.SendEmail("372587513@qq.com", "这是风邮件", "验证码是");
			 System.out.println(res);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * 发送邮件
	 * 
	 * @param toEmailStr
	 * @param title
	 * @param content
	 * @param fromEmail
	 * @param smtpHost
	 * @param user
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public static boolean SendEmail(String toEmailStr, String title,
			String content) throws Exception {

		// ToEmailStr收件人地址
		// Title邮件标题
		// Content邮件内容
		// FromEmail发件人地址
		// log.info(FromEmail);
		String fromEmail = SystemConfig.getMailName();
		String smtpHost = SystemConfig.getMailHost();
		String user = SystemConfig.getMailName();
		String passwd = SystemConfig.getMailPassword();
		boolean fnt = false;
		try {
			Properties props = new Properties();
			Session sendMailSession;
			Transport transport;
			sendMailSession = Session.getInstance(props, null);
			props.put("mail.smtp.host", smtpHost); // "202.106.187.180"
													// 是“smtp.sohu.com”的IP！
			props.put("mail.smtp.auth", "true"); // 允许smtp校验
			transport = sendMailSession.getTransport("smtp");
			transport.connect(smtpHost, user, passwd); // 你在sohu的用户名,密码...........
			Message newMessage = new MimeMessage(sendMailSession);
			// 设置mail主题
			String mail_subject = title;
			//
			// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();

			// newMessage.setSubject("=?GB2312?B?"
			// + enc.encode(mail_subject.getBytes()) + "?=");

			newMessage.setSubject(mail_subject);

			Address address[] = { new InternetAddress(fromEmail) };// 改变发件人地址
			newMessage.addFrom(address);
			// 设置收件人地址
			/*
			 * String[] toEmails=toEmailStr.split(","); for (int
			 * i=0;i<toEmails.length;i++){
			 * newMessage.addRecipient(Message.RecipientType.TO, new
			 * InternetAddress(toEmails[i])); }
			 */
			// 替换为：
			InternetAddress[] iAddress = new InternetAddress()
					.parse(toEmailStr);
			newMessage.setRecipients(Message.RecipientType.TO, iAddress);
			// 设置mail正文
			newMessage.setSentDate(new java.util.Date());
			// newMessage.setSentDate(sendTime);
			// log.info("当前时间为：" + new Date());
			// log.info("发送时间为：" + sendTime);
			String mail_text = content;
			newMessage.setContent(mail_text, "text/plain;charset=gb2312 ");
			newMessage.saveChanges(); // 保存发送信息
			transport.sendMessage(newMessage, newMessage.getAllRecipients()); // 发送邮件
			fnt = true;
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
			fnt = false;
		}
		return fnt;
	}

	/**
	 * 群发邮件
	 * 
	 * @param emailMap
	 * @param title
	 * @param content
	 * @param fromEmail
	 * @param smtpHost
	 * @param user
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public static boolean SendEmail(HashMap<String, String> emailMap,
			String title, String content) throws Exception {
		// ToEmailStr收件人地址
		// Title邮件标题
		// Content邮件内容
		// FromEmail发件人地址
		// log.info(FromEmail);
		String fromEmail = SystemConfig.getMailName();
		String smtpHost = SystemConfig.getMailHost();
		String user = SystemConfig.getMailName();
		String passwd = SystemConfig.getMailPassword();
		boolean fnt = false;
		try {
			Properties props = new Properties();
			Session sendMailSession;
			Transport transport;
			sendMailSession = Session.getInstance(props, null);
			props.put("mail.smtp.host", smtpHost); // "202.106.187.180"
													// 是“smtp.sohu.com”的IP！
			props.put("mail.smtp.auth", "true"); // 允许smtp校验
			transport = sendMailSession.getTransport("smtp");
			transport.connect(smtpHost, user, passwd); // 你在sohu的用户名,密码...........
			Message newMessage = new MimeMessage(sendMailSession);
			// 设置mail主题
			String mail_subject = title;

			// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();

			// newMessage.setSubject("=?GB2312?B?"
			// + enc.encode(mail_subject.getBytes()) + "?=");

			newMessage.setSubject(mail_subject);
			newMessage.setSubject(mail_subject); // 在winxp上只要在action中转成utf-8

			// 设置发信人地址

			// String strFrom="service@jrsoft.com.cn"; // <--------------

			// strFrom=new String(strFrom.getBytes(),"8859_1");

			// newMessage.setFrom(new InternetAddress(strFrom));

			Address address[] = { new InternetAddress(fromEmail) };// 改变发件人地址
			newMessage.addFrom(address);
			// 设置收件人地址
			// emailMap 收件人的邮箱地址的集合
			Set<String> set = emailMap.keySet();
			for (String string : set) {
				newMessage.addRecipient(Message.RecipientType.TO,
						new InternetAddress(emailMap.get(string)));
			}
			// newMessage.setRecipient(Message.RecipientType.TO,new
			// InternetAddress(ToEmailStr));

			// 设置mail正文
			newMessage.setSentDate(new java.util.Date());
			String mail_text = content;
			newMessage.setContent(mail_text, "text/plain;charset=gb2312 ");
			// newMessage.setText("=?GB2312?B?"+enc.encode(mail_text.getBytes())+"?=");
			// newMessage.setText(mail_text);
			newMessage.saveChanges(); // 保存发送信息
			transport.sendMessage(newMessage, newMessage.getAllRecipients()); // 发送邮件
			fnt = true;
			transport.close();
		} catch (Exception e) {
			fnt = false;
		}
		return fnt;
	}

}
