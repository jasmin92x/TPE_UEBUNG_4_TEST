package de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter;


import static org.junit.Assert.*;

import org.junit.Test;

import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.CrypterException;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.IllegalKeyException;

public class CrypterCaesarTests {

	@Test
	public void konstruktionMitG�ltigemSchl�sselLiefertInstanz() throws IllegalKeyException
	{
		//arrange
		Crypter caesar;
		// act
		caesar = new CrypterCaesar("C");
		//assert (eigentlich nicht n�tig)
		assertNotNull(caesar);
	}
	
	@Test(expected=IllegalKeyException.class)
	public void konstruktionMitNullSchl�sselSch�gtFehl() throws IllegalKeyException {
		// arrange
		// act
		new CrypterCaesar(null);
		// assert...
	}
	
	@Test(expected=IllegalKeyException.class)
	public void konstruktionMitUng�ltigemSchl�sselSch�gtFehl() throws IllegalKeyException {
		// arrange
		// act
		new CrypterCaesar("@##!!#!##!?!:-(");
		// assert...
	}
	
	@Test
	public void encryptBeispielMessageLiefertErwartetenCypherText() throws CrypterException {
		
		//arrange
		Crypter caesar;
		caesar = new CrypterCaesar("C");
		String message = "CAESAR";
		String expected = "FDHVDU";
		// act
		String cypherText = caesar.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void encryptBeispielMessageMitUng�ltigenZeichenLiefertErwartetenCypherTextMitBereinigung() throws CrypterException {
		
		//arrange
		Crypter caesar = new CrypterCaesar("C");
		String message = "CAE &&�?*+~~()��SAR-|||?42";
		String expected = "FDHVDU";
		// act
		String cypherText = caesar.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void decryptBeispielCypherTextLiefertErwartetenText() throws CrypterException {
		
		//arrange
		Crypter caesar = new CrypterCaesar("C");
		String cypherText = "FDHVDU";
		String expected = "CAESAR";
		// act
		String text = caesar.decrypt(cypherText);
		//assert
		assertEquals(expected, text);
	}
	
	@Test
	public void entschl�sselungMachtVerschl�sselungR�ckg�ngig() throws CrypterException {
		//arrange
		Crypter caesar = new CrypterCaesar("C");
		String message = "GEHDUALTERESELHEUFRESSEN";
		// act
		String cypherText = caesar.decrypt(message);
		String text = caesar.encrypt(cypherText);
		// assert
		assertEquals(message, text);
	}
		
	@Test
	public void verschl�sselungMachtEntschl�sselungR�ckg�ngig() throws CrypterException {
		//arrange
		Crypter caesar = new CrypterCaesar("C");
		String cypherText = "MALHEUREUSEMENTJESUISMALADE";
		// act
		String text = caesar.decrypt(cypherText);
		String finalText = caesar.encrypt(text);
		// assert
		assertEquals(cypherText, finalText);
	}
	
}
