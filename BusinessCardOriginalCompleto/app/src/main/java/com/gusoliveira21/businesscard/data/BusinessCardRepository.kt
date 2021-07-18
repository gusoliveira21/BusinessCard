package com.gusoliveira21.businesscard.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//TODO 7: Criar um arquivo Repository chamado BusinessCardRepository que será responsável por chamar o Dao
//TODO 8: Realizar as implementações da classe BusinessCardRepository e das funções insert e getAll

class BusinessCardRepository(private val dao: BusinessCardDao) {

    fun insert(businessCard: BusinessCard) = runBlocking {
        launch(Dispatchers.IO) {
            dao.insert(businessCard)
        }
    }

    fun getAll() = dao.getAll()

}

//TODO 9: Criar o AppDatabase - é uma classe que gerencia o banco de dados