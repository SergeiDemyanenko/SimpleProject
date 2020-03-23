package com.example.toDoAndroidClient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication102.R;

public class ItemReview extends AppCompatActivity {

    private String itemText;
    private int itemId;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_review);

        textView = (TextView) findViewById(R.id.textReviewView);
        itemText = getIntent().getExtras().get(MainActivity.NEW_ITEM_TEXT).toString();
        itemId = Integer.parseInt(getIntent().getExtras().get(MainActivity.ITEM_ID).toString());
        textView.setText(itemText);
    }


    public void editItem(View view) {

        Intent intent = new Intent(this, TextEditorActivity.class);


        intent.putExtra(MainActivity.EDIT_TEXT, itemText);
        //  intent.putExtra(MainActivity.ITEM_ID, itemId);

        startActivityForResult(intent, itemId);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == itemId) {

            try {
                String textOfEditItem = data.getStringExtra(MainActivity.NEW_ITEM_TEXT);
                ToDOAdapter.taskList.set(itemId, textOfEditItem);
                //          adapter.add(textOfNewItem);
                textView.setText(textOfEditItem);
                ToDOAdapter.adapter.notifyDataSetChanged();
            } catch (NullPointerException e) {
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void deleteItem(View view) {

        ToDOAdapter.taskList.remove(itemId);
        ToDOAdapter.adapter.notifyDataSetChanged();
        finish();

    }


}


