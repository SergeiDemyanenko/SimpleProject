package com.example.toDoAndroidClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication102.R;

import androidx.appcompat.app.AppCompatActivity;

public class TextEditorActivity extends AppCompatActivity {

    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);
        editText = (EditText) findViewById(R.id.text_input);

        try {
            String currentTextOfItem = getIntent().getExtras().get(MainActivity.EDIT_TEXT).toString();
            if (!currentTextOfItem.isEmpty()) {
                editText.setText(currentTextOfItem);
            }
        } catch (NullPointerException e) {
        }

    }


    public void saveMessage(View view) {

        Intent intent = new Intent(this, MainActivity.class);

        String textOfNewItem = editText.getText().toString();

        intent.putExtra(MainActivity.NEW_ITEM_TEXT, textOfNewItem);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


//
//    // Обработчик нажатия на кнопку. Он всегда паблик и войд.
//    // Аргумент всегда View view это вью на котором мы нажали кнопку
//    public void sendMessage(View view) {
//
//    // Intent это объект, который обеспечивает связывание отдельных компонент
//        // во время выполнения (например, двух активити ).
//        // Intent представляет «намерение что-то сделать».
//        // чаще всего они используются, чтобы начать другую активити .
//
//    //Внутри sendMessage() метода, создайте Intent для запуска активити
//        // под названиемDisplayMessageActivity:
//        Intent intent = new Intent(this, TextEditorActivity.class);
//        startActivity(intent);
//
//    }