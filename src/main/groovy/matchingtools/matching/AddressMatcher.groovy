package matchingtools.matching

import domain.Address
import domain.Match
import services.ExpressionsMatcher
/**
 * Created by Christian Sperandio on 26/08/2016.
 */
class AddressMatcher implements Matcher<Address> {
    
    private Set zoneWays = ["ZI", "ZA"]
    
    private Set streeWays = ["RUE","BD","AV","GR","PL", "PR",
                            "COURS","CHEMIN",
                            "ALLEE","ALLEES","CHEMIN","LOTISSEMENT","LOT", "SQ","ROUTE",
                            "LD","IMPASSE","PARC","RUELLE","PROMENADE",
                            "QUAI","VENELLE","CLOS","ROND POINT","PASSAGE","SENTE","RESIDENCE",
                            "QUARTIER","MONTEE","TRAVERSE","ESPLANADE","CITE"]

    private List articles = ["LE", "LA", "LES", "L", "DE", "DU", "DES", "D"]
    private List useless = [ "ST", "SAINT", "SAINTE" ]

    private ExpressionsMatcher expressionsMatcher

    AddressMatcher() { this.expressionsMatcher= new ExpressionsMatcher(articles + useless)}

    @Override
    Match match(Address item1, Address item2) {
        if (isCompatible(item1, item2)) {
            return compareAddress(item1, item2)
        } else {
            return Match.EMPTY
        }
    }

    Match compareAddress(Address item1, Address item2) {
        def postBoxMatch = comparePostBox(item1.postBox, item2.postBox)
        if (postBoxMatch == Match.UNMATCH) { return Match.UNMATCH }
        if (postBoxMatch == Match.MATCH) { return Match.MATCH }

        if (!item1.name && !item2.name && item1.number > 0) {
            return (item1.way == item2.way) && (item1.number == item2.number) ? Match.MATCH : Match.UNMATCH
        }

        if (compareStreetNumber(item1.number, item2.number) == Match.UNMATCH) {
            return Match.UNMATCH
        }

        //TODO Process the road number

        compareStreetName(item1.name, item2.name)
    }

    Match compareStreetName(String name1, String name2) {
        def words1 = name1.tokenize()
        def words2 = name2.tokenize()

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

        return complete
    }

    Match comparePostBox(int pb1, int pb2) {
        if (pb1 == 0 || pb2 == 0) { return Match.EMPTY }

        if (pb1 == pb2) { return Match.MATCH}
        else { return Match.UNMATCH }
    }

    Match compareStreetNumber(int n1, int n2) {
        // checks 2 numbers are in the same walk side
        if (n1 % 2 != n2 % 2) {
            return Match.UNMATCH
        }

        if (n1 == 0 || n2 == 0) {
            return Match.EMPTY
        }

        int diff = n1 - n2
        int diff2 = diff * diff

        if (diff2 < 400) {
            return Match.MATCH
        } else {
            return Match.UNMATCH
        }
    }

    boolean isCompatible(Address item1, Address item2) {
        def ways = [item1, item2]*.way
        allEmpty(ways) || allZone(ways) || allStreet(ways)
    }

    boolean allEmpty(ArrayList<String> ways) {
            ways.every { !it }
    }

    boolean allStreet(List<String> ways) {
        streeWays.containsAll(ways)
    }

    boolean allZone(List<String> ways) {
        zoneWays.containsAll(ways)
    }
}
