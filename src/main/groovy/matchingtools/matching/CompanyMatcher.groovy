package matchingtools.matching

import domain.Address
import domain.CompanyName
import domain.Match
import domain.Record
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by Christian Sperandio on 27/08/2016.
 */
class CompanyMatcher implements Matcher<Record> {

    private static Logger logger = LoggerFactory.getLogger(CompanyMatcher.class)

    private Siret
    Matcher siretMatcher = new SiretMatcher()
    private CityNameMatcher cityNameMatcher = new CityNameMatcher()
    private AddressMatcher addressMatcher= new AddressMatcher()
    private CompanyNameMatcher companyNameMatcher= new CompanyNameMatcher()

    @Override
    Match match(Record item1, Record item2) {

        logger.debug("Match record [$item1] vs [$item2]")

        if (siretMatcher.match(item1.siret, item2.siret) == Match.UNMATCH) { return Match.UNMATCH }

        logger.debug("Siret test passed")

        if (cityNameMatcher.match(item1.city, item2.city) == Match.UNMATCH) { return Match.UNMATCH }

        logger.debug("City test passed")

        if (compareAddress(item1.addresses, item2.addresses) != Match.MATCH) { return Match.UNMATCH }

        logger.debug("Address test passed")

        compareNames(item1.names, item2.names)
    }

    Match compareNames(List<CompanyName> companyNames1, List<CompanyName> companyNames2) {
        if (!companyNames1 || !companyNames2) { return Match.EMPTY }

        def combinations = [companyNames1, companyNames2].combinations()

        def comparisons = combinations.collect {
            def (name1, name2) = it
            def comparison = companyNameMatcher.match(name1, name2)
            [name1, name2, comparison]
        }.findAll { it[2] != Match.EMPTY }

        if (!comparisons) { return Match.UNMATCH }

        comparisons.any { it[2] == Match.MATCH } ? Match.MATCH : Match.UNMATCH
    }

    Match compareAddress(List<Address> addresses1, List<Address> addresses2) {
        def combinaisons = [addresses1, addresses2].combinations()

        def comparisons = combinaisons.collect {
            def (ad1, ad2) = it
            def comparison = addressMatcher.match(ad1, ad2)
            [ad1, ad2, comparison]
        }

        def allUnmMatch = comparisons.every { it[2] != Match.MATCH }
        if (allUnmMatch) { return Match.UNMATCH }


        def mainAddressComparisons =  comparisons.findAll {
            def (Address ad1, Address ad2, Match match) = it
            ad1.mainAddress || ad2.mainAddress
        }

        if (mainAddressComparisons) {
            mainAddressComparisons.every { it[2] == Match.MATCH} ? Match.MATCH : Match.UNMATCH
        }

        comparisons.any { it[2] == Match.MATCH } ? Match.MATCH : Match.UNMATCH
    }
}
