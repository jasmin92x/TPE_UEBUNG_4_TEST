package de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.CrypterException;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.IllegalKeyException;

/**
 * Mit diesen Test wir das API durchgetestet, das den abgeleiteten Klassen 
 * von der Basisklasse zur Verfügung gestellt wird. Es wird also geprüft,
 * ob die vorgesehene Arbeitsteilung zwischen CrypterBase und ihren Abkömmlingen
 * funktioniert.
 * 
 * @author Jasmin Cano, Silvia Yildiz
 *
 */
public class CryperBaseTests {
	
	/**
	 * Mit diesem Test wird überprüft, ob die Schlüsselvalidierung im 
	 * Konstruktor durchgeführt wird
	 */
	@Test
	public void konstruktorLöstSchlüsselvalidierungAus() {
		
		/**
		 * Da unser "Gedächtnis final sein muss, um von einer lokalen
		 * Klasse verwendet werden zu können, wenden wir einen kleinen Trick
		 * an und benutzen statt einer String-Variablen einen String-Array
		 * mit einem Null-Eintrag. Die Variable selbst ist dann eine Objektreferenz,
		 * die sich nicht ändert, wenn sich etwas am Zustand - hier repräsentiert
		 * durch das einzige Element des Arrays - ändert.
		 */
		final String receivedKey[] = { null }; // unser Gedächtnis
		// arrange
		/**
		 * innere lokale von CrypterBase abgeleitete Klasse,
		 * deren validateKey-Methode etwas mit unserem "Gedächtnis" anstellt -
		 * sie legt den Schlüssel, den sie erhalten hat, darin ab. 
		 * @param key
		 * @throws IllegalKeyException
		 */
		class ValidatingCrypter extends CrypterBase {
			
			/**
			 * Zuständig für den "Geburtsvorgang"
			 * @param key wird "gemerkt"
			 * @throws IllegalKeyException - sollte nicht vorkommen
			 */
			public ValidatingCrypter(String key) throws IllegalKeyException {
				super(key);
			}

			/**
			 * Die Methode hinterlässt eine Spur im "Gedächtnis", wenn sie
			 * aufgerufen wurde!
			 * @param key - wird ignoriert
			 * @throws IllegalKeyException - kommt hier nicht vor
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException {
				receivedKey[0] = key;
			}

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				return 0;
			}

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				return 0;
			}
			
		}
		String key = "someKey";
		// act
		try {
			new ValidatingCrypter(key); 
			// Das Objekt selbst interessiert nicht mehr - lediglich sein
			// "Geburtsvorgang" spielt eine Rolle, da er eine Spur in unserem
			// "Gedächtnis" hinterlassen sollte
		} catch (IllegalKeyException e) { }
		// assert
		assertEquals(key, receivedKey[0]);
		
	}
	
	/**
	 * Dieser Test prüft, ob eine fehlgeschlagene Schlüsselvalidierung 
	 * tatsächlich zu einer IllegalKeyException führt
	 * @throws IllegalKeyException 
	 */
	@Test(expected=IllegalKeyException.class)
	public void illegalKeyExceptionWirdPropagiert() throws IllegalKeyException {
		// arrange
		/**
		 * innere lokale von CrypterBase abgeleitete Klasse,
		 * deren validateKey-Methode fehlschägt. 
		 * @param key
		 * @throws IllegalKeyException
		 */
		class failingKeyValidationCrypter extends CrypterBase {
			
			/**
			 * Zuständig für den "Geburtsvorgang"
			 * @param key - wird ignoriert
			 * @throws IllegalKeyException - sollte hier ausgelöst werden
			 */
			public failingKeyValidationCrypter(String key) throws IllegalKeyException {
				super(key);
			}

			/**
			 * Die Methode schlägt immer fehl
			 * @param key - wird ignoriert
			 * @throws IllegalKeyException - wird hier ausgelöst
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException {
				throw new IllegalKeyException("");
			}

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				return 0;
			}

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				return 0;
			}
			
		}
		// act
		new failingKeyValidationCrypter("someKey"); 
		// assert ...
	}
	
	/**
	 * Überprüft, ob nur deZeichenA- Z akzeptiert werden und andernfalls eine
	 * IllegalArgumentException ausgelöst wird
	 * 
	 * @throws IllegalKeyException
	 */
	@Test
	public void checkCharacterRangeAkzeptiertNurABisZ() throws IllegalKeyException {
		
		// arrange
		class RangeChecker extends CrypterBase {

			/**
			 * 
			 * @param key wird ignoriert
			 * @throws IllegalKeyException sollte nicht auftreten
			 */
			public RangeChecker(String key) throws IllegalKeyException {
				super(key);
			}

			/**
			 * NOOP
			 * @param key
			 * @throws IllegalKeyException
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException { }

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				return 0;
			}

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				return 0;
			}
		}
		RangeChecker rc = new RangeChecker(null);
		int testLength = 100;
		char charSequence[] = new char[testLength];
		for (int i = 0; i < testLength; ++i) {
			charSequence[i] = (char)i;
		}
		int counter = 0;
		int expected = testLength - 26;
		// act 
		for (int i = 0; i < testLength; ++i) {
			try {
				rc.checkCharacterRange(charSequence[i]);
			}
			catch (IllegalArgumentException e) {
				counter++;
			}
		}
		// assert
		assertEquals(expected, counter);
	}
	
	@Test
	public void verschlüsselungInEinKonstantesZeichenWirdDurchgeführt() throws IllegalKeyException, CrypterException {
		
		// arrange
		/**
		 * Diese Klasse führt eine entartete Verschlüsselung durch,
		 * bei der jedes Zeichen durch '*' ersetzt wird
		 * @author Jasmin Cano, Silvia Yildiz
		 *
		 */
		class DegeneratedEncryptor extends CrypterBase {

			/**
			 * 
			 * @param key wird ignoriert
			 * @throws IllegalKeyException solte nicht auftreten
			 */
			public DegeneratedEncryptor(String key) throws IllegalKeyException {
				super(key);
			}
			
			/**
			 * NOOP
			 * @param key
			 * @throws IllegalKeyException
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException { }

			/**
			 * Degenerierte Verchlüsselung in das Zeichen '*'
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				return '*';
			}

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				return 0;
			}
			
		}
		String message = "GEH DU ALTER ESEL HEU FRESSEN";
		String expected = "*****************************";
		// act
		String cypherText= (new DegeneratedEncryptor(null)).encrypt(message);
		// assert
		assertEquals(expected, cypherText);
		
	}

	@Test
	public void entschlüsselungInEinKonstantesZeichenWirdDurchgeführt() throws IllegalKeyException, CrypterException {
		
		// arrange
		/**
		 * Diese Klasse führt eine entartete Entschlüsselung durch,
		 * bei der jedes Zeichen durch '*' ersetzt wird
		 * @author Jasmin Cano, Silvia Yildiz
		 *
		 */
		class DegeneratedDecryptor extends CrypterBase {

			/**
			 * 
			 * @param key wird ignoriert
			 * @throws IllegalKeyException solte nicht auftreten
			 */
			public DegeneratedDecryptor(String key) throws IllegalKeyException {
				super(key);
			}
			
			/**
			 * NOOP
			 * @param key
			 * @throws IllegalKeyException
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException { }

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				return 0;
			}

			/**
			 * Degenerierte Entchlüsselung in das Zeichen '*'
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				return '*';
			}
			
		}
		String cypherText = "GEH DU ALTER ESEL HEU FRESSEN";
		String expected = "*****************************";
		// act
		String text = (new DegeneratedDecryptor(null)).decrypt(cypherText);
		// assert
		assertEquals(expected, text);
		
	}

	@Test
	public void verschlüsselungAlsKopierenWirdDurchgeführt() throws IllegalKeyException, CrypterException {
		
		// arrange
		/**
		 * Diese Klasse führt eine entartete Verschlüsselung durch,
		 * bei der jedes Zeichen durch '*' ersetzt wird
		 * @author Jasmin Cano, Silvia Yildiz
		 *
		 */
		class CopyEncryptor extends CrypterBase {

			/**
			 * 
			 * @param key wird ignoriert
			 * @throws IllegalKeyException solte nicht auftreten
			 */
			public CopyEncryptor(String key) throws IllegalKeyException {
				super(key);
			}
			
			/**
			 * NOOP
			 * @param key
			 * @throws IllegalKeyException
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException { }

			/**
			 * gibt den Input unverändert zurück
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				return c;
			}

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				return 0;
			}
			
		}
		String message = "GEH DU ALTER ESEL HEU FRESSEN";
		String expected = message;
		// act
		String cypherText= (new CopyEncryptor(null)).encrypt(message);
		// assert
		assertEquals(expected, cypherText);
		
	}

	@Test
	public void entschlüsselungAslKopierenWirdDurchgeführt() throws IllegalKeyException, CrypterException {
		
		// arrange
		/**
		 * Diese Klasse führt eine entartete Entschlüsselung durch,
		 * bei der jedes Zeichen durch '*' ersetzt wird
		 * 
		 * @author Jasmin Cano, Silvia Yildiz
		 *
		 */
		class CopyDecryptor extends CrypterBase {

			/**
			 * 
			 * @param key wird ignoriert
			 * @throws IllegalKeyException solte nicht auftreten
			 */
			public CopyDecryptor(String key) throws IllegalKeyException {
				super(key);
			}
			
			/**
			 * NOOP
			 * @param key
			 * @throws IllegalKeyException
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException { }

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				return 0;
			}

			/**
			 * gibt den Input unverändert zurück
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				return c;
			}
			
		}
		String cypherText = "GEH DU ALTER ESEL HEU FRESSEN";
		String expected = cypherText;
		// act
		String text = (new CopyDecryptor(null)).decrypt(cypherText);
		// assert
		assertEquals(expected, text);
		
	}

	@Test
	public void ungültigeZeichenWerdenBeiVerschlüsselungIgnoriert() throws CrypterException {
		
		//arrange
		/**
		 * diese abgeleitete Klasse weigert sich, *, H oder 9 zu verschlüsseln,
		 * indem sie diese Zeichen einfach übergeht
		 * @author Jasmin Cano, Silvia Yildiz
		 *
		 */
		class EncrypterWhichSignalsInvalidInputOnCharacterEncryption extends CrypterBase {

			/**
			 * 
			 * @param key ignored
			 * @throws IllegalKeyException should not occur
			 */
			public EncrypterWhichSignalsInvalidInputOnCharacterEncryption(
					String key) throws IllegalKeyException {
				super(key);
			}

			/**
			 * NOOP
			 * @param key wird ignoriert
			 * @throws IllegalKeyException tritt nicht auf
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException { }

			/**
			 * löst genau dann eine Ausnahme aus, wenn eines der Zeichen
			 * '*','H' oder'9' auftritt, gibt ansonsten den Input zurück
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				switch (c) {
				case '*':
				case 'H':
				case '9':
					throw new IllegalArgumentException();
				}
				return c;
			}

			/**
			 * Dummy,da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				return 0;
			}
		}
		
		String message = "_-@#*+ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String expected = "_-@#+ABCDEFGIJKLMNOPQRSTUVWXYZ012345678";
		CrypterBase crypter= new EncrypterWhichSignalsInvalidInputOnCharacterEncryption("WhateverKey");
		// act
		String cypherText = crypter.encrypt(message);
		// assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void ungültigeZeichenWerdenBeiEntschlüsselungIgnoriert() throws CrypterException {
		
		//arrange
		/**
		 * diese abgeleitete Klasse weigert sich, *, H oder 9 zu entschlüsseln,
		 * indem sie diese Zeichen einfach übergeht
		 * @author Jasmin Cano, Silvia Yildiz
		 *
		 */
		class DecrypterWhichSignalsInvalidInputOnCharacterDecryption extends CrypterBase {

			/**
			 * 
			 * @param key ignored
			 * @throws IllegalKeyException should not occur
			 */
			public DecrypterWhichSignalsInvalidInputOnCharacterDecryption(
					String key) throws IllegalKeyException {
				super(key);
			}

			/**
			 * NOOP
			 * @param key wird ignoriert
			 * @throws IllegalKeyException tritt nicht auf
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException { }

			/**
			 * Dummy,da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				return 0;
			}

			/**
			 * löst genau dann eine Ausnahme aus, wenn eines der Zeichen
			 * '*','H' oder'9' auftritt, gibt ansonsten den Input zurück 
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				switch (c) {
				case '*':
				case 'H':
				case '9':
					throw new IllegalArgumentException();
				}
				return c;
			}
		}
		
		String cypherText = "_-@#*+ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String expected = "_-@#+ABCDEFGIJKLMNOPQRSTUVWXYZ012345678";
		CrypterBase crypter= new DecrypterWhichSignalsInvalidInputOnCharacterDecryption("WhateverKey");
		// act
		String text = crypter.decrypt(cypherText);
		// assert
		assertEquals(expected, text);
	}
	
	@Test
	public void messageArrayWirdInGleichartigenCypherTextArrayVerschlüsselt() throws CrypterException {
		
		// arrange
		/**
		 * Vershlüsselt alle Zeichen von A - Z in sich selbst
		 * @author Jasmin Cano, Silvia Yildiz
		 *
		 */
		class SimpleEncrypter extends CrypterBase {

			/**
			 * 
			 * @param key wird ignoriert
			 * @throws IllegalKeyException sollte nicht auftreten
			 */
			public SimpleEncrypter(String key) throws IllegalKeyException {
				super(key);
			}

			/**
			 * NOOP
			 * @param key wird ignoriert
			 * @throws IllegalKeyException kommt nicht vor
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException { }

			/**
			 * Filtert gültige Zeichen heraus
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				checkCharacterRange(c);
				return c;
			}

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				return 0;
			}
			
		}
		
		List<String> messages = Arrays.asList(new String[] { "@A##_XZBB", "KÖLN-BONN", "EXPO 2000" });
		List<String> expected = Arrays.asList(new String[] { "AXZBB", "KLNBONN", "EXPO" });
		CrypterBase simpleCrypter = new SimpleEncrypter(null);
		
		// act
		List<String> cypherTexts = simpleCrypter.encrypt(messages);
		
		// assert
		assertEquals(expected.size(), cypherTexts.size());
		for (int i = 0; i < expected.size(); ++i) {
			assertEquals(expected.get(i), cypherTexts.get(i));
		}
		
	}
	
	@Test
	public void cypherTextArrayWirdInGleichartigenTextArrayEntschlüsselt() throws CrypterException {
		
		// arrange
		/**
		 * Vershlüsselt alle Zeichen von A - Z in sich selbst
		 * @author Jasmin Cano, Silvia Yildiz
		 *
		 */
		class SimpleDecrypter extends CrypterBase {

			/**
			 * 
			 * @param key wird ignoriert
			 * @throws IllegalKeyException sollte nicht auftreten
			 */
			public SimpleDecrypter(String key) throws IllegalKeyException {
				super(key);
			}

			/**
			 * NOOP
			 * @param key wird ignoriert
			 * @throws IllegalKeyException kommt nicht vor
			 */
			@Override
			void validateKey(String key) throws IllegalKeyException { }

			/**
			 * Dummy, da nicht benutzt
			 * @param c
			 * @return
			 */
			@Override
			char encrypt(char c) {
				return 0;
			}
			
			/**
			 * Filtert gültige Zeichen heraus
			 * @param c
			 * @return
			 */
			@Override
			char decrypt(char c) {
				checkCharacterRange(c);
				return c;
			}

		}
		
		List<String> cypherTexts = Arrays.asList(new String[] { "@A##_XZBB", "KÖLN-BONN", "EXPO 2000" });
		List<String> expected = Arrays.asList(new String[] { "AXZBB", "KLNBONN", "EXPO" });
		CrypterBase simpleCrypter = new SimpleDecrypter(null);
		
		// act
		List<String> texts = simpleCrypter.decrypt(cypherTexts);
		
		// assert
		assertEquals(expected.size(), texts.size());
		for (int i = 0; i < expected.size(); ++i) {
			assertEquals(expected.get(i), texts.get(i));
		}
		
	}
	
}
