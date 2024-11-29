package up.ddm.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import up.ddm.Atributos
import up.ddm.data.AtributosDB
import up.ddm.repositories.AtributosRepository

class AtributosViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AtributosRepository

    init {
        val atributosDAO = AtributosDB.getDatabase(application).atributosDAO()
        repository = AtributosRepository(atributosDAO)
    }

    fun addAtributos(atributos: Atributos) = viewModelScope.launch {
        repository.insert(atributos)
    }

    fun getAtributosById(id: Int, onResult: (Atributos?) -> Unit) = viewModelScope.launch {
        val atributos = repository.getAtributosById(id)
        onResult(atributos)
    }

    fun deleteAtributosById(id: Int) = viewModelScope.launch {
        repository.deleteAtributosById(id)
    }
}
