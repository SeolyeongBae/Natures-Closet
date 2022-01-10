package com.example.naturesCloset.classDirectory

import java.io.Serializable
import java.util.*

data class Colors(var palettename:String="",
                  var color1:String="",
                 var color2:String="",
                 var color3:String="",
                 var color4:String="",
                 var color5:String="",
                 var color6:String="",
                  var username : ArrayList<String> = arrayListOf()

) : Serializable