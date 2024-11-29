package up.ddm.repositories

import up.ddm.Atributos
import up.ddm.data.AtributosDAO

class AtributosRepository(private val atributosDAO: AtributosDAO) {

    suspend fun insert(atributos: Atributos) {
        atributosDAO.insert(atributos)
    }

    suspend fun getAtributosById(id: Int): Atributos {
        return atributosDAO.getAtributosById(id)
    }

    suspend fun deleteAtributosById(id: Int) {
        atributosDAO.deleteAtributosById(id)
    }
}
