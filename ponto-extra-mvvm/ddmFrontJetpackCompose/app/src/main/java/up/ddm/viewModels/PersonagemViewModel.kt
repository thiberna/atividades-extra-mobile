package up.ddm.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import up.ddm.Personagem
import up.ddm.data.AtributosDB
import up.ddm.repositories.PersonagemRepository

class PersonagemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PersonagemRepository

    init {
        val personagemDAO = AtributosDB.getDatabase(application).personagemDAO()
        repository = PersonagemRepository(personagemDAO)
    }

    fun addPersonagem(personagem: Personagem) = viewModelScope.launch {
        repository.insert(personagem)
    }

    fun deletePersonagem(personagem: Personagem) = viewModelScope.launch {
        repository.delete(personagem)
    }

    fun getAllPersonagens() = viewModelScope.launch {
        repository.getAllPersonagens()
    }
}
