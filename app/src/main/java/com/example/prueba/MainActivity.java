package com.example.prueba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


public class MainActivity extends AppCompatActivity {
    private Button btnConnect;
    private Button btnSend;
    private VideoView videoView;

    private Socket socket;



    Button Btn_On_Off, Btn_Confirmar;
    public String URLCOMPLETA = "";
    LinearLayout Linear_URL;
    TextView Txt_IPServidor ;
    EditText Edt_IPServidor;
    public  String IPActual = "http://192.168.43.54:8011/";
    public  String IPNueva = "";
    public  boolean ErrorURL = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = findViewById(R.id.btnConnect);
        btnSend = findViewById(R.id.btnSend);
        videoView = findViewById(R.id.videoView);
        videoView.setVisibility(View.VISIBLE);




        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, " connectToServer();", Toast.LENGTH_SHORT).show();
                connectToServer();


            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSend.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "reproducirVideo();", Toast.LENGTH_SHORT).show();
                reproducirVideo();
            }
        });

        // Ruta del video en la carpeta raw (puede cambiar según el nombre del archivo)



        //Meotodo para guardar Datos//
        Txt_IPServidor = findViewById(R.id.Txt_IPServidor);
        Edt_IPServidor = findViewById(R.id.Edt_IPServidor);
        Btn_On_Off = findViewById(R.id.Btn_On_Off);
        Btn_Confirmar = findViewById(R.id.Btn_Confirmar);
        Linear_URL = findViewById(R.id.Linear_URL);

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
        Btn_Confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contenido=Edt_IPServidor.getText().toString();
                if (contenido.isEmpty()){
                    Linear_URL.setVisibility(View.GONE);
                }else  if (!contenido.isEmpty()) {
                    //    RealizarPost("");

                    Txt_IPServidor.setText(URLCOMPLETA);
                    Linear_URL.setVisibility(View.GONE);
                    GUardar();
                    LanzarAlertaip("Nueva IP.", "Se reiniciará  la aplicación  debido al cambio  de IP.");
                }
            }
        });
        SharedPreferences pref = getSharedPreferences("AgendaIp", Context.MODE_PRIVATE);
        Txt_IPServidor.setText(pref.getString("Ip",  IPActual ));
        Edt_IPServidor.setText(pref.getString("Ip", IPActual));
        String IP = Txt_IPServidor.getText().toString();
        String IpLimpia = IP.replace(" ", "");
        URLCOMPLETA = IpLimpia;



        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // Manejar errores de reproducción aquí
                return false;
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
                // Reproducción del video completada
            }
        });
        Toast.makeText(MainActivity.this, " connectToServer();", Toast.LENGTH_SHORT).show();
        connectToServer();



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

    private void GUardar() {
        String IPN = Edt_IPServidor.getText().toString();
        IPNueva = IPN.replace(" ", "");
        SharedPreferences Preferences = getSharedPreferences("AgendaIp", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = Preferences.edit();
        obj_editor.putString("Ip", IPNueva);
        obj_editor.commit();
        Txt_IPServidor.setText(IPNueva);
        Toast.makeText(this, "La IP ha sido cambiada.", Toast.LENGTH_SHORT).show();
    }

    private void connectToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.100.138", 8766); // Reemplaza con la dirección y puerto del servidor
                    // Realiza cualquier tarea adicional de conexión si es necesario
                    // Envía el texto "start_video" al servidor




                    // Cierra la conexión cuando ya no la necesites
                    //  socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void reproducirVideo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket != null) {
                        // Envía el texto "start_video" al servidor
                        OutputStream outputStream = socket.getOutputStream();
                        String message = "start_video";
                        outputStream.write(message.getBytes());

                        // Utiliza runOnUiThread para interactuar con la vista de la interfaz de usuario
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
                                // Configurar la fuente del video
                                videoView.setVideoURI(Uri.parse(videoPath));
                                // Iniciar la reproducción del video
                                videoView.start();
                            }
                        });
                    } else {
                        // Manejar el caso en el que la conexión no está establecida
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}