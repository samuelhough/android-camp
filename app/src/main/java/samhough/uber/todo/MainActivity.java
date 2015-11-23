package samhough.uber.todo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;

import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;
import java.io.File;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aTodoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.MainTitle);
        setSupportActionBar(toolbar);
        populateArrayItems();
        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aTodoAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("text", todoItems.get(position));
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aTodoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String text = data.getExtras().getString("text");
            int position = data.getExtras().getInt("position", 0);
            todoItems.set(position, text);
            writeItems();
            aTodoAdapter.notifyDataSetChanged();
        }
    }

    public void populateArrayItems() {
        readItems();
        aTodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todos.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch(IOException e){
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todos.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch(IOException e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAddItem(View view){
        String text = etEditText.getText().toString();
        if (text.length() > 0) {
            aTodoAdapter.add(text);
            etEditText.setText("");
            writeItems();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
