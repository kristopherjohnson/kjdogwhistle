package net.kristopherjohnson.dogwhistle;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WhistleWidgetProvider extends AppWidgetProvider {

	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int widgetCount = appWidgetIds.length;

		for (int i = 0; i < widgetCount; i++) {
			int appWidgetId = appWidgetIds[i];

			// Create an Intent to invoke the WhistleService
			Intent intent = new Intent(WhistleService.ACTION_BLOW_WHISTLE,
					null, context, WhistleService.class);
			PendingIntent pendingIntent = PendingIntent.getService(context, 0,
					intent, 0);

			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.whistle_appwidget);
			views.setOnClickPendingIntent(R.id.button, pendingIntent);

			// Tell the AppWidgetManager to perform an update on the current app
			// widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
