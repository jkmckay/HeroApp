package com.example.heroapp.heroList

import com.example.heroapp.herolist.data.model.HeroResponseItem
import com.example.heroapp.herolist.data.service.HeroService
import com.example.heroapp.herolist.domain.model.HeroItem
import com.example.heroapp.herolist.domain.repository.HeroRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HeroListRepositoryTest {

    private val service: HeroService = mockk(relaxed = true)
    private lateinit var repo: HeroRepository
    private val dummyHeroResponseItem = HeroResponseItem(
        appearance = HeroResponseItem.Appearance(
            "blue",
            "male",
            "brown",
            height = emptyList(),
            race = null,
            weight = emptyList()
        ),
        HeroResponseItem.Biography(emptyList(), "", "", "", "", "", ""),
        HeroResponseItem.Connections("", ""),
        0,
        HeroResponseItem.Images("", "", "", ""),
        "",
        HeroResponseItem.Powerstats(0, 0, 0, 0, 0, 0),
        "",
        HeroResponseItem.Work("", "")
    )

    @Before
    fun setup() {
        repo = HeroRepository(service)
    }

    @Test
    fun `GIVEN getHeroes is invoked WHEN service emits a successful response THEN heroFlow returns Result#Success`() =
        runBlocking {
            val successResponse =
                listOf(dummyHeroResponseItem, dummyHeroResponseItem, dummyHeroResponseItem)

            coEvery { service.getHeroes() } returns successResponse
            repo.getHeroes()

            assert(repo.heroFlow.first().isSuccess)
        }

    @Test
    fun `GIVEN getHeroes is invoked WHEN service emits a empty response THEN heroFlow returns Result#Success`() =
        runBlocking {
            val emptyResponse = listOf<HeroResponseItem>()

            coEvery { service.getHeroes() } returns emptyResponse
            repo.getHeroes()

            assert(repo.heroFlow.first().isSuccess)
        }

    @Test
    fun `GIVEN getHeroes is invoked WHEN service emits a error response THEN heroFlow returns Result#Failure`() =
        runBlocking {
            val exception = Exception()

            coEvery { service.getHeroes() } throws exception
            repo.getHeroes()

            assert(repo.heroFlow.first().isFailure)
        }

    @Test
    fun `GIVEN getHeroes is invoked WHEN mapping HeroItemResponse to HeroItem THEN heroFlow returns expected Flow#Result#HeroItem`() =
        runBlocking {
            val successResponse = listOf(
                dummyHeroResponseItem.copy(name = "Superman"),
                dummyHeroResponseItem.copy(name = "Batman"),
                dummyHeroResponseItem.copy(name = "Goku")
            )

            coEvery { service.getHeroes() } returns successResponse
            repo.getHeroes()

            val expected = Result.success(
                successResponse.map {
                    HeroItem(
                        id = it.id,
                        name = it.name,
                        fullName = it.biography.fullName,
                        listImage = it.images.sm,
                        detailImage = it.images.lg,
                        firstAppearance = it.biography.firstAppearance,
                        publisher = it.biography.publisher
                    )
                }
            )

            val actual = repo.heroFlow.first()

            assert(expected == actual)
            assert(expected.getOrNull()?.get(0)?.name == "Superman")
            assert(expected.getOrNull()?.get(1)?.name == "Batman")
            assert(expected.getOrNull()?.get(2)?.name == "Goku")
        }
}
