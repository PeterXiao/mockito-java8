/*
 * Copyright (C) 2015 Marcin Zajączkowski.
 *
 * Licensed under the Apache License, Version 2.0.
 */
package info.solidsoft.mockito.java8;

import info.solidsoft.mockito.java8.domain.ShipSearchCriteria;
import info.solidsoft.mockito.java8.domain.TacticalStation;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;

import java.io.IOException;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static info.solidsoft.mockito.java8.AssertionMatcher.assertArgChecked;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.junit.MockitoJUnit.rule;

public class AssertionMatcherTest {

    @Rule
    public MockitoRule rule = rule();

    @Mock
    private TacticalStation ts;

    private ShipSearchCriteria searchCriteria = new ShipSearchCriteria(1000, 4);

    @Test
    public void shouldAllowToUseArgumentCaptorInClassicWay() {
        //when
        ts.findNumberOfShipsInRangeByCriteria(searchCriteria);
        //then
        ArgumentCaptor<ShipSearchCriteria> captor = ArgumentCaptor.forClass(ShipSearchCriteria.class);
        verify(ts).findNumberOfShipsInRangeByCriteria(captor.capture());
        assertThat(captor.getValue().getMinimumRange()).isLessThan(2000);
    }

    @Test
    public void shouldAllowToUseAssertionInLambda() {
        //when
        ts.findNumberOfShipsInRangeByCriteria(searchCriteria);
        //then
        verify(ts).findNumberOfShipsInRangeByCriteria(assertArg(sc -> assertThat(sc.getMinimumRange()).isLessThan(2000)));
    }

    @Test
    public void shouldAllowToUseAssertionInLambdaWithPrimitiveAsArgument() {
        //when
        ts.fireTorpedo(2);
        //then
        verify(ts).fireTorpedo(assertArg(i -> assertThat(i).isEqualTo(2)));
    }

    @Test
    public void shouldHaveMeaningfulErrorMessage() {
        //when
        ts.findNumberOfShipsInRangeByCriteria(searchCriteria);
        //then
        ThrowableAssert.ThrowingCallable verifyLambda = () -> {
            verify(ts).findNumberOfShipsInRangeByCriteria(assertArg(sc -> assertThat(sc.getMinimumRange()).isLessThan(50)));
        };
        assertThatThrownBy(verifyLambda)
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Argument(s) are different! Wanted:\n" +
                        "ts.findNumberOfShipsInRangeByCriteria(\n" +
                        "    AssertionMatcher reported: \n" +
                        "Expecting:\n" +
                        " <1000>\n" +
                        "to be less than:\n" +
                        " <50> ")
                .hasMessageContaining("Actual invocation has different arguments:\n" +
                        "ts.findNumberOfShipsInRangeByCriteria(\n" +
                        "    ShipSearchCriteria{minimumRange=1000, numberOfPhasers=4}\n" +
                        ");");
    }

    @SuppressWarnings("Convert2MethodRef")
    @Test
    public void shouldAcceptLambdaWhichMayThrowCheckedException() {
        //when
        ts.fireTorpedo(2);
        //then
        verify(ts).fireTorpedo(assertArgChecked(i -> methodDeclaringThrowingCheckedException(i)));
    }

    @Test
    public void shouldAcceptMethodReferenceWhichMayThrowCheckedException() {
        //when
        ts.fireTorpedo(2);
        //then
        verify(ts).fireTorpedo(assertArgChecked(this::methodDeclaringThrowingCheckedException));
    }

    @Test(expected = IOException.class)
    public void shouldPropagateCheckedExceptionIfThrownInLambda() {
        //when
        ts.fireTorpedo(2);
        //then
        verify(ts).fireTorpedo(assertArgChecked(i -> { throw new IOException("Unexpected checked exception"); }));
    }

    private void methodDeclaringThrowingCheckedException(int i) throws Exception {
        assertThat(i).isEqualTo(2);
    }
}
