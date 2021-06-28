package com.joutvhu.date.parser.support;

import com.joutvhu.date.parser.strategy.CenturyStrategy;
import com.joutvhu.date.parser.strategy.EraStrategy;
import com.joutvhu.date.parser.strategy.QuoteStrategy;
import com.joutvhu.date.parser.strategy.Strategy;
import com.joutvhu.date.parser.strategy.WeekStrategy;
import com.joutvhu.date.parser.strategy.WeekdayInMonthStrategy;
import com.joutvhu.date.parser.strategy.WeekdayStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatePatternSplitterTest {
    @Test
    void getStrategyChain_Case0() {
        List<Strategy> result = new DatePatternSplitter("yyyy/MM/dd'T'HH:mm:ss.SSS")
                .getStrategyChain();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(13, result.size());
        Assertions.assertTrue(result.get(5) instanceof QuoteStrategy);
    }

    @Test
    void getStrategyChain_Case1() {
        List<Strategy> result = new DatePatternSplitter("EEEE")
                .getStrategyChain();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0) instanceof WeekdayStrategy);
    }

    @Test
    void getStrategyChain_Case2() {
        List<Strategy> result = new DatePatternSplitter("G")
                .getStrategyChain();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0) instanceof EraStrategy);
    }

    @Test
    void getStrategyChain_Case3() {
        List<Strategy> result = new DatePatternSplitter("C")
                .getStrategyChain();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0) instanceof CenturyStrategy);
    }

    @Test
    void getStrategyChain_Case4() {
        List<Strategy> result = new DatePatternSplitter("F")
                .getStrategyChain();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0) instanceof WeekdayInMonthStrategy);
    }

    @Test
    void getStrategyChain_Case5() {
        List<Strategy> result = new DatePatternSplitter("w")
                .getStrategyChain();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0) instanceof WeekStrategy);
    }
}
