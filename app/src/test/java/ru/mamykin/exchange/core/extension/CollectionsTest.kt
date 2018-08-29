package ru.mamykin.exchange.core.extension

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CollectionsTest {

    @Test
    fun skip_shouldReturnEmptySublist_whenListIsEmpty() {
        val result = emptyList<Int>().skip(1)

        assertThat(result, `is`(emptyList()))
    }

    @Test
    fun skip_shouldReturnEmptyList_whenListContainsSingleItem() {
        val result = listOf(1).skip(1)

        assertThat(result, `is`(emptyList()))
    }

    @Test
    fun skip_shouldReturnSublist_whenListContainsManyItems() {
        val result = listOf(1, 2, 3).skip(1)

        assertThat(result, `is`(listOf(2, 3)))
    }
}