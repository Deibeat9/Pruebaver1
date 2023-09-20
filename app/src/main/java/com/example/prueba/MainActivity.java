package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


public class MainActivity extends AppCompatActivity {
// DAVID //
    private VideoView videoView;
    private Button btnConnect;
    private Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);

        btnConnect = findViewById(R.id.btnConnect);
        btnSend = findViewById(R.id.btnSend);


        //Meotodo para guardar Datos//
        Txt_IPServidor = findViewById(R.id.Txt_IPServidor);
        Edt_IPServidor = findViewById(R.id.Edt_IPServidor);
        Edt_PortServidor = findViewById(R.id.Edt_PortServidor);
        Btn_ConfirmarD = findViewById(R.id.Btn_ConfirmarD);
        SharedPreferences pref = getSharedPreferences("AgendaIp", Context.MODE_PRIVATE);
        Txt_IPServidor.setText(pref.getString("Ip",  IPActual ));
        String IP = Txt_IPServidor.getText().toString();
        String IpLimpia = IP.replace(" ", "");
        URLCOMPLETA = IpLimpia;
        PORTActual1int = Integer.parseInt(PORTActual1);

        Edt_IPServidor.setText(" ");
        Edt_PortServidor.setText(" ");
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, " connectToServer();", Toast.LENGTH_SHORT).show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "reproducirVideo();", Toast.LENGTH_SHORT).show();
            }
        });

        // Ruta del video en la carpeta raw (puede cambiar según el nombre del archivo)


        Btn_On_Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Linear_URL.getVisibility() == View.GONE){
                    Linear_URL.setVisibility(View.VISIBLE);
                }else{
                    Linear_URL.setVisibility(View.GONE);
                }
            }
        });

        Btn_ConfirmarD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contenido=Edt_IPServidor.getText().toString();
                if (contenido.isEmpty()){
                    Linear_URL.setVisibility(View.GONE);
                }else  if (!contenido.isEmpty()) {
                    //    RealizarPost("");


                }

                //V
                String IP1 = Edt_IPServidor.getText().toString();
                //T
                String PORT1 = Edt_PortServidor.getText().toString();

                if (!IP1.isEmpty() && !PORT1.isEmpty()) {
                    Txt_IPServidor.setText(URLCOMPLETA);
                    Linear_URL.setVisibility(View.GONE);
                    LanzarAlertaip("Nuevos Datos.", "Se reiniciará  la aplicación  debido al cambio  de IP.");
                    GuardarValores1();
                    Txt_IPServidor.setText(IP1 + PORT1);

                } else if (!IP1.isEmpty() && PORT1.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Ncesitas escribir  nuevos valores", Toast.LENGTH_LONG).show();

                } else if (!IP1.isEmpty() && PORT1.isEmpty()) {
                    ValGuardar1();

                } else if (IP1.isEmpty() && !PORT1.isEmpty()) {
                    TitGuardar1();
                }
                setContentView(R.layout.activity_main);

                LanzarAlertaip("Nueva Titulo.", "Se reiniciará  la aplicación  debido al cambio  de valores ");

            }
        });

        Toast.makeText(this, IPActual1 + PORTActual1, Toast.LENGTH_SHORT).show();
        // connectToServer();
    }


    private void connectToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(IPActual1, PORTActual1int); // Reemplaza con la dirección y puerto del servidor
                    // Realiza cualquier tarea adicional de conexión si es necesario
                    // Envía el texto "start_video" al servidor

                    OutputStream outputStream = socket.getOutputStream();
                    String message = "start_video";
                    outputStream.write(message.getBytes());

                    String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
                    // Configurar la fuente del video
                    videoView.setVideoURI(Uri.parse(videoPath));
                    // Iniciar la reproducción del video
                    videoView.start();


                    // Cierra la conexión cuando ya no la necesites
                    //  socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void reproducirVideo() {

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        // Configurar la fuente del video
        videoView.setVideoURI(Uri.parse(videoPath));
        // Iniciar la reproducción del video
        videoView.start();

    }

    //GUARDAR DATTOS //
    private void GuardarValores1() {
        String IP1 = Edt_IPServidor.getText().toString();
        IPNuevo1 = IP1;
        String PORT1 = Edt_PortServidor.getText().toString();
        PORTNuevo1 = PORT1;
        SharedPreferences Preferences1 = getSharedPreferences("PORTA1", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editort = Preferences1.edit();
        obj_editort.putString("PORT1", PORTNuevo1);
        obj_editort.commit();
        SharedPreferences Preferencesuno1 = getSharedPreferences("IPA2", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editorv = Preferencesuno1.edit();
        obj_editorv.putString("IP2", IPNuevo1);
        obj_editorv.commit();
    }

    private void LanzarAlertaip(String Titulo, String Msj) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(Titulo);
        builder.setMessage(Msj);
        //builder.setPositiveButton("Entendido", null);
        builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
                startActivity(getIntent());

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void TitGuardar1() {
        String PORT1 = Edt_PortServidor.getText().toString();
        PORTNuevo1 = PORT1;
        SharedPreferences Preferences1 = getSharedPreferences("PORTA1", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editort = Preferences1.edit();
        obj_editort.putString("PORT1", PORTNuevo1);
        obj_editort.commit();
    }
    private void ValGuardar1() {
        String IP1 = Edt_IPServidor.getText().toString();
        IPNuevo1 = IP1;
        SharedPreferences Preferencesuno1 = getSharedPreferences("IPA2", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editorv = Preferencesuno1.edit();
        obj_editorv.putString("IP2", IPNuevo1);
        obj_editorv.commit();
    }



}