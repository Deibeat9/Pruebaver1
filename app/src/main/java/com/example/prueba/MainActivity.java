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

 private WebSocketClient webSocketClient;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button connectButton = findViewById(R.id.connectButton);

            connectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connectToWebSocket();
                }
            });
        }

        private void connectToWebSocket() {
            try {
                URI uri = new URI("ws://localhost:8765");
                webSocketClient = new WebSocketClient(uri) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        // La conexión se ha establecido con éxito
                    }

                    @Override
                    public void onMessage(String s) {
                        // Se recibió un mensaje del servidor
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        // La conexión se cerró
                    }

                    @Override
                    public void onError(Exception e) {
                        // Ocurrió un error
                    }
                };

                webSocketClient.connect();
                sendMessage("start_video"); // Envía el mensaje al presionar el botón
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        private void sendMessage(String message) {
            if (webSocketClient != null && webSocketClient.isOpen()) {
                webSocketClient.send(message);
            }
        }