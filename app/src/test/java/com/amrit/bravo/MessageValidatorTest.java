package com.amrit.bravo;

import com.google.common.truth.Truth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageValidatorTest {

    private MessageValidator messageValidator;

    @Before
    public void setUp() {
        messageValidator = new MessageValidator();
    }

    @Test
    public void hasDateTest() {
        // Considering Indian date notation.
        Truth.assertThat(messageValidator.hasDate("Hello 01-01-2021")).isTrue();
        Truth.assertThat(messageValidator.hasDate("Hello 10-10-2021")).isTrue();
        Truth.assertThat(messageValidator.hasDate("Hello 31-10-21")).isTrue();
        Truth.assertThat(messageValidator.hasDate("Hello 31-12-21")).isTrue();
    }

    @Test
    public void doesNotHasDateTest() {
        // Considering Indian date notation.
        Truth.assertThat(messageValidator.hasDate("Hello 01/01/2021")).isFalse();
        Truth.assertThat(messageValidator.hasDate("Hello 32-02-2021")).isFalse();
        Truth.assertThat(messageValidator.hasDate("Hello 31-13-2021")).isFalse();
        Truth.assertThat(messageValidator.hasDate("Hello 12-12-20213")).isFalse();
        Truth.assertThat(messageValidator.hasDate("Hello, how are you?")).isFalse();

    }

    @Test
    public void hasAmountTest() {
        Truth.assertThat(messageValidator.hasAmount("how are you INR. 30")).isTrue();
        Truth.assertThat(messageValidator.hasAmount("how are you INR. 30.25")).isTrue();
    }

    @Test
    public void doesNotHasAmountTest() {
        Truth.assertThat(messageValidator.hasAmount("Hello, how are you?")).isFalse();
        Truth.assertThat(messageValidator.hasAmount("Hello 21-02-21")).isFalse();
    }

    @After
    public void cleanUp() {
        messageValidator = null;
    }
}
