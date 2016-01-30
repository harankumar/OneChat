package com.petev.kumar.onechat;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMsgs(3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                addMsg("NEW Sender", "Message");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void initMsgs(int amount){
        for(int ind = 0; ind < amount; ind++) {
            messages.add(new Message("Sender", "Message"));
        }

        ArrayAdapter<Message> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.messageList);
        list.setAdapter(adapter);
    }

    public String getSMS() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"id", "address", "date", "body"}, null, null, null);
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            String msgData = "";
            do {
                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                }
                // use msgData
            } while (cursor.moveToNext());
            return msgData;
        } else {
            // empty box, no SMS
            return null;
        }
    }

    public void addMsg(String sender, String message){
        messages.add(0, new Message(sender, message));
        if (messages.size() > 10) {
            messages.remove(10);
        }
        ArrayAdapter<Message> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.messageList);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Message> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.message_view, messages);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.message_view, parent, false);
            }

            Message currentMsg = messages.get(position);
            //ImageView imageView = (ImageView) itemView.findViewById(R.id.);
            //imageView.setImageResource(currentMsg.getIconID());

            TextView senderText = (TextView) itemView.findViewById(R.id.senderName);
            senderText.setText(currentMsg.messageSender);

            TextView messageText = (TextView) itemView.findViewById(R.id.messageContents);
            messageText.setText(currentMsg.messageContents);

            TextView timeText = (TextView) itemView.findViewById(R.id.timeStamp);
            timeText.setText(currentMsg.messageTime);

            return itemView;
        }
    }
}
