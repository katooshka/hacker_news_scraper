package scraper;

import org.junit.Assert;
import org.junit.Test;

public class URIValidatorTest {

    @Test
    public void isValid_ShouldReturnFalse_WhenUriIsInvalid() {
        Assert.assertFalse(URIValidator.isValid("123"));
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenUriIsValid() {
        Assert.assertTrue(URIValidator.isValid("https://www.google.co.uk/"));
    }
}
