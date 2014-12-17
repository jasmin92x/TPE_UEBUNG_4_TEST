package de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter;

import static org.junit.Assert.*;
import org.junit.Test;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.Crypter;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.CrypterFactory;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.CrypterFactory.CrypterType;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.CrypterNull;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.CrypterReverse;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.CrypterSubstitution;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.CrypterXOR;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.IllegalKeyException;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.CrypterCaesar;


/**
 * Diese Klasse testet die einzige Methode der CrypterFactory-Klasse.
 * Die Tests überprüfen im Wesentlichen, ob diese Methode auch tatsächlich
 * Instanzen derjenigen konkreten Crypter-Klassen liefert, die den
 * übergebenen Parameterwerten entsprechen.
 * 
 * Bei denjenigen Klassen, die eine echte Validierung der Übergebenen Schlüssel
 * implementieren, wird auch geprüft, ob ggfs. eine ausgelöste IllegalKeyException
 * "weitergereicht" wird. (Die Key-Validierung selbst wird bei den 
 * betreffenden Klassen getestet. Wir setzen hier also bereits voraus, dass
 * diese Crypter-Klassen korrekt implementiert sind.)
 * 
 * Da für alle CrypterTpe-Werte die zugehörigen Crypter-Klassen implementiert sind,
 * kann der Fall nicht implementierter Klassen nicht getestet werden: Er läst sich
 * einfach nicht mittels der möglichen Parameter ausdrücken.
 * 
 * @author Jasmin Cano, Silvia Yildiz
 *
 */
public class CrypterFactoryTests {

	@Test
	public void instanziierungVonCaesarMitKorrektemSchlüsselLiefertCaesarInstanz() throws IllegalKeyException {

		// arrange
		Class<?> expected = CrypterCaesar.class;
		// act
		Crypter caesar = CrypterFactory.createCrypter("D", CrypterType.CAESAR);
		// assert
		assertEquals(expected, caesar.getClass());
		
	}

	@Test(expected=IllegalKeyException.class)
	public void instanziierungVonCaesarMitUnkorrektemSchlüsselSchlägtFehl() throws IllegalKeyException {

		// arrange
		// act
		CrypterFactory.createCrypter(null, CrypterType.CAESAR);
		// assert...
		
	}

	@Test
	public void instanziierungVonXORMitKorrektemSchlüsselLiefertCaesarInstanz() throws IllegalKeyException {

		// arrange
		Class<?> expected = CrypterXOR.class;
		// act
		Crypter xor = CrypterFactory.createCrypter("WALTERVONDERVOGELWEIDE", CrypterType.XOR);
		// assert
		assertEquals(expected, xor.getClass());
		
	}

	@Test(expected=IllegalKeyException.class)
	public void instanziierungVonXORMitUnkorrektemSchlüsselSchlägtFehl() throws IllegalKeyException {

		// arrange
		// act
		CrypterFactory.createCrypter(null, CrypterType.XOR);
		// assert...
		
	}

	@Test
	public void instanziierungVonSubstitutionMitKorrektemSchlüsselLiefertCaesarInstanz() throws IllegalKeyException {

		// arrange
		Class<?> expected = CrypterSubstitution.class;
		// act
		Crypter xor = CrypterFactory.createCrypter("ABCDEFGHIJKLMNOPQRSTUVWXYZ", CrypterType.SUBSTITUTION);
		// assert
		assertEquals(expected, xor.getClass());
		
	}

	@Test(expected=IllegalKeyException.class)
	public void instanziierungVonSubstitutionMitUnkorrektemSchlüsselSchlägtFehl() throws IllegalKeyException {

		// arrange
		// act
		CrypterFactory.createCrypter(null, CrypterType.SUBSTITUTION);
		// assert...
		
	}

	@Test
	public void instanziierungVonNullLiefertNullInstanz() throws IllegalKeyException {

		// arrange
		Class<?> expected = CrypterNull.class;
		// act
		Crypter cnull = CrypterFactory.createCrypter(null, CrypterType.NULL);
		// assert
		assertEquals(expected, cnull.getClass());
		
	}

	@Test
	public void instanziierungVonReverseLiefertCaesarInstanz() throws IllegalKeyException {

		// arrange
		Class<?> expected = CrypterReverse.class;
		// act
		Crypter reverse = CrypterFactory.createCrypter(null, CrypterType.REVERSE);
		// assert
		assertEquals(expected, reverse.getClass());
		
	}

}
