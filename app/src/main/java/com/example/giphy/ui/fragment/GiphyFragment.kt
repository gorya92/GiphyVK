package com.example.giphy.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.giphy.R
import com.example.giphy.databinding.FragmentGiphyBinding
import com.example.giphy.ui.adapter.GiphyAdapter
import com.example.giphy.ui.adapter.GiphyLoadStateAdapter
import com.example.giphy.ui.viewmodel.GiphyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiphyFragment : Fragment(), GiphyAdapter.OnItemClickListener {

    private lateinit var binding: FragmentGiphyBinding

    private val viewModel by viewModels<GiphyViewModel>()

    private val adapter = GiphyAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGiphyBinding.inflate(layoutInflater, container, false)

        setUpRecyclerView()

        setDataObserve()

        addLoadStateListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun setDataObserve() {
        viewModel.giphy.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun setUpRecyclerView() {
        binding.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.itemAnimator = null
            recyclerview.layoutManager = GridLayoutManager(context, 1)
            recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = GiphyLoadStateAdapter { adapter.retry() },
                footer = GiphyLoadStateAdapter { adapter.retry() },
            )
        }
    }


    private fun addLoadStateListener() {
        binding.apply {
            adapter.addLoadStateListener { loadState ->
                binding.apply {
                    giphySearchProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    recyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
                    giphySearchRetryBTN.isVisible = loadState.source.refresh is LoadState.Error
                    giphySearchErrorTV.isVisible = loadState.source.refresh is LoadState.Error

                    if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1
                    ) {
                        recyclerview.isVisible = false
                        giphySearchEmptyTV.isVisible = true
                    } else {
                        giphySearchEmptyTV.isVisible = false
                    }
                }
            }
        }
    }

    override fun onItemClick(id: String) {
        val action =
            GiphyFragmentDirections.actionGiphyFragmentToGiphyDetailsFragment(
                id
            )

        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerview.scrollToPosition(0)
                    viewModel.searchGiphy(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

}