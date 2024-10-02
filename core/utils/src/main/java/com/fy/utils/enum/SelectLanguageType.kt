package com.fy.utils.enum

enum class SelectLanguageType(val value : Int) {
    SELECTED(1),
    SELECTABLE(0)
}

enum class LanguageType(val value : String,val parameterGroupId : String){
    TR("tr-TR","1"),
    ENG("en-US","2")
}