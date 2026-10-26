package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}

@RestController 
class ServerController {
	private MessageDigest md;
	private ServerController() throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("SHA-256");
	}

	@GetMapping("/hash")
	public String myHash(){
		String data = "Hello, Jacob Truesdale!";
		byte[] hash = md.digest(data.getBytes(StandardCharsets.UTF_8));
		String checksum = bytesToHex(hash);
		return "<p>data: " + data + "</p>\n" + "<p>Cipher Algorithm (SHA-256) = " + checksum + "</p>";
	}
	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for(int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}