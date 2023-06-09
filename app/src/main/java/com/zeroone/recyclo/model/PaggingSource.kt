package com.zeroone.recyclo.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zeroone.recyclo.api.ApiInterface
import com.zeroone.recyclo.api.response.DataItem
import kotlinx.coroutines.flow.first
import java.lang.Exception

class PaggingSource(private val apiService: ApiInterface, private val authentication: SessionPreference) : PagingSource<Int, DataItem>(){

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try{
            val position = params.key ?: PAGE
            val token = authentication.getUser().first().token
            if (token.isNotEmpty()){
                val responseData = apiService.paggingGoods("Bearer $token")
                if (responseData.isSuccessful) {

                    LoadResult.Page(
                        data = responseData.body()?.data ?: emptyList(),
                        prevKey = if (position == PAGE) null else position - 1,
                        nextKey = if (responseData.body()?.data.isNullOrEmpty()) null else position + 1
                    )
                } else {

                    LoadResult.Error(Exception("Failed"))
                }
            }else{
                LoadResult.Error(Exception("Failed"))
            }
        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }
    private companion object{
        const val PAGE = 1
    }

}