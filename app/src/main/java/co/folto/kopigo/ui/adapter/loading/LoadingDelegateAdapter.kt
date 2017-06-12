package co.folto.kopigo.ui.adapter.loading

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.folto.kopigo.R
import co.folto.kopigo.ui.adapter.base.ViewType
import co.folto.kopigo.ui.adapter.base.ViewTypeDelegateAdapter
import co.folto.kopigo.util.inflate

/**
 * Created by Daniel on 5/30/2017 for GitFInder project.
 */
class LoadingDelegateAdapter: ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
            = LoadingViewHolder(parent.inflate(R.layout.item_progress))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {}

    class LoadingViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}

