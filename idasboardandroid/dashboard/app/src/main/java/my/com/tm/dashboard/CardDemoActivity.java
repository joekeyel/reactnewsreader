package my.com.tm.dashboard;

/**
 * Created by user on 5/2/2018.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import my.dashboard.R;

public class CardDemoActivity extends AppCompatActivity implements ListView.OnItemClickListener{

        private ProgressDialog loading;

        private ListView listView;
        EditText editext;


        View myView;

        private String JSON_STRING;
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            myView = inflater.inflate(R.layout.activity_card_demo, container, false);


           // editext = (EditText)myView. findViewById(R.id.gated);

            listView = (ListView) myView.findViewById(R.id.list);
            listView.setOnItemClickListener(this);

//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });

            return myView;

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Intent intent = new Intent(getActivity(), ListGated.class);
           // startActivity(intent);
        }
    }
