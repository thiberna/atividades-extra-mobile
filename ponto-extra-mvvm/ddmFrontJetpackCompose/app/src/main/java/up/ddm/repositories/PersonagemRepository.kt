package up.ddm.repositories

import up.ddm.Personagem
import up.ddm.data.PersonagemDAO

class PersonagemRepository(private val personagemDAO: PersonagemDAO) {

    suspend fun insert(personagem: Personagem) {
        personagemDAO.insert(personagem)
    }

    suspend fun delete(personagem: Personagem) {
        personagemDAO.delete(personagem)
    }

    suspend fun getAllPersonagens(): List<Personagem> {
        return personagemDAO.getAllPersonagens()
    }
}
