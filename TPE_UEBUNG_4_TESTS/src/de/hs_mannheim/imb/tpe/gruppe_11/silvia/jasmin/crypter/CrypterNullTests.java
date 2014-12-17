package de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter;


import static org.junit.Assert.*;

import org.junit.Test;

import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.CrypterException;

public class CrypterNullTests {

	@Test
	public void encryptBeispielMessageLiefertErwartetenCypherText() throws CrypterException {
		
		//arrange
		Crypter caesar;
		caesar = new CrypterNull(null);
		String message = "ZUMDONNERWETTERKRUZEFIXHALLELUJAABERAUCH";
		String expected = "ZUMDONNERWETTERKRUZEFIXHALLELUJAABERAUCH";
		// act
		String cypherText = caesar.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void encryptBeispielMessageMitUngültigenZeichenLiefertErwartetenCypherTextMitBereinigung() throws CrypterException {
		
		//arrange
		Crypter caesar = new CrypterNull(null);
		String message = "ZUMDONNERWETTERKRUZE &&§?*+~~()ßßFIXHALLELUJAABERAUCH-|||?42";
		String expected = "ZUMDONNERWETTERKRUZEFIXHALLELUJAABERAUCH";
		// act
		String cypherText = caesar.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void decryptBeispielCypherTextLiefertErwartetenText() throws CrypterException {
		
		//arrange
		Crypter caesar = new CrypterNull(null);
		String cypherText = "ZUMDONNERWETTERKRUZEFIXHALLELUJAABERAUCH";
		String expected = "ZUMDONNERWETTERKRUZEFIXHALLELUJAABERAUCH";
		// act
		String text = caesar.decrypt(cypherText);
		//assert
		assertEquals(expected, text);
	}
	
	@Test
	public void entschlüsselungMachtVerschlüsselungRückgängig() throws CrypterException {
		//arrange
		Crypter caesar = new CrypterNull(null);
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
		Crypter caesar = new CrypterNull(null);
		String cypherText = "MALHEUREUSEMENTJESUISMALADE";
		// act
		String text = caesar.decrypt(cypherText);
		String finalText = caesar.encrypt(text);
		// assert
		assertEquals(cypherText, finalText);
	}
	
}
