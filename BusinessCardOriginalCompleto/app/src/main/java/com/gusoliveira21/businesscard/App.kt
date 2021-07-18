package com.gusoliveira21.businesscard

import android.app.Application
import com.gusoliveira21.businesscard.data.AppDatabase
import com.gusoliveira21.businesscard.data.BusinessCardRepository

class App:Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BusinessCardRepository(database.businessDao()) }
}