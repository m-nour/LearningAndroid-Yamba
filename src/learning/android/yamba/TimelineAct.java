package learning.android.yamba;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class TimelineAct extends Activity {
	
	DbHelper dbHelper;
	SQLiteDatabase db;
	Cursor cursor;
	ListView listTimeline;
	TimelineAdapter adapter;
	YambaApp yamba;
	
	static final String[] FROM={DbHelper.C_CREATED_AT, DbHelper.C_USER, DbHelper.C_TEXT};
	static final int[] TO={R.id.textCreatedAt, R.id.textUser, R.id.textText};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline_basic);
		
		listTimeline=(ListView)findViewById(R.id.listTimeline);
		
		dbHelper=new DbHelper(this);
		db=dbHelper.getWritableDatabase();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		cursor=db.query(DbHelper.TABLE, null, null, null, null, null, DbHelper.C_CREATED_AT+" DESC");
		startManagingCursor(cursor);
		
		adapter=new TimelineAdapter(this, cursor);
//		adapter.setViewBinder(VIEW_BINDER);
		listTimeline.setAdapter(adapter);
	}
	
//	static final ViewBinder VIEW_BINDER= new ViewBinder() {
//		
//		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
//			if(view.getId()!=R.id.textCreatedAt)
//				return false;
//			
//			long timeStamp=cursor.getLong(columnIndex);
//			CharSequence relTime=DateUtils.getRelativeTimeSpanString(view.getContext(), timeStamp);
//			((TextView)view).setText(relTime);
//			return true;
//		}
//	};
}
