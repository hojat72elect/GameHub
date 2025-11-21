package com.paulrybitskyi.gamedge.feature.news.data

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.feature.news.data.datastores.gamespot.ArticlePublicationDateMapper
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.time.format.DateTimeParseException

internal class ArticlePublicationDateMapperTest {

    private lateinit var SUT: ArticlePublicationDateMapper

    @Before
    fun setup() {
        SUT = ArticlePublicationDateMapper()
    }

    @Test
    fun `Maps date successfully`() {
        assertThat(SUT.mapToTimestamp("2020-03-02 14:30:16")).isEqualTo(1583188216000L)
    }

    @Test
    fun `Throws exception when providing empty date`() {
        assertThrows(DateTimeParseException::class.java) {
            SUT.mapToTimestamp("")
        }
    }

    @Test
    fun `Throws exception when providing blank date`() {
        assertThrows(DateTimeParseException::class.java) {
            SUT.mapToTimestamp("   ")
        }
    }
}
