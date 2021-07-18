package com.gusoliveira21.businesscard.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//Todo 4: colocar a tag Dao do room
@Dao
interface BusinessCardDao {
    //TODO 5: criar as funções getAll e insert
    //TODO 6: Inserir as tags do room nas funções getAll e insert

    @Query("SELECT * FROM BUSINESSCARD") // Nesse caso, a entidade BusinessCard.class foi mapeada
    fun getAll(): LiveData<List<BusinessCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(businessCard: BusinessCard)
}