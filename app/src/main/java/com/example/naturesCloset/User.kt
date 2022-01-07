package com.example.naturesCloset

import java.util.*

data class User(val id:String="",
                val phNum:String="",
                val username:String="",
                val active:Boolean=true,
                var admin:Boolean=false,
                val infect:Int=0,
                val createdAt: Date=Date(),
                val updatedAt: Date=Date()

)