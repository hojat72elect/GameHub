rootProject.name = "Gamedge"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

include(":app")
include(":core")
include(":common-domain")
include(":common-data")
include(":common-ui")
include(":common-ui-widgets")
include(":common-api")
include(":common-testing-domain")
include(":common-testing")
include(":database")
include(":igdb-api")
include(":igdb-apicalypse")
include(":gamespot-api")
include(":feature-category")
include(":feature-likes")
include(":feature-settings")
