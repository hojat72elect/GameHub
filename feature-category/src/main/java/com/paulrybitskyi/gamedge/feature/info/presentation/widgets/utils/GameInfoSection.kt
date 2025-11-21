
package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.paulrybitskyi.gamedge.common.ui.theme.GamedgeTheme
import com.paulrybitskyi.gamedge.common.ui.widgets.GamedgeCard

@Composable
internal fun GameInfoSection(
    title: String,
    modifier: Modifier = Modifier,
    titleBottomPadding: Dp = GamedgeTheme.spaces.spacing_2_5,
    content: @Composable ColumnScope.(PaddingValues) -> Unit,
) {
    GamedgeCard(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        val contentPadding = GamedgeTheme.spaces.spacing_3_5

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = contentPadding),
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .padding(horizontal = contentPadding)
                    .padding(bottom = titleBottomPadding),
                color = GamedgeTheme.colors.onPrimary,
                style = GamedgeTheme.typography.h6,
            )

            content(PaddingValues(horizontal = contentPadding))
        }
    }
}

@Composable
internal fun GameInfoSectionWithInnerList(
    title: String,
    content: LazyListScope.() -> Unit,
) {
    GameInfoSection(title = title) { paddingValues ->
        LazyRow(
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(GamedgeTheme.spaces.spacing_1_5),
            content = content,
        )
    }
}
