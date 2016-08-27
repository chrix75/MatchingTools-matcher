package matchingtools.matching

import domain.Match

/**
 * Created by Christian Sperandio on 27/08/2016.
 */
class CityNameMatcherTest extends GroovyTestCase {
    CityNameMatcher matcher

    @Override
    protected void setUp() throws Exception {
        matcher = new CityNameMatcher()
    }

    void testMatch_1() {
        def city1 = "PARIS"
        def city2 = "PARIS"

        assertEquals(Match.MATCH, matcher.match(city1, city2))
    }

    void testMatch_2() {
        def city1 = "PARIS CEDEX"
        def city2 = "PARIS CEDEX 2"

        assertEquals(Match.MATCH, matcher.match(city1, city2))
    }

    void testMatch_3() {
        def city1 = "PARIS"
        def city2 = "PARIS CEDEX 2"

        assertEquals(Match.MATCH, matcher.match(city1, city2))
    }

    void testMatch_4() {
        def city1 = "AULNAY SOUS BOIS"
        def city2 = "BOIS SUR AULNAY"

        assertEquals(Match.UNMATCH, matcher.match(city1, city2))
    }
}
