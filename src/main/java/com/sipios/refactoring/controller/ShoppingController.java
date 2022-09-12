package com.sipios.refactoring.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.sipios.refactoring.models.Body;
import com.sipios.refactoring.models.Item;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    @PostMapping
    public String getPrice(@RequestBody Body b) {
        double price = 0;
        double discount;

        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        // Compute discount for customer
        discount = computeDiscountForCustomer(b);

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (
            isBetweenTheDay5AndTheDay15allExcluded(cal) &&
                cal.get(Calendar.MONTH) == Calendar.JUNE ||
                isBetweenTheDay5AndTheDay15allExcluded(cal) &&
                    cal.get(Calendar.MONTH) == Calendar.JANUARY
        ) {
            if (b.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    price += 30 * it.getNb() * discount;
                } else if (it.getType().equals("DRESS")) {
                    price += 50 * it.getNb() * 0.8 * discount;
                } else if (it.getType().equals("JACKET")) {
                    price += 100 * it.getNb() * 0.9 * discount;
                }
            }
        } else {
            if (b.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    price += 30 * it.getNb() * discount;
                } else if (it.getType().equals("DRESS")) {
                    price += 50 * it.getNb() * discount;
                } else if (it.getType().equals("JACKET")) {
                    price += 100 * it.getNb() * discount;
                }
            }
        }

        checkPriceLimitAndThrowAnExceptionIfReached(b, price);

        return String.valueOf(price);
    }

    private boolean isBetweenTheDay5AndTheDay15allExcluded(Calendar cal) {
        return cal.get(Calendar.DAY_OF_MONTH) < 15 &&
            cal.get(Calendar.DAY_OF_MONTH) > 5;
    }

    private void checkPriceLimitAndThrowAnExceptionIfReached(Body b, double p) {
        try {
            if (b.getType().equals("STANDARD_CUSTOMER")) {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            } else if (b.getType().equals("PREMIUM_CUSTOMER")) {
                if (p > 800) {
                    throw new Exception("Price (" + p + ") is too high for premium customer");
                }
            } else if (b.getType().equals("PLATINUM_CUSTOMER")) {
                if (p > 2000) {
                    throw new Exception("Price (" + p + ") is too high for platinum customer");
                }
            } else {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private double computeDiscountForCustomer(Body b) {
        double d;
        if (b.getType().equals("STANDARD_CUSTOMER")) {
            d = 1;
        } else if (b.getType().equals("PREMIUM_CUSTOMER")) {
            d = 0.9;
        } else if (b.getType().equals("PLATINUM_CUSTOMER")) {
            d = 0.5;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return d;
    }
}

