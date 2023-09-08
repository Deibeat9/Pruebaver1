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
    //Prueba1
    //Segundo Cambio
    //TERCER COMENTARIO JJ //
    //joseph//
    private VideoView videoView;
    private Button playButton;
    private OkHttpClient client;
    private WebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);

        playButton = findViewById(R.id.PlayButton);

        client = new OkHttpClient();
        // Establecer la conexión WebSocket
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enviar un mensaje al servidor para iniciar la reproducción de video
                //  webSocket.send("start_video");
                Toast.makeText(MainActivity.this, "SI SIRVE", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura la conexión WebSocket
        Request request = new Request.Builder().url("ws://localhost:8765").build();
        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                // La conexión WebSocket se ha establecido
            }
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                if (text.equals("start_video")) {
                    // Reproducir el video cuando se recibe el mensaje "start_video"
                    playVideo();
                }
            }
        };
        webSocket = client.newWebSocket(request, webSocketListener);
    }

    private void playVideo() {
        // Cambia el URI del video para que apunte a tu recurso de video local o una URL remota
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri =   Uri.parse(videoPath);
        videoView.setVideoPath(videoPath);
        videoView.start();
    }
}