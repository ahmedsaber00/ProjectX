package com.afaqy.ptt.remote.features.recipes.mapper

import com.afaqy.ptt.remote.test.factory.RecipeDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class LoginResponseModelMapperTest {

    private val mapper = LoginResponseModelMapper()

    @Test
    fun mapFromModelMapsData() {
        val model = RecipeDataFactory.makeRecipe()
        val entity = mapper.mapFromModel(model)

        assertEquals(model.id, entity.id)
        assertEquals(model.loginLogin.authorName, entity.author)
        assertEquals(model.title, entity.title)
        assertEquals(model.description, entity.description)
        assertEquals(model.url, entity.url)
        assertEquals(model.urlToImage, entity.urlToImage)
        assertEquals(model.publishedAt, entity.publishedAt)
        assertEquals(model.content, entity.content)
    }
}