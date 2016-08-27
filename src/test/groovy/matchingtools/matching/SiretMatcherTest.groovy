package matchingtools.matching

import domain.Match

/**
 * Created by Christian Sperandio on 27/08/2016.
 */
class SiretMatcherTest extends GroovyTestCase {
    SiretMatcher matcher

    @Override
    protected void setUp() throws Exception {
        matcher = new SiretMatcher()
    }

    void testMatch_1() {
        assertEquals(Match.MATCH, matcher.match("1234", "1234"))
    }

    void testMatch_2() {
        assertEquals(Match.EMPTY, matcher.match("", "1234"))
    }

    void testMatch_3() {
        assertEquals(Match.MATCH, matcher.match("001234", "1234"))
    }

    void testMatch_4() {
        assertEquals(Match.UNMATCH, matcher.match("001234", "456"))
    }
}
