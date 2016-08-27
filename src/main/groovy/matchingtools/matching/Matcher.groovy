package matchingtools.matching

import domain.Match

/**
 * Created by Christian Sperandio on 26/08/2016.
 */
interface Matcher<T> {
    Match match(T item1, T item2)
}