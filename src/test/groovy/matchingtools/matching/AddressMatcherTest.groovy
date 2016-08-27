package matchingtools.matching

import domain.Address
import domain.Match
import matchingtools.matching.AddressMatcher

/**
 * Created by Christian Sperandio on 26/08/2016.
 */
class AddressMatcherTest extends GroovyTestCase {

    AddressMatcher matcher

    @Override
    protected void setUp() throws Exception {
        matcher = new AddressMatcher()
    }

    void testCompatibility_1() {
        def addr1 = new Address("", "ZI", "", "", "")
        def addr2 = new Address("", "ZI", "", "", "")

        assertTrue(matcher.isCompatible(addr1, addr2))
    }

    void testCompatibility_2() {
        def addr1 = new Address("", "ZI", "", "", "")
        def addr2 = new Address("", "ZA", "", "", "")

        assertTrue(matcher.isCompatible(addr1, addr2))
    }

    void testCompatibility_3() {
        def addr1 = new Address("", "ZI", "", "", "")
        def addr2 = new Address("", "RUE", "", "", "")

        assertFalse(matcher.isCompatible(addr1, addr2))
    }

    void testCompatibility_4() {
        def addr1 = new Address("", "AV", "", "", "")
        def addr2 = new Address("", "AV", "", "", "")

        assertTrue(matcher.isCompatible(addr1, addr2))
    }

    void testCompareAddress_1() {
        def addr1 = new Address("", "AV", "", "", "")
        def addr2 = new Address("", "AV", "", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.EMPTY, result)

    }

    void testCompareAddress_2() {
        def addr1 = new Address("", "", "", "", "")
        def addr2 = new Address("", "", "", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.EMPTY, result)

    }

    void testCompareAddress_4() {
        def addr1 = new Address("", "AV", "LE GRAND", "", "")
        def addr2 = new Address("", "ZI", "", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.EMPTY, result)
    }

    void testCompareAddress_5() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "", "")
        def addr2 = new Address("", "AV", "SAINT MANDE", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.MATCH, result)
    }

    void testCompareAddress_6() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "", "")
        def addr2 = new Address("90", "AV", "SAINT MANDE", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.UNMATCH, result)
    }

    void testCompareAddress_7() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "", "")
        def addr2 = new Address("12", "AV", "SAINT MANDE", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.MATCH, result)
    }

    void testCompareAddress_8() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "", "")
        def addr2 = new Address("11", "AV", "SAINT MANDE", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.UNMATCH, result)
    }

    void testCompareAddress_9() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "", "")
        def addr2 = new Address("14", "RUE", "SAINT MANDE", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.MATCH, result)
    }

    void testCompareAddress_10() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "", "")
        def addr2 = new Address("14", "RUE", "RENDEZ VOUS", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.UNMATCH, result)
    }

    void testCompareAddress_11() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "123", "")
        def addr2 = new Address("14", "AV", "SAINT MANDE", "456", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.UNMATCH, result)
    }

    void testCompareAddress_12() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "123", "")
        def addr2 = new Address("14", "AV", "RENDEZ VOUS", "123", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.MATCH, result)
    }

    void testCompareAddress_13() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "123", "")
        def addr2 = new Address("14", "AV", "RENDEZ VOUS", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.UNMATCH, result)
    }

    void testCompareAddress_14() {
        def addr1 = new Address("14", "AV", "SAINT MANDE", "", "")
        def addr2 = new Address("14", "AV", "SAINT MANDE", "456", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.MATCH, result)
    }

    void testCompareAddress_15() {
        def addr1 = new Address("14", "AV", "DE LATTRE DE TASSIGNY", "", "")
        def addr2 = new Address("14", "AV", "DU MARECHAL DE LATTRE DE TASSIGNY", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.MATCH, result)
    }

    void testCompareAddress_16() {
        def addr1 = new Address("14", "AV", "DE LATTRE DE TASSIGNY", "", "")
        def addr2 = new Address("14", "AV", "DELATTRE DE TASSIGNY", "", "")

        def result = matcher.compareAddress(addr1, addr2)
        assertEquals(Match.MATCH, result)
    }


}
