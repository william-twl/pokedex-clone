package com.william.pokedex_clone

import com.william.pokedex_clone.model.GeneralObject
import com.william.pokedex_clone.utils.returnId
import com.william.pokedex_clone.utils.toCapitalise
import org.junit.Assert
import org.junit.Test

class TextUtilsUnitTest {
    @Test
    fun testArrayListToCapitalise() {
        val originalArrayList = ArrayList<GeneralObject?>()
        originalArrayList.add((GeneralObject("charizard", "https://pokeapi.co/api/v2/pokemon/6/")))
        originalArrayList.add(GeneralObject("metapod", "https://pokeapi.co/api/v2/pokemon/11/"))
        originalArrayList.add(GeneralObject("pidgey", "https://pokeapi.co/api/v2/pokemon/16/"))

        val actualArrayList = originalArrayList.toCapitalise()

        val expectedArrayList = ArrayList<GeneralObject?>()
        expectedArrayList.add((GeneralObject("Charizard", "https://pokeapi.co/api/v2/pokemon/6/")))
        expectedArrayList.add(GeneralObject("Metapod", "https://pokeapi.co/api/v2/pokemon/11/"))
        expectedArrayList.add(GeneralObject("Pidgey", "https://pokeapi.co/api/v2/pokemon/16/"))

        Assert.assertEquals(expectedArrayList, actualArrayList)
    }

    @Test
    fun testUrlStringReturnId() {
        val originalString = "https://pokeapi.co/api/v2/pokemon/6/"
        val actualString = originalString.returnId()
        val expectedString = "6"

        Assert.assertEquals(expectedString, actualString)
    }

    @Test
    fun testNonUrlStringReturnId() {
        val originalString = "Random String"
        val actualString = originalString.returnId()
        val expectedString = "0"

        Assert.assertEquals(expectedString, actualString)
    }

    @Test
    fun testShortNonUrlStringReturnId() {
        val originalString = "short"
        val actualString = originalString.returnId()
        val expectedString = ""

        Assert.assertEquals(expectedString, actualString)
    }
}