package de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter;

import static org.junit.Assert.*;

import org.junit.Test;

import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.CrypterException;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.IllegalKeyException;


public class CrypterXORTests {

	@Test
	public void konstruktionMitG�ltigemSchl�sselLiefertInstanz() throws IllegalKeyException
	{
		//arrange
		Crypter xor;
		// act
		xor = new CrypterXOR("TPERULES");
		//assert (eigentlich nicht n�tig)
		assertNotNull(xor);
	}
	
	@Test(expected=IllegalKeyException.class)
	public void konstruktionMitNullSchl�sselSch�gtFehl() throws IllegalKeyException {
		// arrange
		// act
		new CrypterXOR(null);
		// assert...
	}
	
	@Test(expected=IllegalKeyException.class)
	public void konstruktionMitUng�ltigemSchl�sselSch�gtFehl() throws IllegalKeyException {
		// arrange
		// act
		new CrypterXOR("@##!!#!##!?!:-(");
		// assert...
	}
	
	@Test
	public void encryptBeispielMessageLiefertErwartetenCypherText() throws CrypterException {
		
		//arrange
		Crypter xor;
		xor = new CrypterXOR("TPERULES");
		String message = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String expected = "URFVPJB[]ZN^XBJCEBVF@ZRKMJ";
		// act
		String cypherText = xor.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void encryptBeispielMessageMitUng�ltigenZeichenLiefertErwartetenCypherTextMitBereinigung() throws CrypterException {
		
		//arrange
		Crypter xor = new CrypterXOR("TPERULES");
		String message = "ABCDEFGHIJKLMN &&�?*+~~()��OPQRSTUVWXYZ-|||?42";
		String expected = "URFVPJB[]ZN^XBJCEBVF@ZRKMJ";
		// act
		String cypherText = xor.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void decryptBeispielCypherTextLiefertErwartetenText() throws CrypterException {
		
		//arrange
		Crypter xor = new CrypterXOR("TPERULES");
		String cypherText = "URFVPJB[]ZN^XBJCEBVF@ZRKMJ";
		String expected = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		// act
		String text = xor.decrypt(cypherText);
		//assert
		assertEquals(expected, text);
	}
	
	@Test
	public void entschl�sselungMachtVerschl�sselungR�ckg�ngig() throws CrypterException {
		//arrange
		Crypter xor = new CrypterXOR("TPERULES");
		String message = "GEHDUALTERESELHEUFRESSEN";
		// act
		String cypherText = xor.decrypt(message);
		String text = xor.encrypt(cypherText);
		// assert
		assertEquals(message, text);
	}
		
	@Test
	public void verschl�sselungMachtEntschl�sselungR�ckg�ngig() throws CrypterException {
		//arrange
		Crypter xor = new CrypterXOR("TPERULES");
		String cypherText = "MALHEUREUSEMENTJESUISMALADE";
		// act
		String text = xor.decrypt(cypherText);
		String finalText = xor.encrypt(text);
		// assert
		assertEquals(cypherText, finalText);
	}
	
}
