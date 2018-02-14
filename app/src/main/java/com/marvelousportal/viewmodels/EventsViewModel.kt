package com.marvelousportal.viewmodels

import android.util.Log
import com.marvelousportal.models.Model
import com.marvelousportal.network.ErrorHandler.getErrorCode
import com.marvelousportal.network.ErrorHandler.getErrorMessage
import com.marvelousportal.repository.MarvelRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class EventsViewModel(private val marvelRepository: MarvelRepository) {
    fun getEvents(): Observable<Model>? {
        return marvelRepository.getEvents()?.debounce(400, TimeUnit.MILLISECONDS)?.map {
            Log.d("Success", "Mapping characters to UIData...")

            Model(it.code, it.status, it.copyright, it.attributionText, it.attributionHTML, it.etag, it.data)
        }?.onErrorReturn {
                    Log.d("Error", it.localizedMessage)
                    Model(getErrorCode(it), getErrorMessage(it), null, null, null, null, null)
                }
    }

    fun getSearchedEvents(query: String): Observable<Model>? {
        return marvelRepository.getSearchedEvents(query)?.debounce(400, TimeUnit.MILLISECONDS)?.map {
            Log.d("Success", "Mapping characters to UIData...")

            Model(it.code, it.status, it.copyright, it.attributionText, it.attributionHTML, it.etag, it.data)
        }?.onErrorReturn {
                    Log.d("Error", it.localizedMessage)
                    Model(getErrorCode(it), getErrorMessage(it), null, null, null, null, null)
                }
    }
}