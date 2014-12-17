package de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter;


import static org.junit.Assert.*;

import org.junit.Test;

import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.CrypterException;

public class CrypterReverseTests {

	@Test
	public void encryptBeispielMessageLiefertErwartetenCypherText() throws CrypterException {
		
		//arrange
		Crypter caesar;
		caesar = new CrypterReverse(null);
		String message = "TPERULEZ";
		String expected = "ZELUREPT";
		// act
		String cypherText = caesar.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void encryptBeispielMessageMitUngültigenZeichenLiefertErwartetenCypherTextMitBereinigung() throws CrypterException {
		
		//arrange
		Crypter caesar = new CrypterReverse(null);
		String message = "TPER &&§?*+~~()ßßULEZ-|||?42";
		String expected = "ZELUREPT";
		// act
		String cypherText = caesar.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void decryptBeispielCypherTextLiefertErwartetenText() throws CrypterException {
		
		//arrange
		Crypter caesar = new CrypterReverse(null);
		String cypherText = "ZELUREPT";
		String expected = "TPERULEZ";
		// act
		String text = caesar.decrypt(cypherText);
		//assert
		assertEquals(expected, text);
	}
	
	@Test
	public void entschlüsselungMachtVerschlüsselungRückgängig() throws CrypterException {
		//arrange
		Crypter caesar = new CrypterReverse(null);
		String message = "GEHDUALTERESELHEUFRESSEN";
		// act
		String cypherText = caesar.decrypt(message);
		String text = caesar.encrypt(cypherText);
		// assert
		assertEquals(message, text);
	}
		
	@Test
	public void verschlüsselungMachtEntschlüsselungRückgängig() throws CrypterException {
		//arrange
		Crypter caesar = new CrypterReverse(null);
		String cypherText = "MALHEUREUSEMENTJESUISMALADE";
		// act
		String text = caesar.decrypt(cypherText);
		String finalText = caesar.encrypt(text);
		// assert
		assertEquals(cypherText, finalText);
	}
	
}
