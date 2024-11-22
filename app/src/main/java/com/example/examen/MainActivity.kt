package com.example.examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.examen.ui.theme.ExamenTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenTheme {
                var alumno = "Carlos"
                Scaffold(
                    topBar  = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "Conversacion con $alumno",
                                    color = Color.LightGray
                                )
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(alumno7)
                        )
                    }
                ) { innerPadding ->
                    Conversacion(SampleData.conversationSample, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

val alumno0= Color(0xFFEA80FC)
val alumno1= Color(0xFF8C9EFF)
val alumno2= Color(0xFFFFD180)
val alumno3= Color(0xFFFF9E80)
val alumno4= Color(0xFFA7FFEB)
val alumno5= Color(0xFFDD2C00)
val alumno6= Color(0xFF64DD17)
val alumno7= Color(0xFF00BFA5)
val alumno8= Color(0xFFAA00FF)
val alumno9= Color(0xFFFFD600)
var colorExpandidoGlobal by mutableStateOf(Color.Magenta)

data class Mensajes(val autor: String, val mensaje: String)

// Creo cada mensaje individual
@Composable
fun MensajesInidividuales(msg: Mensajes){
    Row (modifier = Modifier.padding(all = 8.dp)){
        // Mostramos la foto del profesor ya que siempre es él
        // quien manda todos los mensajes
        Image(
            painter = painterResource(R.drawable.profesor),
            contentDescription = "Profesor",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, Color.Blue, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // creo dos variables, una para saber si esta expandido
        // y otra para medio animar al darle click
        var estaExpandido by remember {
            mutableStateOf(false)
        }
        val colorFondo by animateColorAsState(
            if (estaExpandido) colorExpandidoGlobal else Color.White,
        )

        // Creo la columna y lo pongo clicable para que al darle se cambie
        Column (modifier = Modifier.clickable { estaExpandido = !estaExpandido }) {
            Text(
                text = msg.autor,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.width(4.dp))

            Surface (
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = colorFondo,
                modifier = Modifier
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.mensaje,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (estaExpandido) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// Creo la tarjeta de la parte de arriba
// En la que se ve la foto y el nombre
@Composable
fun CartaAlumno(nombre: String, modifier: Modifier = Modifier){
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .border(1.dp, Color.Gray)
    ) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.avatar7),
                contentDescription = "Foto de Carlos",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Color.Black, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column (
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Alumno: $nombre")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Soy un alumno")
            }
        }
    }
}

@Composable
fun Boton(colorActual: Color, nuevoColor: (Color) -> Unit, color: Color) {
    val listaColores = listOf(Color.Green, Color.Blue, Color.Red, Color.Cyan)
    Button(
        onClick = {
            colorExpandidoGlobal = listaColores.random()
            nuevoColor(colorExpandidoGlobal)
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorActual)
    ) {
        Text("Elegir un nuevo color")
    }
}

// Con la lista de los mensajes los mostramos todo
@Composable
fun Conversacion(lista: List<Mensajes>, modifier: Modifier = Modifier) {
    var colorBoton by remember { mutableStateOf(Color.Magenta) }
    Column(modifier = modifier) {
        CartaAlumno(nombre = "Carlos")
        Spacer(modifier = Modifier.height(8.dp))
        Boton(colorActual = colorBoton, nuevoColor = { newColor -> colorBoton = newColor }, color = colorBoton)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(lista) { mensaje ->
                MensajesInidividuales(msg = mensaje)
            }
        }
    }
}