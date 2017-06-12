package co.folto.kopigo.util

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import org.joda.time.DateTime

/**
 * Created by Daniel on 6/12/2017 for Kopigo project.
 */

fun Resources.obtainDrawable(id: Int, context: Context): Drawable =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            this.getDrawable(id, context.getTheme())
        else
            this.getDrawable(id)

fun String.formatDate(format: String)
        = DateTime(this).toString(format)