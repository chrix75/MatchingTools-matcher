package matchingtools.matching

import domain.Match

/**
 * Created by Christian Sperandio on 27/08/2016.
 */
class SiretMatcher implements Matcher<String> {

    @Override
    Match match(String item1, String item2) {
        if (!item1 || !item2) { return Match.EMPTY }

        item1.padLeft(14, '0') == item2.padLeft(14, '0') ? Match.MATCH : Match.UNMATCH
    }
}
