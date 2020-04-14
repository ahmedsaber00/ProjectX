package io.android.ptt.domain.features.recipes.interactor.browse

import com.nhaarman.mockitokotlin2.whenever
import io.android.ptt.domain.base.executor.PostExecutionThread
import io.android.ptt.domain.features.login.interactor.browse.GetRecipes
import io.android.ptt.domain.features.login.model.LoginModel
import io.android.ptt.domain.features.login.repository.LoginRepository
import io.android.ptt.domain.test.factory.RecipeDataFactory
import io.reactivex.Observable
import org.mockito.Mock
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class LoginTest {
    private lateinit var getRecipes: GetRecipes
    @Mock
    lateinit var loginRepository: LoginRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getRecipes = GetRecipes(loginRepository, postExecutionThread)
    }

    @Test
    fun getRecipesCompletes() {
        stubGetRecipes(Observable.just(RecipeDataFactory.makeRecipesList(3)))
        val testObserver = getRecipes.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getRecipesReturnsData() {
        val recipes = RecipeDataFactory.makeRecipesList(3)
        stubGetRecipes(Observable.just(recipes))
        val testObserver = getRecipes.buildUseCaseObservable().test()
        testObserver.assertValue(recipes)
    }

    private fun stubGetRecipes(observable: Observable<List<LoginModel>>) {
        whenever(loginRepository.login())
            .thenReturn(observable)
    }

}