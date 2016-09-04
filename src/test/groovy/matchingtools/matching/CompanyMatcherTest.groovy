package matchingtools.matching

import domain.Address
import domain.CompanyName
import domain.Match
import domain.Record

/**
 * Created by Christian Sperandio on 27/08/2016.
 */
class CompanyMatcherTest extends GroovyTestCase {

    CompanyMatcher matcher

    Address aguesseau128
    Address aguesseau122
    Address aguesseau137
    Address reine
    Address rendezVous
    Address saintMande

    String paris
    String boulogne

    CompanyName ims
    CompanyName imsCommunicationDirect
    CompanyName cegedim

    String siret1
    String siret2

    @Override
    protected void setUp() throws Exception {
        matcher = new CompanyMatcher()

        aguesseau122 = new Address("122", "RUE", "AGUESSEAU")
        aguesseau128 = new Address("128", "RUE", "AGUESSEAU")
        aguesseau137 = new Address("137", "RUE", "AGUESSEAU")
        reine = new Address("", "ROUTE", "REINE")

        paris = "PARIS"
        boulogne = "BOULOGNE"

        ims = new CompanyName("IMS HEALTH SAS")
        imsCommunicationDirect = new CompanyName("IMS HEALTH COMMUNICATION DIRECT")
        cegedim = new CompanyName("CEGEDIM SA")

        siret1 = "123456"
        siret2 = "98765544"
    }

    void testMatch_1() {
        def record1 = new Record(1, [ims], [aguesseau128], "", boulogne)
        def record2 = new Record(2, [ims], [aguesseau128], "", boulogne)

        assertEquals(Match.MATCH, matcher.match(record1, record2))
    }

    void testMatch_2() {
        def record1 = new Record(1, [ims], [aguesseau122], "", boulogne)
        def record2 = new Record(2, [ims], [aguesseau128], "", boulogne)

        assertEquals(Match.MATCH, matcher.match(record1, record2))
    }

    void testMatch_3() {
        def record1 = new Record(1, [ims], [aguesseau137], "", boulogne)
        def record2 = new Record(2, [ims], [aguesseau128], "", boulogne)

        assertEquals(Match.UNMATCH, matcher.match(record1, record2))
    }

    void testMatch_4() {
        def record1 = new Record(1, [ims], [aguesseau128], "", boulogne)
        def record2 = new Record(2, [cegedim], [aguesseau128], "", boulogne)

        assertEquals(Match.UNMATCH, matcher.match(record1, record2))
    }

    void testMatch_5() {
        def record1 = new Record(3, [ims], [aguesseau128], "", boulogne)
        def record2 = new Record(4, [ims], [aguesseau128, reine], "", boulogne)

        assertEquals(Match.MATCH, matcher.match(record1, record2))
    }

    void testMatch_6() {
        def record1 = new Record(5, [ims], [aguesseau128], "", boulogne)
        def record2 = new Record(6, [ims], [reine], "", boulogne)

        assertEquals(Match.UNMATCH, matcher.match(record1, record2))
    }

    void testMatch_7() {
        def record1 = new Record(1, [ims], [aguesseau128], "", boulogne)
        def record2 = new Record(2, [ims], [aguesseau128], "", paris)

        assertEquals(Match.UNMATCH, matcher.match(record1, record2))
    }

    void testMatch_8() {
        def record1 = new Record(1, [ims], [aguesseau128], siret1, boulogne)
        def record2 = new Record(2, [ims], [aguesseau128], siret2, boulogne)

        assertEquals(Match.UNMATCH, matcher.match(record1, record2))
    }

    void testMatch_9() {
        def record1 = new Record(1, [ims], [reine], siret1, boulogne)
        def record2 = new Record(2, [ims], [aguesseau128], siret1, boulogne)

        assertEquals(Match.UNMATCH, matcher.match(record1, record2))
    }

    void testMatch_10() {
        def record1 = new Record(1, [ims], [aguesseau128], "", boulogne)
        def record2 = new Record(2, [imsCommunicationDirect], [aguesseau128], "", boulogne)

        assertEquals(Match.UNMATCH, matcher.match(record1, record2))
    }


}
