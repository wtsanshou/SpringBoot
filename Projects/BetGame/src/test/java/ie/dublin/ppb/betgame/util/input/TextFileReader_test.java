package ie.dublin.ppb.betgame.util.input;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import ie.dublin.ppb.betgame.model.PpbCurrency;
import ie.dublin.ppb.betgame.TestConfigers;
import ie.dublin.ppb.betgame.model.Game;

public class TextFileReader_test {
	private static PpbReader reader;

	private static PpbCurrency currencyGBP = TestConfigers.currencyGBP;
	private static PpbCurrency currencyEUR = TestConfigers.currencyEUR;
	private static Map<String, PpbCurrency> currencies = TestConfigers.currencies;

	@BeforeClass
	public static void setup() {
		currencies.put("GBP", currencyGBP);
		currencies.put("EUR", currencyEUR);
		reader = new TextFileReader(currencies);
	}

	@Test
	public void returnTheEmptyGame_whenInputEmptyPath() throws IOException {
		String path = "";
		Optional<Game> game = reader.getData(path);
		assertEquals(Optional.empty(), game);
	}

	@Test
	public void returnTheEmptyGame_whenInputAnNonTextFile() throws IOException {
		String path = "app/testFiles/test1.json";
		Optional<Game> game = reader.getData(path);
		assertEquals(Optional.empty(), game);
	}

	@Test
	public void returnTheEmptyGame_whenInputInvalidPath() throws IOException {
		String path = "abc.txt";
		Optional<Game> game = reader.getData(path);
		assertEquals(Optional.empty(), game);
	}

	@Test
	public void returnTheEmptyGame_whenParseUnMatchedColumn() throws IOException {
		String lackColumnPath = "app/testFiles/lackColumn.txt";
		Optional<Game> lackColumnGame = reader.getData(lackColumnPath);
		assertEquals(Optional.empty(), lackColumnGame);

		String excessColumnPath = "app/testFiles/excessColumn.txt";
		Optional<Game> excessColumnGame = reader.getData(excessColumnPath);
		assertEquals(Optional.empty(), excessColumnGame);
	}

	@Test
	public void returnTheEmptyGame_whenParseWrongCurrency() throws IOException {
		String unconfiguredCurrency1 = "app/testFiles/unconfiguredCurrency1.txt";
		Optional<Game> unconfiguredCurrencyGame1 = reader.getData(unconfiguredCurrency1);
		assertEquals(Optional.empty(), unconfiguredCurrencyGame1);

		String unconfiguredCurrency2 = "app/testFiles/unconfiguredCurrency2.txt";
		Optional<Game> unconfiguredCurrencyGame2 = reader.getData(unconfiguredCurrency2);
		assertEquals(Optional.empty(), unconfiguredCurrencyGame2);
	}

	@Test
	public void returnTheEmptyGame_whenParseWrongColumnContent() throws IOException {
		String wrongBetTimeStamp = "app/testFiles/wrongBetTimeStamp.txt";
		Optional<Game> wrongBetTimeStampGame = reader.getData(wrongBetTimeStamp);
		assertEquals(Optional.empty(), wrongBetTimeStampGame);

		String wrongSelectionId = "app/testFiles/wrongSelectionId.txt";
		Optional<Game> wrongSelectionIdGame = reader.getData(wrongSelectionId);
		assertEquals(Optional.empty(), wrongSelectionIdGame);

		String wrongStake = "app/testFiles/wrongStake.txt";
		Optional<Game> wrongStakeGame = reader.getData(wrongStake);
		assertEquals(Optional.empty(), wrongStakeGame);

		String wrongPrice = "app/testFiles/wrongPrice.txt";
		Optional<Game> wrongPriceGame = reader.getData(wrongPrice);
		assertEquals(Optional.empty(), wrongPriceGame);
	}

	@Test
	public void returnTheEmptyGame_whenParseEmptyColumContent() throws IOException {
		String emptyBetId = "app/testFiles/emptyBetId.txt";
		Optional<Game> emptyBetIdGame = reader.getData(emptyBetId);
		assertEquals(Optional.empty(), emptyBetIdGame);
		
		String emptyBetTimeStamp = "app/testFiles/emptyBetTimeStamp.txt";
		Optional<Game> emptyBetTimeStampGame = reader.getData(emptyBetTimeStamp);
		assertEquals(Optional.empty(), emptyBetTimeStampGame);
	
		String emptySelectName = "app/testFiles/emptySelectName.txt";
		Optional<Game> emptySelectNameGame = reader.getData(emptySelectName);
		assertEquals(Optional.empty(), emptySelectNameGame);
		
		String emptyCurrency = "app/testFiles/emptyCurrency.txt";
		Optional<Game> emptyCurrencyGame = reader.getData(emptyCurrency);
		assertEquals(Optional.empty(), emptyCurrencyGame);
	}
	
	@Test
	public void returnTheEmptyGame_whenParsewrongDelimiterFile() throws IOException {
		String wrongDelimiter = "app/testFiles/wrongDelimiter.txt";
		Optional<Game> wrongDelimiterGame = reader.getData(wrongDelimiter);
		assertEquals(Optional.empty(), wrongDelimiterGame);
	}
	
	// Test if the money number with currency symbol
	// 1. other currency symbols than the configured currencies
	// 2. GBP symbol with EUR currency
	// 3. EUR symbol with GBP currency

	@Test
	public void returnTheGameData_whenInputAnValidTextFile() throws IOException {
		String path = "app/testFiles/test1.txt";
		Optional<Game> game = reader.getData(path);
		assertEquals(true, game.isPresent());
		assertNotNull(game.get().getBets());
		assertEquals(24, game.get().getBets().size());
	}

}
