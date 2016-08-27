package matchingtools.matching

import domain.Match
import services.ExpressionsMatcher

/**
 * Created by Christian Sperandio on 27/08/2016.
 */
class CityNameMatcher implements Matcher<String> {
    private List articles = ["LE", "LA", "LES", "L", "DE", "DU", "DES", "D"]
    private List useless = [ "SUR", "SOUS", "SAINTE", "SAINT", "STE", "ST", "CEDEX" ]

    private ExpressionsMatcher expressionsMatcher

    CityNameMatcher() { this.expressionsMatcher = new ExpressionsMatcher(articles + useless, true)}

    @Override
    Match match(String item1, String item2) {

        def words1 = item1.tokenize().findAll { !it.isNumber()}
        def words2 = item2.tokenize().findAll { !it.isNumber()}

        expressionsMatcher.match(words1, words2)
    }

}
