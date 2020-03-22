package com.example.toDoAndroidClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication102.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected static final String NEW_ITEM_TEXT = "itemText";
    protected static final String ITEM_ID = "itemId";
     protected static final String EDIT_TEXT = "editText";

    private ListView itemsList;
    protected static List<String> taskList = new ArrayList<String>();
    protected static ArrayAdapter<String> adapter;
    private int itemId;
    private int selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);

        itemId = taskList.size();
        itemsList = (ListView) findViewById(R.id.itemsList);

        // создаем адаптер
        adapter = new ArrayAdapter<String>(this, R.layout.single_item, taskList);

        // устанавливаем для списка адаптер
        itemsList.setAdapter(adapter);


        // добвляем для списка слушатель
        itemsList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                selectedItemId = position;
                openForReviewListItem(position);
            }
        });
    }


    // Обработчик нажатия на кнопку. Он всегда паблик и войд.
    // Аргумент всегда View view это вью на котором мы нажали кнопку
    public void sendMessage(View view) {

        // Intent это объект, который обеспечивает связывание отдельных компонент
        // во время выполнения (например, двух активити ).
        // Intent представляет «намерение что-то сделать».
        // чаще всего они используются, чтобы начать другую активити .

        //Внутри sendMessage() метода, создайте Intent для запуска активити
        // под названиемDisplayMessageActivity:
        Intent intent = new Intent(this, TextEditorActivity.class);

        startActivityForResult(intent, itemId);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == itemId) {

            try {
                String textOfNewItem = data.getStringExtra(NEW_ITEM_TEXT);
                taskList.add(textOfNewItem);
                //          adapter.add(textOfNewItem);
                adapter.notifyDataSetChanged();
            } catch (NullPointerException e) {
            }
//


        } else if (requestCode == selectedItemId) {


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void openForReviewListItem(int position) {

        Intent intent = new Intent(this, ItemReview.class);
        String itemText = taskList.get(position);
        intent.putExtra(NEW_ITEM_TEXT, itemText);
        intent.putExtra(ITEM_ID, position);

        startActivity(intent);

    }


//    public void clickOnItem(View view) {
//
//        int currentItemId = view.
//        Intent intent = new Intent(this, ItemReview.class);
//        startActivity(intent);
//  //      startActivityForResult(intent, currentItemId);
//    }


}

