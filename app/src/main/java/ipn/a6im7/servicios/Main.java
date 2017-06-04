package ipn.a6im7.servicios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class Main extends Activity {

    Spinner servicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        servicios=(Spinner)findViewById(R.id.servicios);
        servicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lanzarServicio(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void lanzarServicio(int id)
    {
        Intent servicio;
        switch(id){
            case 1:
            servicio=new Intent(this,servicioLogin.class);
                startActivity(servicio);
                break;
            case 2:
                servicio=new Intent(this,servicioCalc.class);
                startActivity(servicio);
                break;
            case 3:
                servicio=new Intent(this,servicioConversor.class);
                startActivity(servicio);
                break;
        }
    }
}
