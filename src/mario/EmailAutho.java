package mario;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailAutho {
	
	String clientEmail = "";
	int code;

	public EmailAutho(String email, int code) {
		this.code = code;
		clientEmail = email;
		
		gmailSend();

	}

	public void gmailSend() {
		String user = "sdchikappp@gmail.com"; 
		String password = "lcqirfokggxasnij"; 

		// SMTP 서버 정보를 설정한다.
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 465);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));

			// 수신자메일주소
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("sdchikappp@gmail.com"));

			// Subject
			message.setSubject("마리오 회원가입 인증 코드"); // 메일 제목을 입력

			// Text
			message.setText("가입 인증 코드 : " + 1234); // 메일 내용을 입력

			// send the message
			Transport.send(message); //// 전송
			System.out.println("message sent successfully...");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EmailAutho("sdchikappp@gmail.com", 1234);
	}
}
