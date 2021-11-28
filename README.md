# NotePad
# 添加笔记时间戳
在布局文件中增加一个TextView来显示时间戳

    <RelativeLayout android:layout_height="match_parent"
        android:layout_width="match_parent"
        xmlns:android="http://schemas.android.com/apk/res/android">
        <TextView xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@android:id/text1"
            android:layout_width="match_parent"
            android:layout_height="?android:attr/listPreferredItemHeight"
            android:textAppearance="?android:attr/textAppearanceLarge"
            android:gravity="center_vertical"
            android:paddingLeft="5dip"
            android:singleLine="true"
            />

        <TextView
            android:id="@+id/text2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingLeft="5dip"
            android:singleLine="true"
            android:gravity="center_vertical"
            />

    </RelativeLayout>

在NodeEditor.java中,找到updateNode()这个函数，选取修改时间这一字段，并将其格式化存入数据库

        //设置包含要在提供程序中更新的值的映射。
        ContentValues values = new ContentValues();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String dateFormat = simpleDateFormat.format(date);
        values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, dateFormat);

在NotePadProvider中添加数据表的创建时间和修改时间的列

    @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + NotePad.Notes.TABLE_NAME + " ("
                    + NotePad.Notes._ID + " INTEGER PRIMARY KEY,"
                    + NotePad.Notes.COLUMN_NAME_TITLE + " TEXT,"
                    + NotePad.Notes.COLUMN_NAME_NOTE + " TEXT,"
                    + NotePad.Notes.COLUMN_NAME_CREATE_DATE + " INTEGER,"// 列名称创建日期
                    + NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE + " INTEGER," //列名称修改日期
                    + ");");
        }

在NoteList.java的PROJECTION数组中增加该字段的描述

     private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE//添加修改时间
    };
    // 要在视图中显示的光标列的名称，初始化为标题列
    private String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;
    // 将显示光标列的视图ID，在中初始化为TextView
    private int[] viewIDs = {android.R.id.text1,R.id.text2 };
# 笔记查询功能
note_search.xml

    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
        <SearchView
            android:id="@+id/search_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:iconifiedByDefault="false"
            android:queryHint="搜索">
        </SearchView>
        <ListView
            android:id="@+id/list_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            >
        </ListView>
    </LinearLayout>

NoteSearch.java

    
    package com.example.android.notepad;

    import android.app.Activity;
    import android.content.Intent;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.widget.ListView;
    import android.widget.SearchView;
    import android.widget.SimpleCursorAdapter;
    import android.widget.Toast;

    public class NoteSearch extends Activity implements SearchView.OnQueryTextListener
    {
        ListView listView;
        SQLiteDatabase sqLiteDatabase;
        /**
         * The columns needed by the cursor adapter
         */
        private static final String[] PROJECTION = new String[]{
                NotePad.Notes._ID, // 0
                NotePad.Notes.COLUMN_NAME_TITLE, // 1
                NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE//时间
        };
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(this, "您选择的是："+query, Toast.LENGTH_SHORT).show();
            return false;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.note_search);
            SearchView searchView = findViewById(R.id.search_view);
            Intent intent = getIntent();
            if (intent.getData() == null) {
                intent.setData(NotePad.Notes.CONTENT_URI);
            }
            listView = findViewById(R.id.list_view);
            sqLiteDatabase = new NotePadProvider.DatabaseHelper(this).getReadableDatabase();
            //设置该SearchView显示搜索按钮
            searchView.setSubmitButtonEnabled(true);
            //设置该SearchView内默认显示的提示文本
            searchView.setQueryHint("查找");
            searchView.setOnQueryTextListener(this);

        }
        public boolean onQueryTextChange(String string) {
             String selection1 = NotePad.Notes.COLUMN_NAME_TITLE+" like ? or "+NotePad.Notes.COLUMN_NAME_NOTE+" like ?";
            String[] selection2 = {"%"+string+"%","%"+string+"%"};
            Cursor cursor = sqLiteDatabase.query(
                    NotePad.Notes.TABLE_NAME,
                    PROJECTION, // The columns to return from the query
                    selection1, // The columns for the where clause
                    selection2, // The values for the where clause
                    null,          // don't group the rows
                    null,          // don't filter by row groups
                    NotePad.Notes.DEFAULT_SORT_ORDER // The sort order
            );
            // The names of the cursor columns to display in the view, initialized to the title column
            String[] dataColumns = {
                    NotePad.Notes.COLUMN_NAME_TITLE,
                    NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
            } ;
            // The view IDs that will display the cursor columns, initialized to the TextView in
            // noteslist_item.xml
            int[] viewIDs = {
                    android.R.id.text1,
                    android.R.id.text2
            };
            // 为ListView创建备份适配器。
            SimpleCursorAdapter adapter
                    = new SimpleCursorAdapter(
                    this,                             // The Context for the ListView
                    R.layout.noteslist_item,         // Points to the XML for a list item
                    cursor,                           // The cursor to get items from
                    dataColumns,
                    viewIDs
            );
            // Sets the ListView's adapter to be the cursor adapter that was just created.
            listView.setAdapter(adapter);
            return true;
        }
    }

实验图片：
![图片](https://user-images.githubusercontent.com/90604287/143771849-f9333305-7c9f-4a0f-9c4d-97a49947efd0.png)
![图片](https://user-images.githubusercontent.com/90604287/143772115-c6f43e66-2b61-4648-b9a5-544fbc52f1e0.png)
