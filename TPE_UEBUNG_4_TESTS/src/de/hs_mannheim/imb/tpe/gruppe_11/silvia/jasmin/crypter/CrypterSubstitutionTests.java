package de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter;

import static org.junit.Assert.*;

import org.junit.Test;





import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.Crypter;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.CrypterSubstitution;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.CrypterException;
import de.hs_mannheim.imb.tpe.gruppe_11.silvia.jasmin.crypter.exceptions.IllegalKeyException;

/**
 * Die Klasse <code>CrypterSubstitutionTest</code> enthält Tests für die Klasse
 * {@link <code>CrypterSubstitution</code>}. Da wir dieselbe Package benutzen wie
 * die Crypter-Implementierungen, können wir diese Klassen direkt instanziieren
 * und sind nicht auf CrypterFactory angewiesen und deshalb davon unabhängig.
 *
 * @author Jasmin Cano, Silvia Yildiz
 *
 * @version $Revision$
 */
public class CrypterSubstitutionTests {

	@Test
	public void konstruktionMitGültigemSchlüsselLiefertInstanz() throws IllegalKeyException
	{
		//arrange
		Crypter substitution;
		// act
		substitution = new CrypterSubstitution("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		//assert (eigentlich nicht nötig)
		assertNotNull(substitution);
	}
	
	@Test(expected=IllegalKeyException.class)
	public void konstruktionMitNullSchlüsselSchägtFehl() throws IllegalKeyException {
		// arrange
		// act
		new CrypterSubstitution(null);
		// assert...
	}
	
	@Test(expected=IllegalKeyException.class)
	public void konstruktionMitUngültigemSchlüsselSchägtFehl() throws IllegalKeyException {
		// arrange
		// act
		new CrypterSubstitution("@##!!#!##!?!:-(");
		// assert...
	}
	
	@Test
	public void encryptBeispielMessageLiefertErwartetenCypherText() throws CrypterException {
		
		//arrange
		Crypter substitution;
		substitution = new CrypterSubstitution("UFLPWDRASJMCONQYBVTEXHZKGI");
		String message = "WIKIPEDIAISTINFORMATIV";
		String expected = "ZSMSYWPSUSTESNDQVOUESH";
		// act
		String cypherText = substitution.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void encryptBeispielMessageMitUngültigenZeichenLiefertErwartetenCypherTextMitBereinigung() throws CrypterException {
		
		//arrange
		Crypter substitution = new CrypterSubstitution("UFLPWDRASJMCONQYBVTEXHZKGI");
		String message = "WIKI.PEDIA IST &&§?\\*+~~()ßß[]INFORMATIV-___|||?42";
		String expected = "ZSMSYWPSUSTESNDQVOUESH";
		// act
		String cypherText = substitution.encrypt(message);
		//assert
		assertEquals(expected, cypherText);
	}
	
	@Test
	public void decryptBeispielCypherTextLiefertErwartetenText() throws CrypterException {
		
		//arrange
		Crypter substitution = new CrypterSubstitution("UFLPWDRASJMCONQYBVTEXHZKGI");
		String cypherText = "";
		String expected = "";
		// act
		String text = substitution.decrypt(cypherText);
		//assert
		assertEquals(expected, text);
	}
	
	@Test
	public void entschlüsselungMachtVerschlüsselungRückgängig() throws CrypterException {
		//arrange
		Crypter substitution = new CrypterSubstitution("UFLPWDRASJMCONQYBVTEXHZKGI");
		String message = "GEHDUALTERESELHEUFRESSEN";
		// act
		String cypherText = substitution.decrypt(message);
		String text = substitution.encrypt(cypherText);
		// assert
		assertEquals(message, text);
	}
		
	@Test
	public void verschlüsselungMachtEntschlüsselungRückgängig() throws CrypterException {
		//arrange
		Crypter substitution = new CrypterSubstitution("UFLPWDRASJMCONQYBVTEXHZKGI");
		String cypherText = "MALHEUREUSEMENTJESUISMALADE";
		// act
		String text = substitution.decrypt(cypherText);
		String finalText = substitution.encrypt(text);
		// assert
		assertEquals(cypherText, finalText);
	}
	
}