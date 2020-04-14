package com.afaqy.ptt.presentation.features.browse

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import com.afaqy.ptt.presentation.R
import com.afaqy.ptt.presentation.di.ViewModelProviderFactory
import com.afaqy.ptt.presentation.features.bookmarked.BookmarkedActivity
import com.afaqy.ptt.presentation.base.model.RecipeView
import com.afaqy.ptt.presentation.base.state.Resource
import com.afaqy.ptt.presentation.base.state.ResourceState
import kotlinx.android.synthetic.main.browse_activity.*
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    @Inject
    lateinit var browseAdapter: BrowseAdapter
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var browseViewModel: BrowseRecipesViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, BrowseActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_activity)
        AndroidInjection.inject(this)
        browseViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(BrowseRecipesViewModel::class.java)
        setupBrowseRecycler()
    }

    override fun onStart() {
        super.onStart()
        browseViewModel.getRecipes().observe(this,
            Observer<Resource<List<RecipeView>>> { it?.let { handleDataState(it) } })
        browseViewModel.fetchRecipes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bookmarked -> {
                startActivity(BookmarkedActivity.getStartIntent(this))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupBrowseRecycler() {
        browseAdapter.recipeListener = recipeListener
        recyclerRecipes.layoutManager = LinearLayoutManager(this)
        recyclerRecipes.adapter = browseAdapter
    }

    private fun handleDataState(resource: Resource<List<RecipeView>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data)
            }
            ResourceState.LOADING -> {
                progressView.visibility = View.VISIBLE
            }
            ResourceState.ERROR -> {
            }
        }
    }

    private fun setupScreenForSuccess(recipes: List<RecipeView>?) {
        progressView.visibility = View.GONE
        recipes?.let {
            browseAdapter.recipes = it
            browseAdapter.notifyDataSetChanged()
            recyclerRecipes.visibility = View.VISIBLE
        } ?: run {

        }
    }

    private val recipeListener = object : RecipeListener {
        override fun onBookmarkedRecipeClicked(recipeId: Long) {
            browseViewModel.unBookmarkRecipe(recipeId)
        }

        override fun onRecipeClicked(recipeId: Long) {
            browseViewModel.bookmarkRecipe(recipeId)
        }
    }

}