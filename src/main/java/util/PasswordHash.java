package util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

public class PasswordHash {
	
	Argon2 argon;
	
	public PasswordHash() {
		argon = Argon2Factory.create(Argon2Types.ARGON2id);
	}
	
	public String hashPassword(String password) {
		String hash = argon.hash(4, 1024 * 1024, 8, password.toCharArray());
		return hash;
	}
	
	public boolean verifyPassword(String hash, String password) {
		boolean verify = false;
		if (password != null) {
			verify = argon.verify(hash, password.toCharArray());
		}
		return verify;
	}
}
