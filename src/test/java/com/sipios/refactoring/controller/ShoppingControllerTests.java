package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER"))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1:30.0","2:60.0","3:90.0","4:120.0" ,"5:150.0"}, delimiter = ':')
    void whenStandardCustomerWithTShirtReturnAValue(String quantity,String priceExpected) {
        final Item[] items = {new Item("TSHIRT", Integer.parseInt(quantity))};


      assertEquals(priceExpected,
            controller.getPrice(new Body(items, "STANDARD_CUSTOMER"))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"7:toto","7:toto"}, delimiter = ':')
    void whenStandardCustomerWithNbTShirttohightReturnAnException(String quantity,String exceptionExpected) {
        final Item[] items = {new Item("TSHIRT", Integer.parseInt(quantity))};


        assertThrows(Exception.class,
            ()-> controller.getPrice(new Body(items, "STANDARD_CUSTOMER")),exceptionExpected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1:27.0","2:54.0","3:81.0","4:108.0" ,"5:135.0"}, delimiter = ':')
    void whenPremiumCustomerReturnValue(String quantity,String priceExpected) {
        final Item[] items = {new Item("TSHIRT", Integer.parseInt(quantity))};


        assertEquals(priceExpected,
            controller.getPrice(new Body(items, "PREMIUM_CUSTOMER"))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"120:toto","200:toto"}, delimiter = ':')
    void whenPremiumCustomerWithNbTShirttohightReturnAnException(String quantity,String exceptionExpected) {
        final Item[] items = {new Item("TSHIRT", Integer.parseInt(quantity))};


        assertThrows(Exception.class,
            ()-> controller.getPrice(new Body(items, "PREMIUM_CUSTOMER")),exceptionExpected);
    }


    @ParameterizedTest
    @CsvSource(value = {"1:15.0","2:30.0","3:45.0","4:60.0" ,"5:75.0"}, delimiter = ':')
    void whenPlatiniumCustomerReturnValue(String quantity,String priceExpected) {
        final Item[] items = {new Item("TSHIRT", Integer.parseInt(quantity))};


        assertEquals(priceExpected,
            controller.getPrice(new Body(items, "PLATINUM_CUSTOMER"))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"200:toto","300:toto"}, delimiter = ':')
    void whenPlatiniumCustomerWithNbTShirttohightReturnAnException(String quantity,String exceptionExpected) {
        final Item[] items = {new Item("TSHIRT", Integer.parseInt(quantity))};


        assertThrows(Exception.class,
            ()-> controller.getPrice(new Body(items, "PLATINUM_CUSTOMER")),exceptionExpected);
    }

}
