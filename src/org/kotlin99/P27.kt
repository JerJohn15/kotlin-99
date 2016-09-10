package org.kotlin99

import com.natpryce.hamkrest.anyElement
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

fun <T> group3(list: List<T>): List<List<List<T>>> =
    combinations(2, list).flatMap { listOfTwo ->
        val filteredList = list.filterNot { listOfTwo.contains(it) }
        combinations(3, filteredList).flatMap { listOfThree ->
            val filteredList2 = filteredList.filterNot { listOfThree.contains(it) }
            combinations(4, filteredList2).map{ listOf(it, listOfThree, listOfTwo) }
        }
    }

fun <T> group(sizes: List<Int>, list: List<T>): List<List<List<T>>> =
    if (sizes.isEmpty()) listOf(emptyList())
    else combinations(sizes.first(), list).flatMap { combination ->
            val filteredList = list.filterNot { combination.contains(it) }
            group(sizes.drop(1), filteredList).map{ it + listOf(combination) }
         }


class P27Test {
    @Test fun `a) group the elements into 3 disjoint subgroups of 2, 3 and 4`() {
        group3(listOf("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")).apply {
            forEach { println(it) }
            assertThat(this, anyElement(hasSameElementsAs(
                    listOf(listOf("Aldo", "Beat"), listOf("Carla", "David", "Evi"), listOf("Flip", "Gary", "Hugo", "Ida"))
            )))
            assertThat(size, equalTo(1260))
        }
    }

    @Test fun `b) group the elements into disjoint subgroups of specified sizes`() {
        group(listOf(2, 2, 5), listOf("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")).apply {
            forEach { println(it) }
            assertThat(this, anyElement(hasSameElementsAs(
                    listOf(listOf("Aldo", "Beat"), listOf("Carla", "David"), listOf("Evi", "Flip", "Gary", "Hugo", "Ida"))
            )))
            assertThat(size, equalTo(756))
        }
    }
}
