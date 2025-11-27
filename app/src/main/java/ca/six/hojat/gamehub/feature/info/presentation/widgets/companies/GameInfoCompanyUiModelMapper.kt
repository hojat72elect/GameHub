package ca.six.hojat.gamehub.feature.info.presentation.widgets.companies

import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.common.domain.games.entities.InvolvedCompany
import ca.six.hojat.gamehub.core.factories.IgdbImageExtension
import ca.six.hojat.gamehub.core.factories.IgdbImageSize
import ca.six.hojat.gamehub.core.factories.IgdbImageUrlFactory
import ca.six.hojat.gamehub.core.factories.IgdbImageUrlFactory.Config
import ca.six.hojat.gamehub.core.providers.StringProvider
import javax.inject.Inject

internal interface GameInfoCompanyUiModelMapper {
    fun mapToUiModel(company: InvolvedCompany): GameInfoCompanyUiModel
}

internal class GameInfoCompanyUiModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
    private val stringProvider: StringProvider,
) : GameInfoCompanyUiModelMapper {

    private companion object {
        private const val COMPANY_ROLE_SEPARATOR = ", "
    }

    override fun mapToUiModel(company: InvolvedCompany): GameInfoCompanyUiModel {
        return GameInfoCompanyUiModel(
            id = company.company.id,
            logoUrl = company.createLogoUrl(),
            logoWidth = company.company.logo?.width,
            logoHeight = company.company.logo?.height,
            websiteUrl = company.company.websiteUrl,
            name = company.company.name,
            roles = company.createRolesString(),
        )
    }

    private fun InvolvedCompany.createLogoUrl(): String? {
        return company.logo?.let { image ->
            igdbImageUrlFactory.createUrl(image, Config(IgdbImageSize.HD, IgdbImageExtension.PNG))
        }
    }

    private fun InvolvedCompany.createRolesString(): String {
        return buildList {
            if (isDeveloper) add(R.string.company_role_developer)
            if (isPublisher) add(R.string.company_role_publisher)
            if (isPorter) add(R.string.company_role_porter)
            if (isSupporting) add(R.string.company_role_supporting)
        }
            .joinToString(
                separator = COMPANY_ROLE_SEPARATOR,
                transform = stringProvider::getString,
            )
    }
}

internal fun GameInfoCompanyUiModelMapper.mapToUiModels(
    companies: List<InvolvedCompany>,
): List<GameInfoCompanyUiModel> {
    if (companies.isEmpty()) return emptyList()

    val comparator = compareByDescending(InvolvedCompany::isDeveloper)
        .thenByDescending(InvolvedCompany::isPublisher)
        .thenByDescending(InvolvedCompany::isPorter)
        .thenByDescending { it.company.hasLogo }

    return companies
        .sortedWith(comparator)
        .map(::mapToUiModel)
}
