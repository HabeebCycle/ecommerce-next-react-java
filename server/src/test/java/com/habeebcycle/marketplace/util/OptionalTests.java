package com.habeebcycle.marketplace.util;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/*
Title: Avoiding the Null Pointer Exception With Optional in Java, How to avoid the “billion-dollar mistake” in Java
Author: Brilian Firdaus
Date: Oct 7, 2020
Link: https://medium.com/better-programming/avoiding-the-null-pointer-exception-with-optional-in-java-96cd75d9b058
*/

public class OptionalTests {

    /*Optional.of accepts any type with a non-nullable value in its parameter.
    To create an Optional object with Optional.of, we just have to pass a value in its parameter.*/
    @Test
    public void initializeOptional_optionalOf() {
        Optional<String> helloWorldOptional = Optional.of("Hello, world");
        assert helloWorldOptional.isPresent();
        assert "Hello, world".equals(helloWorldOptional.get());
    }

    /*Be very careful when you are passing a value to the Optional.of.
    Remember that Optional.of doesn’t accept null values in its parameter.
    If you try to pass a null value, it will produce a NullPointerException.*/
    @Test
    public void initializeOptional_optionalOf_null() {
        try {
            Optional.of(null);
        } catch (Exception e) {
            assert e instanceof NullPointerException;
        }
    }

    /*Optional.ofNullable is similar to Optional.of. It accepts any type.
    The difference is, with Optional.ofNullable, you can pass a null value to its parameter.*/
    @Test
    public void initializeOptional_optionalOfNullable() {
        Optional<String> helloWorldOptional = Optional.ofNullable("Hello, world");
        assert helloWorldOptional.isPresent();
        assert "Hello, world".equals(helloWorldOptional.get());
    }

    /*When Optional.ofNullable is initialized using a null object, it will return an empty Optional.*/
    @Test
    public void initializeOptional_optionalOfNullable_null() {
        Optional<String> helloWorldOptional = Optional.ofNullable(null);
        assert !helloWorldOptional.isPresent();
        try {
            helloWorldOptional.get();
        } catch (Exception e) {
            assert e instanceof NoSuchElementException;
        }
    }

    /*An empty Optional can be initialized by using Optional.empty().*/
    @Test
    public void initializeOptional_optionalEmpty() {
        Optional<String> helloWorldOptional = Optional.empty();
        assert !helloWorldOptional.isPresent();
    }

    /*A pretty straightforward method. The get method will return the value of Optional
        if it is present and throw a NoSuchElementException if the value doesn’t exist.*/
    @Test
    public void get_test() {
        Optional<String> helloWorldOptional = Optional.of("Hello, World");
        assert "Hello, World".equals(helloWorldOptional.get());
    }

    @Test
    public void get_null_test() {
        Optional<String> helloWorldOptional = Optional.empty();
        try {
            helloWorldOptional.get();
        } catch (Exception e) {
            assert e instanceof NoSuchElementException;
        }
    }

    /*If you want to use a default value if the Optional is empty, you can usethe orElse method.*/
    @Test
    public void orElse_test() {
        Optional<String> helloWorldOptional = Optional.of("Hello, World");
        assert "Hello, World".equals(helloWorldOptional.orElse("default"));
    }

    @Test
    public void orELseNull_test() {
        Optional<String> helloWorldOptional = Optional.empty();
        assert "default".equals(helloWorldOptional.orElse("default"));
    }

    /*orElseGet is very similar to the orElse method. It’s just that orElseGet accepts Supplier<T> as its parameter.*/
    @Test
    public void orElseGet_test() {
        Optional<String> helloWorldOptional = Optional.of("Hello, World");
        assert "Hello, World".equals(helloWorldOptional.orElseGet(() ->"default"));
    }

    @Test
    public void orELseGet_Null_test() {
        Optional<String> helloWorldOptional = Optional.empty();
        assert "default".equals(helloWorldOptional.orElseGet(() ->"default"));
    }

    /*orElseThrow will return the value of the Optional or throw an exception if the value of the Optional is empty.*/
    @Test
    public void orElseThrow_test() {
        Optional<String> helloWorldOptional = Optional.of("Hello, World");
        assert "Hello, World".equals(helloWorldOptional.orElseThrow(NullPointerException::new));
    }

    @Test
    public void orELseThrow_Null_test() {
        Optional<String> helloWorldOptional = Optional.empty();
        try {
            helloWorldOptional.orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            assert e instanceof NullPointerException;
        }
    }

    /*map is the most used method when processing an Optional object.
    It accepts Function<? super T, ? extends U> as its parameter and returns an Optional<U>.
    This means you can use a Function with any type of parameter and the return value will be
        wrapped to Optional in the map method.*/
    @Test
    public void processingOptional_map_test() {
        Optional<String> stringOptional = Optional.of("Hello, World")
                .map(a -> a + ", Hello");

        assert stringOptional.isPresent();
        assert "Hello, World, Hello".equals(stringOptional.get());
    }

    /*If you try to return a null value in Function<? super T, ? extends U>,
    the map method will return an empty Optional.*/
    @Test
    public void processingOptional_map_empty_test() {
        Optional<String> stringOptional = Optional.of("Hello, World")
                .map(a -> null);

        assert !stringOptional.isPresent();
    }

    /*An empty optional won’t be processed by map. We can confirm this with the following test:*/
    @Test
    public void processingOptional_map_empty_notProcessed_test() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Optional<String> stringOptional = Optional.of("Hello, World")
                .map(a -> null)
                .map(a -> {
                    atomicBoolean.set(true);
                    return "won't be processed";
                });

        assert !stringOptional.isPresent();
        assert atomicBoolean.get() == false;
    }


    /*flatMap: This is similar to map, but flatMap won’t wrap the return value of the Function to Optional.
    The flatMap method accepts Function<? super T, ? extends Optional<? extends U>> as its parameter.
    This means that you’ll need to define a Function that accepts any type and returns an Optional.
    You will usually use the flatMap method when your code calls another method that returns an Optional object.*/
    @Test
    public void processingOptional_flatmap_test() {
        Optional<String> stringOptional = Optional.of("Hello, World")
                .flatMap(this::getString);

        assert "Hello, World, Hello".equals(stringOptional.get());
    }

    @Test
    public void processingOptional_flatmap_randomString_test() {
        Optional<String> stringOptional = Optional.of(UUID.randomUUID().toString())
                .flatMap(this::getString);

        assert !stringOptional.isPresent();
    }

    public Optional<String> getString(String s) {
        if ("Hello, World".equals(s)) {
            return Optional.of("Hello, World, Hello");
        }
        return Optional.empty();
    }


    /*filter: In the previous example of flatMap, we used a declarative style to differentiate
        the return value of the getString method. But we can actually use a functional
        style for that with the filter method.*/
    @Test
    public void processingOptional_filter_test() {
        Optional<String> stringOptional = Optional.of("Hello, World")
                .filter(helloWorldString -> "Hello, World".equals(helloWorldString))
                .map(helloWorldString -> helloWorldString + ", Hello");

        assert "Hello, World, Hello".equals(stringOptional.get());
    }

    @Test
    public void processingOptional_filter_randomString_test() {
        Optional<String> stringOptional = Optional.of(UUID.randomUUID().toString())
                .filter(helloWorldString -> "Hello, World".equals(helloWorldString))
                .map(helloWorldString -> helloWorldString + ", Hello");

        assert !stringOptional.isPresent();
    }


    /*ifPresent: The ifPresent method accepts a Consumer that will only be executed if the Optional is not empty.*/
    @Test
    public void processingOptional_ifPresent_test() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Optional.of("Hello, World")
                .ifPresent(helloWorldString -> atomicBoolean.set(true));
        assert atomicBoolean.get();
    }

    @Test
    public void processingOptional_ifPresent_empty_test() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Optional.empty()
                .ifPresent(helloWorldString -> atomicBoolean.set(true));
        assert !atomicBoolean.get();
    }


    /*Creating a method that accepts Optional as a parameter might introduce
        a problem it wants to solve, NullPointerException.
    If a person using the method with the Optional parameter is not aware of it,
        they might pass a null to the method instead of Optional.empty().
    Processing a null will produce a NullPointerException.*/
    @Test
    public void optionalAsParameter_test() {
        try {
            isPhoneNumberPresent(null);
        } catch (Exception e) {
            assert e instanceof NullPointerException;
        }
    }

    public boolean isPhoneNumberPresent(Optional<String> phoneNumber) {
        return phoneNumber.isPresent();
    }

    /*If you’re using Optional, then you should avoid using the get method if you can.
    If you still want to use it for some reason, make sure that you check it with the isPresent method
    first because if you use get on an empty Optional, it will produce a NoSuchMethodException.*/
    @Test
    public void getWithIsPresent_test() {
        Optional<String> helloWorldOptional = Optional.ofNullable(null);
        if (helloWorldOptional.isPresent()) {
            System.out.println(helloWorldOptional.get());
        }
    }

    @Test
    public void getWithoutIsPresent_error_test() {
        Optional<String> helloWorldOptional = Optional.ofNullable(null);
        try {
            System.out.println(helloWorldOptional.get());
        } catch (Exception e) {
            assert e instanceof NoSuchElementException;
        }
    }
}
