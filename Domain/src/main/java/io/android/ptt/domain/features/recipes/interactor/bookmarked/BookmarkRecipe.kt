package io.android.ptt.domain.features.recipes.interactor.bookmarked

import io.android.ptt.domain.base.executor.PostExecutionThread
import io.android.ptt.domain.base.interactor.CompletableUseCase
import io.android.ptt.domain.features.recipes.repository.RecipesRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class BookmarkRecipe @Inject constructor(
    private val recipesRepository: RecipesRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<BookmarkRecipe.Params>(postExecutionThread) {
    public override fun buildUseCaseCompletable(params: BookmarkRecipe.Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null !!")
        return recipesRepository.bookmarkRecipe(params.recipeId)
    }

    data class Params constructor(val recipeId: Long) {
        companion object {
            fun forRecipe(recipeId: Long): Params {
                return Params(recipeId)
            }
        }
    }

}