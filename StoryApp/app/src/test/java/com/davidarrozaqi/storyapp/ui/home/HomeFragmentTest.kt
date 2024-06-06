package com.davidarrozaqi.storyapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.davidarrozaqi.storyapp.adapter.StoryAdapter
import com.davidarrozaqi.storyapp.data.dto.story.StoryResponse
import com.davidarrozaqi.storyapp.data.repository.StoryRepository
import com.davidarrozaqi.storyapp.utils.DataDummy
import com.davidarrozaqi.storyapp.utils.MainDispatcherRule
import com.davidarrozaqi.storyapp.utils.PreferenceManager
import com.davidarrozaqi.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeFragmentTest : KoinTest {
    @get:Rule
    val instantExecuteRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: StoryRepository

    @Mock
    private lateinit var pref: PreferenceManager

    @Test
    fun `when Get Story should not null and return data`() = runTest {
        val dummyStory = DataDummy.generateDummyStory()
        val data: PagingData<StoryResponse> = HomePagingSourceTest.snapshot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<StoryResponse>>()
        Mockito.`when`(repository.getAllStories(3)).thenReturn(expectedStory)
        expectedStory.value = data

        val homeViewModel = HomeViewModel(repository, pref)
        val actualStory: PagingData<StoryResponse> = homeViewModel.storyResult.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0], differ.snapshot()[0])
    }

    @Test
    fun `when get story empty should return no data`() = runTest {
        val data: PagingData<StoryResponse> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<StoryResponse>>()
        expectedStory.value = data
        Mockito.`when`(repository.getAllStories(3)).thenReturn(expectedStory)
        val homeViewModel = HomeViewModel(repository, pref)
        val actualStory: PagingData<StoryResponse> = homeViewModel.storyResult.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)
        Assert.assertEquals(0, differ.snapshot().size)
    }
}

val listUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {
    }

    override fun onRemoved(position: Int, count: Int) {

    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {

    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
    }
}