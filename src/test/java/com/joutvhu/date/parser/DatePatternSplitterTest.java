package com.joutvhu.date.parser;

import com.joutvhu.date.parser.strategy.Strategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatePatternSplitterTest {
    @Test
    public void testGetStrategyChain() {
        List<Strategy> result = new DatePatternSplitter("yyyy/MM/dd'T'HH:mm:ss.SSS")
                .getStrategyChain();
        Assertions.assertNotNull(result);
    }
}
