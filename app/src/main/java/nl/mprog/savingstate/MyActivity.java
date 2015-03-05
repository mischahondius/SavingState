package nl.mprog.savingstate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MyActivity extends Activity {

    EditText notesEditText;
    Button btnSettings;
    private static final int SETTINGS_INFO = 1;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    
        notesEditText = (EditText) findViewById(R.id.notesEditText);
        
        // If no saved instance -> go create one
        if(savedInstanceState != null){
            
            String notes = savedInstanceState.getString("NOTES");
            
            notesEditText.setText(notes);    
        }
        
        //If saved instance, go get data
        String sPNotes = getPreferences(Context.MODE_PRIVATE).getString("NOTES",
                "EMPTY");
        
        //If not empty, we retreived something, so go get it
        if(!sPNotes.equals("EMPTY")){
            
            notesEditText.setText(sPNotes);
        }

        //initialize button
        btnSettings = (Button) findViewById(R.id.btnSettings);
        
        btnSettings.setOnClickListener(new View.OnClickListener() {
           
           //if clicked, open up settings sctivity layout (class)
            @Override
            public void onClick(View view) {
                Intent intentPreferences = new Intent(getApplicationContext(),
                        SettingsActivity.class);
                
                startActivityForResult(intentPreferences, SETTINGS_INFO);
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode== SETTINGS_INFO){
            updateNoteText();
        }
        
    }

    //update changes made in settings text box
    private void updateNoteText(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        
        //if bold is unchecked, change to bold
        if(sharedPreferences.getBoolean("pref_text_bold", false)){
            notesEditText.setTypeface(null, Typeface.BOLD);
        
        } else {
            
            notesEditText.setTypeface(null, Typeface.NORMAL);
        }
        
        String textSizeStr = sharedPreferences.getString("pref_text_size", "16");
        
        float textSizeFloat = Float.parseFloat(textSizeStr);
        
        notesEditText.setTextSize(textSizeFloat);
        
        
        
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putString("NOTES",
                notesEditText.getText().toString());
        
        super.onSaveInstanceState(outState);
    }

    private void saveSettings(){
        
        //Create preferences editor (in order to save data in case of a user shutdown)
        SharedPreferences.Editor sPEditor = getPreferences(Context.MODE_PRIVATE).edit();
        
        sPEditor.putString("NOTES",
                notesEditText.getText().toString());
        
        //Commit changes
        sPEditor.commit();
    }

    @Override
    protected void onStop() {
        //Save everything in case of a forced stop
        saveSettings();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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
}
