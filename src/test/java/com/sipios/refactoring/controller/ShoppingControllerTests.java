package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void whenStandardCustomerWithTShirtReturn30() {
        final Item[] items = {new Item("TSHIRT",1)};
        final String price = controller.getPrice(new Body(items, "STANDARD_CUSTOMER"));
        System.out.println("--->>>"+price);


      assertEquals("30.0",
            controller.getPrice(new Body(items, "STANDARD_CUSTOMER"))
        );
    }

    @Test
    void whenPremiumCustomerReturn0() {
        final String price = controller.getPrice(new Body(new Item[]{}, "PREMIUM_CUSTOMER"));
        System.out.println("--->>>"+price);


        assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, "PREMIUM_CUSTOMER"))
        );
    }

    @Test
    void whenPlatiniumCustomerReturn0() {
        final String price = controller.getPrice(new Body(new Item[]{}, "PLATINUM_CUSTOMER"));
        System.out.println("--->>>"+price);


        assertEquals("0.0",
            controller.getPrice(new Body(new Item[] {}, "PLATINUM_CUSTOMER"))
        );
    }
}
