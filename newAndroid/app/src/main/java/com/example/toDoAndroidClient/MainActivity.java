package com.example.toDoAndroidClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication102.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected static final String NEW_ITEM_TEXT = "itemText";
    protected static final String ITEM_ID = "itemId";
    protected static final String EDIT_TEXT = "editText";
    protected static ToDOAdapter toDOAdapter;

    private int itemId;
    private int selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);

        ListView itemsList = (ListView) findViewById(R.id.itemsList);

        // создаем объект адаптера
        toDOAdapter = new ToDOAdapter(this);
        itemId = toDOAdapter.getAdapter().getCount();

        // устанавливаем для списка адаптер
        itemsList.setAdapter(toDOAdapter.getAdapter());

        // добвляем для списка слушатель
        itemsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                selectedItemId = position;
                openForReviewListItem(position);
            }
        });
    }


    public void sendMessage(View view) {

        Intent intent = new Intent(this, TextEditorActivity.class);
        startActivityForResult(intent, itemId);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == itemId) {
            try {
                String textOfNewItem = data.getStringExtra(NEW_ITEM_TEXT);
                toDOAdapter.putNewItem(textOfNewItem);
                toDOAdapter.getAdapter().notifyDataSetChanged();
            } catch (NullPointerException e) {
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void openForReviewListItem(int position) {

        Intent intent = new Intent(this, ItemReview.class);
        String itemText = toDOAdapter.getAdapter().getItem(position);
        intent.putExtra(NEW_ITEM_TEXT, itemText);
        intent.putExtra(ITEM_ID, position);
        startActivity(intent);

    }
}

