package co.folto.kopigo.ui.adapter.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by Daniel on 5/30/2017 for GitFInder project.
 */
interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}