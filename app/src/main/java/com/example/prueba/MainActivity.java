package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    // DAVID //
    private VideoView videoView;
    private Button btnConnect;
    private Button btnSend;
    private Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.videoView);
        btnConnect = findViewById(R.id.btnConnect);
        btnSend = findViewById(R.id.btnSend);

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
                Toast.makeText(MainActivity.this, "reproducirVideo", Toast.LENGTH_SHORT).show();
                if (socket != null) {
                    sendStartVideoMessage();


                } else {
                    sendStartVideoMessage();
                    Toast.makeText(MainActivity.this, "No se ha establecido la conexión al servidor.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void connectToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {//Creas la conexion
                    Socket socket = new Socket("192.168.1.79", 8766);
                    /* Realiza cualquier tarea adicional de conexión si es necesario
                    // Envía el texto "start_video" al servidor
                    OutputStream outputStream = socket.getOutputStream();//Envias mensaje
                    String message = "start_video";
                    outputStream.write(message.getBytes());
                    String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
                    // Configurar la fuente del video
                    videoView.setVideoURI(Uri.parse(videoPath));
                    // Iniciar la reproducción del video
                    videoView.start();
                    // Cierra la conexión cuando ya no la necesites
                    //  socket.close();*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void sendStartVideoMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    String message = "start_video";
                    outputStream.write(message.getBytes());
                    // Configurar la fuente del video
                    String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
                    // Configurar la fuente del video
                    videoView.setVideoURI(Uri.parse(videoPath));
                    // Iniciar la reproducción del video
                    videoView.start();
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


}