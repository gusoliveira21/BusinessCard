package com.gusoliveira21.businesscard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO 1: classe responsável por passar para o banco as informações que queremos gravar

//TODO 2: para integrar a entidade ao Room, é necessário usar a tag @entity pacote androidx.room.entity
//TODO 3: Agora é necessário criar um Dao, que será onde vai ser realizada a persistência no banco (BusinessCardDao)

@Entity
data class BusinessCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome:String,
    val empresa:String,
    val telefone:String,
    val email:String,
    val fundoPersonalizado:String
){

}