package com.example.neves.oliveira.lucas.primeiroApp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.neves.oliveira.lucas.primeiroApp.ui.theme.PrimeiroAppTheme

/**
 * Essa é a classe principal da aplicação. Ela é responsável por criar a interface de usuário.
 * Contem um campo de texto e um botão para enviar a mensagem.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrimeiroAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        ContentScreen {
                            // Cria intent com a classe TextActivity
                            val intent = Intent(this@MainActivity, TextActivity::class.java)
                            // Coloca a mensagem no intent pela chave fornecida pela mesma classe
                            intent.putExtra(TextActivity.MESSAGE, it)
                            // Inicia a activity
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentScreen(onTap: (String) -> Unit){
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    var message by rememberSaveable("message") { mutableStateOf("") }

    if(isPortrait){
        Column {
            Content(message, { message = it }, onTap)
        }
    }else{
        Row  {
            Content(message, { message = it }, onTap)
        }
    }
}

@Composable
private fun Content(
    message: String,
    onValueChange: (String) -> Unit,
    onTap: (String) -> Unit
){
    OutlinedTextField(
        value = message,
        onValueChange = onValueChange,
        label = { Text("Message") },
        modifier = Modifier.fillMaxHeight(0.4f)
    )
    Button(
        onClick = { onTap(message) },
        enabled = message.isNotBlank()
    ) {
        Text("Enviar")
    }
}