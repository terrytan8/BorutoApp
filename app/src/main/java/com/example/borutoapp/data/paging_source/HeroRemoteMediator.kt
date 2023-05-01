package com.example.borutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.borutoapp.data.local.BorutoDatabase
import com.example.borutoapp.data.remote.BorutoApi
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.model.HeroRemoteKey
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = borutoDatabase.heroDao()
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeysDao()

    private suspend fun getRemoteKeyClosetToCurrentPosition(state: PagingState<Int, Hero>): HeroRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Hero>): HeroRemoteKey? {

        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { hero ->
                heroRemoteKeysDao.getRemoteKeys(hero.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Hero>): HeroRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { hero ->
                heroRemoteKeysDao.getRemoteKeys(hero.id)
            }
    }

    /**1.Determine which page to load from network
     * 2. Trigger network request
     * 3. Perform Mediator result success or Error
     */
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Hero>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosetToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                /**start**/
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                /**Last**/
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }
            val response = borutoApi.getAllHeroes(page = page)
            if (response.heroes.isNotEmpty()) {
                /** from network to local DB**/
                borutoDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.heroes.map {
                        HeroRemoteKey(
                            id = it.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    /**Initialize**/
                    heroRemoteKeysDao.addAllRemoteKeys(keys)
                    heroDao.addHeroes(response.heroes)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }

    }
}