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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        etEditText = (EditText) findViewById(R.id.editItemText);

        position = getIntent().getIntExtra("position", 0);
        text = getIntent().getStringExtra("text");

        toolbar.setTitle("Edit me!");
        System.out.println(toolbar.getTitle());
        etEditText.setText(text);
    }

    public void onUpdateItem(View view){
        Intent data = new Intent();

        data.putExtra("position", position);
        data.putExtra("text",  etEditText.getText().toString());

        setResult(RESULT_OK, data);
        finish();
    }

}
