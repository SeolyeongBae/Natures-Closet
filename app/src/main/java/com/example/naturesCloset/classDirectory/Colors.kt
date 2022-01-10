package com.example.naturesCloset.classDirectory

import java.io.Serializable
import java.util.*

data class Colors(var palettename:String="",
                  var col1:String="",
                 var col2:String="",
                 var col3:String="",
                 var col4:String="",
                 var col5:String="",
                 var col6:String="",
                  var username : ArrayList<String> = arrayListOf()

) : Serializable