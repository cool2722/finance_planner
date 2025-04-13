package com.example.domain.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepeatTypeTest {
    @Test
    void mapsCodeCorrectly() {
        assertEquals(RepeatType.MONTHLY, RepeatType.fromCode("M"));
        assertEquals(RepeatType.YEARLY, RepeatType.fromCode("y"));
        assertEquals(RepeatType.NONE, RepeatType.fromCode("random"));
    }
    
    @Test
    void identifiesRecurringCorrectly() {
        assertTrue(RepeatType.MONTHLY.isRecurring());
        assertFalse(RepeatType.NONE.isRecurring());
    }
    
}
