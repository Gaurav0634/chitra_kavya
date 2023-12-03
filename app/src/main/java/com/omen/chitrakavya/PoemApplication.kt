package com.omen.chitrakavya


import android.app.Application
import com.omen.chitrakavya.api.PoemService
import com.omen.chitrakavya.api.RetrofitInstance
import com.omen.chitrakavya.db.PoemDatabase
import com.omen.chitrakavya.repository.PoemRepository

class PoemApplication : Application() {
    lateinit var poemRepository: PoemRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val poemService = RetrofitInstance.getRetrofitInstance().create(PoemService::class.java)
        val database = PoemDatabase.getDatabase(applicationContext)
        poemRepository = PoemRepository(poemService, database, applicationContext)

    }

}