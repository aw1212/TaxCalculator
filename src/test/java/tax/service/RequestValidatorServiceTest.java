package tax.service;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import tax.controller.exceptions.InvalidRequestParameterException;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidatorServiceTest {

    private RequestValidatorService requestValidatorService = new RequestValidatorService();

    @Test(expected = InvalidRequestParameterException.class)
    public void givenInvalidEmail_whenValidatingRequest_thenExceptionThrown() throws AddressException {
        InternetAddress internetAddress = mock(InternetAddress.class);
        doThrow(new AddressException()).when(internetAddress).validate();
        requestValidatorService.validateRequest("invalid", "2014/15", 300000.0);
    }

    @Test(expected = InvalidRequestParameterException.class)
    public void givenOldTaxYear_whenValidatingRequest_thenExceptionThrown() {
        requestValidatorService.validateRequest("test@test.com", "2013/14", 100000.0);
    }

    @Test(expected = InvalidRequestParameterException.class)
    public void givenFutureYear_whenValidatingRequest_thenExceptionThrown() {
        requestValidatorService.validateRequest("test@test.com", "2016/17", 100000.0);
    }

    @Test(expected = InvalidRequestParameterException.class)
    public void givenSalaryBelowMinAllowed_whenValidatingRequest_thenExceptionThrown() {
        requestValidatorService.validateRequest("test@test.com", "2014/15", 0);
    }

    @Test(expected = InvalidRequestParameterException.class)
    public void givenSalaryAboveMaxAllowed_whenValidatingRequest_thenExceptionThrown() {
        requestValidatorService.validateRequest("test@test.com", "2014/15", 300000.0);
    }

}
