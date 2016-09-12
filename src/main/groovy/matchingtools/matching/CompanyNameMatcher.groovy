package matchingtools.matching

import domain.CompanyName
import domain.Match
import services.ExpressionsMatcher
/**
 * Created by Christian Sperandio on 26/08/2016.
 */
class CompanyNameMatcher implements Matcher<CompanyName> {
    private List articles = ["LE", "LA", "LES", "L", "DE", "DU", "DES", "D"]
    private List extensions = [ "SA", "SAS", "SARL", "EURL", "GMBH", "GROUPE", "GROUP", "GRP" ]
    private List useless = [ "ST", "SAINT", "SAINTE", "CHEZ", "ET",
                             "MONSIEUR", "MADAME", "MME", "MADEMOISELLE",
                             "MLE", "MLLE", "MELLE", "MR" ]

    private ExpressionsMatcher expressionsMatcher

    CompanyNameMatcher() { expressionsMatcher = new ExpressionsMatcher(articles + extensions + useless)}


    @Override
    Match match(CompanyName item1, CompanyName item2) {
        if (item1.isService || item2.isService) { return Match.EMPTY }

        def words1 = item1.wordsName.findAll { it.size() > 1}
        def words2 = item2.wordsName.findAll { it.size() > 1}

        def complete = expressionsMatcher.match(words1, words2)
        if (complete == Match.MATCH) { return complete }
        if (words1.size() == 1 || words2.size() == 1) { return complete }

        if (words1.size() > 1) {
            def wordsMinusBeginning1 = words1[1..-1]
            if (expressionsMatcher.match(wordsMinusBeginning1, words2) == Match.MATCH) {
                return Match.MATCH
            }
        }

        if (words2.size() > 1) {
            def wordsMinusBeginning2 = words2[1..-1]
            if (expressionsMatcher.match(words1, wordsMinusBeginning2) == Match.MATCH) {
                return Match.MATCH
            }
        }

        if (words1.size() > 1) {
            def wordsMinusEnd1 = words1[0..-2]
            if (expressionsMatcher.match(wordsMinusEnd1, words2) == Match.MATCH) {
                return Match.MATCH
            }
        }

        if (words2.size() > 1) {
            def wordsMinusEnd2 = words2[0..-2]
            if (expressionsMatcher.match(words1, wordsMinusEnd2) == Match.MATCH) {
                return Match.MATCH
            }
        }

        return complete

    }
}
