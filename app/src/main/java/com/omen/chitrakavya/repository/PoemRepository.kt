package com.omen.chitrakavya.repository


import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.omen.chitrakavya.Utils.NetworkUtils
import com.omen.chitrakavya.api.PoemService
import com.omen.chitrakavya.db.PoemDatabase
import com.omen.chitrakavya.models.PoemListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PoemRepository(
    private val poemService: PoemService,
    private val poemDatabase: PoemDatabase,
    private val applicationContext: Context
) {

    private val poemsLiveData = MutableLiveData<List<PoemListItem>?>()

    val poems: MutableLiveData<List<PoemListItem>?>
        get() = poemsLiveData

    suspend fun getPoems(author: String, title: String) {
        withContext(Dispatchers.IO) {
            if (NetworkUtils.isInternetAvailable(applicationContext)) {
                val response = poemService.getPoems(author, title)
                if (response.isSuccessful) {
                    val poemList = response.body()
                    poemList?.let {
                        poemDatabase.poemDao().addPoems(it)
                        withContext(Dispatchers.Main) {
                            // Update LiveData on the main thread
                            poemsLiveData.value = it
                        }
                    }
                }
            } else {
                // Show toast on the main thread
                withContext(Dispatchers.Main) {
                    showToast("Please connect to the internet")
                }

                // Fetch stored poems from the database
                val poems = poemDatabase.poemDao().getPoems()
                withContext(Dispatchers.Main) {
                    // Update LiveData on the main thread
                    poemsLiveData.value = poems
                }
            }
        }
    }

    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
