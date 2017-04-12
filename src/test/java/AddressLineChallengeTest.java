import de.aeroworx.AddressLineChallenge;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddressLineChallengeTest {

    private AddressLineChallenge challenge = new AddressLineChallenge();

    @Test
    public void testSingleNumberAtEnd() {
        assertCorrectAddress("Winterallee 3",
                "Winterallee",
                "3");
    }

    @Test
    public void testMultipleNumberAtEnd() {
        assertCorrectAddress("Musterstrasse 45",
                "Musterstrasse",
                "45");
    }

    @Test
    public void testCharNumberNumberAtEnd() {
        assertCorrectAddress("Blaufeldweg 123B",
                "Blaufeldweg",
                "123B");
    }

    @Test
    public void testUmlauteStreetNumberAtEnd() {
        assertCorrectAddress("Am Bächle 23",
                "Am Bächle",
                "23");
    }

    @Test
    public void testCharNumberWithSpaceNumberAtEnd() {
        assertCorrectAddress("Auf der Vogelwiese 23 b",
                "Auf der Vogelwiese",
                "23 b");
    }

    @Test
    public void testNumberAtBeginningWithComma() {
        assertCorrectAddress("4, rue de la revolution",
                "rue de la revolution",
                "4");
    }

    @Test
    public void testNumberAtBeginningWithSpace() {
        assertCorrectAddress("200 Broadway Av",
                "Broadway Av",
                "200");
    }

    @Test
    public void testNumberAtEndWithComma() {
        assertCorrectAddress("Calle Aduana, 29",
                "Calle Aduana",
                "29");
    }

    @Test
    public void testMultipleNumbers() {
        assertCorrectAddress("Calle 39 No 1540",
                "Calle 39",
                "No 1540");
    }

    @Test
    public void testNumberInStreetnameWithDot() {
        assertCorrectAddress("Straße des 17. Juni 35",
                "Straße des 17. Juni",
                "35");
    }

    @Test
    public void testStreetnameWithDashes() {
        assertCorrectAddress("Hans-Eisler-Allee 35",
                "Hans-Eisler-Allee",
                "35");
    }

    @Test
    public void testNoNumber() {
        assertCorrectAddress("Straße",
                "Straße",
                "");
    }

    @Test
    public void testNull() {
        String resultAddress = challenge.getAddressFromStreet(null);

        String format = "{\"%s\", \"%s\"}";
        assertThat(resultAddress, equalTo(String.format(format, "", "")));
    }

    private void assertCorrectAddress(String street, String adrStreet, String adrNumber) {
        String resultAddress = challenge.getAddressFromStreet(street);

        String format = "{\"%s\", \"%s\"}";
        assertThat(resultAddress, equalTo(String.format(format, adrStreet, adrNumber)));
    }
}
