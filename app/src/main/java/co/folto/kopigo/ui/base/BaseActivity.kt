package co.folto.kopigo.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import icepick.Icepick

/**
 * Created by Daniel on 7/4/2017 for Kopigo project.
 */
open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Icepick.saveInstanceState(this, outState)
    }
}