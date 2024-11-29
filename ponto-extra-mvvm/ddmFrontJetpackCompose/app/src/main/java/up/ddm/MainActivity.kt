package up.ddm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import up.ddm.data.AtributosDAO
import up.ddm.data.AtributosDB
import up.ddm.viewModels.AtributosViewModel
import up.ddm.viewModels.PersonagemViewModel

class MainActivity : ComponentActivity() {
    private var atributos = Atributos()

    private lateinit var atributosDAO: AtributosDAO
    private val personagemViewModel: PersonagemViewModel by viewModels()
    private val atributosViewModel: AtributosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AtributosDB.getDatabase(this)
        atributosDAO = db.atributosDAO()

        setContent {
            AtributosScreen(
                atributos = Atributos(),
                personagem = Personagem(),
                saveClick = { saveAtributos() },
                deleteClick = { deletePersonagem() },
                salvarClick = { salvarPersonagem() }
            )
        }
    }

    private fun saveAtributos() {
        lifecycleScope.launch {
            atributosViewModel.addAtributos(atributos)
        }
    }

    private fun deletePersonagem() {
        personagemViewModel.deletePersonagem(Personagem())
        lifecycleScope.launch {
            atributosViewModel.deleteAtributosById(Personagem().atributosId)
        }
    }

    private fun salvarPersonagem() {
        personagemViewModel.addPersonagem(Personagem())
        saveAtributos()
    }
}

@Composable
fun AtributosScreen(
    atributos: Atributos,
    personagem: Personagem,
    saveClick: () -> Unit,
    deleteClick: () -> Unit,
    salvarClick: () -> Unit
) {
    var pontosRestantes by remember { mutableStateOf(atributos.getPontosDisponiveis()) }
    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Criação de Personagem", fontSize = 18.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Pontos restantes:", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "$pontosRestantes", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Atributo", modifier = Modifier.weight(1f))
            Text(
                text = "Valor",
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Text(
                text = "Raça",
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Text(
                text = "Mod",
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AtributoInputRow(
            "Força",
            atributos,
            { pontosRestantes = atributos.getPontosDisponiveis() },
            { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow(
            "Destreza",
            atributos,
            { pontosRestantes = atributos.getPontosDisponiveis() },
            { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow(
            "Constituição",
            atributos,
            { pontosRestantes = atributos.getPontosDisponiveis() },
            { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow(
            "Sabedoria",
            atributos,
            { pontosRestantes = atributos.getPontosDisponiveis() },
            { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow(
            "Inteligência",
            atributos,
            { pontosRestantes = atributos.getPontosDisponiveis() },
            { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })
        Spacer(modifier = Modifier.height(8.dp))
        AtributoInputRow(
            "Carisma",
            atributos,
            { pontosRestantes = atributos.getPontosDisponiveis() },
            { showMessage(it, { snackbarMessage = it; snackbarVisible = true }) })

        if (snackbarVisible) {
            Snackbar(
                action = {
                    Button(onClick = { snackbarVisible = false }) {
                        Text("Fechar")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(snackbarMessage)
            }
        }

        Button(onClick = saveClick, Modifier.align(Alignment.CenterHorizontally)) {
            Text("Salvar atributos")
        }

        Button(onClick = salvarClick, Modifier.align(Alignment.CenterHorizontally)) {
            Text("Salvar Personagem")
        }

        Button(onClick = deleteClick, Modifier.align(Alignment.CenterHorizontally)) {
            Text("Deletar Personagem")
        }
    }
}

@Composable
fun AtributoInputRow(
    label: String,
    atributos: Atributos,
    updatePontos: () -> Unit,
    onError: (String) -> Unit
) {
    var textValue by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))

        OutlinedTextField(
            value = textValue,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    textValue = newValue
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .weight(1f)
                .width(50.dp)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused && textValue.isNotEmpty()) {
                        try {
                            val valor = textValue.toInt()
                            atributos.setAtributo(valor)
                            updatePontos()
                        } catch (e: IllegalArgumentException) {
                            onError(e.message ?: "Erro desconhecido")
                            textValue = ""
                        } catch (e: NumberFormatException) {
                            onError("Por favor, insira um número válido.")
                            textValue = ""
                        }
                    }
                }
        )

        Text(
            text = "0",
            modifier = Modifier.weight(1f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Text(
            text = "0",
            modifier = Modifier.weight(1f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun FilledButtonPersistencia(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Persistência")
    }
}

fun showMessage(message: String, callback: (String) -> Unit) {
    callback(message)
}
