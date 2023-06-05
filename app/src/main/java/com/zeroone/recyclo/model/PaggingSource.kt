package com.zeroone.recyclo.model

import androidx.paging.PagingSource
import com.zeroone.recyclo.api.ApiInterface
import java.lang.Exception
//
//class PaggingSource(private val apiService: ApiInterface, private val authentication: SessionPreference) : PagingSource<Int, ListStoryItem>(){
//
//    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
//        return state.anchorPosition?.let {
//            val anchorPage = state.closestPageToPosition(it)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
//        return try{
//            val position = params.key ?: PAGE
//            val token = authentication.getUser().first().token
//            if (token.isNotEmpty()){
//                val responseData = apiService.getStories("Bearer $token",position, params.loadSize)
//                if (responseData.isSuccessful) {
//
//                    LoadResult.Page(
//                        data = responseData.body()?.listStory ?: emptyList(),
//                        prevKey = if (position == PAGE) null else position - 1,
//                        nextKey = if (responseData.body()?.listStory.isNullOrEmpty()) null else position + 1
//                    )
//                } else {
//
//                    LoadResult.Error(Exception("Failed"))
//                }
//            }else{
//                LoadResult.Error(Exception("Failed"))
//            }
//        }catch (e: Exception){
//            return LoadResult.Error(e)
//        }
//    }
//    private companion object{
//        const val PAGE = 1
//    }
//
//}