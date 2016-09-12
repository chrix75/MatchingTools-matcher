package matchingtools.matching

import domain.CompanyName
import domain.Match
/**
 * Created by Christian Sperandio on 26/08/2016.
 */
class CompanyNameMatcherTest extends GroovyTestCase {
    CompanyNameMatcher matcher

    @Override
    protected void setUp() throws Exception {
        matcher = new CompanyNameMatcher()
    }

    void testMatch_1() {
        def cie1 = new CompanyName("IMS HEALTH")
        def cie2 = new CompanyName("IMS HEALTH")

        assertEquals(Match.MATCH, matcher.match(cie1, cie2))
    }

    void testMatch_2() {
        def cie1 = new CompanyName("IMS HEALTH")
        def cie2 = new CompanyName("IMS HEALTH SA")

        assertEquals(Match.MATCH, matcher.match(cie1, cie2))
    }

    void testMatch_3() {
        def cie1 = new CompanyName("IMS HEALTH COMMUNICATION DIRECT")
        def cie2 = new CompanyName("IMS HEALTH SA")

        assertEquals(Match.UNMATCH, matcher.match(cie1, cie2))
    }

    void testMatch_4() {
        def cie1 = new CompanyName("")
        def cie2 = new CompanyName("IMS HEALTH SA")

        assertEquals(Match.EMPTY, matcher.match(cie1, cie2))
    }

    void testMatch_5() {
        def cie1 = new CompanyName("SERVICE COMPTABILITE")
        def cie2 = new CompanyName("IMS HEALTH SA")

        assertEquals(Match.EMPTY, matcher.match(cie1, cie2))
    }

    void testMatch_6() {
        def cie1 = new CompanyName("IMS HEALT")
        def cie2 = new CompanyName("IMS HEALTH SA")

        assertEquals(Match.MATCH, matcher.match(cie1, cie2))
    }

    void testMatch_7() {
        def cie1 = new CompanyName("BOULANGERIE")
        def cie2 = new CompanyName("BOULANGERIE DE LA MAIRIE")

        assertEquals(Match.UNMATCH, matcher.match(cie1, cie2))
    }

    void testMatch_8() {
        def cie1 = new CompanyName("BOULANGERIE DE MASSY PALAISEAU")
        def cie2 = new CompanyName("BOUCHERIE DE MASSY PALAISEAU")

        assertEquals(Match.UNMATCH, matcher.match(cie1, cie2))
    }

    void testMatch_9() {
        def cie1 = new CompanyName("LE CALUMET")
        def cie2 = new CompanyName("RESTAURANT LE CALUMET")

        assertEquals(Match.MATCH, matcher.match(cie1, cie2))
    }

    void testMatch_10() {
        def cie1 = new CompanyName("ROBERT DEMENAGEMENT")
        def cie2 = new CompanyName("ROBERT DEMENAGMENT ET FILS")

        assertEquals(Match.MATCH, matcher.match(cie1, cie2))
    }

    void testMatch_12() {
        def cie1 = new CompanyName("MASSEBEUF J")
        def cie2 = new CompanyName("MONSIEUR MASSEBEUF J")

        assertEquals(Match.MATCH, matcher.match(cie1, cie2))
    }

    void testMatch_13() {
        def cie1 = new CompanyName("MULTISERV SUD SA")
        def cie2 = new CompanyName("MULTISERV SUD SA SCA6")

        assertEquals(Match.MATCH, matcher.match(cie1, cie2))
    }

}
