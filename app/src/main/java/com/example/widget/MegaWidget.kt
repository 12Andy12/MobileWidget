package com.example.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.TableLayout

fun rand(left: Int, right: Int): Int {
    return (left..right).random()
}
fun rand(arr : ArrayList<ArrayList<String>>): ArrayList<String> {
    return arr[(0 until arr.size).random()]
}

/**
 * Implementation of App Widget functionality.
 */
public class MegaWidget : AppWidgetProvider() {
    companion object {
        var listOfData = ArrayList<ArrayList<String>>()
    }

    lateinit var table: TableLayout
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}


@SuppressLint("RemoteViewLayout")
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    var randomData : ArrayList<String> = arrayListOf("none", "none", "none", "none", "none")
    if(MegaWidget.listOfData.isNotEmpty())
        randomData = rand(MegaWidget.listOfData)

    //val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.mega_widget)
    views.setTextViewText(R.id.id_label, appWidgetId.toString())
//    var truePrice =  MegaWidget.listOfData[4].toDouble() / MegaWidget.listOfData[2].toDouble()
    views.setTextViewText(R.id.data, randomData[3] + "    " + randomData[4])
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cbr.ru/currency_base/daily/"))
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
    views.setOnClickPendingIntent(R.id.launch_url, pendingIntent)

    //Создадим Intent с дейстивем AppWidgetManager.ACTION_APPWIDGET_UPDATE
    val intentUpdate = Intent(context, MegaWidget::class.java)
    intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

//Назначим обновление всем инстансам виджета
    val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context, MegaWidget::class.java))
    intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)

//Назначим этот intent как PendingIntent, используя PendingIntent.getBroadcast()
    val pendingUpdate = PendingIntent.getBroadcast(
        context,
        appWidgetId,
        intentUpdate,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
//Назначим этот pendingIntent откликом на нажатие пользователем кнопки ‘Обновить’
    views.setOnClickPendingIntent(R.id.data, pendingUpdate)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}