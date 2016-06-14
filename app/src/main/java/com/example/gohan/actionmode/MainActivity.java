package com.example.gohan.actionmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{
    boolean is_in_action_mode = false;
    TextView counterTextView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Toolbar toolbar;
    ArrayList<Contact> arrayList = new ArrayList<Contact>();
    ArrayList<Contact> selectionList = new ArrayList<Contact>();
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // initialise recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        counterTextView = (TextView) findViewById(R.id.cnt_text);
        counterTextView.setVisibility(View.GONE);
        String [] Name=getResources().getStringArray(R.array.names);
        int i=0;
        for(String NAME:Name)
        {
            Contact contact = new Contact(NAME);
            arrayList.add(contact);
            i++;
        }
        adapter = new RecyclerAdapter(arrayList,MainActivity.this);
        recyclerView.setAdapter(adapter);
    }
    //adding menu to toolbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onLongClick(View v) {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_action_mode);
        is_in_action_mode = true;
        counterTextView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        // home button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    public void prepareselection(View view,int position)
    {
        //change view to checkbox
        if(((CheckBox)view).isChecked())
        {
            selectionList.add(arrayList.get(position));
            counter++;
            updateCnt(counter);
        }
        else {
            selectionList.remove(arrayList.get(position));
            counter--;
            updateCnt(counter);
        }
    }

    public void updateCnt(int counter)
    {
        if(counter==0)
        {
            counterTextView.setText("0 item selected");
        }
        else {
            counterTextView.setText(counter + " item selected");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.it_delete)
        {

            RecyclerAdapter recyclerAdapter = (RecyclerAdapter) adapter;
            recyclerAdapter.updateAdapter(selectionList);
            clearActionM();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clearActionM()
    {
        is_in_action_mode = false;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_activity_main);
        //remove home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        counterTextView.setVisibility(View.GONE);
        counterTextView.setText("0 item selected");
        counter = 0;
        selectionList.clear();
    }

    @Override
    public void onBackPressed() {
        if(is_in_action_mode)
        {
            clearActionM();
            adapter.notifyDataSetChanged();
        }
        else {
            finish();
        }
        super.onBackPressed();
    }
}
