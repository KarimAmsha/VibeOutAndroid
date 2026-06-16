package com.vibeout.talaa

import com.vibeout.talaa.ui.common.isStrongPassword
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationTest {
    @Test fun strongPasswordRequiresLettersAndNumbers() {
        assertTrue("StrongPassword123".isStrongPassword())
        assertFalse("onlyletters".isStrongPassword())
        assertFalse("12345678".isStrongPassword())
        assertFalse("A1".isStrongPassword())
    }
}
