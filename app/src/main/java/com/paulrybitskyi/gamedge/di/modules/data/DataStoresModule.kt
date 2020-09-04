/*
 * Copyright 2020 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paulrybitskyi.gamedge.di.modules.data

import android.content.Context
import com.paulrybitskyi.gamedge.cache.GamesCacheDataStoreFactory
import com.paulrybitskyi.gamedge.data.datastores.GamesDataStore
import com.paulrybitskyi.gamedge.database.datastore.GamesDatabaseDataStoreFactory
import com.paulrybitskyi.gamedge.di.qualifiers.DataStore
import com.paulrybitskyi.gamedge.igdb.api.datastore.GamesServerDataStoreFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal object DataStoresModule {


    @Singleton
    @DataStore(DataStore.Type.SERVER)
    @Provides
    fun provideGamesServerDataStore(): GamesDataStore {
        return GamesServerDataStoreFactory.create()
    }


    @Singleton
    @DataStore(DataStore.Type.DATABASE)
    @Provides
    fun provideGamesDatabaseDataStore(@ApplicationContext context: Context): GamesDataStore {
        return GamesDatabaseDataStoreFactory.create(context)
    }


    @Singleton
    @DataStore(DataStore.Type.CACHE)
    @Provides
    fun provideGamesCacheServerDataStore(): GamesDataStore {
        return GamesCacheDataStoreFactory.create()
    }


}