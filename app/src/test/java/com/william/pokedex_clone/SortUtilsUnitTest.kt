package com.william.pokedex_clone

import com.william.pokedex_clone.model.GeneralObject
import com.william.pokedex_clone.utils.assignIds
import com.william.pokedex_clone.utils.sortById
import com.william.pokedex_clone.utils.sortByNameAscending
import com.william.pokedex_clone.utils.sortByNameDescending
import org.junit.Assert
import org.junit.Test

class SortUtilsUnitTest {
    @Test
    fun testArrayListAssignId() {
        val originalArrayList = ArrayList<GeneralObject?>()
        originalArrayList.add(GeneralObject("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
        originalArrayList.add(GeneralObject("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/"))
        originalArrayList.add(GeneralObject("venusaur", "https://pokeapi.co/api/v2/pokemon/3/"))

        val actualArrayList = originalArrayList.assignIds()

        val expectedArrayList = ArrayList<GeneralObject?>()
        expectedArrayList.add(GeneralObject("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/", 0))
        expectedArrayList.add(GeneralObject("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/", 1))
        expectedArrayList.add(GeneralObject("venusaur", "https://pokeapi.co/api/v2/pokemon/3/", 2))

        Assert.assertEquals(expectedArrayList, actualArrayList)
    }

    @Test
    fun testArrayListSortById() {
        val originalArrayList = ArrayList<GeneralObject?>()
        originalArrayList.add(GeneralObject("venusaur", "https://pokeapi.co/api/v2/pokemon/3/", 2))
        originalArrayList.add(GeneralObject("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/", 0))
        originalArrayList.add(GeneralObject("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/", 1))

        val actualArrayList = originalArrayList.sortById()

        val expectedArrayList = ArrayList<GeneralObject?>()
        expectedArrayList.add(GeneralObject("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/", 0))
        expectedArrayList.add(GeneralObject("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/", 1))
        expectedArrayList.add(GeneralObject("venusaur", "https://pokeapi.co/api/v2/pokemon/3/", 2))

        Assert.assertEquals(expectedArrayList, actualArrayList)
    }

    @Test
    fun testArrayListSortByNameAscending() {
        val originalArrayList = ArrayList<GeneralObject?>()
        originalArrayList.add(GeneralObject("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
        originalArrayList.add(GeneralObject("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/"))
        originalArrayList.add(GeneralObject("venusaur", "https://pokeapi.co/api/v2/pokemon/3/"))
        originalArrayList.add(GeneralObject("charmander", "https://pokeapi.co/api/v2/pokemon/4/"))
        originalArrayList.add(GeneralObject("charmeleon", "https://pokeapi.co/api/v2/pokemon/5/"))
        originalArrayList.add(GeneralObject("charizard", "https://pokeapi.co/api/v2/pokemon/6/"))

        val actualArrayList = originalArrayList.sortByNameAscending()

        val expectedArrayList = ArrayList<GeneralObject?>()
        expectedArrayList.add(GeneralObject("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
        expectedArrayList.add(GeneralObject("charizard", "https://pokeapi.co/api/v2/pokemon/6/"))
        expectedArrayList.add(GeneralObject("charmander", "https://pokeapi.co/api/v2/pokemon/4/"))
        expectedArrayList.add(GeneralObject("charmeleon", "https://pokeapi.co/api/v2/pokemon/5/"))
        expectedArrayList.add(GeneralObject("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/"))
        expectedArrayList.add(GeneralObject("venusaur", "https://pokeapi.co/api/v2/pokemon/3/"))

        Assert.assertEquals(expectedArrayList, actualArrayList)
    }

    @Test
    fun testArrayListSortByNameDescending() {
        val originalArrayList = ArrayList<GeneralObject?>()
        originalArrayList.add(GeneralObject("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
        originalArrayList.add(GeneralObject("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/"))
        originalArrayList.add(GeneralObject("venusaur", "https://pokeapi.co/api/v2/pokemon/3/"))
        originalArrayList.add(GeneralObject("charmander", "https://pokeapi.co/api/v2/pokemon/4/"))
        originalArrayList.add(GeneralObject("charmeleon", "https://pokeapi.co/api/v2/pokemon/5/"))
        originalArrayList.add(GeneralObject("charizard", "https://pokeapi.co/api/v2/pokemon/6/"))

        val actualArrayList = originalArrayList.sortByNameDescending()

        val expectedArrayList = ArrayList<GeneralObject?>()
        expectedArrayList.add(GeneralObject("venusaur", "https://pokeapi.co/api/v2/pokemon/3/"))
        expectedArrayList.add(GeneralObject("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/"))
        expectedArrayList.add(GeneralObject("charmeleon", "https://pokeapi.co/api/v2/pokemon/5/"))
        expectedArrayList.add(GeneralObject("charmander", "https://pokeapi.co/api/v2/pokemon/4/"))
        expectedArrayList.add(GeneralObject("charizard", "https://pokeapi.co/api/v2/pokemon/6/"))
        expectedArrayList.add(GeneralObject("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))

        Assert.assertEquals(expectedArrayList, actualArrayList)
    }
}