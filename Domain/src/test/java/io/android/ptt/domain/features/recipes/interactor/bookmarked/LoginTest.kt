package io.android.ptt.domain.features.recipes.interactor.bookmarked

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.android.ptt.domain.base.executor.PostExecutionThread
import io.android.ptt.domain.features.login.interactor.bookmarked.Login
import io.android.ptt.domain.features.login.repository.RecipesRepository
import io.android.ptt.domain.test.factory.DataFactory
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class LoginTest {

    private lateinit var login: Login
    @Mock lateinit var recipesRepository: RecipesRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        login = Login(recipesRepository,postExecutionThread)
    }

    @Test
    fun bookmarkRecipeCompletes(){
        stubBookmarkRecipe(Completable.complete())
        val testObserver = login.buildUseCaseCompletable(
            Login.Params.forRecipe(DataFactory.uniqueId())
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkRecipeThrowException(){
        login.buildUseCaseCompletable().test()
    }

    private fun stubBookmarkRecipe(completable: Completable){
        whenever(recipesRepository.bookmarkRecipe(any()))
            .thenReturn(completable)
    }

}