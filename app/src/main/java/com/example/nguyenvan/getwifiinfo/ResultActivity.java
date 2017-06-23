package com.example.nguyenvan.getwifiinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private String nameBuilding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = this.getIntent();
        nameBuilding = intent.getStringExtra("nameBuild");
        String msg = nameBuilding ;
        TextView txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText(msg);

    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent();
        setResult(RESULT_CANCELED, backIntent );
        finishActivity(RESULT_CANCELED);
        super.onBackPressed();
    }
}
