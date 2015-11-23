package samhough.uber.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class EditItemActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    EditText etEditText;
    int position;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit);
        toolbar.setTitle("Edit Item");
        setSupportActionBar(toolbar);

        etEditText = (EditText) findViewById(R.id.editItemText);
        text = getIntent().getStringExtra("text");

        position = getIntent().getIntExtra("position", 0);
        etEditText.setText(text);

        etEditText.requestFocus();
        if (text.length() > 0) {
            etEditText.setSelection(text.length());
        }
    }

    public void onUpdateItem(View view){
        String newText =  etEditText.getText().toString();

        if(newText.length() > 0) {
            Intent data = new Intent();

            data.putExtra("position", position);
            data.putExtra("text", newText);

            setResult(RESULT_OK, data);
            finish();
        }
    }

}
