package com.koushik.data.repository

import com.koushik.core.util.Constants
import com.koushik.data.api.ApiService
import com.koushik.data.api.BaseResponse
import com.koushik.data.model.ItemDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryTest {

    @MockK
    private lateinit var apiService: ApiService

    private lateinit var repository: NewsRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
        repository = NewsRepository(apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchItems should return success when API response is successful`() = runTest {
        // Arrange
        val itemDto = ItemDto("1", "Title", "Desc", "https://img.com")
        val responseBody = BaseResponse("success", listOf(itemDto))
        val response = Response.success(responseBody)

        coEvery { apiService.getItems(Constants.API_KEY) } returns response

        // Act
        val result = repository.fetchItems()

        // Assert
        assertTrue(result.isSuccess)
        val items = result.getOrNull()
        assertNotNull(items)
        assertEquals(1, items!!.size)
        assertEquals("1", items[0].articleId)
        assertEquals("Title", items[0].title)
    }

    @Test
    fun `fetchItems should return failure when API response is error`() = runTest {
        // Arrange
        val errorResponse = Response.error<BaseResponse<List<ItemDto>>>(
            500,
            ResponseBody.create(null, "Internal Server Error")
        )
        coEvery { apiService.getItems(Constants.API_KEY) } returns errorResponse

        // Act
        val result = repository.fetchItems()

        // Assert
        val error = result.exceptionOrNull()
        println("Error Message: ${error?.message}")
        assertTrue(result.isFailure)
        assertEquals("Response.error()", error?.message) // Adjust if you modify handler
    }

    @Test
    fun `fetchItems should return failure when API throws exception`() = runTest {
        // Arrange
        val exception = Exception("Network Failure")
        coEvery { apiService.getItems(Constants.API_KEY) } throws exception

        // Act
        val result = repository.fetchItems()

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Network Failure", result.exceptionOrNull()?.message)
    }
}